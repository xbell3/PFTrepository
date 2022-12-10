package com.controlador.servicios.alimentos;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.controlador.servicios.ClientResponse;
import com.modelo.logica.alimentos.GestionAlimentacionService;
import com.modelo.logica.customException.AlimentoException;
import com.modelo.logica.customException.TerneraException;
import com.vista.alimentos.AlimentoDTO;
import com.vista.alimentos.RegistroAlimentacionDTO;
import com.vista.medicamentos.RegistroMedicacionDTO;
import com.vista.terneras.TerneraDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@Named(value="gestionAlimentacion")
@SessionScoped
public class GestionAlimentacion implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	GestionAlimentacionService persistenciaBean;
	
	private int snigSeleccionado;
	private String alimentoSeleccionado;
	private boolean fechaAuto;
	private FacesMessage facesMsg;
	
	protected GestionAlimentacion() {
		super();
	}

	@PostConstruct
	public void init() {
		snigSeleccionado=0;
	}
	
	public void registrarAlimentacion(RegistroAlimentacionDTO registroAlimentacionDTO) {
		try {			
			if (registroAlimentacionDTO.getFecAlimentacion().getTime() > System.currentTimeMillis())
				facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,null, "La fecha ingresada no puede superar la fecha actual");
			else {
				registroAlimentacionDTO.setTerneraDTO(createTerneraDTO(snigSeleccionado));
				registroAlimentacionDTO.setAlimentoDTO(createAlimentoDTO(alimentoSeleccionado));
				ClientResponse clientResponse = persistenciaBean.registrarAlimentacion(registroAlimentacionDTO);
				
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
			te.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,te.getErrorCode(), te.getErrorMessage()));
		}
		return null;
	}
	
	private AlimentoDTO createAlimentoDTO(String alimento) {
		try {
			return persistenciaBean.createAlimentoDTO(alimento);
		} catch (AlimentoException ae) {
			ae.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,ae.getErrorCode(), ae.getErrorMessage()));
		}
		return null;
	}
	
	public List<String> rellenarCbxAlimento() {
		return persistenciaBean.listarAlimentos();
	}
	
	public String saveAlimentacionInSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("registroAlimentacion", new RegistroAlimentacionDTO());
		return "RegistrarAlimentacion.xhtml";
	}

	public int getSnigSeleccionado() {
		return snigSeleccionado;
	}

	public void setSnigSeleccionado(int snigSeleccionado) {
		this.snigSeleccionado = snigSeleccionado;
	}

	public String getAlimentoSeleccionado() {
		return alimentoSeleccionado;
	}

	public void setAlimentoSeleccionado(String alimentoSeleccionado) {
		this.alimentoSeleccionado = alimentoSeleccionado;
	}

	public GestionAlimentacionService getPersistenciaBean() {
		return persistenciaBean;
	}

	public void setPersistenciaBean(GestionAlimentacionService persistenciaBean) {
		this.persistenciaBean = persistenciaBean;
	}

	public boolean isFechaAuto() {
		return fechaAuto;
	}

	public void setFechaAuto(boolean fechaAuto) {
		this.fechaAuto = fechaAuto;
	}
	
}
