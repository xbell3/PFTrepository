package com.modelo.logica.terneras;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import com.controlador.persistencia.entidades.ternera.RazaTambo;
import com.controlador.persistencia.entidades.ternera.TerneraTambo;
import com.controlador.persistencia.entidades.ternera.TipoPartoTambo;
import com.controlador.persistencia.servicios.ternera.RazaBean;
import com.controlador.persistencia.servicios.ternera.TerneraBean;
import com.controlador.persistencia.servicios.ternera.TipoPartoBean;
import com.controlador.servicios.ClientResponse;
import com.controlador.servicios.ConstructorFecha;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.modelo.logica.customException.TerneraException;
import com.vista.terneras.RazaDTO;
import com.vista.terneras.TerneraDTO;
import com.vista.terneras.TipoPartoDTO;
import com.vista.terneras.VariacionPesoDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@Stateless
@LocalBean
public class GestionTerneraService implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	TerneraBean terneraDAO;
	
	@EJB
	RazaBean razaDAO;
	
	@EJB
	TipoPartoBean tipoPartoDAO;

	@EJB
	TerneraValidaor terneraValidator;
	
	public GestionTerneraService() {
		
	}
	
	public TerneraDTO fromTerneraTambo(TerneraTambo terneraTambo) {
		return TerneraDTO.builder()
				.idTernera(terneraTambo.getIdternera())
				.idLocal(terneraTambo.getIdlocal())
				.snig(terneraTambo.getSnig())
				.snigMadre(terneraTambo.getSnigmadre())
				.snigPadre(terneraTambo.getSnigpadre())
				.fecNac(terneraTambo.getFecnac())
				.pesoNac(terneraTambo.getPesonac())
				.raza(fromRazaTambo(terneraTambo.getRaza()))
				.tipoParto(fromTipoPartoTambo(terneraTambo.getTipoParto()))
				.estado(1)
				.build();
	}
	
	public TerneraTambo toTerneraTambo(TerneraDTO terneraDTO) {
		return TerneraTambo.builder()
				.idternera(terneraDTO.getIdTernera())
				.idlocal(terneraDTO.getIdLocal())
				.snig(terneraDTO.getSnig())
				.snigmadre(terneraDTO.getSnigMadre())
				.snigpadre(terneraDTO.getSnigPadre())
				.fecnac(terneraDTO.getFecNac())
				.pesonac(terneraDTO.getPesoNac())
				.raza(toRazaTambo(terneraDTO.getRaza()))
				.tipoParto(toTipoPartoTambo(terneraDTO.getTipoParto()))
				.estado(1)
				.build();
	}
	
	public RazaDTO fromRazaTambo(RazaTambo razaTambo) {
		return RazaDTO.builder()
				.idRaza(razaTambo.getIdraza())
				.raza(razaTambo.getRaza())
				.build();
	}
	
	public RazaTambo toRazaTambo(RazaDTO razaDTO) {
		return RazaTambo.builder()
				.idraza(razaDTO.getIdRaza())
				.raza(razaDTO.getRaza())
				.build();
	}
	
	public RazaDTO construirRaza(String nombreRaza) {
		RazaDTO raza= fromRazaTambo(razaDAO.filtrarRaza(nombreRaza));
		return raza;
	}
	
	public TipoPartoDTO fromTipoPartoTambo(TipoPartoTambo tipoPartoTambo) {
		return TipoPartoDTO.builder()
				.idParto(tipoPartoTambo.getIdparto())
				.tipoParto(tipoPartoTambo.getTipoparto())
				.build();
	}
	
	public TipoPartoTambo toTipoPartoTambo(TipoPartoDTO tipoPartoDTO) {
		return TipoPartoTambo.builder()
				.idparto(tipoPartoDTO.getIdParto())
				.tipoparto(tipoPartoDTO.getTipoParto())
				.build();
	}
	
	public TipoPartoDTO construirTipoParto(String TipoPartoNombre) {
		TipoPartoDTO TipoParto= fromTipoPartoTambo(tipoPartoDAO.filtrarTipoParto(TipoPartoNombre));
		return TipoParto;
	}
	
	public List<TerneraDTO> listarTerneras(){
		return terneraDAO.listarxActivas().stream().map(ternera -> fromTerneraTambo(ternera)).collect(Collectors.toList());
	}
	
	public List<RazaDTO> listarRazas(){
		return razaDAO.listar().stream().map(raza -> fromRazaTambo(raza)).collect(Collectors.toList());
	}
	
	public List<TipoPartoDTO> listarTipoPartos(){
		return tipoPartoDAO.listar().stream().map(tipoParto -> fromTipoPartoTambo(tipoParto)).collect(Collectors.toList());
	}

	public ClientResponse altaTernera(TerneraDTO terneraDTO) throws TerneraException{
		try {
			if (terneraValidator.validateCreation(terneraDTO) == HttpResponseCodes.SC_OK)
				if(terneraDAO.crear(toTerneraTambo(terneraDTO), terneraDTO.getRaza().getIdRaza(), terneraDTO.getTipoParto().getIdParto())) 
					return new ClientResponse(HttpResponseCodes.SC_OK, ClientResponse.ALTA_TERNERA_CORRECTA, "Ternera dada de alta exitosamente");
				else
					return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "500", "Ha ocurrido un error al registrar la ternera");
			else
				return new ClientResponse(0, "", "");
		} catch (TerneraException te) {
			te.printStackTrace();
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, te.getErrorCode(), te.getErrorMessage());
		}
	}
	
	public ClientResponse bajaTernera(int snig)  throws TerneraException{
		try {
			if (terneraValidator.validateDelete(snig) == HttpResponseCodes.SC_OK)
				if (terneraDAO.bajaLogica(terneraDAO.ListarxSNIG(snig)))
					return new ClientResponse(HttpResponseCodes.SC_OK, ClientResponse.BAJA_TERNERA_CORRECTA, "Ternera eliminada exitosamente");
				else
					return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "500", "Ha ocurrido un error al eliminar la ternera");

		} catch (TerneraException te) {
			te.printStackTrace();
			return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, te.getErrorCode(), te.getErrorMessage());

		}
		return new ClientResponse(0, "", "");
	}
	
	public ClientResponse editarTernera(TerneraDTO terneraDTO, String snigPrevio, String idLocalPrevio) throws TerneraException {
			try {
				if(terneraValidator.validateUpdate(terneraDTO, Integer.valueOf(snigPrevio), Integer.valueOf(idLocalPrevio)) == HttpResponseCodes.SC_OK)
					if (terneraDAO.actualizar(toTerneraTambo(terneraDTO), terneraDTO.getRaza().getIdRaza(), terneraDTO.getTipoParto().getIdParto())) 
						return new ClientResponse(HttpResponseCodes.SC_OK, ClientResponse.MODIFICACION_TERNERA_CORRECTA, "Ternera modificada exitosamente");
					else
						return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, "500", "Ha ocurrido un error al actualizar la ternera");
			
			} catch (TerneraException te) {
				te.printStackTrace();
				return new ClientResponse(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR, te.getErrorCode(), te.getErrorMessage());
			}
			return new ClientResponse(0, "", "");
	}
	
	public TerneraDTO listarTerneraxSNIG(int snig) throws TerneraException{
		try {
			TerneraDTO terneraDTO = fromTerneraTambo(terneraDAO.ListarxSNIGActiva(snig));
			if (terneraDTO != null)
				return terneraDTO;
			else
				throw new TerneraException("LM001", TerneraException.SNIG_INEXISTENTE);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public RazaDTO listarRazaxNombre(String nombre) {
		try {
			RazaDTO razaDTO= fromRazaTambo(razaDAO.filtrarRaza(nombre));
			return razaDTO;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public TipoPartoDTO listarTipoPartoxNombre(String nombre) {
		try {
			TipoPartoDTO tipoPartoDTO=fromTipoPartoTambo(tipoPartoDAO.filtrarTipoParto(nombre));
			return tipoPartoDTO;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Float listarCostosPorTernera(Long snigCosto, Date fechaInicioCosto, Date fechaFinCosto) {
		return terneraDAO.listarCostoAlimentacionPorTernera(snigCosto, fechaInicioCosto, fechaFinCosto);
	}

	public Float listarCostosPorGuachera(Long idGuachera, Date fechaInicio, Date fechaFin) {
		return terneraDAO.listarCostoAlimentacionPorGuachera(idGuachera, fechaInicio, fechaFin);
	}

	public List<VariacionPesoDTO> listarVariacionPesoPorTernera(Long snig, Date fechaInicio, Date fechaFin) {
		List<Object[]> registros = terneraDAO.listarVariacionDePesoPorTernera(snig, fechaInicio, fechaFin);
		return fromGenericQuery(registros);
	}
	
	public List<VariacionPesoDTO> listarVariacionPesoPorGuachera(Long idGuachera, Date fechaInicio, Date fechaFin) {
		List<Object[]> registros = terneraDAO.listarVariacionDePesoPorGuachera(idGuachera, fechaInicio, fechaFin);
		return fromGenericQuery(registros);
	}

	private List<VariacionPesoDTO> fromGenericQuery(List<Object[]> registros) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
		List<VariacionPesoDTO> variaciones = new ArrayList<VariacionPesoDTO>();
		int index = 0;
		
			for (Object[] registro : registros) {
				Float peso = ((Number) registro[0]).floatValue();
				Float pesoAnterior = index == 0 ? 0 : ((Number)registros.get(index - 1)[0]).floatValue();
				
				variaciones.add(VariacionPesoDTO.builder()
						.peso(peso)
						.pesoAnterior(pesoAnterior)
						.fecPeso(formatter.format(registro[1]))
						.ganancia(peso - pesoAnterior)
						.build());
				index++;
			}
			return variaciones;
	}


}
