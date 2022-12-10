package com.modelo.logica.alimentos;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.controlador.persistencia.entidades.alimento.AlimentoTambo;
import com.controlador.persistencia.entidades.alimento.UnidadTambo;
import com.controlador.persistencia.servicios.alimento.AlimentoBean;
import com.modelo.logica.customException.AlimentoException;
import com.vista.alimentos.AlimentoDTO;
import com.vista.alimentos.UnidadDTO;

@LocalBean
@Stateless
public class GestionAlimentoService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	private AlimentoBean alimentoDAO;
	
	protected GestionAlimentoService() {
		super();
	}

	public AlimentoDTO fromAlimentoTambo (AlimentoTambo alimentoTambo) {
		return AlimentoDTO.builder()
				.idAlimento(alimentoTambo.getIdalimento())
				.nombre(alimentoTambo.getNombre())
				.marca(alimentoTambo.getMarca())
				.unidad(fromUnidadTambo(alimentoTambo.getUnidad()))
				.stock(alimentoTambo.getStock())
				.costoUnidad(alimentoTambo.getCostounidad())
				.build();		
	}
	
	public AlimentoTambo toAlimentoTambo(AlimentoDTO alimentoDTO) {
		return AlimentoTambo.builder()
				.idalimento(alimentoDTO.getIdAlimento())
				.nombre(alimentoDTO.getNombre())
				.marca(alimentoDTO.getMarca())
				.unidad(toUnidadTambo(alimentoDTO.getUnidad()))
				.stock(alimentoDTO.getStock())
				.costounidad(alimentoDTO.getCostoUnidad())
				.build();
		}
	
	private UnidadTambo toUnidadTambo(UnidadDTO unidadDTO) {
		return UnidadTambo.builder()
				.idunidad(unidadDTO.getIdUnidad())
				.nombre(unidadDTO.getNombre())
				.descripcion(unidadDTO.getDescripcion())
				.build();
	}

	private UnidadDTO fromUnidadTambo(UnidadTambo unidadTambo) {
		return UnidadDTO.builder()
				.idUnidad(unidadTambo.getIdunidad())
				.nombre(unidadTambo.getNombre())
				.descripcion(unidadTambo.getDescripcion())
				.build();
	}

	public AlimentoDTO listarxNombre(String alimento) throws AlimentoException {
		try {
			AlimentoDTO alimentoDTO = fromAlimentoTambo(alimentoDAO.listarxNombre(alimento));
			if (alimentoDTO != null)
				return alimentoDTO;
			else
				throw new AlimentoException("LA001", AlimentoException.ALIMENTO_INEXISTENTE);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> listarAlimentos() {
		return alimentoDAO.listar().stream().map(alimento -> fromAlimentoTambo(alimento).getNombre()).collect(Collectors.toList());
	}
}
