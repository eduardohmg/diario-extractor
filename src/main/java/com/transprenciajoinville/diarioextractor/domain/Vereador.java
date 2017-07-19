package com.transprenciajoinville.diarioextractor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vereador {
	
	private long id;
	private String name;
	private boolean active;
}
