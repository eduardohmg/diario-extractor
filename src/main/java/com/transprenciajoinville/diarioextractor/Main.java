package com.transprenciajoinville.diarioextractor;

import static java.lang.System.getProperty;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.transprenciajoinville.diarioextractor.indicacao.Indicacao;
import com.transprenciajoinville.diarioextractor.indicacao.IndicacaoExtractor;
import com.transprenciajoinville.diarioextractor.indicacao.IndicacaoExtractorImpl;
import com.transprenciajoinville.diarioextractor.pdfextractor.PDFToText;
import com.transprenciajoinville.diarioextractor.pdfextractor.PDFToTextImpl;
import com.transprenciajoinville.diarioextractor.xlsxexporter.WorkbookExport;
import com.transprenciajoinville.diarioextractor.xlsxexporter.WorkbookExportImpl;
import com.transprenciajoinville.diarioextractor.xlsxexporter.WorkbookFileWriter;
import com.transprenciajoinville.diarioextractor.xlsxexporter.WorkbookFileWriterImpl;

// FIXME Translate all generic to english
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

		PDFToText pdfExtractor = new PDFToTextImpl();
		String diarioText = pdfExtractor.fromPath(getProperty("user.dir") + "/Diário");

		IndicacaoExtractor extractor = new IndicacaoExtractorImpl();
		List<Indicacao> indicacoes = extractor.extractFromText(diarioText);

		WorkbookExport workbookExporter = new WorkbookExportImpl();
		Workbook workbook = workbookExporter.createWorkbook(indicacoes);

		WorkbookFileWriter workbootFileWriter = new WorkbookFileWriterImpl();
		workbootFileWriter.write(workbook, "indicacoes.xls");
	}
}
