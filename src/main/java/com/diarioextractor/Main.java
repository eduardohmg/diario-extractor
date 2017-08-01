package com.diarioextractor;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.diarioextractor.domain.Indicacao;
import com.diarioextractor.indicacao.IndicacaoExtractor;
import com.diarioextractor.indicacao.IndicacaoExtractorImpl;
import com.diarioextractor.pdfextractor.PDFToText;
import com.diarioextractor.pdfextractor.PDFToTextImpl;
import com.diarioextractor.xlsxexporter.WorkbookExport;
import com.diarioextractor.xlsxexporter.WorkbookExportImpl;
import com.diarioextractor.xlsxexporter.WorkbookFileWriter;
import com.diarioextractor.xlsxexporter.WorkbookFileWriterImpl;

// FIXME Translate all generic to english
public class Main {

	public static int main(String[] args) {

		try {
			if (args.length == 0)
				throw new RuntimeException("A pdf file name is necessary");

			PDFToText pdfExtractor = new PDFToTextImpl();
			String diarioText = pdfExtractor.fromPath(args[0]);

			IndicacaoExtractor extractor = new IndicacaoExtractorImpl();
			List<Indicacao> indicacoes = extractor.extractFromText(diarioText);
			
			System.out.println("Extraction completed");
			System.out.println("Creating workbook...");
			
			WorkbookExport workbookExporter = new WorkbookExportImpl();
			Workbook workbook = workbookExporter.createWorkbook(indicacoes);

			WorkbookFileWriter workbootFileWriter = new WorkbookFileWriterImpl();
			
			System.out.println("Workbook created");
			System.out.println("Writing to file...");
			
			String xlsFileName = "default.xls";

			if (args.length > 1)
				xlsFileName = args[1];

			workbootFileWriter.write(workbook, xlsFileName);
			
			System.out.println("Process finished!");
			return indicacoes.size();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return 0;
		}
	}
}
