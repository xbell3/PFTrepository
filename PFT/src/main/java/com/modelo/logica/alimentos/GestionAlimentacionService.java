package com.modelo.logica.alimentos;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.controlador.persistencia.entidades.alimento.RegistroAlimentacionTambo;
import com.controlador.persistencia.servicios.alimento.RegistroAlimentacionBean;
import com.controlador.servicios.ClientResponse;
import com.modelo.logica.customException.AlimentoException;
import com.modelo.logica.customException.TerneraException;
import com.modelo.logica.terneras.GestionTerneraService;
import com.vista.alimentos.AlimentoDTO;
import com.vista.alimentos.RegistroAlimentacionDTO;
import com.vista.terneras.TerneraDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@Stateless
@LocalBean
public class GestionAlimentacionService implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private GestionTerneraService gestionTerneraService;

	@EJB
	private GestionAlimentoService gestionAlimentoService;
	
	@EJB
	private RegistroAlimentacionBean registroAlimentacionDAO;
	
	@EJB
	private AlimentoValidator alimentoValidator;
	
	protected GestionAlimentacionService() {
		super();
	}

	private RegistroAlimentacionDTO fromRegistroAlimentacionTambo (RegistroAlimentacionTambo regAliTambo) {
		return RegistroAlimentacionDTO.builder()
				.idRegistroAlimentacion(regAliTambo.getIdRegistroAlimentacion())
				.terneraDTO(gestionTerneraService.fromTerneraTambo(regAliTambo.getTernera()))
				.alimentoDTO(gestionAlimentoService.fromAlimentoTambo(regAliTambo.getAlimento()))
				.fecAlimentacion(regAliTambo.getFecAlimentacion())
				.cantidad(regAliTambo.getCantidad())
				.build();	
	}
	
	private RegistroAlimentacionTambo toRegistroAlimentacionTambo (RegistroAlimentacionDTO regAliDTO) {
		return RegistroAlimentacionTambo.builder()
				.idRegistroAlimentacion(regAliDTO.getIdRegistroAlimentacion())
				.ternera(gestionTerneraService.toTerneraTambo(regAliDTO.getTerneraDTO()))
				.alimento(gestionAlimentoService.toAlimentoTambo(regAliDTO.getAlimentoDTO()))
				.fecAlimentacion(regAliDTO.getFecAlimentacion())
				.cantidad(regAliDTO.getCantidad())
				.build();	
	}
	
	public TerneraDTO createTerneraDTO(int snig) throws TerneraException{
		try {
			return gestionTerneraService.listarTerneraxSNIG(snig);
		} catch (TerneraException te) {
			te.printStackTrace();
		}
		return null;
	}

	public AlimentoDTO createAlimentoDTO(String alimento) throws AlimentoException{
		try {
			return gestionAlimentoService.listarxNombre(alimento);
		} catch (AlimentoException ae) {
			ae.printStackTrace();
		}
		return null;
	}

	public ClientResponse registrarAlimentacion(RegistroAlimentacionDTO registroAlimentacionDTO) throws AlimentoException, TerneraException{
		try {
			if (alimentoValidator.validateAlimentacion(registroAlimentacionDTO) == HttpResponseCodes.SC_OK)
				if (registroAlimentacionDAO.registrarAlimentacion(toRegistroAlimentacionTambo(registroAlimentacionDTO)))
					return new ClientResponse(HttpResponseCodes.SC_OK, ClientResponse.REGISTRO_ALIMENTACION_CORRECTA, "Registro realizado correctamente");
				else
					return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "500", "Ha ocurrido un error interno registrando la medicacion");
			else
				return new ClientResponse(0, "", "");
		} catch (AlimentoException ae) {
			ae.printStackTrace();
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, ae.getErrorCode(), ae.getErrorMessage());
		}
		catch (TerneraException te) {
			te.printStackTrace();
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, te.getErrorCode(), te.getErrorMessage());
		}
	}

	public List<String> listarAlimentos() {
		return gestionAlimentoService.listarAlimentos();
	}
}
