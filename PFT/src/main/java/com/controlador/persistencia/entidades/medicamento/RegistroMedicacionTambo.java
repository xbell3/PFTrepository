package com.controlador.persistencia.entidades.medicamento;

import java.io.Serializable;
import java.util.Date;

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

import com.controlador.persistencia.entidades.ternera.TerneraTambo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "RegistroMedicacion.findAll", query = "SELECT R FROM RegistroMedicacionTambo R"),
	@NamedQuery(name = "RegistroMedicacion.findFiltrar", query = "SELECT R FROM RegistroMedicacionTambo R where r.ternera=:IDTERNERA and r.medicamento=:IDMEDICAMENTO and r.fecRegistro=:FECREGISTRO") })

@Table(name = "REGISTRO_MEDICACION", uniqueConstraints = {
@UniqueConstraint(name = "UK_REGISTRO_MEDICACION", columnNames = { "IDTERNERA", "IDMEDICAMENTO", "fecRegistro" }) })

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroMedicacionTambo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_REGISTRO_MEDICACION", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REGISTRO_MEDICACION")
	private Long idRegistroMedicacion;

	@ManyToOne
	@JoinColumn(name = "IDTERNERA", foreignKey = @ForeignKey(name = "FK_REGISTRO_MEDICACION_TERNERA"))
	private TerneraTambo ternera;

	@ManyToOne
	@JoinColumn(name = "IDMEDICAMENTO", foreignKey = @ForeignKey(name = "FK_REGISTRO_MEDICACION_MEDICAMENTO"))
	private MedicamentoTambo medicamento;

	private Date fecRegistro;

	private Integer dosisAdministrada;
}
