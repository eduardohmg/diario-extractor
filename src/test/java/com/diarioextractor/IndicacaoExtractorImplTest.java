package com.diarioextractor;

import static java.lang.System.getProperty;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.diarioextractor.domain.Indicacao;
import com.diarioextractor.domain.Rua;
import com.diarioextractor.indicacao.IndicacaoExtractor;
import com.diarioextractor.indicacao.IndicacaoExtractorImpl;
import com.diarioextractor.pdfextractor.PDFToText;
import com.diarioextractor.pdfextractor.PDFToTextImpl;

// FIXME Make tests for more cases
public class IndicacaoExtractorImplTest {

	private PDFToText pdfExtractor;
	private IndicacaoExtractor indicacaoExtractor;

	@Before
	public void init() {
		pdfExtractor = new PDFToTextImpl();
		indicacaoExtractor = new IndicacaoExtractorImpl();
	}

	@Test
	public void sampleOne() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(0);

		assertThat(indicacoes.size(), equalTo(53));

		assertThat(indicacao.getNumber(), equalTo("16404"));
		assertThat(indicacao.getYear(), equalTo("2021"));
		assertThat(indicacao.getVereador().toUpperCase(), equalTo("Cassiano Ucker".toUpperCase()));
		assertThat(indicacao.getDescricao(), equalTo("Substituição de uma placa de velocidade na Rua Paranaense no Bairro Comasa, placa foi alterada (30 para 80 km/h), pichada colocando em risco os pedestres e principalmente as crianças que nesta rua fica brincando, por ser uma rua de pouco movimento."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Paranaense").build())));
		assertThat(indicacao.getBairro(), equalTo("Comasa"));
	}

	@Test
	public void bairroWithAccentuation() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(39);

		assertThat(indicacoes.size(), equalTo(53));

		assertThat(indicacao.getNumber(), equalTo("16443"));
		assertThat(indicacao.getYear(), equalTo("2021"));
		assertThat(indicacao.getVereador().toUpperCase(), equalTo("Kiko do Restaurante".toUpperCase()));
		assertThat(indicacao.getDescricao(), equalTo("Ensaibramento e patrolamento na parte não pavimentada da Rua Ernesto Romanus, no Bairro América."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Ernesto Romanus").build())));
		assertThat(indicacao.getBairro(), equalTo("América"));
	}

	@Test
	public void verifyIfDeletedHeaderCorrectly() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(52);

		assertThat(indicacoes.size(), equalTo(53));

		assertThat(indicacao.getNumber(), equalTo("16456"));
		assertThat(indicacao.getYear(), equalTo("2021"));
		assertThat(indicacao.getVereador(), equalTo("Nado"));
		assertThat(indicacao.getDescricao(), equalTo("Troca de meio-fio na rua Antônio Geraldo Pereira, nº 68, bairro Costa e Silva."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Antônio Geraldo Pereira").build())));
		assertThat(indicacao.getBairro(), equalTo("Costa e Silva"));
	}

	@Test
	public void verifyIfDeletedHeaderCorrectly2() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário2");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(8);

		assertThat(indicacoes.size(), equalTo(70));

		assertThat(indicacao.getNumber(), equalTo("16465"));
		assertThat(indicacao.getYear(), equalTo("2021"));
		assertThat(indicacao.getVereador(), equalTo("Nado"));
		assertThat(indicacao.getDescricao(), equalTo("Ensaibramento e patrolamento da Rua Canoas, em toda a sua extensão, no Bairro Jardim Iririú."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Canoas").build())));
		assertThat(indicacao.getBairro(), equalTo("Jardim Iririú"));
	}

	@Test
	public void verifyLast() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário2");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(69);

		assertThat(indicacoes.size(), equalTo(70));

		assertThat(indicacao.getNumber(), equalTo("16526"));
		assertThat(indicacao.getYear(), equalTo("2021"));
		assertThat(indicacao.getVereador(), equalTo("Nado"));
		assertThat(indicacao.getDescricao(), equalTo("Operação tapa-buraco na Rua 1º de maio, em toda sua extensão, pois existem vários buracos, no Bairro Boa vista.")); //FIXME resolver problema de não ter espaço ao remover \n
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("1º de maio").build())));
		assertThat(indicacao.getBairro(), equalTo("Boa Vista"));
	}

	@Test
	public void verifyLast2() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(52);

		assertThat(indicacoes.size(), equalTo(53));

		assertThat(indicacao.getNumber(), equalTo("16456"));
		assertThat(indicacao.getYear(), equalTo("2021"));
		assertThat(indicacao.getVereador(), equalTo("Nado"));
		assertThat(indicacao.getDescricao(), equalTo("Troca de meio-fio na rua Antônio Geraldo Pereira, nº 68, bairro Costa e Silva."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Antônio Geraldo Pereira").build())));
		assertThat(indicacao.getBairro(), equalTo("Costa e Silva"));
	}

	@Test
	public void bairroWithoutSpaces() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário2");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(55);

		assertThat(indicacoes.size(), equalTo(70));

		assertThat(indicacao.getNumber(), equalTo("16512"));
		assertThat(indicacao.getYear(), equalTo("2021"));
		assertThat(indicacao.getVereador(), equalTo("Kiko do Restaurante"));
		assertThat(indicacao.getDescricao(), equalTo("Pavimentação asfáltica na Rua Filadélfia, no Bairro Paranaguamirim."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Filadélfia").build())));
		assertThat(indicacao.getBairro(), equalTo("Paranaguamirim"));
	}

	@Test
	public void bairroWrongWritten() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário2");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(68);

		assertThat(indicacoes.size(), equalTo(171));

		assertThat(indicacao.getNumber(), equalTo("9004"));
		assertThat(indicacao.getYear(), equalTo("2017"));
		assertThat(indicacao.getVereador(), equalTo("Natanael Jordão"));
		assertThat(indicacao.getDescricao(), equalTo("Ensaibramento e patrolamento da Rua Nara Leão, em toda asua extensão, no Bairro Ulisses Guimarães."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Nara Leão").build())));
		assertThat(indicacao.getBairro(), equalTo("Ulysses Guimarães"));
	}

	@Ignore
	@Test
	public void moreThanOneStreet() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário2");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(126);

		assertThat(indicacoes.size(), equalTo(171));

		assertThat(indicacao.getNumber(), equalTo("9089"));
		assertThat(indicacao.getYear(), equalTo("2017"));
		assertThat(indicacao.getVereador(), equalTo("Lioilson Corrêa"));
		assertThat(indicacao.getDescricao(), equalTo("Solicitar limpeza de Rio entre as ruas Bernardo Rech, ElisabethRech a Juvenal Macedo, próximo aos prédios da MRV no bairro Paranaguamirim."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Bernardo Rech").build(), Rua.builder().name("ElisabethRech").build(), Rua.builder().name("Juvenal Macedo").build())));
		assertThat(indicacao.getBairro(), equalTo("Paranaguamirim"));
	}
}
