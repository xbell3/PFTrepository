package com.controlador.persistencia.servicios.medicamento;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.controlador.persistencia.entidades.medicamento.MedicamentoTambo;
import com.controlador.persistencia.entidades.medicamento.RegistroMedicacionTambo;
import com.controlador.persistencia.entidades.ternera.TerneraTambo;
import com.controlador.persistencia.servicios.ServiciosException;

@LocalBean
@Stateless
public class RegistroMedicacionBean {

	@PersistenceContext
	EntityManager em;
	
    public RegistroMedicacionBean() {
    }

    public boolean registrarMedicacion(RegistroMedicacionTambo regMedicacion) {
    	try {
    		restarDosisMedicamento(regMedicacion);
        	em.persist(regMedicacion);
        	em.flush();
    		return true;
		} catch (Exception e) {
	    	return false;

		}
    }
    
    public RegistroMedicacionTambo listarxFiltro(int idTernera,int idMedicamento, String fecRegistro) {
    	try {
			return em.createNamedQuery("RegistroMedicacion.findFiltrar",RegistroMedicacionTambo.class)
			.setParameter("IDTERNERA", (long)	idTernera)
			.setParameter("IDMEDICAMENTO", idMedicamento)
			.setParameter("FECREGISTRO", fecRegistro).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    
	public boolean actualizar(RegistroMedicacionTambo registroMedicacionDTO) throws ServiciosException{
		try {
			registroMedicacionDTO.setTernera(em.find(TerneraTambo.class, registroMedicacionDTO.getTernera().getIdternera()));
			registroMedicacionDTO.setMedicamento(em.find(MedicamentoTambo.class, registroMedicacionDTO.getMedicamento().getIdmedicamento()));
			em.merge(registroMedicacionDTO);
			em.flush();
			return true;
		}catch (PersistenceException e) {
			return false;
		}
	}
	
	public boolean borrar(RegistroMedicacionTambo regMedicacion) {
		try {
			RegistroMedicacionTambo regEliminar=em.find(RegistroMedicacionTambo.class, regMedicacion.getIdRegistroMedicacion());
			em.remove(regEliminar);
			em.flush();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
    
    public List<RegistroMedicacionTambo> listar(){
    	return em.createNamedQuery("RegistroMedicacion.findAll",RegistroMedicacionTambo.class).getResultList();
    }
	
	
	public void restarDosisMedicamento(RegistroMedicacionTambo regMedicacion) {
		MedicamentoTambo medicamento=em.find(MedicamentoTambo.class, regMedicacion.getMedicamento().getIdmedicamento());
		medicamento.setStock(medicamento.getStock()-regMedicacion.getDosisAdministrada());
		em.merge(medicamento);
		em.flush();
	}
}
