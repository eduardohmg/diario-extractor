package com.transprenciajoinville.diarioextractor;

import static java.lang.System.getProperty;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.transprenciajoinville.diarioextractor.domain.Indicacao;
import com.transprenciajoinville.diarioextractor.domain.Rua;
import com.transprenciajoinville.diarioextractor.domain.Vereador;
import com.transprenciajoinville.diarioextractor.indicacao.IndicacaoExtractor;
import com.transprenciajoinville.diarioextractor.pdfextractor.PDFToText;

// FIXME Make tests for more cases
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndicacaoExtractorImplTest {

	@Autowired
	@Qualifier("PDFToTextImpl")
	private PDFToText pdfExtractor;

	@Autowired
	@Qualifier("IndicacaoExtractorImpl")
	private IndicacaoExtractor indicacaoExtractor;

	@Test
	public void sampleOne() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);

		Indicacao completeIndicacao = Indicacao.builder().number("8422") //
				.year("2017") //
				.vereadores(asList(Vereador.builder().name("Pelé").build())) //
				.descricao("Substituição de tampa da boca de lobo na Rua Antônio do Livramento, em frente ao Nº 284, no Bairro Espinheiros.") //
				.ruas(asList(Rua.builder().name("Antônio do Livramento").build())) //
				.bairro("Espinheiros") //
				.build();

		assertThat(indicacoes.size(), equalTo(170));

		assertThat(indicacoes.get(6).equals(completeIndicacao), equalTo(true));
	}

	@Test
	public void bairroWithAccentuation() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);

		Indicacao completeIndicacao = Indicacao.builder().number("8552") //
				.year("2017") //
				.vereadores(asList(Vereador.builder().name("Wilson Paraiba").build())) //
				.descricao("Operação tapa-buraco na Rua dos Tucanos, nas proximidades do nº 392 e 408, no Bairro Jardim Iririu.") //
				.ruas(asList(Rua.builder().name("dos Tucanos").build())) //
				.bairro("Jardim Iririú") //
				.build();

		assertThat(indicacoes.size(), equalTo(170));

		assertThat(indicacoes.get(21).equals(completeIndicacao), equalTo(true));
	}

	@Test
	public void verifyIfDeletedHeaderCorrectly() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);

		Indicacao completeIndicacao = Indicacao.builder().number("8532") //
				.year("2017") //
				.vereadores(asList(Vereador.builder().name("Fernando Krelling").build())) //
				.descricao("Patrolamento da Rua Faustino Soares, em toda sua extensão, no Bairro Escolinha.") //
				.ruas(asList(Rua.builder().name("Faustino Soares").build())) //
				.bairro("Escolinha") //
				.build();

		assertThat(indicacoes.size(), equalTo(170));

		assertThat(indicacoes.get(15).equals(completeIndicacao), equalTo(true));
	}

	@Test
	public void verifyIfDeletedHeaderCorrectly2() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário2");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);

		Indicacao completeIndicacao = Indicacao.builder().number("8867") //
				.year("2017") //
				.vereadores(asList(Vereador.builder().name("Pelé").build())) //
				.descricao("Conserto de buraco no asfalto da Rua Prefeito Baltazar Buschle, em frenteao n° 630, no Bairro Comasa.") //
				.ruas(asList(Rua.builder().name("Prefeito Baltazar Buschle").build())) //
				.bairro("Comasa") //
				.build();

		assertThat(indicacoes.size(), equalTo(171));

		assertThat(indicacoes.get(8).equals(completeIndicacao), equalTo(true));
	}

	@Test
	public void verifyLast() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário2");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);

		Indicacao completeIndicacao = Indicacao.builder().number("9262") //
				.year("2017") //
				.vereadores(asList(Vereador.builder().name("Fábio Dalonso").build())) //
				.descricao("Pintura de sinalização horizontal, em toda a extensão da RuaAgostinho José Cognaco,no Bairro Costa e silva.") //
				.ruas(asList(Rua.builder().name("Agostinho José Cognaco").build())) //
				.bairro("Costa e Silva") //
				.build();

		assertThat(indicacoes.size(), equalTo(171));

		assertThat(indicacoes.get(170).equals(completeIndicacao), equalTo(true));
	}

	@Test
	public void verifyLast2() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);

		Indicacao completeIndicacao = Indicacao.builder().number("8944") //
				.year("2017") //
				.vereadores(asList(Vereador.builder().name("Maurício Peixer").build())) //
				.descricao("Roçada e recolhimento de entulhos na Rua Afonso Pena, próximo ao nº 235, no Bairro Bucarein.") //
				.ruas(asList(Rua.builder().name("Afonso Pena").build())) //
				.bairro("Bucarein") //
				.build();

		assertThat(indicacoes.size(), equalTo(170));

		assertThat(indicacoes.get(169).equals(completeIndicacao), equalTo(true));
	}
	
	@Ignore
	@Test
	public void bairroWithoutSpaces() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário2");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);

		Indicacao completeIndicacao = Indicacao.builder().number("8953") //
				.year("2017") //
				.vereadores(asList(Vereador.builder().name("Tânia Larson").build())) //
				.descricao("Limpeza das bocas de lobo da Rua Heriberto Petry, no Bairro JoãoCosta.") //
				.ruas(asList(Rua.builder().name("Afonso Pena").build())) //
				.bairro("João Costa") //
				.build();

		assertThat(indicacoes.size(), equalTo(171));

		assertThat(indicacoes.get(22).equals(completeIndicacao), equalTo(true));
	}
}
