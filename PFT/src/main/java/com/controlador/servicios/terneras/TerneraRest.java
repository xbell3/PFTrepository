package com.controlador.servicios.terneras;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.controlador.servicios.ClientResponse;
import com.modelo.logica.customException.TerneraException;
import com.modelo.logica.terneras.GestionTerneraService;
import com.vista.terneras.RazaDTO;
import com.vista.terneras.TerneraDTO;
import com.vista.terneras.TipoPartoDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@Path("/ternera")
public class TerneraRest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	GestionTerneraService gestionTerneraService;
	
	@GET
	@Path("/listarTerneras")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TerneraDTO> listarTerneras(){
		return gestionTerneraService.listarTerneras();
	}
	
	@POST
	@Path("/altaTernera/{nombreRaza}-{nombreTipoParto}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TerneraDTO altaTernera(TerneraDTO ternera,@PathParam("nombreRaza") String nombreRaza,@PathParam("nombreTipoParto") String nombreTipoParto) {
		try {
			ternera.setRaza(gestionTerneraService.construirRaza(nombreRaza));
			ternera.setTipoParto(gestionTerneraService.construirTipoParto(nombreTipoParto));
			ClientResponse clientResponse = gestionTerneraService.altaTernera(ternera);
			if(clientResponse.getStatusCode() == HttpResponseCodes.SC_OK) {
				return ternera;
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@GET
	@Path("/filtrarTernera/{SNIG}")
	@Produces(MediaType.APPLICATION_JSON)
	public TerneraDTO filtrarTernera(@PathParam("SNIG") int snig) throws TerneraException{
		return gestionTerneraService.listarTerneraxSNIG(snig);
	}
	
	
	@GET
	@Path("/listarRazas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RazaDTO> listarRazas(){
		return gestionTerneraService.listarRazas();
	}
	
	@GET
	@Path("/listarTiposParto")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TipoPartoDTO> listarTiposParto(){
		return gestionTerneraService.listarTipoPartos();
	}
	
	@GET
	@Path("/obtenerRaza/{nombreRaza}")
	@Produces(MediaType.APPLICATION_JSON)
	public RazaDTO obtenerRaza(@PathParam("nombreRaza") String nombreRaza) {
		try {
			RazaDTO raza=gestionTerneraService.listarRazaxNombre(nombreRaza);
			return raza;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@GET
	@Path("/obtenerTipoParto/{nombreTipoParto}")
	@Produces(MediaType.APPLICATION_JSON)
	public TipoPartoDTO obtenerTipoParto(@PathParam("nombreTipoParto") String nombreTipoParto) {
		try {
			return gestionTerneraService.listarTipoPartoxNombre(nombreTipoParto);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DELETE
	@Path("/bajaTernera/{SNIG}")
	@Produces(MediaType.APPLICATION_JSON)
	public ClientResponse bajaTernera(@PathParam("SNIG") Integer snig) throws TerneraException {
		try {
			return gestionTerneraService.bajaTernera(snig);
		} 
		catch (TerneraException e) {
			e.printStackTrace();
		}
		return null;
	}
}
