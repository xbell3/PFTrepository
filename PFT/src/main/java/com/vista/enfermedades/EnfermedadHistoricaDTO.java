package com.vista.enfermedades;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnfermedadHistoricaDTO {
		private int idTernera;
		private int SNIG;
		private String nombreEnfermedad;
		private String variante;
		private String severidad;
		@JsonFormat(pattern="dd/MM/yyyy")
		private Date fecDeteccion;
}
