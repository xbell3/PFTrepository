package com.controlador.servicios.enfermedades;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.controlador.servicios.ConstructorFecha;
import com.modelo.logica.enfermedades.GestionEnfermedadService;
import com.vista.enfermedades.EnfermedadDTO;
import com.vista.enfermedades.EnfermedadHistoricaDTO;

@Path("/enfermedad")
public class EnfermedadRest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	GestionEnfermedadService gestionEnfermedadService;
	
	@GET
	@Path("/listarEnfermedades")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EnfermedadDTO> listarEnfermedades(){
		return gestionEnfermedadService.listarEnfermedades();
	}

	@POST
	@Path("/subirArchivo")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_JSON)
	public byte[] subirArchivo(byte[] input) {
		String algo=input.toString();
		return input;
	}
	@POST
	@Path("/cargaHistoricos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<EnfermedadHistoricaDTO> cargaHistoricos(List<EnfermedadHistoricaDTO> enfermedadHistoricas) {
		try {
			gestionEnfermedadService.eliminarDatosHistoricosEnfermedad();
			for(EnfermedadHistoricaDTO efd:enfermedadHistoricas) {
				EnfermedadDTO enfermedad=gestionEnfermedadService.devolverEnfermedadPorNombre(efd.getNombreEnfermedad(),Integer.valueOf(efd.getSeveridad()), efd.getVariante());
				gestionEnfermedadService.cargarDatosHistoricosEnfermedad(Integer.valueOf(enfermedad.getIdEnfermedad().toString()), efd.getIdTernera(),ConstructorFecha.dateToString(efd.getFecDeteccion()));
			}
			return enfermedadHistoricas;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@GET
	@Path("/listarHistoricos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EnfermedadHistoricaDTO> listarHistoricos(){
		try {
			List<EnfermedadHistoricaDTO> listar=gestionEnfermedadService.ListarEnfermedadesHistorico();
			return listar;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@GET
	@Path("/listarHistoricosxNombre/{nombre}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EnfermedadHistoricaDTO> listarHistoricosxNombre(@PathParam("nombre") String nombre){
		try {
			return gestionEnfermedadService.ListarEnfermedadesHistoricoxNombre(nombre);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/listarHistoricosxSnig/{snig}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EnfermedadHistoricaDTO> listarHistoricosxSnig(@PathParam("snig") int snig){
		try {
			return gestionEnfermedadService.ListarEnfermedadesHistoricoxSnig(snig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
