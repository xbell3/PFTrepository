package com.controlador.persistencia.servicios.ternera;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.controlador.persistencia.entidades.ternera.RazaTambo;
import com.controlador.persistencia.entidades.ternera.TerneraTambo;
import com.controlador.persistencia.entidades.ternera.TipoPartoTambo;
import com.controlador.persistencia.servicios.ServiciosException;
import com.vista.terneras.VariacionPesoDTO;

@LocalBean
@Stateless
public class TerneraBean{

	@PersistenceContext
	EntityManager em;
	
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  

    public TerneraBean() {
        // TODO Auto-generated constructor stub
    }

   
		public boolean crear(TerneraTambo ternera,int raza, int parto) {
		try {
			ternera.setRaza(em.find(RazaTambo.class, raza));
			ternera.setTipoParto(em.find(TipoPartoTambo.class, parto));
			ternera.setEstado(1);
			em.persist(ternera);
			em.flush();
			return true;
			
		} catch (PersistenceException e) {;
			return false;
		}
	}

    
   //BORRADO FISICO
	public boolean borrar(Long id) {
		try {
			TerneraTambo ternera = em.find(TerneraTambo.class, id);
			em.remove(ternera);
			em.flush();
			return true;
		}catch (PersistenceException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	public boolean bajaLogica(TerneraTambo ternera) {
		try {
			TerneraTambo ternera1=ternera;
			ternera1.setEstado(0);
			ternera1.setRaza(em.find(RazaTambo.class, ternera.getRaza().getIdraza()));
			ternera1.setTipoParto(em.find(TipoPartoTambo.class, ternera.getTipoParto().getIdparto()));
			em.merge(ternera1);
			em.flush();
			return true;
		}catch (PersistenceException e) {
			e.printStackTrace();
			return false;		
		}
	}
	
	//USADO PARA ACTUALIZAR DATOS
	public boolean actualizar(TerneraTambo ternera,int idRaza,int idRarto) {
		try {
			ternera.setRaza(em.find(RazaTambo.class, idRaza));
			ternera.setTipoParto(em.find(TipoPartoTambo.class, idRarto));
			em.merge(ternera);
			em.flush();
			return true;
		}catch (PersistenceException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
    public List<TerneraTambo> listar(){
    	return em.createNamedQuery("Ternera.findAll",TerneraTambo.class).getResultList();
    }
	

    public List<TerneraTambo> listarxActivas(){
    	return em.createNamedQuery("Ternera.findxEstado",TerneraTambo.class).getResultList();
    }
	
    public List<Object[]> listarTernerasOrdenadas(String filtro, String tipoFiltro){
    	return em.createNativeQuery(""
    			+ "SELECT T.idlocal, T.snig, T.snigmadre, T.snigpadre, T.fecnac, T.pesonac, R.raza, TP.tipoparto from ternera T \r\n"
    			+ "LEFT JOIN raza R\r\n"
    			+ "    ON T.idraza = R.idraza\r\n"
    			+ "LEFT JOIN TIPO_PARTO TP\r\n"
    			+ "    ON T.idparto = TP.idparto WHERE T.estado=1\r\n"
    			+ "ORDER BY " + filtro + " "+tipoFiltro ).getResultList();
    }


	public TerneraTambo ListarxSNIG(int SNIG) {
		try {
			return em.createNamedQuery("Ternera.devolverxSNIG", TerneraTambo.class).setParameter("SNIG", SNIG)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public TerneraTambo ListarxSNIGActiva(int SNIG) {
		try {
			return em.createNamedQuery("Ternera.devolverSoloSNIG", TerneraTambo.class).setParameter("SNIG", SNIG)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean existeIdLocal(int idLocal) {
		try {
			TerneraTambo ternera = em.createNamedQuery("Ternera.devolverIdLocal", TerneraTambo.class).setParameter("IDLOCAL", idLocal).getSingleResult();
			return ternera.getIdlocal() == idLocal;
		} catch (Exception e) {
			return false;
		}
	}
	
	public List<TerneraTambo> existePorClaves(int snig, int idLocal) {
		try {
			return em.createNamedQuery("Ternera.devolverPorClaves", TerneraTambo.class).setParameter("SNIG", snig).setParameter("IDLOCAL", idLocal).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	
	public Float listarCostoAlimentacionPorTernera(Long SNIG, Date fecInicio, Date fecFin) {		
		try {
			return Float.valueOf(em.createNativeQuery(""
					+ "select TO_NUMBER(SUM(RA.cantidad * AL.costounidad)) AS COSTO_ALIMENTACION \r\n"
					+ "FROM REGISTRO_ALIMENTACION RA INNER JOIN ALIMENTOTAMBO AL\r\n"
					+ "    ON RA.idalimento = AL.idalimento\r\n"
					+ "LEFT JOIN TERNERATAMBO T\r\n"
					+ "    ON RA.idternera = T.idternera \r\n"
					+ "WHERE fecAlimentacion between TO_DATE(?1, 'DD/MM/YYYY') AND TO_DATE(?2, 'DD/MM/YYYY') AND T.snig=?3").setParameter(1, dateFormat.format(fecInicio)).setParameter(2, dateFormat.format(fecFin)).setParameter(3, SNIG).getSingleResult().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Float listarCostoAlimentacionPorGuachera(Long idGuachera, Date fecInicio, Date fecFin) {		
		try {
			return Float.valueOf(em.createNativeQuery(""
					+ "select SUM(RM.cantidad * AL.costounidad) AS COSTO_ALIMENTACION \r\n"
					+ "FROM REGISTRO_ALIMENTACION RM INNER JOIN ALIMENTOTAMBO AL\r\n"
					+ "    ON rm.idalimento = al.idalimento\r\n"
					+ "LEFT JOIN TERNERATAMBO T\r\n"
					+ "    ON rm.idternera = t.idternera \r\n"
					+ "LEFT JOIN PERTENECE PE\r\n"
					+ "    ON pe.idternera = rm.idternera\r\n"
					+ "WHERE rm.fecalimentacion between TO_DATE(?1, 'DD/MM/YYYY') AND TO_DATE(?2, 'DD/MM/YYYY') AND PE.idGuachera = ?3").setParameter(1, dateFormat.format(fecInicio)).setParameter(2, dateFormat.format(fecFin)).setParameter(3, idGuachera).getSingleResult().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<Object[]> listarVariacionDePesoPorTernera(Long SNIG, Date fecInicio, Date fecFin) {
		try {
			List<Object[]> variaciones = em.createNativeQuery(""
					+ "SELECT RP.peso, RP.fecpeso\r\n"
					+ "FROM REGISTRO_PESO RP INNER JOIN TERNERATAMBO T\r\n"
					+ "    ON RP.idternera = T.idternera\r\n"
					+ "WHERE RP.fecpeso between TO_DATE(?1, 'DD/MM/YYYY') AND TO_DATE(?2, 'DD/MM/YYYY') AND T.snig = ?3 ORDER BY rp.fecpeso").setParameter(1, dateFormat.format(fecInicio)).setParameter(2, dateFormat.format(fecFin)).setParameter(3, SNIG).getResultList();
			
			return variaciones;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<Object[]> listarVariacionDePesoPorGuachera(Long idGuachera, Date fecInicio, Date fecFin) {
		try {
			List<Object[]> variacionesGuachera = em.createNativeQuery(""
					+ "select SUM(RP.peso) AS PESO, rp.fecpeso\r\n"
					+ "FROM REGISTRO_PESO RP INNER JOIN TERNERATAMBO T\r\n"
					+ "    ON RP.idTernera = T.idTernera\r\n"
					+ "LEFT JOIN PERTENECE PE\r\n"
					+ "    ON RP.idTernera = PE.idTernera\r\n"
					+ "WHERE RP.fecPeso between TO_DATE(?1, 'DD/MM/YYYY') AND TO_DATE(?2, 'DD/MM/YYYY') AND PE.idGuachera = ?3\r\n"
					+ "GROUP BY rp.fecpeso\r\n"
					+ "ORDER BY RP.fecPeso").setParameter(1, dateFormat.format(fecInicio)).setParameter(2, dateFormat.format(fecFin)).setParameter(3, idGuachera).getResultList();
		return variacionesGuachera;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
