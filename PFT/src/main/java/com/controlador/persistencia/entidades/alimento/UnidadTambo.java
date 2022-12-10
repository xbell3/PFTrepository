package com.controlador.persistencia.entidades.alimento;

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
import lombok.NoArgsConstructor;

@Entity
@NamedQueries({
	@NamedQuery(name="Unidad.findAll", query="SELECT u FROM UnidadTambo u"),
	@NamedQuery(name="Unidad.findxNombre", query="SELECT u FROM UnidadTambo u where u.nombre=:NOMBRE")
})
@Table(uniqueConstraints= {
		@UniqueConstraint(name = "UK_UNIDAD_NOMBRE", columnNames= {"nombre"}),
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UnidadTambo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_UNIDAD" , initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_UNIDAD")
	private Integer idunidad;

	private String descripcion;
	private String nombre;
}