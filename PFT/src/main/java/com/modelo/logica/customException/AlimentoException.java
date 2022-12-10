package com.modelo.logica.customException;

public class AlimentoException extends CustomException {

	public static final String STOCK_INSUFICIENTE = "No hay stock suficiente para la alimentacion que desea registrar";
	public static final String ALIMENTO_INEXISTENTE = "No existe un alimento con ese nombre";

	public AlimentoException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

}
