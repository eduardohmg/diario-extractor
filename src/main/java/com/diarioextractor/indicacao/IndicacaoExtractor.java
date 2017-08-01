package com.diarioextractor.indicacao;

import java.util.List;

import com.diarioextractor.domain.Indicacao;

public interface IndicacaoExtractor {
	List<Indicacao> extractFromText(String rawText);
}
