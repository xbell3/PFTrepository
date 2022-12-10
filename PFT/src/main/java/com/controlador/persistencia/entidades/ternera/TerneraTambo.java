package com.controlador.persistencia.entidades.ternera;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NamedQueries({
	@NamedQuery(name="Ternera.findAll", query="SELECT t FROM TerneraTambo t"),
	@NamedQuery(name="Ternera.findxEstado", query="SELECT t FROM TerneraTambo t WHERE t.estado=1"),
	@NamedQuery(name="Ternera.devolverxSNIG",query="SELECT t FROM TerneraTambo t WHERE t.snig LIKE :SNIG"),
	@NamedQuery(name="Ternera.devolverSoloSNIG", query="SELECT t FROM TerneraTambo t WHERE t.snig = :SNIG AND t.estado=1"),
	@NamedQuery(name="Ternera.devolverIdLocal", query="SELECT t FROM TerneraTambo t WHERE t.idlocal LIKE :IDLOCAL"),
	@NamedQuery(name="Ternera.devolverIdTerneraxSNIG",query="SELECT t.idternera FROM TerneraTambo t WHERE t.snig LIKE :SNIG"),
	@NamedQuery(name="Ternera.devolverPorClaves",query="SELECT t FROM TerneraTambo t WHERE t.snig LIKE :SNIG OR t.idlocal LIKE :IDLOCAL")
	
})
@Table(uniqueConstraints= {
		@UniqueConstraint(name = "UK_TERNERA_IDLOCAL", columnNames= {"idlocal"}),
		@UniqueConstraint(name= "UK_TERNERA_SNIG",columnNames= {"snig"})
})

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TerneraTambo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_TERNERA", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TERNERA")
	private Long idternera;

	private int idlocal;
	
	private int snig;

	private int snigmadre;

	private Integer snigpadre;
	
	@Temporal(TemporalType.DATE)
	private Date fecnac;
	
	private int estado;

	private float pesonac;

	//bi-directional many-to-one association to Raza
	@ManyToOne
	@JoinColumn(name="IDRAZA",foreignKey=@ForeignKey(name="FK_TERNERA_RAZA"))
	private RazaTambo raza;

	//bi-directional many-to-one association to TipoParto
	@ManyToOne
	@JoinColumn(name="IDPARTO",foreignKey=@ForeignKey(name="FK_TERNERA_TIPO_PARTO"))
	private TipoPartoTambo tipoParto;

	@Override
	public String toString() {
		return "Ternera [idternera=" + idternera + ", estado=" + estado + ", fecnac=" + fecnac + ", idlocal=" + idlocal
				+ ", pesonac=" + pesonac + ", snig=" + snig + ", snigmadre=" + snigmadre + ", snigpadre=" + snigpadre
				+ ", raza=" + raza + ", tipoParto=" + tipoParto + "]";
	}	
}