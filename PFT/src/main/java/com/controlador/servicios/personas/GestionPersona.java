package com.controlador.servicios.personas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped; //JEE8
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.controlador.servicios.CIValidator;
import com.controlador.servicios.ClientResponse;
import com.ibm.wsdl.util.StringUtils;
import com.modelo.logica.personas.GestionPersonaService;
import com.vista.personas.EncargadoDTO;
import com.vista.personas.PersonaDTO;
import com.vista.personas.PersonalDTO;

import org.jboss.resteasy.util.HttpResponseCodes;
import javax.naming.*;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;

@Named(value = "gestionPersona")
@SessionScoped
public class GestionPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	GestionPersonaService PersistenciaBean;

	private PersonaDTO personaSeleccionada;
	private EncargadoDTO encargadoSeleccionado;
	private PersonalDTO personalSeleccionado;
	private String ciAValidar;
	
	private boolean tituloPermitido = true;
	private boolean cantHorasPermitido = true;
	
	private String nombreUsuarioLogin;
	private String contraseniaLogin;

	private String rol;
	ArrayList<String> funcionalidadesEncargado = new ArrayList<>(
			Arrays.asList("listarUsuarios", "listarTerneras","listarMedicamentos",
							"altaTernera", "altaMedicamento", "bajaTernera", "bajaMedicamento",
							"modificacionTernera", "modificacionMedicamento", "registrarMedicacion", 
							"registrarAlimentacion", "variacionesPeso","listarCostos", "cargarHistoricos", "listarHistoricos"));
	
	ArrayList<String> funcionalidadesPersonal = new ArrayList<>(
			Arrays.asList("registrarMedicacion", "registrarAlimentacion", "listarTerneras", "listarMedicamentos"));

	private FacesMessage facesMsg;
	private ClientResponse clientResponse;
	private CIValidator validator;

	public GestionPersona() {

	}

	@PostConstruct
	public void init() {
		personaSeleccionada = new PersonaDTO();
		encargadoSeleccionado = new EncargadoDTO();
		personalSeleccionado = new PersonalDTO();
		validator = new CIValidator();
	}
	
	public void validarCamposEspecificos (AjaxBehaviorEvent event) {
		try {
			Integer rol = (Integer) ((UIOutput)event.getSource()).getValue();
			
			if (rol == 1) {
				tituloPermitido = false;
				cantHorasPermitido = true;
			}
			else if (rol == 2) {
				cantHorasPermitido = false;
				tituloPermitido = true;
			}
			else {
				tituloPermitido = true;
				cantHorasPermitido = true;
			}
		} catch (Exception e) {
			tituloPermitido = true;
			cantHorasPermitido = true;
			e.printStackTrace();
		}
	}
	
	public void validarTeclas(AjaxBehaviorEvent event) {
		String tecla = (String) ((UIOutput)event.getSource()).getValue();			
	}

	public String salvarCambios() {
		try {
			ClientResponse cr = validator.validateCi(ciAValidar);
			if (cr.getStatusCode() == HttpResponseCodes.SC_OK) {
				personaSeleccionada.setCedula(Integer.valueOf(ciAValidar.replace("-", "").replace(".", "")));
				switch (personaSeleccionada.getIdRol()) {
					case 0:
						clientResponse = PersistenciaBean.agregarPersona(personaSeleccionada);

						if (clientResponse.getStatusCode() == HttpResponseCodes.SC_OK)
							facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, clientResponse.getInternalCode(),
									clientResponse.getMessage());
						else
							facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, clientResponse.getInternalCode(),
									clientResponse.getMessage());
						FacesContext.getCurrentInstance().addMessage(null, facesMsg);
						break;

					case 1:
						// Se relena el encargado
						encargadoSeleccionado.setNombre(personaSeleccionada.getNombre());
						encargadoSeleccionado.setApellido(personaSeleccionada.getApellido());
						encargadoSeleccionado.setCedula(personaSeleccionada.getCedula());
						encargadoSeleccionado.setEstado(personaSeleccionada.getEstado());
						encargadoSeleccionado.setNombreUsuario(personaSeleccionada.getNombreUsuario());
						encargadoSeleccionado.setContrasenia(personaSeleccionada.getContrasenia());
						encargadoSeleccionado.setIdRol(1);
						encargadoSeleccionado.setNombreRol("Encargado");

						clientResponse = PersistenciaBean.agregarEncargadoTambo(encargadoSeleccionado);

						if (clientResponse.getStatusCode() == HttpResponseCodes.SC_OK)
							facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, clientResponse.getInternalCode(),
									clientResponse.getMessage());
						else
							facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, clientResponse.getInternalCode(),
									clientResponse.getMessage());
						FacesContext.getCurrentInstance().addMessage(null, facesMsg);
						break;

					case 2:
						personalSeleccionado.setNombre(personaSeleccionada.getNombre());
						personalSeleccionado.setApellido(personaSeleccionada.getApellido());
						personalSeleccionado.setCedula(personaSeleccionada.getCedula());
						personalSeleccionado.setEstado(personaSeleccionada.getEstado());
						personalSeleccionado.setNombreUsuario(personaSeleccionada.getNombreUsuario());
						personalSeleccionado.setContrasenia(personaSeleccionada.getContrasenia());
						personalSeleccionado.setIdRol(2);
						personalSeleccionado.setNombreRol("Personal");

						clientResponse = PersistenciaBean.agregarPersonalTambo(personalSeleccionado);

						if (clientResponse.getStatusCode() == HttpResponseCodes.SC_OK)
							facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, clientResponse.getInternalCode(),
									clientResponse.getMessage());
						else
							facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, clientResponse.getInternalCode(),
									clientResponse.getMessage());
						FacesContext.getCurrentInstance().addMessage(null, facesMsg);

						break;
				}
				return "";
			}
			else
			{
				facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, cr.getInternalCode(), cr.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return "";

	}

	public String borrarUsuario(int cedula) {

		try {
			PersonaDTO personaNueva = PersistenciaBean.borrarPersona(cedula);
			return "index.xhtml";
		} catch (Exception e) {
			FacesMessage facesMsgp = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La persona que intenta borrar no existe", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsgp);

			e.printStackTrace();
		}

		return "";
	}

	public List<PersonaDTO> listarPersonas() {
		try {
			List<PersonaDTO> personas = PersistenciaBean.listarPersonas();
			return personas;
		} catch (Exception e) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

			e.printStackTrace();
		}
		return null;
	}

	public String enviarPersonaBaja(Long idPersona) {
		try {

			PersonaDTO personaNueva = PersistenciaBean.listarPersonaxId(idPersona);

			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("persona", personaNueva);
			return "BajaPersona.xhtml";

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public String enviarPersonaModificacion(Long idPersona) {
		try {
			PersonaDTO personaNueva = PersistenciaBean.listarPersonaxId(idPersona);
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("persona", personaNueva);
			return "ModificacionPersona.xhtml";

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public String Login() {
		try {
			PersonaDTO personaNueva = PersistenciaBean.loginPersona(nombreUsuarioLogin);

			if (personaNueva == null || personaNueva.getContrasenia().equals(contraseniaLogin) == false) {
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"El usuario o contrase�a son incorrectas.", "");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			} else {
				// contraseña correcta, sigue el flujo
				personaSeleccionada = new PersonaDTO();
				rol = personaNueva.getNombreRol();
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", this);
				Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
				System.out.println(sessionMap.get("user"));
				return "Menu.xhtml";
			}

		} catch (Exception e) {
			// Persona no registrada
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El usuario o contraseña son incorrectas.", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		return "";
	}
	
	
	public String LoginAD(String nombreUsuario, String contrasenia) {
		String url = "ldap://Servidor.PFT2022.utec.edu:3268";
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, nombreUsuario+"@PFT2022.utec.edu");
		env.put(Context.SECURITY_CREDENTIALS, contrasenia);
		try {
			DirContext ctx = new InitialDirContext(env);
			System.out.println("Conectado!!");
			
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			List<String> roles=new ArrayList<String>();
			roles.add("Administrador");
			roles.add("Encargado");
			roles.add("Personal");
			String rol="";
			for(String OU:roles) {
				String searchFilter = "(&(objectClass=person)(samAccountName="+nombreUsuario+"))";
				NamingEnumeration<SearchResult> answer = ctx.search(
						"OU="+OU+",OU=Usuarios,OU=PFT2022,DC=PFT2022,DC=utec,DC=edu", 
						searchFilter, 
						searchControls
						);
				int encontro = 0;
				if(encontro==0) {
			        while (answer.hasMoreElements()) {
			            SearchResult sr = (SearchResult) answer.next();
			            encontro++;
			            rol=OU;
			            Attributes attrs = sr.getAttributes();
			            System.out.println("Nombre de usuario: " + attrs.get("samAccountName"));
			        }
				}
			}
			System.out.println(rol);
			ctx.close();
			return "Menu.xhtml";
			
		} catch (AuthenticationNotSupportedException ex) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El servidor no soporta auth.", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (AuthenticationException ex) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El usuario o contrase�a son incorrectas.", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (NamingException ex) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error al crear contexto.", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		return "";
	}
	

	public void actualizarPersona(PersonaDTO p) {
		PersistenciaBean.editarPersona(p, encargadoSeleccionado, personalSeleccionado);
	}

	public String reset() {
		personaSeleccionada = new PersonaDTO();
		return "";
	}

	public GestionPersonaService getPersistenciaBean() {
		return PersistenciaBean;
	}

	public void setPersistenciaBean(GestionPersonaService persistenciaBean) {
		PersistenciaBean = persistenciaBean;
	}

	public PersonaDTO getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(PersonaDTO personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public EncargadoDTO getEncargadoSeleccionado() {
		return encargadoSeleccionado;
	}

	public void setEncargadoSeleccionado(EncargadoDTO encargadoSeleccionado) {
		this.encargadoSeleccionado = encargadoSeleccionado;
	}

	public PersonalDTO getPersonalSeleccionado() {
		return personalSeleccionado;
	}

	public void setPersonalSeleccionado(PersonalDTO personalSeleccionado) {
		this.personalSeleccionado = personalSeleccionado;
	}

	public String getCiAValidar() {
		return ciAValidar;
	}

	public void setCiAValidar(String ciAValidar) {
		this.ciAValidar = ciAValidar;
	}
	
	public boolean isTituloPermitido() {
		return tituloPermitido;
	}

	public void setTituloPermitido(boolean tituloPermitido) {
		this.tituloPermitido = tituloPermitido;
	}

	public boolean isCantHorasPermitido() {
		return cantHorasPermitido;
	}

	public void setCantHorasPermitido(boolean cantHorasPermitido) {
		this.cantHorasPermitido = cantHorasPermitido;
	}

	public String getNombreUsuarioLogin() {
		return nombreUsuarioLogin;
	}

	public void setNombreUsuarioLogin(String nombreUsuarioLogin) {
		this.nombreUsuarioLogin = nombreUsuarioLogin;
	}

	public String getContraseniaLogin() {
		return contraseniaLogin;
	}

	public void setContraseniaLogin(String contraseniaLogin) {
		this.contraseniaLogin = contraseniaLogin;
	}

	public String enviarListar() {
		return "index.xhtml";
	}

	public String enviarAlta() {
		return "AltaPersona.xhtml";
	}

	public String enviarModificacion() {
		return "ModificacionPersona.xhtml";
	}

	public String enviarBaja() {
		return "BajaPersona.xhtml";
	}

	public String volver() {
		try {
			return "Menu.xhtml";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public boolean iniciar(String funcionalidad) {
		switch (rol) {
			case "Administrador":
				return false;
			case "Encargado":
				return !funcionalidadesEncargado.stream().filter(f -> f.equals(funcionalidad)).findAny().isPresent();
			case "Personal":
				return !funcionalidadesPersonal.stream().filter(f -> f.equals(funcionalidad)).findAny().isPresent();
		}
		return true;
	}

	public void mensaje(String mensaje) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, "");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

}