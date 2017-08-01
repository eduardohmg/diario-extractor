package com.diarioextractor.pdfextractor;

import java.io.File;
import java.io.FileInputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;

public class PDFToTextImpl implements PDFToText {

	@Override
	public String fromFile(File file) {
		String resultText = "";

		try {
			FileInputStream inputstream = new FileInputStream(file);
			BodyContentHandler handler = new BodyContentHandler(-1);
			Metadata metadata = new Metadata();
			ParseContext pcontext = new ParseContext();

			PDFParserConfig config = new PDFParserConfig();
			config.setSortByPosition(true);

			PDFParser pdfparser = new PDFParser();
			pdfparser.setPDFParserConfig(config);
			
			System.out.println("Parsing PDF to TEXT...");
			
			pdfparser.parse(inputstream, handler, metadata, pcontext);
			resultText = handler.toString();
			
			System.out.println("Parsing complete");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return resultText;
	}

	@Override
	public String fromPath(String path) {
		System.out.println("Opening file...");
		File file = new File(path);
		if(!file.exists())
			throw new RuntimeException("File not exists");
		System.out.println("File opened");
		return fromFile(file);
	}

}
