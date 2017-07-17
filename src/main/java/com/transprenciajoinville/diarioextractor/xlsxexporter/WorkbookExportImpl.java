package com.transprenciajoinville.diarioextractor.xlsxexporter;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.transprenciajoinville.diarioextractor.indicacao.Indicacao;

// FIXME Write unit tests
public class WorkbookExportImpl implements WorkbookExport {

	// FIXME This method is too long
	@Override
	public Workbook createWorkbook(List<Indicacao> indicacoes) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Indicações");

		int rownum = 0;
		int colnum = 0;
		Cell cell;
		Row row;

		row = sheet.createRow(rownum++);
		cell = row.createCell(colnum++);
		cell.setCellValue("Diário");

		cell = row.createCell(colnum++);
		cell.setCellValue("Número");

		cell = row.createCell(colnum++);
		cell.setCellValue("Data");

		cell = row.createCell(colnum++);
		cell.setCellValue("Vereador");

		cell = row.createCell(colnum++);
		cell.setCellValue("Descrição");

		cell = row.createCell(colnum++);
		cell.setCellValue("Rua");

		cell = row.createCell(colnum++);
		cell.setCellValue("Obs");

		cell = row.createCell(colnum++);
		cell.setCellValue("Bairro");

		for (Indicacao ind : indicacoes) {
			row = sheet.createRow(rownum++);
			// FIXME Stop ignoring diary number and extract, maybe from metadata
			colnum = 1; // Ignoring diary number

			cell = row.createCell(colnum++);
			cell.setCellValue(ind.getNumero());

			colnum++; // Ignoring date column

			cell = row.createCell(colnum++);
			String vereadores = "";

			for (String vereador : ind.getVereadores())
				vereadores += vereador + " - ";

			cell.setCellValue(vereadores.substring(0, vereadores.length() - 3));

			cell = row.createCell(colnum++);
			cell.setCellValue(ind.getDescricao());

			cell = row.createCell(colnum++);
			cell.setCellValue(ind.getRua());

			// FIXME Think a way to extract obs
			colnum++; // Ignoring obs column

			cell = row.createCell(colnum++);
			cell.setCellValue(ind.getBairro());
		}

		for (int i = 0; i < colnum; i++)
			sheet.autoSizeColumn(i);

		return workbook;
	}
}
