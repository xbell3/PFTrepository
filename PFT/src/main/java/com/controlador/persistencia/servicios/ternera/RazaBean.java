package com.controlador.persistencia.servicios.ternera;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.controlador.persistencia.entidades.ternera.RazaTambo;
import com.controlador.persistencia.servicios.ServiciosException;

@LocalBean
@Stateless
public class RazaBean{

	@PersistenceContext
	EntityManager em;
	
    public RazaBean() {
        // TODO Auto-generated constructor stub
    }

    

	public boolean crear(RazaTambo raza) throws ServiciosException {
		try {
			em.persist(raza);
			em.flush();
			return true;
		} catch (PersistenceException e) {
				throw new ServiciosException("No se pudo crear la raza");
				
		}
	}
	
	
	public List<RazaTambo> listar() {
		return em.createNamedQuery("Raza.findAll",RazaTambo.class).getResultList();	
	}
	

	public RazaTambo filtrarRaza(String raza) {  
		return em.createNamedQuery("Raza.obtenerxRaza",RazaTambo.class).setParameter("raza", raza).getSingleResult();
	}

}
