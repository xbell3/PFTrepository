package com.vista.terneras;

import java.util.Date;

import javax.ejb.MessageDriven;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TerneraDTO {

	@NotNull
	private Long idTernera;
	
	@NotNull(message = "No puede haber campos obligatorios vac�os")
	@Min(value=1000, message = "El campo id local debe tener 4 digitos num�ricos no decimales")
	@Max(value=9999, message = "El campo id local debe tener 4 digitos num�ricos no decimales")
	private Integer idLocal;
	
	@NotNull(message = "No puede haber campos obligatorios vac�os")
	@Min(value=1000, message = "El campo snig debe tener 4 digitos num�ricos no decimales")
	@Max(value=9999, message = "El campo snig debe tener 4 digitos num�ricos no decimales")
	private Integer snig;
	
	@NotNull(message = "No puede haber campos obligatorios vac�os")
	@Min(value=1000, message = "El campo snig madre debe tener 4 digitos num�ricos no decimales")
	@Max(value=9999, message = "El campo snig madre debe tener 4 digitos num�ricos no decimales")
	private Integer snigMadre;

	@Min(value=1000, message = "El campo snig padre debe tener 4 digitos num�ricos no decimales")
	@Max(value=9999, message = "El campo snig padre debe tener 4 digitos num�ricos no decimales")
	private Integer snigPadre;
	
	@NotNull(message = "No puede haber campos obligatorios vac�os")
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date fecNac;
	
	@NotNull(message = "No puede haber campos obligatorios vac�os")
	@Digits(integer=3, fraction=2, message = "El campo peso puede tener 3 digitos enteros y 2 decimales")
	@Min(value=1, message = "El campo peso puede tener 3 digitos enteros y 2 decimales")
	@Max(value=999, message = "El campo peso puede tener 3 digitos enteros y 2 decimales")
	private float pesoNac;
	
	@NotNull(message = "No puede haber campos obligatorios vac�os")
	private RazaDTO raza;
	
	@NotNull(message = "No puede haber campos obligatorios vac�os")
	private TipoPartoDTO tipoParto;
	
	private int estado;
}