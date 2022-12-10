package com.controlador.persistencia.servicios.ternera;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.controlador.persistencia.entidades.ternera.TipoPartoTambo;
import com.controlador.persistencia.servicios.ServiciosException;

@LocalBean
@Stateless
public class TipoPartoBean{


	@PersistenceContext
	EntityManager em;
	
    public TipoPartoBean() {
        // TODO Auto-generated constructor stub
    }

	public boolean crear(TipoPartoTambo parto) throws ServiciosException {
		try {
			
			em.persist(parto);
			em.flush();
			return true;
		} catch (PersistenceException e) {
				throw new ServiciosException("No se pudo crear el parto");
				
		}
	}
   
    public List<TipoPartoTambo> listar(){
    	return em.createNamedQuery("TipoParto.findAll",TipoPartoTambo.class).getResultList();
    }
	
	
	public TipoPartoTambo filtrarTipoParto(String tipoParto) {
		return em.createNamedQuery("TipoParto.obtenerxTipoParto",TipoPartoTambo.class).setParameter("tipoParto", tipoParto).getSingleResult();
	}	

}
