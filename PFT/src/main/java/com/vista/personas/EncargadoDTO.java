package com.vista.personas;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EncargadoDTO extends PersonaDTO {

	@Length(min = 5, max = 255, message = "El titulo debe contener entre 5 y 255 caracteres")
	private String titulo;

	public EncargadoDTO(@NotNull Long idpersona, @NotNull String nombre, @NotNull String apellido,
			@NotNull Integer cedula, String nombreUsuario, @NotNull String contrasenia, Integer idRol, String nombreRol,
			@NotNull int estado, String titulo) {
		super(idpersona, nombre, apellido, cedula, nombreUsuario, contrasenia, idRol, nombreRol, estado);
		this.titulo = titulo;
	}
}
