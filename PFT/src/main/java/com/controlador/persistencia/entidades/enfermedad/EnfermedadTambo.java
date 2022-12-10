package com.controlador.persistencia.entidades.enfermedad;

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

@Entity
@NamedQueries({
	@NamedQuery(name="Enfermedad.findAll", query="SELECT e FROM EnfermedadTambo e"),
	@NamedQuery(name="Enfermedad.findxNombre", query="SELECT e FROM EnfermedadTambo e where e.nombre=:NOMBRE and e.gravedad=:IDGRAVEDAD and e.variante=:VARIANTE"),
	@NamedQuery(name="Enfermedad.findxId", query="SELECT e FROM EnfermedadTambo e where e.idenfermedad=:IDENFERMEDAD")
})

@Table(uniqueConstraints= {
		@UniqueConstraint(name = "UK_ENFERMEDAD_NOMBRE", columnNames= {"nombre","gravedad","variante"})
})
@AllArgsConstructor
@Builder
@Data
public class EnfermedadTambo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ENFERMEDAD_IDENFERMEDAD_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ENFERMEDAD_IDENFERMEDAD_GENERATOR")
	private Long idenfermedad;

	private String nombre;
	
	private String descripcion;
	
	private String tratamiento;
	
	private String variante;

	@ManyToOne
	@JoinColumn(name="GRAVEDAD",foreignKey=@ForeignKey(name="FK_ENFERMEDAD_GRAVEDAD"))
	private GravedadTambo gravedad;

	public EnfermedadTambo() {
	}

}