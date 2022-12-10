package com.vista.terneras;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VariacionPesoDTO {
	
	private Float peso;
	private Float pesoAnterior;
	private String fecPeso;
	private Float ganancia;
}
