package com.vista.medicamentos;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vista.terneras.TerneraDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroMedicacionDTO {
	
	private Long idRegistroMedicacion;
	private TerneraDTO terneraDTO;
	private MedicamentoDTO medicamentoDTO;
	
	@NotNull
	@JsonFormat(pattern="dd/MM/yyyy@HH:mm")
	private Date fecMedicacion;
	
	@NotNull(message = "No puede haber campos obligatorios vacios")
	@Min(value=1, message = "La minima dosis administrada puede ser: 1")
	@Max(value=100, message = "La dosis maxima es: 100")
	private Integer dosisAdministrada;
}
