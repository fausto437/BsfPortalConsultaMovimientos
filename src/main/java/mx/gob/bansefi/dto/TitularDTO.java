package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
public class TitularDTO{

	@Getter @Setter
	private String nombre;
	@Getter @Setter
	private String idInternoPe;
	@Getter @Setter
	private String error;
}