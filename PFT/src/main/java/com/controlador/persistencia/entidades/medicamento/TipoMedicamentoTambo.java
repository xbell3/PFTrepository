package com.controlador.persistencia.entidades.medicamento;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="TIPO_MEDICAMENTO")
@NamedQueries({
	@NamedQuery(name="TipoMedicamento.findAll", query="SELECT t FROM TipoMedicamentoTambo t"),
	@NamedQuery(name="TipoMedicamento.devolverxidTipoMedicamento", query="SELECT t FROM TipoMedicamentoTambo t WHERE t.idtipo LIKE :idtipo"),
	@NamedQuery(name="TipoMedicamento.devolverxNombreTipoMedicamento", query="SELECT t FROM TipoMedicamentoTambo t WHERE t.tipo LIKE :tipo")
})

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TipoMedicamentoTambo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_TIPO_MEDICAMENTO", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TIPO_MEDICAMENTO")
	private Integer idtipo;

	private String tipo;

	//bi-directional many-to-one association to Medicamento
	@OneToMany(mappedBy="tipoMedicamento")
	private List<MedicamentoTambo> medicamentos;

	@Override
	public String toString() {
		return "TipoMedicamento [idtipo=" + idtipo + ", tipo=" + tipo;
	}
}