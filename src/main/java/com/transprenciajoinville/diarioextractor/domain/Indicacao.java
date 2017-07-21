package com.transprenciajoinville.diarioextractor.domain;

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

	@Getter(AccessLevel.NONE)
	private List<Vereador> vereadores;

	private String descricao;
	private List<Rua> ruas;
	private String bairro;
	// FIXME Add "justificativa" and find and extract it from pdf
	// FIXME Add "date" and extract it

	public void addVereador(Vereador vereador) {
		if (this.vereadores == null)
			setVereadores(new ArrayList<>());
		this.vereadores.add(vereador);
	}

	public List<Vereador> getVereadores() {
		if (this.vereadores == null)
			this.vereadores = new ArrayList<>();
		return this.vereadores;
	}

	@Override
	public String toString() {
		String indicacao = getNumber() + " - ";

		indicacao += vereadoresToString();
		indicacao += " - " + getDescricao();
		indicacao += " - " + ruasToString();

		indicacao += " - " + getBairro();

		return indicacao;
	}

	public String vereadoresToString() {
		String text = "";

		if (getVereadores().isEmpty())
			return "";

		for (Vereador vereador : getVereadores())
			text += vereador.getName() + " - ";

		text = text.substring(0, text.length() - 3);

		return text;
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
