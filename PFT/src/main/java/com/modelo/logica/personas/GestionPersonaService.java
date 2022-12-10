package com.modelo.logica.personas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.controlador.persistencia.entidades.persona.EncargadoTambo;
import com.controlador.persistencia.entidades.persona.PersonaTambo;
import com.controlador.persistencia.entidades.persona.PersonalTambo;
import com.controlador.persistencia.servicios.persona.PersonaBean;
import com.controlador.persistencia.servicios.persona.RolBean;
import com.controlador.servicios.ClientResponse;
import com.modelo.logica.customException.PersonaException;
import com.vista.personas.EncargadoDTO;
import com.vista.personas.PersonaDTO;
import com.vista.personas.PersonalDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@Stateless
@LocalBean
public class GestionPersonaService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	PersonaBean personaDAO;
	
	@EJB
	RolBean rolDAO;
	
	@EJB
	PersonaValidator personaValidation;

    public GestionPersonaService() {
    }

    public PersonaDTO fromPersonaTambo(PersonaTambo e) {
    	return PersonaDTO.builder()
    		.idpersona(e.getIdpersona())
    		.nombre(e.getNombre())
    		.apellido(e.getApellido())
    		.cedula(e.getCedula())
    		.nombreUsuario(e.getNombreusuario())
    		.contrasenia(e.getContrasenia())
    		.idRol(e.getRol().getIdrol())
    		.nombreRol(e.getRol().getNombre())
    		.estado(e.getEstado()).build();
    }
    
    public PersonaTambo toPersonaTambo(PersonaDTO personaDTO) {
    	return PersonaTambo.builder()
    			.idpersona(personaDTO.getIdpersona())
    			.nombre(personaDTO.getNombre())
    			.apellido(personaDTO.getApellido())
    			.cedula(personaDTO.getCedula())
    			.nombreusuario(personaDTO.getNombreUsuario())
    			.contrasenia(personaDTO.getContrasenia())
    			.rol(rolDAO.listarxId(personaDTO.getIdRol()))
    			.build();
    }
    
    public EncargadoDTO fromEncargadoTambo(EncargadoTambo et) {
    	return (EncargadoDTO) EncargadoDTO.builder()
	    	.idpersona(et.getIdpersona())
			.nombre(et.getNombre())
			.apellido(et.getApellido())
			.cedula(et.getCedula())
			.nombreUsuario(et.getNombreusuario())
			.contrasenia(et.getContrasenia())
			.idRol(et.getRol().getIdrol())
			.nombreRol(et.getRol().getNombre())
			.estado(et.getEstado()).build();
    }
    
    public EncargadoTambo toEncargadoTambo(EncargadoDTO encargadoDTO) {
    	return EncargadoTambo.builder()
    			.idpersona(encargadoDTO.getIdpersona())
    			.nombre(encargadoDTO.getNombre())
    			.apellido(encargadoDTO.getApellido())
    			.cedula(encargadoDTO.getCedula())
    			.nombreusuario(encargadoDTO.getNombreUsuario())
    			.rol(rolDAO.listarxId(encargadoDTO.getIdRol()))
    			.contrasenia(encargadoDTO.getContrasenia())
    			.titulo(encargadoDTO.getTitulo())
    			.build();
    }

    public PersonalDTO fromPersonalTambo(PersonalTambo pt) {
    	return (PersonalDTO) PersonalDTO.builder()
    			.idpersona(pt.getIdpersona())
    			.nombre(pt.getNombre())
    			.apellido(pt.getApellido())
    			.cedula(pt.getCedula())
    			.nombreUsuario(pt.getNombreusuario())
    			.contrasenia(pt.getContrasenia())
    			.idRol(pt.getRol().getIdrol())
    			.nombreRol(pt.getRol().getNombre())
    			.estado(pt.getEstado()).build();
    }
    
    public PersonalTambo toPersonalTambo(PersonalDTO personalDTO) {
    	return PersonalTambo.builder()
    			.idpersona(personalDTO.getIdpersona())
    			.nombre(personalDTO.getNombre())
    			.apellido(personalDTO.getApellido())
    			.cedula(personalDTO.getCedula())
    			.nombreusuario(personalDTO.getNombreUsuario())
    			.contrasenia(personalDTO.getContrasenia())
    			.rol(rolDAO.listarxId(personalDTO.getIdRol()))
    			.cantHoras(personalDTO.getCantHoras())
    			.build();
    }
    
    public List<PersonaDTO> listarPersonas(){
    	List<PersonaTambo> personasTambo= personaDAO.listarxEstado();
    	List<PersonaDTO> ListaPersonas=new ArrayList<PersonaDTO>();
    	for(PersonaTambo PTambo: personasTambo) {
    		ListaPersonas.add(fromPersonaTambo(PTambo));
    	}
    	return ListaPersonas;
    }
    
    
	public ClientResponse agregarPersona(PersonaDTO personaIngresada) throws PersonaException {
		try {
			if (personaValidation.validateCreation(personaIngresada) == HttpResponseCodes.SC_OK)
				if (personaDAO.crear(toPersonaTambo(personaIngresada)) != null)
					return new ClientResponse(HttpResponseCodes.SC_OK, ClientResponse.ALTA_PERSONA_CORRECTA, "Persona de tipo administrador dada de alta exitosamente");
				else
					return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "500", "Ha ocurrido un error interno dando de alta la persona de tipo administrador");
		} catch (PersonaException pe) {
			pe.printStackTrace();
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, pe.getErrorCode(), pe.getErrorMessage());
		}
		return new ClientResponse(0, "", "");
	}
    
    
    public ClientResponse agregarEncargadoTambo(EncargadoDTO encargadoIngresado) throws PersonaException  {
    	try {
			if (personaValidation.validateCreation(encargadoIngresado) == HttpResponseCodes.SC_OK)
				if (personaDAO.crearEncargado(toEncargadoTambo(encargadoIngresado)) != null)
					return new ClientResponse(HttpResponseCodes.SC_OK, ClientResponse.ALTA_ENCARGADO_CORRECTA, "Persona de tipo encargado dada de alta exitosamente");
				else
					return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "500", "Ha ocurrido un error interno dando de alta la persona de tipo encargado");
					
		} catch (PersonaException pe) {
			pe.printStackTrace();
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, pe.getErrorCode(), pe.getErrorMessage());		
		}
		return new ClientResponse();
    }
   
    
    
    public ClientResponse agregarPersonalTambo(PersonalDTO personalSeleccionado) throws PersonaException  {
    	try {
    		if (personaValidation.validateCreation(personalSeleccionado) == HttpResponseCodes.SC_OK)
    			if (personaDAO.crearPersonal(toPersonalTambo(personalSeleccionado)) != null)
    				return new ClientResponse(HttpResponseCodes.SC_OK, ClientResponse.ALTA_PERSONAL_CORRECTA, "Persona de tipo personal dada de alta exitosamente");
    			else
    				return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "500", "Ha ocurrido un error interno dando de alta la persona de tipo personal");
    				
		} catch (PersonaException pe) {
			pe.printStackTrace();
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, pe.getErrorCode(), pe.getErrorMessage());
		}
		return new ClientResponse();
	} 
    
    
    public PersonaDTO borrarPersona(int cedula) throws Exception{
    		PersonaTambo e= personaDAO.bajaLogicaxCedula(cedula);
    		return fromPersonaTambo(e);
    }
    
    public PersonaDTO listarPersonaxId(Long idPersona) throws Exception{
    	try {
			PersonaTambo e= personaDAO.devolverPersonaxId(idPersona);
			return fromPersonaTambo(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public PersonaDTO listarPersonaxCedula(int cedula) throws Exception{
    	try {
			PersonaTambo e= personaDAO.devolverPersonaxCedula(cedula);
			return fromPersonaTambo(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public void editarPersona(PersonaDTO personaSeleccionada, EncargadoDTO encargadoSeleccionado, PersonalDTO personalSeleccionado) {
    	try {
    		int idRol=personaDAO.devolverPersonaxId(personaSeleccionada.getIdpersona()).getRol().getIdrol();
    		int nuevoRol=personaSeleccionada.getIdRol();
    		
    		encargadoSeleccionado.setIdpersona(personaSeleccionada.getIdpersona());
    		personalSeleccionado.setIdpersona(personaSeleccionada.getIdpersona());

    		encargadoSeleccionado.setIdRol(personaSeleccionada.getIdRol());
    		personalSeleccionado.setIdRol(personaSeleccionada.getIdRol());
    		
    		if (personaDAO.actualizar(toPersonaTambo(personaSeleccionada))) {
    			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
										"Se actualizo el usuario ", "");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}
    		

    		switch (idRol) { //IDENTIFICAMOS QUE ROL ES
				case 0: //PERSONA ADMINISTRADORA
				
					switch (nuevoRol) { //SE ANALIZA NUEVO ROL
					
						case 1: //ENCARGADO, SE DA DE ALTA UN REGISTRO EN ENCARGADO	
							if(personaDAO.relacionarEncargado(toEncargadoTambo(encargadoSeleccionado)))
						break;
						
						case 2: //PERSONAL, SE DA DE ALTA UN REGISTRO EN PERSONAL
							if(personaDAO.relacionarPersonal(toPersonalTambo(personalSeleccionado)))
						break;
					}	
				break;
				
				case 1: //ENCARGADO

					switch (nuevoRol) { //SE ANALIZA NUEVO ROL
						
						case 0://AHORA ES ADMINISTRADOR, SE ELIMINA REGISTRO ENCARGADO
							personaDAO.bajaEncargado(personaSeleccionada.getIdpersona());
							break;
					
					case 1:// SI ES ENCARGADO SE ACTUALIZA SUS DATOS
						if(personaDAO.actualizarEncargado(toEncargadoTambo(encargadoSeleccionado)))
					break;
					
					case 2://SE HACE PERSONAL
						personaDAO.bajaEncargado(personaSeleccionada.getIdpersona());
						if (personaDAO.relacionarPersonal(toPersonalTambo(personalSeleccionado)))
					break;
					}
				break;
				
				case 2: //PERSONAL

					switch (nuevoRol) { // SE ANALIZA NUEVO ROL

						case 0:// AHORA ES ADMINISTRADOR, SE ELIMINA REGISTRO PERSONAL
							personaDAO.bajaPersonal(personaSeleccionada.getIdpersona());
							break;

						case 1:// ES PERSONAL Y AHORA ENCARGADO
							personaDAO.bajaPersonal(personaSeleccionada.getIdpersona());
							if (personaDAO.relacionarEncargado(toEncargadoTambo(encargadoSeleccionado)))
						break;

						case 2:// ES PERSONAL, SE ACTUALIZAN LOS DATOS
							if (personaDAO.actualizarPersonal(toPersonalTambo(personalSeleccionado)))
						break;
					}
					break;
			}
    		
    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    public PersonaDTO loginPersona(String nombreUsuario) {
    	try {
    		PersonaTambo e= personaDAO.devolverPersonaxUsuario(nombreUsuario);
    		if(e!=null) {
    			return fromPersonaTambo(e);
    		}else {
    			return null;
    		}
    		
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return null;
    	
    }
}
