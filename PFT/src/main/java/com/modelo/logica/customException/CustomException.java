package com.modelo.logica.customException;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomException extends Exception{
	
	private String errorCode;
	private String errorMessage;
}
