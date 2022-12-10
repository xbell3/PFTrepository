package com.controlador.persistencia.entidades.enfermedad;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@NamedQuery(name="Gravedad.findAll", query="SELECT g FROM GravedadTambo g"),
	@NamedQuery(name="Gravedad.findxId", query="SELECT g FROM GravedadTambo g where g.idGravedad = :ID")
})


@Table(uniqueConstraints= {
		@UniqueConstraint(name = "UK_GRAVEDAD_GRAVEDAD", columnNames= {"gravedad"}),
})
@AllArgsConstructor
@Builder
@Data
public class GravedadTambo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_GRAVEDAD", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GRAVEDAD")
	private int idGravedad;

	private String gravedad;

	public GravedadTambo() {
	}
}