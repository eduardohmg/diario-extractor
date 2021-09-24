package com.diarioextractor.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Indicacao {

	private long id;
	private String number;
	private String year;
	private String vereador;

	private String descricao;
	private List<Rua> ruas;
	private String bairro;
	// FIXME Add "justificativa" and find and extract it from pdf
	// FIXME Add "date" and extract it

	@Override
	public String toString() {
		String indicacao = getNumber() + "/";
		String patternSeparate = " - ";

		indicacao += getYear() + patternSeparate;

		indicacao += patternSeparate + getVereador();
		indicacao += patternSeparate + getDescricao();
		indicacao += patternSeparate + ruasToString();

		indicacao += patternSeparate + getBairro();

		return indicacao;
	}

	public String ruasToString() {
		String text = "";

		if (getRuas().isEmpty())
			return "";

		for (Rua rua : getRuas())
			text += rua.getName() + " - ";

		text = text.substring(0, text.length() - 3);

		return text;
	}
}
