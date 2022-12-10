package com.controlador.servicios.medicamentos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.controlador.servicios.ClientResponse;
import com.modelo.logica.customException.MedicamentoException;
import com.modelo.logica.customException.TerneraException;
import com.modelo.logica.medicamentos.GestionMedicacionService;
import com.vista.medicamentos.MedicamentoDTO;
import com.vista.medicamentos.RegistroMedicacionDTO;
import com.vista.terneras.TerneraDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@Named(value="gestionMedicacion")
@SessionScoped
public class GestionMedicacion implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	GestionMedicacionService persistenciaBean;
	
	private int snigSeleccionado;
	private String productoSeleccionado;
	
	private FacesMessage facesMsg;
	
	@PostConstruct
	public void init() {
		snigSeleccionado=0;
		productoSeleccionado="";
	}
	
	public void registrarMedicacion(RegistroMedicacionDTO registroMedicacionDTO) {
		try {			
			if (registroMedicacionDTO.getFecMedicacion().getTime() > System.currentTimeMillis())
				facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,null, "La fecha ingresada no puede superar la fecha actual");
			
			else {
				registroMedicacionDTO.setTerneraDTO(createTerneraDTO(snigSeleccionado));
				registroMedicacionDTO.setMedicamentoDTO(createMedicamentoDTO(productoSeleccionado));
				ClientResponse clientResponse = persistenciaBean.registrarMedicacion(registroMedicacionDTO);
				
				if (clientResponse.getStatusCode() == HttpResponseCodes.SC_OK)
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,clientResponse.getInternalCode(), clientResponse.getMessage());
				else
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,clientResponse.getInternalCode(), clientResponse.getMessage());
			}
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String saveMedicacionInSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("registroMedicacion", new RegistroMedicacionDTO());
		return "RegistrarMedicacion.xhtml";
	}
	
	public TerneraDTO createTerneraDTO(int snig) {
		try {
			return persistenciaBean.createTerneraDTO(snig);
		} catch (TerneraException te) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,te.getErrorCode(), te.getErrorMessage()));
		}
		return null;
	}
	
	public MedicamentoDTO createMedicamentoDTO(String producto) {
		try {
			return persistenciaBean.createMedicamentoDTO(producto);
		} catch (MedicamentoException me) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,me.getErrorCode(), me.getErrorMessage()));
		}
		return null;
	}
	

	public GestionMedicacionService getPersistenciaBean() {
		return persistenciaBean;
	}

	public void setPersistenciaBean(GestionMedicacionService persistenciaBean) {
		this.persistenciaBean = persistenciaBean;
	}
	
	public int getSnigSeleccionado() {
		return snigSeleccionado;
	}

	public void setSnigSeleccionado(int snigSeleccionado) {
		this.snigSeleccionado = snigSeleccionado;
	}

	public String getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(String productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

}
