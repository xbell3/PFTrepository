package com.vista.alimentos;

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

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegistroAlimentacionDTO {

	private Long idRegistroAlimentacion;
	private TerneraDTO terneraDTO;
	private AlimentoDTO alimentoDTO;
	
	@NotNull(message = "No puede haber campos obligatorios vacios")
	@JsonFormat(pattern="dd/MM/yyyy@HH:mm")
	private Date fecAlimentacion;
	
	@NotNull(message = "No puede haber campos obligatorios vacios")
	@Min(value=1, message = "La minima cantidad suministrada puede ser: 1")
	@Max(value=100, message = "La cantidad maxima es: 100")
	private float cantidad;
	
}
