package com.transprenciajoinville.diarioextractor.pdfextractor;

import java.io.File;

public interface PDFToText {
	String fromFile(File file);

	String fromPath(String path);
}
