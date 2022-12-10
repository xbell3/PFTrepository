package com.controlador.persistencia.servicios.alimento;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.controlador.persistencia.entidades.alimento.AlimentoTambo;
import com.controlador.persistencia.entidades.alimento.RegistroAlimentacionTambo;

@LocalBean
@Stateless
public class RegistroAlimentacionBean{

	@PersistenceContext
	EntityManager em;
	
    public RegistroAlimentacionBean() {
    }
    

    public boolean registrarAlimentacion(RegistroAlimentacionTambo regAlimentacion) {
    	try {
    		restarCantidadAlimento(regAlimentacion);
    		em.persist(regAlimentacion);
    		em.flush();
    		return true;
    	} catch (Exception e) {}
    	return false;
    }
	
	public List<RegistroAlimentacionTambo> listar(){
		return em.createNamedQuery("RegistroAlimentacion.findAll",RegistroAlimentacionTambo.class).getResultList();
	}
	
    
    public void restarCantidadAlimento(RegistroAlimentacionTambo regAlimentacion) {
		AlimentoTambo alimento=em.find(AlimentoTambo.class, regAlimentacion.getAlimento().getIdalimento());
		alimento.setStock(alimento.getStock()-regAlimentacion.getCantidad());
		em.merge(alimento);
		em.flush();
	}
}
