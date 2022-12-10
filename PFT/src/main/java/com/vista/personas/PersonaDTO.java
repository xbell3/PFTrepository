package com.vista.personas;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {

	@NotNull
	private Long idpersona;

	@NotNull(message="No puede haber campos obligatorios vacios")
	@Length(min=3, max=30, message="El nombre debe tener de 3 a 30 digitos")
	private String nombre;
	
	@NotNull(message="No puede haber campos obligatorios vacios")
	@Length(min=3, max=30, message="El apellido debe tener de 3 a 30 digitos")
	private String apellido;
	
	@NotNull(message="No puede haber campos obligatorios vacios")
	private Integer cedula;

	@NotNull(message="No puede haber campos obligatorios vacios")
	@Length(min=3, max=40, message="El nombre de usuario debe tener de 3 a 40 digitos")
	private String nombreUsuario;
	
	@NotNull
	@Length(min=3,	max=40, message="La contraseña debe tener de 3 a 40 digitos")
	private String contrasenia;

	private Integer idRol;
	private String nombreRol;
			
	@NotNull
	private int estado;
}
