package com.controlador.persistencia.servicios.medicamento;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.controlador.persistencia.entidades.medicamento.MedicamentoTambo;
import com.controlador.persistencia.entidades.medicamento.TipoMedicamentoTambo;
import com.controlador.persistencia.servicios.ServiciosException;

@LocalBean
@Stateless
public class MedicamentoBean{

	@PersistenceContext
	EntityManager em;
	
    public MedicamentoBean() {
    }
    
	public boolean crear(MedicamentoTambo medicamento, Integer idTipoMedicamento) {
		try {
			medicamento.setTipoMedicamento(em.find(TipoMedicamentoTambo.class, idTipoMedicamento));
			medicamento.setEstado(1);
			em.persist(medicamento);
			em.flush();
			return true;

		} catch (PersistenceException e) {
			e.printStackTrace();
			return false;
		}
	}
    
    
	public boolean bajaLogica(MedicamentoTambo medicamento) {
		try {	
			medicamento.setTipoMedicamento(em.find(TipoMedicamentoTambo.class, medicamento.getTipoMedicamento().getIdtipo()));
			medicamento.setEstado(0);
			em.merge(medicamento);
			em.flush();
			return true;
		}catch (PersistenceException e) {
			e.printStackTrace();
			return false;
		}
	}
    
    
	
    public boolean actualizar(MedicamentoTambo medicamento) {
		try {
			medicamento.setTipoMedicamento(em.find(TipoMedicamentoTambo.class, medicamento.getTipoMedicamento().getIdtipo()));
			medicamento.setEstado(1);
			em.merge(medicamento);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace();
			return false;
		}
	}
	
    
	public List<MedicamentoTambo> listar(){
		return em.createNamedQuery("Medicamento.findAll",MedicamentoTambo.class).getResultList();
	}
    
	
    public MedicamentoTambo listarxProducto(String producto) {
    	try {
        	return em.createNamedQuery("Medicamento.devolverxProducto",MedicamentoTambo.class).setParameter("PRODUCTO", producto).getSingleResult();
		} catch (Exception e) {
			return null;
		}
    }
    
    public MedicamentoTambo listarxId(Long idMedicamento) {
    	try {
			return em.createNamedQuery("Medicamento.devolverxId",MedicamentoTambo.class).setParameter("id", idMedicamento).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
    }
	
	
	public boolean existeProducto(String producto) {
		try {
			MedicamentoTambo medicamento = em.createNamedQuery("Medicamento.devolverxProducto", MedicamentoTambo.class)
					.setParameter("PRODUCTO", producto).getSingleResult();
			return medicamento.getProducto().equals(producto);
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public boolean dosisSuficiente(int dosisAdministrada, String producto) {
		return em.createNamedQuery("Medicamento.devolverxProducto",MedicamentoTambo.class).setParameter("PRODUCTO", producto).getSingleResult().getStock() < dosisAdministrada;
	}
	
	
	public List<Object[]> listarMedicamentosOrdenados(String filtro, String tipoFiltro){
		return em.createNativeQuery(""
				+ "SELECT M.producto, TM.tipo, M.stock, M.costo FROM MEDICAMENTO M\r\n"
				+ "LEFT JOIN TIPO_MEDICAMENTO TM\r\n"
				+ "    ON tm.idTipo = M.idTipo\r\n"
				+ "WHERE M.estado = 1\r\n"
				+ "ORDER BY " + filtro + " " + tipoFiltro).getResultList();
	}


}
