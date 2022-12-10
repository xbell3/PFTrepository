package com.modelo.logica.customException;

public class TerneraException extends CustomException{

	public static final String SNIG_EXISTE = "Ya existe una ternera registrada con dicho snig";
	public static final String IDLOCAL_EXISTE = "Ya existe una ternera registrada con dicho id local";
	
	public static final String SNIG_INEXISTENTE = "No existe una ternera con dicho snig";
	public static final String SNIG_ELIMINADO = "La ternera se encuentra dada de baja";
	
	public TerneraException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
}
