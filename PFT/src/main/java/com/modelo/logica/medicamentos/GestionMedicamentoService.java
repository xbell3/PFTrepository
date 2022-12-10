package com.modelo.logica.medicamentos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.controlador.persistencia.entidades.medicamento.MedicamentoTambo;
import com.controlador.persistencia.entidades.medicamento.TipoMedicamentoTambo;
import com.controlador.persistencia.servicios.ServiciosException;
import com.controlador.persistencia.servicios.medicamento.MedicamentoBean;
import com.controlador.persistencia.servicios.medicamento.RegistroMedicacionBean;
import com.controlador.persistencia.servicios.medicamento.TipoMedicamentoBean;
import com.controlador.servicios.ClientResponse;
import com.modelo.logica.customException.MedicamentoException;
import com.vista.medicamentos.MedicamentoDTO;
import com.vista.medicamentos.TipoMedicamentoDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@Stateless
@LocalBean
public class GestionMedicamentoService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	MedicamentoBean medicamentoDAO;
	
	@EJB
	TipoMedicamentoBean tipoMedicamentoDAO;
	
	@EJB 
	MedicamentoValidator medicamentoValidator;
	
	@EJB
	RegistroMedicacionBean registroMedicacionDAO;
	
	public GestionMedicamentoService() {
		
	}
	
	public MedicamentoDTO fromMedicamentoTambo(MedicamentoTambo medicamentoTambo) {
		return MedicamentoDTO.builder()
				.idMedicamento(medicamentoTambo.getIdmedicamento())
				.producto(medicamentoTambo.getProducto())
				.costo(medicamentoTambo.getCosto())
				.stock(medicamentoTambo.getStock())
				.estado(medicamentoTambo.getEstado())
				.tipoMedicamento(fromTipoMedicamentoTambo(medicamentoTambo.getTipoMedicamento()))
				.build();
	}
	
	public MedicamentoTambo toMedicamentoTambo(MedicamentoDTO medicamentoDTO) {
		return MedicamentoTambo.builder()
				.idmedicamento(medicamentoDTO.getIdMedicamento())
				.producto(medicamentoDTO.getProducto())
				.costo(medicamentoDTO.getCosto())
				.stock(medicamentoDTO.getStock())
				.tipoMedicamento(toTipoMedicamentoTambo(medicamentoDTO.getTipoMedicamento()))
				.build();
	}
	
	public TipoMedicamentoDTO fromTipoMedicamentoTambo(TipoMedicamentoTambo tpm) {
		return TipoMedicamentoDTO.builder()
				.idTipoMedicamento(tpm.getIdtipo())
				.tipo(tpm.getTipo())
				.build();
	}
	
	public TipoMedicamentoTambo toTipoMedicamentoTambo(TipoMedicamentoDTO tipoMedicamentoDTO) {
		return TipoMedicamentoTambo.builder()
		.idtipo(tipoMedicamentoDTO.getIdTipoMedicamento())
		.tipo(tipoMedicamentoDTO.getTipo())
		.build();
	}
	
	public List<MedicamentoDTO> listarMedicamentos(){
		List<MedicamentoTambo> medicamentosTambo=medicamentoDAO.listar();
		List<MedicamentoDTO> listaMedicamentos=new ArrayList<MedicamentoDTO>();
		for(MedicamentoTambo med:medicamentosTambo) {
			listaMedicamentos.add(fromMedicamentoTambo(med));
		}
		return listaMedicamentos;
	}
	
	public List<TipoMedicamentoDTO> listarTipoMedicamentos(){
		List<TipoMedicamentoTambo> TipoMedicamentoTambo=tipoMedicamentoDAO.listar();
		List<TipoMedicamentoDTO> listaTipoMedicamentosDTO=new ArrayList<TipoMedicamentoDTO>();
		for(TipoMedicamentoTambo tmt: TipoMedicamentoTambo) {
			listaTipoMedicamentosDTO.add(fromTipoMedicamentoTambo(tmt));
		}
		return listaTipoMedicamentosDTO;
	}
	
	public MedicamentoDTO listarMedicamentoxId(Long idMedicamento) {
		try {
			MedicamentoTambo mt=medicamentoDAO.listarxId(idMedicamento);
			return fromMedicamentoTambo(mt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ClientResponse altaMedicamento(MedicamentoDTO medicamentoDTO) throws MedicamentoException{
		try {
			if (medicamentoValidator.validateCreation(medicamentoDTO) == HttpResponseCodes.SC_OK)
				if(medicamentoDAO.crear(toMedicamentoTambo(medicamentoDTO), medicamentoDTO.getTipoMedicamento().getIdTipoMedicamento())) 
					return new ClientResponse(HttpResponseCodes.SC_OK, ClientResponse.ALTA_MEDICAMENTO_CORRECTA, "Medicamento dado de alta exitosamente");
				else
					return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "500", "Ha ocurrido un error interno dando de alta el medicamento");
			
		} catch (MedicamentoException me) {
			me.printStackTrace();
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, me.getErrorCode(), me.getErrorMessage());
		}
		return new ClientResponse(0, "", "");
	}
	
	public ClientResponse editarMedicamento(MedicamentoDTO medicamentoDTO, String productoPrevioModificacion ) {
		try {
			if (medicamentoValidator.validateUpdate(medicamentoDTO, productoPrevioModificacion) == HttpResponseCodes.SC_OK)
				if(medicamentoDAO.actualizar(toMedicamentoTambo(medicamentoDTO)))
					return new ClientResponse(HttpResponseCodes.SC_OK, ClientResponse.MODIFICACION_MEDICAMENTO_CORRECTA, "Medicamento actualizado exitosamente");
				else
					return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "500", "Ha ocurrido un error interno actualizando el medicamento");

		} catch (MedicamentoException me) {
			me.printStackTrace();
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, me.getErrorCode(), me.getErrorMessage());
		}
		return new ClientResponse(0, "", "");
	}
	
	public ClientResponse bajaMedicamento(Long idMedicamento) {
		try {
			if (medicamentoValidator.validateDelete(idMedicamento) == HttpResponseCodes.SC_OK)
				if (medicamentoDAO.bajaLogica( medicamentoDAO.listarxId(idMedicamento)))
					return new ClientResponse(HttpResponseCodes.SC_OK, ClientResponse.BAJA_MEDICAMENTO_CORRECTA, "Medicamento dado de baja exitosamente");
				else
					return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "500", "Ha ocurrido un error interno dando de baja el medicamento");

		} catch (MedicamentoException me) {
			me.printStackTrace();
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, me.getErrorCode(), me.getErrorMessage());

		}
		return new ClientResponse(0, "", "");	
		}
	
	public TipoMedicamentoDTO construirTipoMedicamento(String nombreTipoMedicamento) {
		TipoMedicamentoDTO tipoMedicamento=fromTipoMedicamentoTambo(tipoMedicamentoDAO.listarxNombreTipoMedicamento(nombreTipoMedicamento));
		return tipoMedicamento;
	}

	public MedicamentoDTO listarMedicamentoxNombre(String producto) throws MedicamentoException {
		try {
			MedicamentoDTO medicamentoDTO=fromMedicamentoTambo(medicamentoDAO.listarxProducto(producto));
			
			if (medicamentoDTO != null)
				return medicamentoDTO;
			else
				throw new MedicamentoException("LM003", MedicamentoException.PRODUCTO_INEXISTENTE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
