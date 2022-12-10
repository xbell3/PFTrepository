package com.controlador.servicios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse{

	public static final String ALTA_PERSONA_CORRECTA = "AP200";
	public static final String ALTA_ENCARGADO_CORRECTA = "AP-E200";
	public static final String ALTA_PERSONAL_CORRECTA = "AP-P200";
	
	public static final String ALTA_TERNERA_CORRECTA = "AT200";	
	
	public static final String BAJA_TERNERA_CORRECTA = "BT-200";
	
	public static final String MODIFICACION_TERNERA_CORRECTA = "AT200";
	
	public static final String ALTA_MEDICAMENTO_CORRECTA = "AM200";
	public static final String BAJA_MEDICAMENTO_CORRECTA = "BM200";
	public static final String MODIFICACION_MEDICAMENTO_CORRECTA = "MM200";
	
	public static final String REGISTRO_MEDICACION_CORRECTA = "RM200";
	
	public static final String REGISTRO_ALIMENTACION_CORRECTA = "RA200";
	
	private int statusCode;
	private String internalCode;
	private String message;
}
