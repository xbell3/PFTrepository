package com.controlador.persistencia.entidades.permisos;
import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
	@NamedQuery(name="Funcionalidad.findAll", query="SELECT f FROM Funcionalidad f"),
	@NamedQuery(name="Funcionalidad.devolverxNombre",query="SELECT f FROM Funcionalidad f WHERE f.nombre LIKE :NOMBRE")
})

public class Funcionalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_FUNCIONALIDAD", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_FUNCIONALIDAD")
	private int idfuncionalidad;

	private String nombre;
	
	private String descripcion;


	@ManyToMany(mappedBy="funcionalidades")
	private List<Rol> roles;

	public Funcionalidad() {
	}

	public int getIdfuncionalidad() {
		return idfuncionalidad;
	}

	public void setIdfuncionalidad(int idfuncionalidad) {
		this.idfuncionalidad = idfuncionalidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	@Override
	public String toString() {
		return "Funcionalidad [idfuncionalidad=" + idfuncionalidad + ", nombre=" + nombre + ", descripcion="
				+ descripcion;
	}
	
	


	

}