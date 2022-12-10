package com.controlador.servicios.terneras;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.modelo.logica.terneras.GestionTerneraService;
import com.vista.terneras.VariacionPesoDTO;


@Named(value="gestionVariaciones")
@SessionScoped
public class GestionVariaciones implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	GestionTerneraService persistenciaBean;
	
	private Long snig;
	private Long idGuachera;
	private Date fechaInicio;
	private Date fechaFin;
	
	private List<VariacionPesoDTO> variacionesTernera;
	private List<VariacionPesoDTO> variacionesGuachera;

	@PostConstruct
	public void init() {
		fechaInicio = new Date();
		fechaFin = new Date();
		variacionesTernera = new ArrayList<VariacionPesoDTO>();
		variacionesGuachera =  new ArrayList<VariacionPesoDTO>();
	}
	
	public void listarVariacionPesoPorTernera() {
		variacionesTernera = persistenciaBean.listarVariacionPesoPorTernera(snig, fechaInicio, fechaFin);
	}
	
	public void listarVariacionPesoPorGuachera() {
		variacionesGuachera= persistenciaBean.listarVariacionPesoPorGuachera(idGuachera, fechaInicio, fechaFin);
	}
	
	public BufferedImage generarQR(String info){
		return null;
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

	public List<VariacionPesoDTO> getVariacionesTernera() {
		return variacionesTernera;
	}

	public void setVariacionesTernera(List<VariacionPesoDTO> variacionesTernera) {
		this.variacionesTernera = variacionesTernera;
	}

	public List<VariacionPesoDTO> getVariacionesGuachera() {
		return variacionesGuachera;
	}

	public void setVariacionesGuachera(List<VariacionPesoDTO> variacionesGuachera) {
		this.variacionesGuachera = variacionesGuachera;
	}
}
