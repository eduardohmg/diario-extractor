package com.diarioextractor.xlsxexporter;

import org.apache.poi.ss.usermodel.Workbook;

public interface WorkbookFileWriter {

	boolean write(Workbook workbook, String path);
}
