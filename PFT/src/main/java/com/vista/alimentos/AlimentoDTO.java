package com.vista.alimentos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AlimentoDTO {

	private Long idAlimento;
	private String nombre;
	private String marca;
	private UnidadDTO unidad;
	private float stock;
	private float costoUnidad;
	
}
