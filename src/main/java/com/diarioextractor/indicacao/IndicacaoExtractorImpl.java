package com.diarioextractor.indicacao;

import static com.diarioextractor.statics.Lists.BAIRROS;
import static com.diarioextractor.statics.Lists.VEREADORES;
import static com.diarioextractor.statics.Patterns.BAIRRO;
import static com.diarioextractor.statics.Patterns.HEADERS_ADENDO;
import static com.diarioextractor.statics.Patterns.INDICACOES;
import static com.diarioextractor.statics.Patterns.MATERIA_ORDEM;
import static com.diarioextractor.statics.Patterns.NUMBER;
import static com.diarioextractor.statics.Patterns.RUA;
import static java.lang.Math.max;
import static java.util.Arrays.asList;
import static java.util.regex.Pattern.compile;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.diarioextractor.domain.Indicacao;
import com.diarioextractor.domain.Rua;
import com.diarioextractor.domain.Vereador;

import info.debatty.java.stringsimilarity.Levenshtein;

// FIXME Organize this class
public class IndicacaoExtractorImpl implements IndicacaoExtractor {

	private static final String PAGE_HEADER = "\n\n\n\n" + "CÂMARA DE VEREADORES DE JOINVILLE\\s*" + "\n\n" + "ESTADO DE SANTA CATARINA";
	private String text;

	Levenshtein levenCompare = new Levenshtein();

	// FIXME This method is too long
	@Override
	public List<Indicacao> extractFromText(final String raw) {
		this.text = raw;

		removeAllBeforeIndicacoes();
		removeHeaders();
		removeAdendo();
		tryToRemoveAllAfterIndicacoes();

		List<String> indicacoesText = splitIndicacoes();
		indicacoesText = removeFirst(indicacoesText);
		List<Indicacao> indicacoes = new ArrayList<>();
		
		int count = 0;
		
		System.out.print("Extracted 0 indicacoes");

		for (String indicacaoText : indicacoesText) {
			Indicacao indicacao = extractIndicacao(indicacaoText);
			indicacoes.add(indicacao);
			count++;
			System.out.print("\rExtracted " + count + " indicacoes");
		}
		
		System.out.println("");

		return indicacoes;
	}

	private void tryToRemoveAllAfterIndicacoes() {
		Matcher matcherMateriaOrderm = MATERIA_ORDEM.matcher(text);

		if (matcherMateriaOrderm.find())
			text = text.substring(0, matcherMateriaOrderm.start());
	}

	private List<String> removeFirst(List<String> indicacoesText) {
		List<String> withoutFirst = new ArrayList<>(indicacoesText);
		withoutFirst.remove(0);
		return withoutFirst;
	}

	private void removeHeaders() {
		text = text.replaceAll(PAGE_HEADER, "");
	}

	private void removeAllBeforeIndicacoes() {
		Matcher matcherIndicacoes = INDICACOES.matcher(text);
		if (matcherIndicacoes.find())
			text = text.substring(matcherIndicacoes.start() + 10);
	}

	private void removeAdendo() {
		Matcher matcher2 = HEADERS_ADENDO.matcher(text);
		if (matcher2.find())
			text = text.substring(0, matcher2.start());
	}

	private List<String> splitIndicacoes() {
		String[] arrayResult = text.split("\\nNº");
		return new ArrayList<>(asList(arrayResult));
	}

	// FIXME This method is too long
	private Indicacao extractIndicacao(String raw) {

		Indicacao indicacao = new Indicacao();

		int fim = 0;

		String regexVereadores = "-.*.-";
		Pattern patternVereadores = compile(regexVereadores);
		Matcher matcherVereadores = patternVereadores.matcher(raw);
		String secaoVereadores = "";

		while (matcherVereadores.find())
			fim = matcherVereadores.end();

		secaoVereadores = raw.substring(0, fim);

		String[] vereadores = secaoVereadores.split(", ");

		for (String vereador : vereadores) {
			for (String nome : VEREADORES)
				if (vereador.toUpperCase().contains(nome.toUpperCase()))
					indicacao.addVereador(Vereador.builder().name(nome).build()); // FIXME Get active vereador from the db by name
		}

		String numero[] = extractNumber(raw).split("/");
		indicacao.setNumber(numero[0]);
		indicacao.setYear(numero[1]);

		raw = removeSpaceAndBreakLines(raw);

		String descricao = extractDescricao(raw);
		indicacao.setDescricao(descricao);

		String rua = extractRua(raw);
		indicacao.setRuas(asList(Rua.builder().name(rua).build())); // FIXME Verify if already exists this street e create method add

		String bairro = extractBairro(raw);
		indicacao.setBairro(bairro);

		return indicacao;
	}

	private String removeSpaceAndBreakLines(String text) {
		return text.replaceAll("\\n", "").replaceAll("^\\s*", "");
	}

	private String extractNumber(String text) {
		String number = "";

		Matcher matcherNumero = NUMBER.matcher(text);

		if (matcherNumero.find())
			number = text.substring(0, matcherNumero.start());

		return number.trim();
	}

	private String extractBairro(String raw) {
		Matcher matcherBairro = BAIRRO.matcher(raw);

		if (matcherBairro.find()) {
			String bairroRaw = raw.substring(matcherBairro.start());
			for (String bairro : BAIRROS)
				if (levenComparePercent(cleanBairro(bairroRaw), cleanBairro(bairro)) < 0.2)
					return bairro;
		}

		return "";
	}

	private double levenComparePercent(String a, String b) {
		double distance = levenCompare.distance(a, b);
		double ratio = distance / (max(a.length(), b.length()));

		return ratio;
	}

	private String extractDescricao(String raw) {
		String descricao = "";
		String[] indSplit = raw.split(" - ");

		boolean canAdd = false;

		for (int i = 3; i < indSplit.length; i++) {
			boolean achou = false;
			// FIXME Extract FOR loop
			// FIXME Reduce cyclomatic complexity
			for (String nome : VEREADORES) {
				if (indSplit[i].toUpperCase().contains(nome.toUpperCase())) {
					achou = true;
					break;
				}
			}
			if (achou && !canAdd || indSplit[i].length() <= 4)
				continue;
			canAdd = true;
			descricao += indSplit[i];
		}

		return descricao.trim();
	}

	// FIXME Sometimes it has more than one street
	private String extractRua(final String raw) {
		String rua = "";

		Matcher matcherRua = RUA.matcher(raw);

		if (matcherRua.find()) {
			rua = raw.substring(matcherRua.start());

			int firstComma = raw.substring(matcherRua.start()).indexOf(",");

			if (firstComma > 0)
				rua = rua.substring(0, raw.substring(matcherRua.start()).indexOf(",")).substring(3);
		}

		return rua.trim();
	}

	private String withoutAccentuation(final String text) {
		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""); // This string can be extracted and have a good name
	}

	// FIXME Give a better name to this method
	private String cleanBairro(final String bairro) {
		return withoutAccentuation(bairro).toUpperCase().replace("BAIRRO", "").replace(".", "").trim();
	}
}
