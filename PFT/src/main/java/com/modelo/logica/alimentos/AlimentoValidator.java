package com.modelo.logica.alimentos;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.controlador.persistencia.servicios.alimento.AlimentoBean;
import com.controlador.persistencia.servicios.alimento.RegistroAlimentacionBean;
import com.controlador.persistencia.servicios.ternera.TerneraBean;
import com.modelo.logica.customException.AlimentoException;
import com.modelo.logica.customException.TerneraException;
import com.vista.alimentos.RegistroAlimentacionDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@LocalBean
@Stateless
public class AlimentoValidator {

	@EJB
	private AlimentoBean alimentoDAO;
	
	@EJB
	private TerneraBean terneraDAO;
	
	@EJB
	private RegistroAlimentacionBean registroAlimentacionTambo;
	
	public int validateAlimentacion(RegistroAlimentacionDTO registroAlimentacionDTO) throws AlimentoException, TerneraException {
		if (registroAlimentacionDTO.getTerneraDTO() == null)
			throw new TerneraException("RA001", TerneraException.SNIG_INEXISTENTE);
		
		else if(registroAlimentacionDTO.getTerneraDTO().getEstado() == 0)
			throw new TerneraException("RA002", TerneraException.SNIG_ELIMINADO);
		
		else if (alimentoDAO.stockSuficiente(registroAlimentacionDTO.getCantidad(), registroAlimentacionDTO.getAlimentoDTO().getNombre()))
			throw new AlimentoException("RA003", AlimentoException.STOCK_INSUFICIENTE);
		else
			return HttpResponseCodes.SC_OK;
	}

}
