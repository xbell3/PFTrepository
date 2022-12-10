package com.controlador.persistencia.entidades.persona;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@PrimaryKeyJoinColumn(referencedColumnName="idpersona",foreignKey=@ForeignKey(name="FK_ENCARGADO_PERSONA"))
@NamedQueries({
	@NamedQuery(name="Encargado.findAll",query="SELECT E from EncargadoTambo e"),
	@NamedQuery(name="Encargado.findAllxCedula",query="SELECT e from EncargadoTambo e where e.idpersona=:idPersona")
})

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class EncargadoTambo extends PersonaTambo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String titulo;
	
	@Override
	public String toString() {
		return super.toString() + "Encargado [titulo=" + titulo + "]";
	}
}