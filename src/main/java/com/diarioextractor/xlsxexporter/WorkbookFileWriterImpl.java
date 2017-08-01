package com.diarioextractor.xlsxexporter;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Workbook;

// FIXME Write unit tests
public class WorkbookFileWriterImpl implements WorkbookFileWriter {

	@Override
	public boolean write(Workbook workbook, String path) {
		try {
			FileOutputStream out = new FileOutputStream(new File(path));
			workbook.write(out);
			out.close();
			workbook.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
