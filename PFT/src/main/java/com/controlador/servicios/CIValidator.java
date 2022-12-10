package com.controlador.servicios;

import java.util.Random;

import org.jboss.resteasy.util.HttpResponseCodes;

public class CIValidator {

    /**
     * Returns if the identification number is valid
     *
     * @param ci
     * @return boolean
     */
	public ClientResponse validateCi(String ci) {
		String cleanCi = this.cleanCi(ci);

		if (ci.matches(".*[a-zA-Z].*") || cleanCi.length() == 0)
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "CI001", "El formato de cedula debe ser x.xxx.xxx-x O xxxxxxxx");
		else if (cleanCi.length() != 7 && cleanCi.length() != 8)
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "CI001", "El formato de cedula debe ser x.xxx.xxx-x O xxxxxxxx");
		else {
			char validationDigit = cleanCi.charAt(cleanCi.length() - 1);
			
			if (Character.getNumericValue(validationDigit) != this.validationDigit(cleanCi))
				return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "CI002", "Digito verificador incorrecto");
			else
				return new ClientResponse(HttpResponseCodes.SC_OK, "CI200", "");
		}
	}

    /**
     * Clean up removing all characters except numbers
     *
     * @param ci
     * @return String
     */
    public String cleanCi(String ci) {
        return ci.replaceAll("[^0-9]", "");
    }

    /**
     * Check validation digit from a number of ci
     *
     * @param ci String
     * @return Integer
     */
    protected Integer validationDigit(String ci) {
        String cleanCi = this.cleanCi(ci);
        int a = 0;
        String baseNumber = "2987634";
        String addZeros = String.format("%8s", cleanCi).replace(" ", "0");

        for (int i = 0; i < baseNumber.length(); i++) {
            int baseDigit = Character.getNumericValue(baseNumber.charAt(i));
            int ciWithZeros = Character.getNumericValue(addZeros.charAt(i));
            a += (baseDigit * ciWithZeros) % 10;
        }
        return a % 10 == 0 ? 0 : 10 - a % 10;
    }

    /**
     * Generate a valid random identification number
     *
     * @return String
     */
    public String randomCi() {
        int randomNumber = 10000000 + new Random().nextInt(90000000);
        String ci = String.valueOf(randomNumber).substring(0, 7) + this.validationDigit(String.valueOf(randomNumber));
        return ci;
    }
}
