package com.controlador.persistencia.servicios.medicamento;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.controlador.persistencia.entidades.medicamento.TipoMedicamentoTambo;

@LocalBean
@Stateless
public class TipoMedicamentoBean{

	@PersistenceContext
	EntityManager em;
	
	
    public TipoMedicamentoBean() {
    }

   
    public List<TipoMedicamentoTambo> listar(){
    	return em.createNamedQuery("TipoMedicamento.findAll",TipoMedicamentoTambo.class).getResultList(); 
    }
    
 
    public TipoMedicamentoTambo  listarxIdTipoMedicamento(int idTipoMedicamento) {
    	return em.createNamedQuery("TipoMedicamento.devolverxidTipoMedicamento",TipoMedicamentoTambo.class).setParameter("idtipo", idTipoMedicamento).getSingleResult();
    }
    
    public TipoMedicamentoTambo listarxNombreTipoMedicamento(String nombre) {
    	return em.createNamedQuery("TipoMedicamento.devolverxNombreTipoMedicamento",TipoMedicamentoTambo.class).setParameter("tipo", nombre).getSingleResult();
    }
    
}
