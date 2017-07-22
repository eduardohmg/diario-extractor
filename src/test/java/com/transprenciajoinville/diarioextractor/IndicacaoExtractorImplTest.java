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
		Indicacao indicacao = indicacoes.get(6);

		assertThat(indicacoes.size(), equalTo(170));

		assertThat(indicacao.getNumber(), equalTo("8422"));
		assertThat(indicacao.getYear(), equalTo("2017"));
		assertThat(indicacao.getVereadores(), equalTo(asList(Vereador.builder().name("Pelé").build())));
		assertThat(indicacao.getDescricao(), equalTo("Substituição de tampa da boca de lobo na Rua Antônio do Livramento, em frente ao Nº 284, no Bairro Espinheiros."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Antônio do Livramento").build())));
		assertThat(indicacao.getBairro(), equalTo("Espinheiros"));
	}

	@Test
	public void bairroWithAccentuation() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(21);

		assertThat(indicacoes.size(), equalTo(170));

		assertThat(indicacao.getNumber(), equalTo("8552"));
		assertThat(indicacao.getYear(), equalTo("2017"));
		assertThat(indicacao.getVereadores(), equalTo(asList(Vereador.builder().name("Wilson Paraiba").build())));
		assertThat(indicacao.getDescricao(), equalTo("Operação tapa-buraco na Rua dos Tucanos, nas proximidades do nº 392 e 408, no Bairro Jardim Iririu."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("dos Tucanos").build())));
		assertThat(indicacao.getBairro(), equalTo("Jardim Iririú"));
	}

	@Test
	public void verifyIfDeletedHeaderCorrectly() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(15);

		assertThat(indicacoes.size(), equalTo(170));

		assertThat(indicacao.getNumber(), equalTo("8532"));
		assertThat(indicacao.getYear(), equalTo("2017"));
		assertThat(indicacao.getVereadores(), equalTo(asList(Vereador.builder().name("Fernando Krelling").build())));
		assertThat(indicacao.getDescricao(), equalTo("Patrolamento da Rua Faustino Soares, em toda sua extensão, no Bairro Escolinha."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Faustino Soares").build())));
		assertThat(indicacao.getBairro(), equalTo("Escolinha"));
	}

	@Test
	public void verifyIfDeletedHeaderCorrectly2() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário2");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(8);

		assertThat(indicacoes.size(), equalTo(171));

		assertThat(indicacao.getNumber(), equalTo("8867"));
		assertThat(indicacao.getYear(), equalTo("2017"));
		assertThat(indicacao.getVereadores(), equalTo(asList(Vereador.builder().name("Pelé").build())));
		assertThat(indicacao.getDescricao(), equalTo("Conserto de buraco no asfalto da Rua Prefeito Baltazar Buschle, em frenteao n° 630, no Bairro Comasa."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Prefeito Baltazar Buschle").build())));
		assertThat(indicacao.getBairro(), equalTo("Comasa"));
	}

	@Test
	public void verifyLast() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário2");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(170);

		assertThat(indicacoes.size(), equalTo(171));

		assertThat(indicacao.getNumber(), equalTo("9262"));
		assertThat(indicacao.getYear(), equalTo("2017"));
		assertThat(indicacao.getVereadores(), equalTo(asList(Vereador.builder().name("Fábio Dalonso").build())));
		assertThat(indicacao.getDescricao(), equalTo("Pintura de sinalização horizontal, em toda a extensão da RuaAgostinho José Cognaco,no Bairro Costa e silva."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Agostinho José Cognaco").build())));
		assertThat(indicacao.getBairro(), equalTo("Costa e Silva"));
	}

	@Test
	public void verifyLast2() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(169);

		assertThat(indicacoes.size(), equalTo(170));

		assertThat(indicacao.getNumber(), equalTo("8944"));
		assertThat(indicacao.getYear(), equalTo("2017"));
		assertThat(indicacao.getVereadores(), equalTo(asList(Vereador.builder().name("Maurício Peixer").build())));
		assertThat(indicacao.getDescricao(), equalTo("Roçada e recolhimento de entulhos na Rua Afonso Pena, próximo ao nº 235, no Bairro Bucarein."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Afonso Pena").build())));
		assertThat(indicacao.getBairro(), equalTo("Bucarein"));
	}

	@Test
	public void bairroWithoutSpaces() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário2");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(22);

		assertThat(indicacoes.size(), equalTo(171));

		assertThat(indicacao.getNumber(), equalTo("8953"));
		assertThat(indicacao.getYear(), equalTo("2017"));
		assertThat(indicacao.getVereadores(), equalTo(asList(Vereador.builder().name("Tânia Larson").build())));
		assertThat(indicacao.getDescricao(), equalTo("Limpeza das bocas de lobo da Rua Heriberto Petry, no Bairro JoãoCosta."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Heriberto Petry").build())));
		assertThat(indicacao.getBairro(), equalTo("João Costa"));
	}
	
	@Test
	public void bairroWrongWritten() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/test/resources/pdfs/Diário2");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		Indicacao indicacao = indicacoes.get(68);

		assertThat(indicacoes.size(), equalTo(171));

		assertThat(indicacao.getNumber(), equalTo("9004"));
		assertThat(indicacao.getYear(), equalTo("2017"));
		assertThat(indicacao.getVereadores(), equalTo(asList(Vereador.builder().name("Natanael Jordão").build())));
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
		assertThat(indicacao.getVereadores(), equalTo(asList(Vereador.builder().name("Lioilson Corrêa").build())));
		assertThat(indicacao.getDescricao(), equalTo("Solicitar limpeza de Rio entre as ruas Bernardo Rech, ElisabethRech a Juvenal Macedo, próximo aos prédios da MRV no bairro Paranaguamirim."));
		assertThat(indicacao.getRuas(), equalTo(asList(Rua.builder().name("Bernardo Rech").build(), Rua.builder().name("ElisabethRech").build(), Rua.builder().name("Juvenal Macedo").build())));
		assertThat(indicacao.getBairro(), equalTo("Paranaguamirim"));
	}
}
