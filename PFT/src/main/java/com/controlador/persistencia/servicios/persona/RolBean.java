package com.controlador.persistencia.servicios.persona;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.UserTransaction;

import com.controlador.persistencia.entidades.permisos.Rol;

@LocalBean
@Stateless
public class RolBean{

	@PersistenceContext
	EntityManager em;
	
    public RolBean() {
        // TODO Auto-generated constructor stub
    }

    
    public boolean crear(Rol rol) {
    try {
    	
    	/*for (Funcionalidad funcionalidad : rol.getFuncionalidades()) {
        	rol.agregarFuncionalidad(em.find(Funcionalidad.class, funcionalidad.getIdfuncionalidad()));
		}*/
    	Rol rol1=em.find(Rol.class, rol.getIdrol());
  
    	em.persist(rol1);
    	em.flush();
    	
    	//em.getTransaction().begin();
		//em.persist(rol);
		//em.getTransaction().commit();
    	//rol.agregarFuncionalidad((Funcionalidad.class, rol.getFuncionalidades()).getIdfuncionalidad());
    	
		return true;
    }catch (PersistenceException e) {
    	e.printStackTrace();
    }
	return false;
    	
    }
    
    
	
    public List<Rol> listar(){
    	return em.createNamedQuery("Rol.findAll",Rol.class).getResultList();
    }
    
	
    public Rol listarxNombre(String nombre) {
		try {
			return em.createNamedQuery("Rol.devolverxNombre",Rol.class).setParameter("NOMBRE", nombre).getSingleResult();
		}catch (Exception e) {
		e.printStackTrace();	
		}
		return null;
   
    }
    
    public Rol listarxId(int idRol) {
		try {
			return em.createNamedQuery("Rol.devolverxId",Rol.class).setParameter("IDROL", idRol).getSingleResult();
		}catch (Exception e) {
		e.printStackTrace();	
		}
		return null;
   
    }
   
	
	public List<String> listarFuncionalidesxRol(int id){
		return em.createNativeQuery("select f.nombre from funcionalidad f INNER JOIN ROL_FUNCIONALIDAD rf ON f.idfuncionalidad=rf.fk_rolfuncionalidad_funcionalidad where rf.fk_rolfuncionalidad_rol LIKE :IDROL")
		.setParameter("IDROL", id).getResultList();
	}
	
	
}
