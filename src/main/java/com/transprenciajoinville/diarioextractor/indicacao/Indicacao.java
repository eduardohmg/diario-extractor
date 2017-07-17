package com.transprenciajoinville.diarioextractor.indicacao;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Indicacao {

	private String numero;
	private List<String> vereadores;
	private String descricao;
	private String rua;
	private String bairro;
	// FIXME Add "justificativa" and find and extract it from pdf

	public void addVereador(String vereador) {
		if (this.vereadores == null)
			setVereadores(new ArrayList<>());
		this.vereadores.add(vereador);
	}

	@Override
	public String toString() {
		String indicacao = getNumero() + " - ";

		for (String vereador : getVereadores())
			indicacao += vereador + " - ";

		indicacao += getDescricao();
		indicacao += " - " + getRua();
		indicacao += " - " + getBairro();

		return indicacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((rua == null) ? 0 : rua.hashCode());
		result = prime * result + ((vereadores == null) ? 0 : vereadores.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Indicacao other = (Indicacao) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (rua == null) {
			if (other.rua != null)
				return false;
		} else if (!rua.equals(other.rua))
			return false;
		if (vereadores == null) {
			if (other.vereadores != null)
				return false;
		} else if (!vereadores.equals(other.vereadores))
			return false;
		return true;
	}
}
