package com.vista.personas;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PersonalDTO extends PersonaDTO {
	
	@Max(value = 999999999, message = "La cantidad máxmia de horas es de 9 digitos")
	@Min(value = 0, message = "La cantidad minima de horas es 0")
	private Integer cantHoras;
	
	public PersonalDTO(@NotNull Long idpersona, @NotNull String nombre, @NotNull String apellido,
			@NotNull Integer cedula, String nombreUsuario, @NotNull String contrasenia, Integer idRol, String nombreRol,
			@NotNull int estado, Integer cantHoras) {
		super(idpersona, nombre, apellido, cedula, nombreUsuario, contrasenia, idRol, nombreRol,estado);
		this.cantHoras = cantHoras;
	}
	
	public Integer getCantHoras() {
		return cantHoras;
	}

	public void setCantHoras(Integer cantHoras) {
		this.cantHoras = cantHoras;
	}
}
