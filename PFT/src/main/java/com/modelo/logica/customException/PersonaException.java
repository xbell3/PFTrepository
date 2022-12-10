package com.modelo.logica.customException;

public class PersonaException extends CustomException {
	
	public static final String USUARIO_EXISTE = "Ya existe una persona con dicho nombre de usuario";
	public static final String CEDULA_EXISTE = "Ya existe una persona con dicha cedula";
	
	public PersonaException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
}
