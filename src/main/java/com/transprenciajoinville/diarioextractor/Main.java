package com.transprenciajoinville.diarioextractor;

import static java.lang.System.getProperty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.transprenciajoinville.diarioextractor.indicacao.IndicacaoExtractor;
import com.transprenciajoinville.diarioextractor.indicacao.IndicacaoExtractorImpl;
import com.transprenciajoinville.diarioextractor.pdfextractor.PDFToText;
import com.transprenciajoinville.diarioextractor.pdfextractor.PDFToTextImpl;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

		PDFToText pdfExtractor = new PDFToTextImpl();
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/src/main/resources/pdfs/Diário");

		IndicacaoExtractor extractor = new IndicacaoExtractorImpl();
		extractor.extractFromText(diarioText);
	}
}
