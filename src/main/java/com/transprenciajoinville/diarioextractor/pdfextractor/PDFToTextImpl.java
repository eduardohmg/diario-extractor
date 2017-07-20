package com.transprenciajoinville.diarioextractor.pdfextractor;

import java.io.File;
import java.io.FileInputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Component;

@Component(value = "PDFToTextImpl")
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
			pdfparser.parse(inputstream, handler, metadata, pcontext);

			resultText = handler.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return resultText;
	}

	@Override
	public String fromPath(String path) {
		File file = new File(path);
		return fromFile(file);
	}

}
