package com.diarioextractor.xlsxexporter;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.diarioextractor.domain.Indicacao;

public interface WorkbookExport {

	Workbook createWorkbook(List<Indicacao> indicacoes);
}
