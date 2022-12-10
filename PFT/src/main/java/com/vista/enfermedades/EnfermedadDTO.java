package com.vista.enfermedades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnfermedadDTO {

	private Long idEnfermedad;
	
	private String nombre;
	
	private String descripcion;
	
	private String tratamiento;
	
	private String variante;
	
	private GravedadDTO gravedad;
}
