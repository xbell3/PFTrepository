package com.modelo.logica.personas;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.controlador.persistencia.servicios.persona.PersonaBean;
import com.modelo.logica.customException.PersonaException;
import com.vista.personas.PersonaDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@LocalBean
@Stateless
public class PersonaValidator {

	@EJB
	private PersonaBean personaDAO;

	public int validateCreation(PersonaDTO personaDTO) throws PersonaException {
		if (personaDAO.devolverPersonaxUsuario(personaDTO.getNombreUsuario()) != null)
			throw new PersonaException("AP001", PersonaException.USUARIO_EXISTE);

		else if (personaDAO.devolverPersonaxCedula(personaDTO.getCedula()) != null)
			throw new PersonaException("AP002", PersonaException.CEDULA_EXISTE);

		else
			return HttpResponseCodes.SC_OK;
	}

}
