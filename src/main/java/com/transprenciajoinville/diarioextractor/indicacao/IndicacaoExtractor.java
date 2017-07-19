package com.transprenciajoinville.diarioextractor.indicacao;

import java.util.List;

import com.transprenciajoinville.diarioextractor.domain.Indicacao;

public interface IndicacaoExtractor {
	List<Indicacao> extractFromText(String rawText);
}
