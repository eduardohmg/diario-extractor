package com.transprenciajoinville.diarioextractor.xlsxexporter;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.transprenciajoinville.diarioextractor.indicacao.Indicacao;

public interface WorkbookExport {

	Workbook createWorkbook(List<Indicacao> indicacoes);
}
