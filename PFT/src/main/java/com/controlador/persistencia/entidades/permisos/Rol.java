

package com.controlador.persistencia.entidades.permisos;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;



@Entity
@NamedQueries({
	@NamedQuery(name="Rol.findAll", query="SELECT r FROM Rol r"),
	@NamedQuery(name="Rol.devolverxNombre", query="SELECT r FROM Rol r WHERE r.nombre LIKE :NOMBRE"),
	@NamedQuery(name="Rol.devolverxId", query="SELECT r FROM Rol r WHERE r.idrol LIKE :IDROL")
})
@Table(uniqueConstraints= {
		@UniqueConstraint(name = "UK_ROL_NOMBRE", columnNames= {"nombre"}),
})

public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ROL", initialValue = 1, allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ROL")
	private int idrol;

	
	private String nombre;

	
	 @JoinTable(
		        name = "ROL_FUNCIONALIDAD",
		        joinColumns = @JoinColumn(name = "FK_ROLFUNCIONALIDAD_ROL", nullable = false),
		        inverseJoinColumns = @JoinColumn(name="FK_ROLFUNCIONALIDAD_FUNCIONALIDAD", nullable = false)
		    )
	 @ManyToMany(cascade=CascadeType.MERGE)
	private List<Funcionalidad> funcionalidades; 
	
	

	public Rol() {
	}
	
	  public void agregarFuncionalidad(Funcionalidad funcionalidad){
	        if(this.funcionalidades == null){
	            this.funcionalidades = new ArrayList<>();
	        }
	        this.funcionalidades.add(funcionalidad);
	    }


	public Rol(int idrol, String nombre) {
		this.idrol = idrol;
		this.nombre = nombre;
	}


	public int getIdrol() {
		return idrol;
	}

	public void setIdrol(int idrol) {
		this.idrol = idrol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Funcionalidad> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<Funcionalidad> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	@Override
	public String toString() {
		return "Rol [idrol=" + idrol + ", nombre=" + nombre +"]";
	}

	
	
	

}