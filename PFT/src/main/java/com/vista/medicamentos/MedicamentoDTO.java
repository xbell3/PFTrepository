package com.vista.medicamentos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoDTO {

	private Long idMedicamento;
	
	@NotNull(message = "No puede haber campos obligatorios vacios")
	@Length(min=1, max=255, message = "El nombre del producto debe tener 255 caracteres como maximo")
	private String producto;
	
	@NotNull(message = "No puede haber campos obligatorios vacios")
	@Min(value=1, message="El costo minimo es: 1")
	@Max(value=999999999, message = "El costo supera el maximo permitido: 999.999.999")
	private float costo;
	
	@Min(value=0, message="El stock minimo puede ser 0")
	@Max(value=999999999, message = "El stock supera el maximo permitido: 999.999.999")
	private Integer stock;
	
	private int estado;
	
	@NotNull(message = "No puede haber campos obligatorios vacios")
	private TipoMedicamentoDTO tipoMedicamento;
	
}
