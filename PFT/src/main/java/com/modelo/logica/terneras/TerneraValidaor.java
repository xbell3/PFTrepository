package com.modelo.logica.terneras;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.controlador.persistencia.entidades.ternera.TerneraTambo;
import com.controlador.persistencia.servicios.ternera.TerneraBean;
import com.modelo.logica.customException.TerneraException;
import com.vista.terneras.TerneraDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@LocalBean
@Stateless
public class TerneraValidaor {

	@EJB
	TerneraBean terneraDAO;

	public int validateCreation(TerneraDTO terneraDTO) throws TerneraException{
			TerneraTambo terneraResponse = null;
			List<TerneraTambo> existePorClaves = terneraDAO.existePorClaves(terneraDTO.getSnig(), terneraDTO.getIdLocal());

			terneraResponse = existePorClaves.size() > 0 ? existePorClaves.get(0) : null;

			if (terneraResponse != null) {
				if (terneraResponse.getSnig() == terneraDTO.getSnig())
					throw new TerneraException("AT001", TerneraException.SNIG_EXISTE);
				
				else if (terneraResponse.getIdlocal() == terneraDTO.getIdLocal())
					throw new TerneraException("AT002", TerneraException.IDLOCAL_EXISTE);		
			}
			else 
				return HttpResponseCodes.SC_OK; 
		return 0;
	}

	public int validateDelete(int snig) throws TerneraException {
		TerneraTambo terneraResponse = terneraDAO.ListarxSNIG(snig);
		
		if (terneraResponse == null)
			throw new TerneraException("BT001", TerneraException.SNIG_INEXISTENTE);
		else if (terneraResponse.getEstado() == 0)
			throw new TerneraException("BT002", TerneraException.SNIG_ELIMINADO);
		else
			return HttpResponseCodes.SC_OK;			
	}

	public int validateUpdate(TerneraDTO terneraDTO, int snigPrevio, int idLocalPrevio) throws TerneraException {
		
		if (terneraDTO.getSnig() != snigPrevio) {
			if (terneraDAO.ListarxSNIG(terneraDTO.getSnig()) != null)
				throw new TerneraException("AT001", TerneraException.SNIG_EXISTE);
		}
		if(terneraDTO.getIdLocal() != idLocalPrevio) {
			if(terneraDAO.existeIdLocal(terneraDTO.getIdLocal()))
				throw new TerneraException("AT002", TerneraException.IDLOCAL_EXISTE);
		}
		
		return HttpResponseCodes.SC_OK;
	}

}
