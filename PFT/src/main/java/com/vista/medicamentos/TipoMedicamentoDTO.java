package com.vista.medicamentos;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoMedicamentoDTO {
	
	private Integer idTipoMedicamento;
	
	@NotNull
	private String tipo;
}
