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
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="TIPO_PARTO")
@NamedQueries({
	@NamedQuery(name="TipoParto.findAll", query="SELECT t FROM TipoPartoTambo t"),
	@NamedQuery(name="TipoParto.obtenerxTipoParto" , query="SELECT t FROM TipoPartoTambo t WHERE t.tipoparto LIKE :tipoParto")
})

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TipoPartoTambo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idparto;

	private String tipoparto;

	//bi-directional many-to-one association to Ternera
	@OneToMany(mappedBy="tipoParto")
	private List<TerneraTambo> terneras;

	@Override
	public String toString() {
		return "TipoParto [idparto=" + idparto + ", tipoparto=" + tipoparto +"]";
	}
}