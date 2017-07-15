package com.transprenciajoinville.diarioextractor.indicacao;

import java.util.List;

public interface IndicacaoExtractor {
	List<Indicacao> extractFromText(String rawText);
}
