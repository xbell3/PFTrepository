package com.controlador.persistencia.entidades.alimento;

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
@Table(uniqueConstraints= {
		@UniqueConstraint(name = "UK_ALIMENTO_NOMBRE", columnNames= {"nombre"}),
})
@NamedQueries({
	@NamedQuery(name="Alimento.findAll", query="SELECT a FROM AlimentoTambo a"),
	@NamedQuery(name="Alimento.findxNombre",query="SELECT a FROM AlimentoTambo a WHERE a.nombre=:NOMBRE")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AlimentoTambo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ALIMENTO", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ALIMENTO")
	private Long idalimento;
	
	private String nombre;
	private String marca;
	
	@ManyToOne
	@JoinColumn(name="IDUNIDAD",foreignKey=@ForeignKey(name="FK_ALIMENTO_UNIDAD"))
	private UnidadTambo unidad;
	
	private float stock;
	private float costounidad;
}