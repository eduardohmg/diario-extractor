package com.diarioextractor.statics;

import static java.util.regex.Pattern.compile;

import java.util.regex.Pattern;

public class Patterns {

	private static final String REG_HEADERS = "(\\nDIÁRIO DA CÂMARA DE VEREADORES DE JOINVILLE)";
	public static final Pattern HEADERS_ADENDO = compile(REG_HEADERS);

	private static final String REG_DOUBLE_BREAK_LINE = "(\\n\\n)";
	public static final Pattern DOUBLE_BREAK_LINE = compile(REG_DOUBLE_BREAK_LINE);

	private static final String REG_UNKNOWN_SPACES = "((\\n)(\\n|\\s)\\n)";
	public static final Pattern UNKNOWN_SPACES = compile(REG_UNKNOWN_SPACES);

	private static final String REG_MARILIZA = "(Marilza Ferreira)";
	public static final Pattern MARILIZA = compile(REG_MARILIZA);

	private static final String REG_RUA = "(Rua)";
	public static final Pattern RUA = compile(REG_RUA);

	private static final String REG_BAIRRO = "(Bairro)";
	public static final Pattern BAIRRO = compile(REG_BAIRRO);

	private static final String REG_NUMBER = "(-)";
	public static final Pattern NUMBER = compile(REG_NUMBER);

	private static final String REG_MATERIA_ORDEM = "(MATÉRIA DA ORDEM DO DIA)";
	public static final Pattern MATERIA_ORDEM = compile(REG_MATERIA_ORDEM);

	private static final String REG_INDICACOES = "(INDICAÇÕES)";
	public static final Pattern INDICACOES = compile(REG_INDICACOES);

	private static final String REG_MOCOES = "(MOÇÕES)";
	public static final Pattern MOCOES = Pattern.compile(REG_MOCOES);
}
