package com.controlador.servicios.terneras;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ListenerFor;
import javax.inject.Inject;
import javax.inject.Named;

import com.controlador.servicios.ClientResponse;
import com.controlador.servicios.ConstructorFecha;
import com.modelo.logica.customException.TerneraException;
import com.modelo.logica.terneras.GestionTerneraService;
import com.vista.terneras.RazaDTO;
import com.vista.terneras.TerneraDTO;
import com.vista.terneras.TipoPartoDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@Named(value="gestionTernera")
@SessionScoped
public class GestionTernera implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	GestionTerneraService persistenciaBean;	
	
	private Date fecNac;
	private String nombreRaza;
	private String nombreTipoParto;
	private FacesMessage facesMsg;
	
	@PostConstruct
	public void init() {
		fecNac=new Date();
		nombreRaza="";
		nombreTipoParto="";
	}
	
	public boolean altaTernera(TerneraDTO ternera) {
			try {
				ternera.setRaza(persistenciaBean.construirRaza(nombreRaza));
				ternera.setTipoParto(persistenciaBean.construirTipoParto(nombreTipoParto));
				ternera.setFecNac(fecNac);
				ternera.setEstado(1);
				ClientResponse clientResponse = persistenciaBean.altaTernera(ternera);
				
				if(clientResponse.getStatusCode() == HttpResponseCodes.SC_OK) {
					 facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,clientResponse.getInternalCode(), clientResponse.getMessage());
					 FacesContext.getCurrentInstance().addMessage(null, facesMsg);
					 return true;
				}
				else  {
					 facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,clientResponse.getInternalCode(), clientResponse.getMessage());
					 FacesContext.getCurrentInstance().addMessage(null, facesMsg);
					 return false;
				}
			} 
			
			catch (Exception e) {
				e.printStackTrace();
			}
			return false;
	}
	
	public void borrarTernera(int snig) {
		try {
			ClientResponse clientResponse = persistenciaBean.bajaTernera(snig);
			if(clientResponse.getStatusCode() == HttpResponseCodes.SC_OK) {
				 facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,clientResponse.getInternalCode(), clientResponse.getMessage());
				 FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}
			else  {
				 facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,clientResponse.getInternalCode(), clientResponse.getMessage());
				 FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<TerneraDTO> listarTerneras(){
		try {
			return persistenciaBean.listarTerneras();
		} catch (Exception e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			e.printStackTrace();
		}
		return null;	
	}
	
	public void actualizarTernera(TerneraDTO ternera) {
		try {
			ternera.setRaza(persistenciaBean.construirRaza(nombreRaza));
			ternera.setTipoParto(persistenciaBean.construirTipoParto(nombreTipoParto));
			ternera.setFecNac(fecNac);
			ClientResponse clientResponse = persistenciaBean.editarTernera(ternera, 
					String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("snigModificacionPrevio")),
					String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idLocalModificacionPrevio")));
		
			if(clientResponse.getStatusCode() == HttpResponseCodes.SC_OK) {
				 facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,clientResponse.getInternalCode(), clientResponse.getMessage());
				 FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}
			else  {
				 facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,clientResponse.getInternalCode(), clientResponse.getMessage());
				 FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String fechaString(Date fecha) {
		return ConstructorFecha.dateToString(fecha);
	}
	
	public String saveTerneraInSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ternera", new TerneraDTO());
		return "AltaTernera.xhtml";
	}
	
	public String listaxhtml() {
		return "ListaTernera.xhtml";
	}
	
	public String bajaXhtml() {
		return "BajaTernera.xhtml";
	}
	public String enviarTerneraModificacion(int SNIG) {
		try {
			TerneraDTO t= persistenciaBean.listarTerneraxSNIG(SNIG);
			this.fecNac=t.getFecNac();
			Map<String,Object> sessionMap= FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("ternera", t);
			sessionMap.put("snigModificacionPrevio", SNIG);
			sessionMap.put("idLocalModificacionPrevio", t.getIdLocal());
			return "ModificacionTernera.xhtml";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String enviarTerneraBaja(int snig) {
		try {
			TerneraDTO ternera= persistenciaBean.listarTerneraxSNIG(snig);

			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("ternera", ternera);
			return "BajaTernera.xhtml";

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	
	public List<String> rellenarCbxRaza() {
		return persistenciaBean.listarRazas().stream().map(RazaDTO::getRaza).collect(Collectors.toList());
	}
	
	public List<String> rellenarCbxTipoParto(){
		return persistenciaBean.listarTipoPartos().stream().map(TipoPartoDTO::getTipoParto).collect(Collectors.toList());
	}

	public Date getFecNac() {
		return fecNac;
	}

	public void setFecNac(Date fecNac) {
		this.fecNac = fecNac;
	}

	public String getNombreRaza() {
		return nombreRaza;
	}

	public void setNombreRaza(String nombreRaza) {
		this.nombreRaza = nombreRaza;
	}

	public String getNombreTipoParto() {
		return nombreTipoParto;
	}

	public void setNombreTipoParto(String nombreTipoParto) {
		this.nombreTipoParto = nombreTipoParto;
	}
}
