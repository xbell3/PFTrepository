package com.controlador.persistencia.servicios.enfermedad;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.controlador.persistencia.entidades.enfermedad.EnfermedadTambo;
import com.controlador.persistencia.entidades.enfermedad.GravedadTambo;


/**
 * Session Bean implementation class EnfermedadBean
 */
@Stateless
public class EnfermedadBean{

	@PersistenceContext
	EntityManager em;
	
    public EnfermedadBean() {
        // TODO Auto-generated constructor stub
    }
    
    
    public List<EnfermedadTambo> listar(){
    	return em.createNamedQuery("Enfermedad.findAll",EnfermedadTambo.class).getResultList();
    }
    
    
    public EnfermedadTambo devolverEnfermedadPorNombre(String nombre,int idGravedad,String variante) {
    	try {
    		GravedadTambo gravedad=listargravedadxId(idGravedad);

    	return em.createNamedQuery("Enfermedad.findxNombre",EnfermedadTambo.class)
    			.setParameter("NOMBRE", nombre)
    			.setParameter("IDGRAVEDAD", gravedad)
    			.setParameter("VARIANTE", variante)
    			.getSingleResult();
    }catch(Exception e) {
		e.printStackTrace();
	}
		return null;
    }

    
	public boolean cargarDatosHistoricosEnfermedad(int idEnfermedad, int idTernera, String fecDeteccion) {
		try {
			em.createNativeQuery("INSERT INTO DETECCION_ENFERMEDAD (idEnfermedad, idTernera, fecDeteccion) "
					+ "values (?1, ?2, TO_DATE(?3, 'DD/MM/YYYY'))")
			.setParameter(1, idEnfermedad)
			.setParameter(2, idTernera)
			.setParameter(3, fecDeteccion)
			.executeUpdate();
			em.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
    
    
    public void eliminarDatosHistoricosEnfermedad() {
    	try {
    		em.createNativeQuery("DELETE FROM DETECCION_ENFERMEDAD").executeUpdate();
    		em.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		
    }
    
    
    public EnfermedadTambo devolverEnfermedadPorId(int idEnfermedad) {
    	try {
    	return em.createNamedQuery("Enfermedad.findxId",EnfermedadTambo.class)
    			.setParameter("IDENFERMEDAD", idEnfermedad)
    			.getSingleResult();
    }catch(Exception e) {
		e.printStackTrace();
	}
		return null;
    }
    
    
    public List<GravedadTambo> listarGravedad(){
    	return em.createNamedQuery("Gravedad.findAll",GravedadTambo.class).getResultList();
    }
    
    
    public GravedadTambo listargravedadxId(int id) {
    	try {
    	return em.createNamedQuery("Gravedad.findxId",GravedadTambo.class).setParameter("ID", id).getSingleResult();
    }catch(Exception e) {
		return null;
	}
    }
    
    public List<Object[]> listarEnfermedadesHistorica() {
    	try {
    	return em.createNativeQuery("SELECT T.SNIG,E.NOMBRE,E.VARIANTE,g.gravedad,de.fecdeteccion \r\n"
    			+ "FROM DETECCION_ENFERMEDAD DE \r\n"
    			+ "INNER JOIN ENFERMEDADTAMBO E ON DE.IDENFERMEDAD=E.IDENFERMEDAD \r\n"
    			+ "INNER JOIN TERNERATAMBO T ON DE.IDTERNERA=T.IDTERNERA \r\n"
    			+ "INNER JOIN GRAVEDADTAMBO G ON E.GRAVEDAD=g.idgravedad").getResultList();
    }catch(Exception e) {}
		return null;
    }
    
   
    public List<Object[]> filtrarEnfermedadesHistoricasNombre(String nombre) {
    	try {
    	return em.createNativeQuery("SELECT T.SNIG,E.NOMBRE,E.VARIANTE,g.gravedad,de.fecdeteccion \r\n"
    			+ "FROM DETECCION_ENFERMEDAD DE \r\n"
    			+ "INNER JOIN ENFERMEDADTAMBO E ON DE.IDENFERMEDAD=E.IDENFERMEDAD \r\n"
    			+ "INNER JOIN TERNERATAMBO T ON DE.IDTERNERA=T.IDTERNERA \r\n"
    			+ "INNER JOIN GRAVEDADTAMBO G ON E.GRAVEDAD=g.idgravedad "
    			+ "WHERE E.NOMBRE=:NOMBRE").setParameter("NOMBRE", nombre).getResultList();
    }catch(Exception e) {
    	e.printStackTrace();
    }
		return null;
    }
    
    public List<Object[]> filtrarEnfermedadesHistoricasSnig(int snig) {
    	try {
    	return em.createNativeQuery("SELECT T.SNIG,E.NOMBRE,E.VARIANTE,g.gravedad,de.fecdeteccion \r\n"
    			+ "FROM DETECCION_ENFERMEDAD DE \r\n"
    			+ "INNER JOIN ENFERMEDADTAMBO E ON DE.IDENFERMEDAD=E.IDENFERMEDAD \r\n"
    			+ "INNER JOIN TERNERATAMBO T ON DE.IDTERNERA=T.IDTERNERA \r\n"
    			+ "INNER JOIN GRAVEDADTAMBO G ON E.GRAVEDAD=g.idgravedad "
    			+ "WHERE t.SNIG=:SNIG").setParameter("SNIG", snig).getResultList();
    }catch(Exception e) {
    	e.printStackTrace();
    }
		return null;
    }
}
