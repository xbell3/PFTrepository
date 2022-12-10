document.addEventListener("DOMContentLoaded", function(e) {
	listarPersonas();
	//document.getElementById("bodyTablaPersona").innerHTML = listarPersonas();
});

function listarPersonas(){
	
	fetch('http://localhost:8080/PFT/rest/personas/devolver', {
				method: 'GET'
			}).then(response =>{
				
				console.log(response.json())
				
				
			})
	
	
	
}