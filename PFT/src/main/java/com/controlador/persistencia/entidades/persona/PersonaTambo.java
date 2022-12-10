package com.controlador.persistencia.entidades.persona;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.controlador.persistencia.entidades.permisos.Rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NamedQueries({
    @NamedQuery(name="Persona.findAll", query="SELECT p FROM PersonaTambo p"),
    @NamedQuery(name="Persona.listarActivas", query="SELECT p FROM PersonaTambo p WHERE p.estado=1"),
    @NamedQuery(name="Persona.devolverxIdPersona",query="SELECT p FROM PersonaTambo p WHERE p.idpersona LIKE :IDPERSONA"),
    @NamedQuery(name="Persona.devolverxCedula",query="SELECT p FROM PersonaTambo p WHERE p.cedula LIKE :CEDULA"),
    @NamedQuery(name="Persona.devolverxNombreUsuario",query="SELECT p FROM PersonaTambo p WHERE p.nombreusuario LIKE :NOMBREUSUARIO AND p.estado=1")
})
@Table(uniqueConstraints= {
		@UniqueConstraint(name = "UK_PERSONA_CEDULA", columnNames= {"cedula"}),
		@UniqueConstraint(name="Uk_PERSONA_NOMBREUSUARIO",columnNames= {"nombreusuario"})
})
@Inheritance(strategy=InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class PersonaTambo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_PERSONA", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_PERSONA")
	private Long idpersona;

	private String apellido;
	
	private Integer cedula;

	private String contrasenia;

	private int estado;

	private String nombre;

	private String nombreusuario;
	
		@ManyToOne
		@JoinColumn(name="IDROL",foreignKey=@ForeignKey(name="FK_PERSONA_ROL"))
		private Rol rol;

		
	@Override
	public String toString() {
		return "PersonaTambo [idpersona=" + idpersona + ", apellido=" + apellido + ", cedula=" + cedula
				+ ", contrasenia=" + contrasenia + ", estado=" + estado + ", nombre=" + nombre + ", nombreusuario="
				+ nombreusuario + ", rol=" + rol + "]";
	}


	protected PersonaTambo(String apellido, Integer cedula, String contrasenia, int estado,
			String nombre, String nombreusuario, Rol rol) {
		super();
		this.idpersona = idpersona;
		this.apellido = apellido;
		this.cedula = cedula;
		this.contrasenia = contrasenia;
		this.estado = estado;
		this.nombre = nombre;
		this.nombreusuario = nombreusuario;
		this.rol = rol;
	}
}
