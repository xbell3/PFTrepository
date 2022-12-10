package com.vista.enfermedades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GravedadDTO {
	
	private int idGravedad;
	
	private String gravedad;

}
