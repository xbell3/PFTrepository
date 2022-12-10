package com.controlador.servicios.personas;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.modelo.logica.personas.GestionPersonaService;
import com.vista.personas.PersonaDTO;

@Path("/persona")
public class PersonaRest {
	
	@EJB
	GestionPersonaService gestionPersonaService;
	
	@GET
	@Path("/listarPersonas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PersonaDTO> getPersonas(){
		List<PersonaDTO> personas=gestionPersonaService.listarPersonas();
		return personas;
	}
	
	@GET
	@Path("/login{nombreUsuario}-{contrasenia}")
	@Produces(MediaType.APPLICATION_JSON)
	public PersonaDTO login(@PathParam("nombreUsuario") String nombreUsuario,@PathParam("contrasenia") String contrasenia) {
		try {
			PersonaDTO personaNueva = gestionPersonaService.loginPersona(nombreUsuario);
			if (personaNueva.getContrasenia().equals(contrasenia) == false || personaNueva == null) {
				return null;
			} else {
				// contraseña correcta, sigue el flujo
				return personaNueva;
			}
			
		} catch (Exception e) {
			return null;
		}
	}
}
