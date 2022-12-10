package com.controlador.servicios;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ConstructorFecha{


	public static String dateToString(Date fecha) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		return dateFormat.format(fecha);
	}
	
	public static Date stringToDate(String fechaString) {
		try {
			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
			dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT-3"));
			Date fecha= dateFormatter.parse(fechaString);
			return fecha;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
