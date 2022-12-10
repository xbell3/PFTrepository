package com.controlador.persistencia.entidades.ternera;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NamedQueries({
	@NamedQuery(name="Raza.findAll", query="SELECT r FROM RazaTambo r"),
	@NamedQuery(name="Raza.obtenerxRaza", query="SELECT r FROM RazaTambo r where r.raza LIKE :raza")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RazaTambo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idraza;

	private String raza;

	//bi-directional many-to-one association to Ternera
	@OneToMany(mappedBy="raza")
	private List<TerneraTambo> terneras;

	@Override
	public String toString() {
		return "Raza [idraza=" + idraza + ", raza=" + raza +"]";
	}
}