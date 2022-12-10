package com.modelo.logica.medicamentos;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.controlador.persistencia.entidades.medicamento.RegistroMedicacionTambo;
import com.controlador.persistencia.servicios.medicamento.RegistroMedicacionBean;
import com.controlador.servicios.ClientResponse;
import com.modelo.logica.customException.MedicamentoException;
import com.modelo.logica.customException.TerneraException;
import com.modelo.logica.terneras.GestionTerneraService;
import com.vista.medicamentos.MedicamentoDTO;
import com.vista.medicamentos.RegistroMedicacionDTO;
import com.vista.terneras.TerneraDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@Stateless
@LocalBean
public class GestionMedicacionService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	GestionMedicamentoService gestionMedicamentoService;
	
	@EJB 
	GestionTerneraService gestionTerneraService;
	
	@EJB
	RegistroMedicacionBean registroMedicacionDAO;
	
	@EJB 
	MedicamentoValidator medicamentoValidator;
	
	public RegistroMedicacionDTO fromRegistroMedicacionTambo(RegistroMedicacionTambo regMedTambo) {
		return RegistroMedicacionDTO.builder()
				.idRegistroMedicacion(regMedTambo.getIdRegistroMedicacion())
				.terneraDTO(gestionTerneraService.fromTerneraTambo(regMedTambo.getTernera()))
				.medicamentoDTO(gestionMedicamentoService.fromMedicamentoTambo(regMedTambo.getMedicamento()))
				.fecMedicacion(regMedTambo.getFecRegistro())
				.dosisAdministrada(regMedTambo.getDosisAdministrada())
				.build();
		
	}
	
	public RegistroMedicacionTambo toRegistroMedicacionTambo (RegistroMedicacionDTO regMedDTO) {
		return RegistroMedicacionTambo.builder()
				.ternera(gestionTerneraService.toTerneraTambo(regMedDTO.getTerneraDTO()))
				.medicamento(gestionMedicamentoService.toMedicamentoTambo(regMedDTO.getMedicamentoDTO()))
				.fecRegistro(regMedDTO.getFecMedicacion())
				.dosisAdministrada(regMedDTO.getDosisAdministrada())
				.build();	
	}
	
	public ClientResponse registrarMedicacion(RegistroMedicacionDTO registroMedicacionDTO) throws MedicamentoException, TerneraException {
		try {
			if (medicamentoValidator.validateMedicacion(registroMedicacionDTO) == HttpResponseCodes.SC_OK)
				if (registroMedicacionDAO.registrarMedicacion(toRegistroMedicacionTambo(registroMedicacionDTO)))
					return new ClientResponse(HttpResponseCodes.SC_OK, ClientResponse.REGISTRO_MEDICACION_CORRECTA, "Medicacion registrada correctamente");
				else
					return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "500", "Ha ocurrido un error interno dando de alta el medicamento");
			else
				return new ClientResponse(0, "", "");
		} catch (MedicamentoException me) {
			me.printStackTrace();
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, me.getErrorCode(), me.getErrorMessage());
		}
		catch (TerneraException te) {
			te.printStackTrace();
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, te.getErrorCode(), te.getErrorMessage());
		}
	}

	public TerneraDTO createTerneraDTO(int snig) throws TerneraException{
		try {
			return gestionTerneraService.listarTerneraxSNIG(snig);
		} catch (TerneraException te) {
			te.printStackTrace();
		}
		return null;
	}

	public MedicamentoDTO createMedicamentoDTO(String producto) throws MedicamentoException{
		try {
			return gestionMedicamentoService.listarMedicamentoxNombre(producto);
		} catch (MedicamentoException me) {
			me.printStackTrace();
		}
		return null;
	}
	
}
