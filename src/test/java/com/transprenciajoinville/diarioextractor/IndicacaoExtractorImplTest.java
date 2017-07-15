package com.transprenciajoinville.diarioextractor;

import static java.lang.System.getProperty;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.transprenciajoinville.diarioextractor.indicacao.Indicacao;
import com.transprenciajoinville.diarioextractor.indicacao.IndicacaoExtractor;
import com.transprenciajoinville.diarioextractor.pdfextractor.PDFToText;

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
	public void sample() {
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/main/resources/pdfs/Diário");

		List<Indicacao> indicacoes = indicacaoExtractor.extractFromText(diarioText);
		
		Indicacao completeIndicacao = Indicacao.builder().numero("8422/2017") //
				.vereadores(asList("Pelé")) //
				.descricao("Substituição de tampa da boca de lobo na Rua Antônio do Livramento, em frente ao Nº 284, no Bairro Espinheiros.") //
				.rua("Antônio do Livramento") //
				.bairro("Espinheiros") //
				.build();

		assertThat(indicacoes.size(), equalTo(170));
		
		assertThat(indicacoes.get(6).equals(completeIndicacao), equalTo(true));
	}
}
