package com.vista.alimentos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UnidadDTO {
	
	private Integer idUnidad;
	private String nombre;
	private String descripcion;
	
}
