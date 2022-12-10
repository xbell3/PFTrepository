document.addEventListener("DOMContentLoaded", function(e) {
	const formElem = document.getElementById('formAltaPersona');
	formElem.addEventListener('submit', (evento) => {
		evento.preventDefault()
		const formData = new FormData(formElem);

		if (validateRoleData(formData.get("roles")))
			fetch('http://localhost:8080/PFT/rest/personas/altaPersona', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify({
					"idPersona": "",
					"nombre": formData.get("nombre"),
					"apellido": formData.get("apellido"),
					"cedula": formData.get("cedula"),
					"contrasenia": formData.get("contrasenia"),
					"idRol": formData.get("roles"),
					"titulo": formData.get("titulo"),
					"cantHoras": formData.get("horas"),
					"estado": 1,
					"nombreUsuario": formData.get("nombreUsuario")

				})
			}).then(response => response.json().then(jsonResponse => showResponse(jsonResponse)))
	})
});

function manageRoleFields() {
	let rol = document.getElementById("roles").value
	if (rol == 0) {
		document.getElementById("titulo").disabled = true;
		document.getElementById("horas").disabled = true;
		document.getElementById("titulo").value = "";
		document.getElementById("horas").value = "";
	}
	else if (rol == 1) {
		document.getElementById("titulo").disabled = false;
		document.getElementById("horas").disabled = true;
		document.getElementById("horas").value = "";
	}
	else if (rol == 2) {
		document.getElementById("titulo").disabled = true;
		document.getElementById("titulo").value = "";
		document.getElementById("horas").disabled = false;
	}
}

function validateRoleData(rol) {
	let titulo = document.getElementById("titulo").value;
	let horas = document.getElementById("horas").value;

	if (rol == 1) {
		if (titulo.length == 0){
			window.alert("El campo titulo es obligatorio");
			return false;
		}
	}
	else if (rol == 2) {
		if (horas.length == 0 || horas < 0){
			window.alert("Debe ingresar una cantidad de horas valida");
			return false;
		}
	}
	return true;
}

function showResponse(response){
	window.alert("-----> Código de respuesta: " + response.internalCode + "\n\n" + "-----> " + response.message)
}

