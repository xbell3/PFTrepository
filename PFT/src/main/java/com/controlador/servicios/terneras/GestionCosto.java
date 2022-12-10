package com.controlador.servicios.terneras;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import com.modelo.logica.terneras.GestionTerneraService;

@Named(value="gestionCosto")
@SessionScoped
public class GestionCosto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	GestionTerneraService persistenciaBean;
	
	private Long snig;
	private Long idGuachera;
	private Date fechaInicio;
	private Date fechaFin;
	
	private Float costoTernera;
	private Float costoGuachera;
	
	private FacesMessage facesMsg;

	@PostConstruct
	public void init() {
		fechaInicio = new Date();
		fechaFin = new Date();
	}
	
	public void listarCostosAlimentacionPorTernera() {
		if (fechaInicio.getTime() > System.currentTimeMillis() || fechaFin.getTime() > System.currentTimeMillis())
			facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,null, "Las fechas ingresadas no pueden superar la fecha actual");
		else 		
			costoTernera = persistenciaBean.listarCostosPorTernera(snig, fechaInicio, fechaFin);
	}
	
	public void listarCostosAlimentacionPorGuachera() {
		if (fechaInicio.getTime() > System.currentTimeMillis() || fechaFin.getTime() > System.currentTimeMillis())
			facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,null, "Las fechas ingresadas no pueden superar la fecha actual");
		else
			costoGuachera = persistenciaBean.listarCostosPorGuachera(idGuachera, fechaInicio, fechaFin);
	}

	public Long getSnig() {
		return snig;
	}

	public void setSnig(Long snig) {
		this.snig = snig;
	}

	public Long getIdGuachera() {
		return idGuachera;
	}

	public void setIdGuachera(Long idGuachera) {
		this.idGuachera = idGuachera;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Float getCostoTernera() {
		return costoTernera;
	}

	public void setCostoTernera(Float costoTernera) {
		this.costoTernera = costoTernera;
	}

	public Float getCostoGuachera() {
		return costoGuachera;
	}

	public void setCostoGuachera(Float costoGuachera) {
		this.costoGuachera = costoGuachera;
	}
}
