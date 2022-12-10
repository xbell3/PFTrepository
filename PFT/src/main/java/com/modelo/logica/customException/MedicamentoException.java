package com.modelo.logica.customException;

public class MedicamentoException extends CustomException{

	public static final String EXISTE_PRODUCTO = "Ya existe un medicamento registrado con dicho nombre";
	public static final String DOSIS_INSUFICIENTE = "No hay stock suficiente para la medicación que desea registrar";
	
	public static final String PRODUCTO_INEXISTENTE = "No existe un medicamento con dicho nombre";
	public static final String PRODUCTO_DADO_DE_BAJA = "El medicamento indicado se encuentra dado de baja";

	public MedicamentoException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

}
