package com.controlador.servicios.enfermedades;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

import com.controlador.servicios.ConstructorFecha;
import com.modelo.logica.enfermedades.GestionEnfermedadService;
import com.vista.enfermedades.EnfermedadHistoricaDTO;

@Named(value="gestionEnfermedad")
@SessionScoped
@MultipartConfig
public class GestionEnfermedad implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	GestionEnfermedadService persistenciaBean;
	
	private FacesMessage facesMsg;
	
	Part archivoSubido;
	
	@PostConstruct
	public void init(){
	}
	
	public void importar(){
		try {
			String nombre=getFileName(archivoSubido);
			archivoSubido.write("C:\\data\\"+getFileName(archivoSubido));
			persistenciaBean.importarDatos();
			 facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,"hola", "chau");
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		
		
	}
	
	public List<EnfermedadHistoricaDTO> listarHistoricos(){
		try {
			return persistenciaBean.ListarEnfermedadesHistorico();
		} catch (Exception e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			e.printStackTrace();
		}
		return null;
	}
	
	private static String getFileName(Part part) {
		for(String cd: part.getHeader("content-disposition").split(";")) {
			if(cd.trim().startsWith("filename")) {
				String filename= cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/')+1 ).substring(filename.lastIndexOf('\\')+1);
			}
		}
		return null;
	}
	
	public String fechaString(Date fecha) {
		return ConstructorFecha.dateToString(fecha);
	}

	public GestionEnfermedadService getPersistenciaBean() {
		return persistenciaBean;
	}

	public void setPersistenciaBean(GestionEnfermedadService persistenciaBean) {
		this.persistenciaBean = persistenciaBean;
	}

	public Part getArchivoSubido() {
		return archivoSubido;
	}

	public void setArchivoSubido(Part archivoSubido) {
		this.archivoSubido = archivoSubido;
	}
	
	

}


