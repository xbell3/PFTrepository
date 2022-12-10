package com.controlador.persistencia.servicios.persona;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.controlador.persistencia.entidades.permisos.Rol;
import com.controlador.persistencia.entidades.persona.EncargadoTambo;
import com.controlador.persistencia.entidades.persona.PersonaTambo;
import com.controlador.persistencia.entidades.persona.PersonalTambo;
import com.controlador.persistencia.servicios.ServiciosException;

@LocalBean
@Stateless
public class PersonaBean {

	@PersistenceContext
	EntityManager em;

	public PersonaBean() {
		// TODO Auto-generated constructor stub
	}

	public PersonaTambo crear(PersonaTambo persona) {
		try {
			persona.setEstado(1);
			em.persist(persona);
			em.flush();
			return persona;
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		return null;

	}

	public EncargadoTambo crearEncargado(EncargadoTambo encargado) {
		try {
			encargado.setEstado(1);
			em.persist(encargado);
			em.flush();
			return encargado;
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		return null;

	}

	public boolean bajaEncargado(Long idPersona) {
		try {
			em.createNativeQuery("DELETE FROM ENCARGADOTAMBO WHERE idPersona=?1").setParameter(1, idPersona)
					.executeUpdate();
			em.flush();
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		return false;
	}

	public PersonalTambo crearPersonal(PersonalTambo personal) {
		try {
			personal.setEstado(1);
			em.persist(personal);
			em.flush();
			return personal;
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean bajaPersonal(Long idPersona) {
		try {
			em.createNativeQuery("DELETE FROM PERSONALTAMBO WHERE idPersona=?1").setParameter(1, idPersona)
					.executeUpdate();
			em.flush();
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean bajaLogica(PersonaTambo persona) throws ServiciosException {
		try {
			PersonaTambo persona1 = persona;
			persona1.setEstado(0);
			persona1.setRol(em.find(Rol.class, persona.getRol().getIdrol()));
			em.merge(persona1);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			return false;
		}
	}

	public PersonaTambo bajaLogicaxCedula(int cedula) throws ServiciosException {
		try {
			PersonaTambo persona1 = listarxCedula(cedula);
			persona1.setEstado(0);
			persona1.setRol(em.find(Rol.class, persona1.getRol().getIdrol()));
			em.merge(persona1);
			em.flush();
			return persona1;
		} catch (PersistenceException e) {
			return null;
		}
	}

	public boolean actualizar(PersonaTambo persona) throws ServiciosException {
		try {
			PersonaTambo persona1 = em.find(PersonaTambo.class, persona.getIdpersona());
			persona1.setNombre(persona.getNombre());
			persona1.setApellido(persona.getApellido());
			persona1.setCedula(persona.getCedula());
			persona1.setNombreusuario(persona.getNombreusuario());
			persona1.setContrasenia(persona.getContrasenia());
			persona1.setRol(em.find(Rol.class, persona.getRol().getIdrol()));
			persona1.setEstado(1);
			em.merge(persona1);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean actualizarEncargado(EncargadoTambo encargado) throws ServiciosException {
		try {
			EncargadoTambo encargado1 = encargado;
			encargado1.setRol(em.find(Rol.class, encargado.getRol().getIdrol()));
			em.merge(encargado1);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			return false;
		}
	}

	public boolean actualizarPersonal(PersonalTambo personal) throws ServiciosException {
		try {
			PersonalTambo personal1 = personal;
			personal1.setRol(em.find(Rol.class, personal.getRol().getIdrol()));
			em.merge(personal1);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			return false;
		}
	}

	public boolean relacionarEncargado(EncargadoTambo encargado) throws ServiciosException {
		try {
			em.createNativeQuery("INSERT INTO ENCARGADOTAMBO (idPersona,titulo) VALUES(?1, ?2)")
					.setParameter(1, encargado.getIdpersona()).setParameter(2, encargado.getTitulo()).executeUpdate();
			em.flush();
			return true;
		} catch (PersistenceException e) {
			return false;
		}
	}

	public boolean relacionarPersonal(PersonalTambo personal) throws ServiciosException {
		try {
			em.createNativeQuery("INSERT INTO PERSONALTAMBO (idPersona,cantHoras) VALUES(?1, ?2)")
					.setParameter(1, personal.getIdpersona()).setParameter(2, personal.getCantHoras()).executeUpdate();
			em.flush();
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace();
			return false;

		}
	}

	public List<PersonaTambo> listarxEstado() {
		return em.createNamedQuery("Persona.listarActivas", PersonaTambo.class).getResultList();
	}

	public PersonaTambo existePersona(String nombreUsuario) {
		try {
			System.out.println(nombreUsuario);
			PersonaTambo p = em.createNamedQuery("Persona.devolverxNombreUsuario", PersonaTambo.class)
					.setParameter("NOMBRE", nombreUsuario).getSingleResult();
			return p;
		} catch (Exception e) {
			return new PersonaTambo();
		}

	}

	public PersonaTambo devolverPersonaxId(Long idPersona) {
		try {
			PersonaTambo p = em.createNamedQuery("Persona.devolverxIdPersona", PersonaTambo.class)
					.setParameter("IDPERSONA", idPersona).getSingleResult();
			return p;
		} catch (Exception e) {
			return null;
		}

	}

	public PersonaTambo existePersonaxUsuario(String nombreUsuario) {
		try {
			PersonaTambo p = em.createNamedQuery("Persona.devolverxNombreUsuario", PersonaTambo.class)
					.setParameter("NOMBRE", nombreUsuario).getSingleResult();
			return p;
		} catch (Exception e) {
			return null;
		}

	}

	public PersonaTambo listarxCedula(int cedula) {
		try {
			return em.createNamedQuery("Persona.devolverxCedula", PersonaTambo.class).setParameter("CEDULA", cedula)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<EncargadoTambo> listarEncargados() {
		return em.createNamedQuery("Encargado.findAll", EncargadoTambo.class).getResultList();
	}

	public List<PersonalTambo> listarPersonales() {
		return em.createNamedQuery("Personal.findAll", PersonalTambo.class).getResultList();
	}

	public EncargadoTambo listarEncargadoxCedula(int cedula) {
		try {
			PersonaTambo persona = listarxCedula(cedula);
			return em.createNamedQuery("Encargado.findAllxCedula", EncargadoTambo.class)
					.setParameter("idPersona", persona.getIdpersona()).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public PersonalTambo listarPersonalxCedula(int cedula) {
		try {
			PersonaTambo persona = listarxCedula(cedula);
			return em.createNamedQuery("Personal.findAllxCedula", PersonalTambo.class)
					.setParameter("idPersona", persona.getIdpersona()).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public PersonaTambo devolverPersonaxUsuario(String nombreUsuario) {
		try {
			PersonaTambo p = em.createNamedQuery("Persona.devolverxNombreUsuario", PersonaTambo.class)
					.setParameter("NOMBREUSUARIO", nombreUsuario).getSingleResult();
			return p;
		} catch (Exception e) {
			return null;
		}

	}

	public PersonaTambo devolverPersonaxCedula(int cedula) {
		try {
			return em.createNamedQuery("Persona.devolverxCedula", PersonaTambo.class).setParameter("CEDULA", cedula)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}