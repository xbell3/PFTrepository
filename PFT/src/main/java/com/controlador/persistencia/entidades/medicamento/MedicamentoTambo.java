
package com.controlador.persistencia.entidades.medicamento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NamedQueries({
	@NamedQuery(name="Medicamento.findAll", query="SELECT m FROM MedicamentoTambo m where m.estado=1"),
	@NamedQuery(name="Medicamento.devolverxProducto", query="SELECT m FROM MedicamentoTambo m where m.producto LIKE :PRODUCTO"),
	@NamedQuery(name="Medicamento.devolverxId", query="SELECT m FROM MedicamentoTambo m where m.idmedicamento LIKE :id")
})
@Table(uniqueConstraints= {
		@UniqueConstraint(name = "UK_MEDICAMENTO_PRODUCTO", columnNames= {"producto"}),
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MedicamentoTambo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_MEDICAMENTO", initialValue = 1, allocationSize = 1  )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_MEDICAMENTO")
	private Long idmedicamento;

	private float costo;

	private Integer stock;

	private int estado;

	private String producto;

	//bi-directional many-to-one association to TipoMedicamento
	@ManyToOne
	@JoinColumn(name="IDTIPO",foreignKey=@ForeignKey(name="FK_MEDICAMENTO_TIPO_MEDICAMENTO"))
	private TipoMedicamentoTambo tipoMedicamento;

	@Override
	public String toString() {
		return "Medicamento [idmedicamento=" + idmedicamento + ", costo=" + costo + ", dosis=" + stock + ", estado="
				+ estado + ", producto=" + producto + ", tipoMedicamento=" + tipoMedicamento + "]";
	}
}