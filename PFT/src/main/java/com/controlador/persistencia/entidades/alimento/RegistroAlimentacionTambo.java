package com.controlador.persistencia.entidades.alimento;

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
import javax.persistence.UniqueConstraint;

import com.controlador.persistencia.entidades.ternera.TerneraTambo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NamedQueries({
	@NamedQuery(name="RegistroAlimentacion.findAll",query="SELECT R FROM RegistroAlimentacionTambo R")
})
@Table(name="REGISTRO_ALIMENTACION",
uniqueConstraints= {@UniqueConstraint(name = "UK_REGISTRO_ALIMENTACION", columnNames= {"IDTERNERA","IDALIMENTO","FECALIMENTACION"})
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegistroAlimentacionTambo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_REGISTRO_ALIMENTACION", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REGISTRO_ALIMENTACION")
	private Long idRegistroAlimentacion;
	
	@ManyToOne
	@JoinColumn(name="IDTERNERA",foreignKey=@ForeignKey(name="FK_REGISTRO_ALIMENTACION_TERNERA"))
	private TerneraTambo ternera;
	
	@ManyToOne
	@JoinColumn(name="IDALIMENTO",foreignKey=@ForeignKey(name="FK_REGISTRO_ALIMENTACION_ALIMENTO"))
	private AlimentoTambo alimento;   
	
	private Date fecAlimentacion;
	private float cantidad;

}
