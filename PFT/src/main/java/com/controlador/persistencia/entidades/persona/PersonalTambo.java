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
@PrimaryKeyJoinColumn(referencedColumnName="idpersona",foreignKey=@ForeignKey(name="FK_PERSONAL_PERSONA"))
@NamedQueries({
	@NamedQuery(name="Personal.findAll",query="SELECT p from PersonalTambo p"),
	@NamedQuery(name="Personal.findAllxCedula",query="SELECT e from PersonalTambo e where e.idpersona like :idPersona")
})

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class PersonalTambo extends PersonaTambo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int cantHoras;
	
	@Override
	public String toString() {
		return super.toString() + "Personal [cantHoras=" + cantHoras + "]";
	} 
}
