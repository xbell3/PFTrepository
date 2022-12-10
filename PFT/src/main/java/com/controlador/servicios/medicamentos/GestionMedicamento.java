package com.controlador.servicios.medicamentos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.controlador.servicios.ClientResponse;
import com.modelo.logica.medicamentos.GestionMedicamentoService;
import com.vista.medicamentos.MedicamentoDTO;
import com.vista.medicamentos.TipoMedicamentoDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@Named(value="gestionMedicamento")
@SessionScoped
public class GestionMedicamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	GestionMedicamentoService persistenciaBean;
	
	private String nombreTipoMedicamento;
	
	private FacesMessage facesMsg;
	
	@PostConstruct
	public void init() {
		nombreTipoMedicamento="";
	}
	
	public void altaMedicamento(MedicamentoDTO medicamentoDTO) {
		try {
			medicamentoDTO.setTipoMedicamento(persistenciaBean.construirTipoMedicamento(nombreTipoMedicamento));
			medicamentoDTO.setEstado(1);
			ClientResponse clientResponse = persistenciaBean.altaMedicamento(medicamentoDTO);
			
			if (clientResponse.getStatusCode() == HttpResponseCodes.SC_OK)
				facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,clientResponse.getInternalCode(), clientResponse.getMessage());
			else
				facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,clientResponse.getInternalCode(), clientResponse.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<MedicamentoDTO> listarMedicamentos(){
		try {
			return persistenciaBean.listarMedicamentos();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
	
	public void actualizarMedicamento(MedicamentoDTO medicamento) {
		try {
			medicamento.setTipoMedicamento(persistenciaBean.construirTipoMedicamento(nombreTipoMedicamento));
			ClientResponse clientResponse = persistenciaBean.editarMedicamento(medicamento, String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("productoPrevioModificacion")));
			
			if (clientResponse.getStatusCode() == HttpResponseCodes.SC_OK)
				facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,clientResponse.getInternalCode(), clientResponse.getMessage());
			else
				facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,clientResponse.getInternalCode(), clientResponse.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void eliminarMedicamento(Long idMedicamento) {
		ClientResponse clientResponse = persistenciaBean.bajaMedicamento(idMedicamento);
		
		if (clientResponse.getStatusCode() == HttpResponseCodes.SC_OK)
			facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,clientResponse.getInternalCode(), clientResponse.getMessage());
		else
			facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,clientResponse.getInternalCode(), clientResponse.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}
	
	public String saveMedicamentoInSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("medicamento", new MedicamentoDTO());
		return "AltaMedicamento.xhtml";
	}
	
	public String enviarMedicamentoModificacion(Long idMedicamento) {
		try {
			MedicamentoDTO medicamentoDTO=persistenciaBean.listarMedicamentoxId(idMedicamento);
			this.nombreTipoMedicamento=medicamentoDTO.getTipoMedicamento().getTipo();
			
			Map<String,Object> sessionMap= FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("medicamento", medicamentoDTO);
			sessionMap.put("productoPrevioModificacion", medicamentoDTO.getProducto());
			return "ModificacionMedicamento.xhtml";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String enviarMedicamentoBaja(Long idMedicamento) {
		try {
			MedicamentoDTO md=persistenciaBean.listarMedicamentoxId(idMedicamento);
			Map<String,Object> sessionMap= FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("medicamento", md);
			return "BajaMedicamento.xhtml";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public List<String> rellenarCbxTipoMedicamento() {
		List<TipoMedicamentoDTO> tipoMedicamentosDTO=persistenciaBean.listarTipoMedicamentos();
		List<String> tipoNombres=new ArrayList<String>();
		for(TipoMedicamentoDTO tipos: tipoMedicamentosDTO) {
			tipoNombres.add(tipos.getTipo());
		}
		return tipoNombres;
	}
	
	public GestionMedicamentoService getPersistenciaBean() {
		return persistenciaBean;
	}

	public void setPersistenciaBean(GestionMedicamentoService persistenciaBean) {
		this.persistenciaBean = persistenciaBean;
	}

	public String getNombreTipoMedicamento() {
		return nombreTipoMedicamento;
	}

	public void setNombreTipoMedicamento(String nombreTipoMedicamento) {
		this.nombreTipoMedicamento = nombreTipoMedicamento;
	}
	
	

}
