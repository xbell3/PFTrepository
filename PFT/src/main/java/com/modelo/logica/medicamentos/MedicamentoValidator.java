package com.modelo.logica.medicamentos;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.controlador.persistencia.entidades.medicamento.MedicamentoTambo;
import com.controlador.persistencia.entidades.ternera.TerneraTambo;
import com.controlador.persistencia.servicios.medicamento.MedicamentoBean;
import com.controlador.persistencia.servicios.ternera.TerneraBean;
import com.modelo.logica.customException.MedicamentoException;
import com.modelo.logica.customException.TerneraException;
import com.vista.medicamentos.MedicamentoDTO;
import com.vista.medicamentos.RegistroMedicacionDTO;

import org.jboss.resteasy.util.HttpResponseCodes;

@Stateless
@LocalBean
public class MedicamentoValidator {

	@EJB
	private MedicamentoBean medicamentoDAO;
	@EJB
	private TerneraBean terneraDAO;

	public int validateCreation(MedicamentoDTO medicamentoDTO) throws MedicamentoException {
		if (medicamentoDAO.listarxProducto(medicamentoDTO.getProducto()) != null)
			throw new MedicamentoException("AM001", MedicamentoException.EXISTE_PRODUCTO);
		else
			return HttpResponseCodes.SC_OK;
	}

	public int validateMedicacion(RegistroMedicacionDTO registroMedicacionDTO) throws MedicamentoException, TerneraException {
		if (registroMedicacionDTO.getTerneraDTO() == null)
			throw new TerneraException("RA001", TerneraException.SNIG_INEXISTENTE);
		
		else if (registroMedicacionDTO.getTerneraDTO().getEstado() == 0)
			throw new TerneraException("RA002", TerneraException.SNIG_ELIMINADO);
		
		else if (registroMedicacionDTO.getMedicamentoDTO() == null)
			throw new MedicamentoException("RA003", MedicamentoException.PRODUCTO_INEXISTENTE);
		
		else if (medicamentoDAO.dosisSuficiente(registroMedicacionDTO.getDosisAdministrada(), registroMedicacionDTO.getMedicamentoDTO().getProducto()))
			throw new MedicamentoException("RM004", MedicamentoException.DOSIS_INSUFICIENTE);
		else
			return HttpResponseCodes.SC_OK;
	}

	public int validateDelete(Long idMedicamento) throws MedicamentoException {
		MedicamentoTambo medicamentoResponse = medicamentoDAO.listarxId(idMedicamento);

		if (medicamentoResponse == null)
			throw new MedicamentoException("BM001", MedicamentoException.PRODUCTO_INEXISTENTE);
		else if (medicamentoResponse.getEstado() == 0)
			throw new MedicamentoException("BM002", MedicamentoException.PRODUCTO_DADO_DE_BAJA);
		else
			return HttpResponseCodes.SC_OK;
	}

	public int validateUpdate(MedicamentoDTO medicamentoDTO, String productoPrevioModificacion) throws MedicamentoException {

		if (!medicamentoDTO.getProducto().equals(productoPrevioModificacion)) {
			if (medicamentoDAO.listarxProducto(medicamentoDTO.getProducto()) != null)
				throw new MedicamentoException("MM001", MedicamentoException.EXISTE_PRODUCTO);
		}
		return HttpResponseCodes.SC_OK;
	}
}
