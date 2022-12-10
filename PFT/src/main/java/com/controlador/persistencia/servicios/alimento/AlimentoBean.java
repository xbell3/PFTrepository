package com.controlador.persistencia.servicios.alimento;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.controlador.persistencia.entidades.alimento.AlimentoTambo;
import com.controlador.persistencia.entidades.alimento.UnidadTambo;

@LocalBean
@Stateless
public class AlimentoBean {

	@PersistenceContext
	EntityManager em;

	public AlimentoBean() {
	}

	public List<AlimentoTambo> listar() {
		return em.createNamedQuery("Alimento.findAll", AlimentoTambo.class).getResultList();
	}

	public AlimentoTambo listarxNombre(String alimento) {
		try {
			return em.createNamedQuery("Alimento.findxNombre", AlimentoTambo.class).setParameter("NOMBRE", alimento)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<UnidadTambo> listarUnidades() {
		return em.createNamedQuery("Unidad.findAll", UnidadTambo.class).getResultList();
	}

	public UnidadTambo listarUnidadxNombre(String nombre) {
		try {
			return em.createNamedQuery("Unidad.findxNombre", UnidadTambo.class).setParameter("NOMBRE", nombre)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean stockSuficiente(float cantidad, String nombre) {
		return cantidad > listarxNombre(nombre).getStock();
	}
}
