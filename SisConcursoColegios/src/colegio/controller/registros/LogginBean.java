package colegio.controller.registros;

/**
 * 
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import colegio.acceso.entidades.Menu;
import colegio.controller.generic.Mensaje;
import colegio.manager.ManagerAcceso;
import colegio.manager.RegistrosDAO;
import colegio.model.entidades.ColEstudiante;
import colegio.model.entidades.ColEvaluacionEstudiantil;
import colegio.model.entidades.ColInstitucion;

/**
 * @author jestevez
 * 
 */
@SessionScoped
@ManagedBean
public class LogginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1799091747010165818L;
	
	private ManagerAcceso mngAcc;
	private RegistrosDAO manager;

	private List<Menu> menu;

	// Atributo para el nombre del Estudiante
	private String est_nombre;

	private long dia;
	private long hora;
	private long minuto;
	private long segundo;

	private long t_par;

	private String time;

	// Atributos para el loggin
	private String usuario;
	private String contrasena;

	// Atributo para obtener la institucion
	private ColInstitucion institucion;
	private ColEstudiante estudiante;

	private StreamedContent file;
	private StreamedContent file2;
	private StreamedContent file3;

	// atributo de control de parametros
	private boolean parametroIns;

	// atributo de calificacion
	private Integer calificacion = 0;
	private String tiempo_eva;

	private Integer idguardar = 0;

	public LogginBean() {
		institucion = new ColInstitucion();
		manager = new RegistrosDAO();
		mngAcc = new ManagerAcceso();
		this.DownloadFile();
		this.DownloadFile2();
		this.DownloadFile3();
		t_par = 0L;
		// this.validarParametroIns();
	}

	public String getTiempo_eva() {
		return tiempo_eva;
	}

	public void setTiempo_eva(String tiempo_eva) {
		this.tiempo_eva = tiempo_eva;
	}

	public Integer getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}

	public StreamedContent getFile3() {
		return file3;
	}

	public void setFile3(StreamedContent file3) {
		this.file3 = file3;
	}

	public ColEstudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(ColEstudiante estudiante) {
		this.estudiante = estudiante;
	}

	/**
	 * @return the idguardar
	 */
	public Integer getIdguardar() {
		return idguardar;
	}

	/**
	 * @param idguardar
	 *            the idguardar to set
	 */
	public void setIdguardar(Integer idguardar) {
		this.idguardar = idguardar;
	}

	/**
	 * @return the institucion
	 */
	public ColInstitucion getInstitucion() {
		return institucion;
	}

	/**
	 * @param institucion
	 *            the institucion to set
	 */
	public void setInstitucion(ColInstitucion institucion) {
		this.institucion = institucion;
	}

	/**
	 * @return the est_nombre
	 */
	public String getEst_nombre() {
		return est_nombre;
	}

	/**
	 * @param est_nombre
	 *            the est_nombre to set
	 */
	public void setEst_nombre(String est_nombre) {
		this.est_nombre = est_nombre;
	}

	/**
	 * @return the parametroIns
	 */
	public boolean isParametroIns() {
		// ColParametro p = manager.ParametroByNombre("institucion");
		// parametroIns=p.isPar_valor();
		return parametroIns;
	}

	/**
	 * @param parametroIns
	 *            the parametroIns to set
	 */
	public void setParametroIns(boolean parametroIns) {
		this.parametroIns = parametroIns;
	}

	public StreamedContent getFile2() {
		return file2;
	}

	public StreamedContent getFile() {
		return file;
	}

	/**
	 * @return the menu
	 */
	public List<Menu> getMenu() {
		return menu;
	}

	/**
	 * @param menu
	 *            the menu to set
	 */
	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the contrasena
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * @param contrasena
	 *            the contrasena to set
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Metodo para ejecutar todos los procesos de logueado
	 * 
	 * @return
	 */
	public String ingresoLogin() {
		String r = "";
		if (usuario != null && contrasena != null) {
			r = this.resultado();
			if (r.isEmpty() || r == "") {
				r = this.loginEst();
			}
			if (r.isEmpty() || r == "") {
				r = this.loginCoo();
			}
			if (r.isEmpty() || r == "") {
//				r = this.login();
				r= this.loginInterno();
			}
			if (r.isEmpty() || r == "") {
				Mensaje.crearMensajeERROR("Usuario o contrase�a Inexistente");
			}
		} else {
			Mensaje.crearMensajeERROR("Usuario o contrase�a Inexistente");
			r = "";
		}
		if (r == "a") {
			r = "";
		}
		return r;
	}

	/**
	 * Metodo para obtener la informacion de una evaluaci�n estudiantil
	 * 
	 * @return
	 */
	public String resultado() {
		String r = "";
		try {
			ColEstudiante estudiante = manager.findEstudianteByDNI(usuario
					.trim());
			if (estudiante != null) {
				ColEvaluacionEstudiantil evaluacion = manager
						.findCalificacionEvaluacionEstudiante(estudiante);
				if (evaluacion != null
						&& evaluacion.getEesCalificacion() != null) {
					if (evaluacion.getEesCalificacion() > 0)
						calificacion = evaluacion.getEesCalificacion();
					else
						calificacion = 0;
					tiempo_eva = evaluacion.getEesTiempo();
					RequestContext.getCurrentInstance().execute(
							"PF('close').show();");
					r = "a";
				}
			}
		} catch (Exception e) {
			Mensaje.crearMensajeERROR("ERROR! " + e.getMessage());
			e.printStackTrace();
		}
		// for (ColEstudiante e : manager.findAllEstudiantes()) {
		// if (e.getEstCedula().trim().equals(usuario.trim())) {
		// for (ColEvaluacionEstudiantil ev : manager
		// .findAllEvaEstudiantil()) {
		// if (ev.getColEstudiante().getEstId() == e.getEstId()
		// && ev.getEesCalificacion() != null) {
		// if (ev.getEesCalificacion()<=0){
		// calificacion = 0;
		// }else{
		// calificacion = ev.getEesCalificacion();
		// }
		// tiempo_eva = ev.getEesTiempo();
		// RequestContext context = RequestContext
		// .getCurrentInstance();
		// context.execute("PF('close').show();");
		// r = "a";
		// }
		// }
		// }
		// }
		return r;
	}

	/**
	 * Metodo para ingresar al sistema como Coordinador
	 * 
	 * @param usu
	 * @param pass
	 * @return
	 */
	public String loginCoo() {
		String r = "";
		ColInstitucion insti = manager.findInsXUsuarioPass(usuario.trim(),
				contrasena.trim());
		if (insti != null) {
			setInstitucion(insti);
			// session.setAttribute("sessionBean", usuario);
			r = "views/alumnos?faces-redirect=true";
		}
		return r;
	}

	/**
	 * Metodo para ingresar al sistema como Coordinador
	 * 
	 * @param usu
	 * @param pass
	 * @return
	 */
	public String loginEst() {
		String r = "";
		ColEstudiante est = manager.findEstXUsuarioPass(usuario.trim(),
				contrasena.trim());
		if (est != null) {
			if ((new Date().after(est.getEstFechaIni()))
					&& (new Date().before(est.getEstFechaFin()))) {
				setEstudiante(est);
				if (est.getEstArea().trim().equals("Matem�ticas"))
					r = "views/home_mate.xhtml";
				if (est.getEstArea().trim().equals("Qu�mica"))
					r = "views/home_quim.xhtml";
				if (est.getEstArea().trim().equals("F�sica"))
					r = "views/home_fisi.xhtml";
				if (est.getEstArea().trim().equals("Biolog�a"))
					r = "views/home_biol.xhtml";
				
			}
			if ((new Date().after(est.getEstFechaIni()))
					&& (new Date().after(est.getEstFechaFin()))) {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('close').show();");
				r = "a";
			}
			if ((new Date().before(est.getEstFechaIni()))
					&& (new Date().before(est.getEstFechaFin()))) {
				est_nombre = est.getEstNombres() + " " + est.getEstApellidos();
				t_par = est.getEstFechaIni().getTime();
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('info').show();");
				context.execute("PF('poll').start();");
				r = "a";
			}
		}
		return r;
	}
	
	

	/**
	 * Metodo que cierra el dialog y finaliza el poll
	 * 
	 * @return
	 */
	public String cerrarDialog() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('poll').stop();");
		return "";
	}

	/**
	 * Permite logearse al sistema
	 * 
	 * @return
	 */
	public String loginInterno() {
		String r="";
		try {
			if(usuario.trim().equals("aquina") && contrasena.trim().equals("123"))
			r="views/index?faces-redirect=true";
			if(usuario.trim().equals("krivadeneira") && contrasena.trim().equals("Yachay2015"))
			r="views/index?faces-redirect=true";
		} catch (Exception e) {
			Mensaje.crearMensajeWARN(e.getMessage());
			return "";
		}
		return r;
	}
	
	/**
	 * Permite logearse al sistema
	 * 
	 * @return
	 */
	public String login() {
		try {
			List<Menu> lstmenu = mngAcc.loginWS(getUsuario(), getContrasena(),
					"OLIMP");
			if (lstmenu.isEmpty())
				throw new Exception("Usuario o contrase�a Inexistente.");
			setMenu(lstmenu);
			setContrasena("");
			// session.setAttribute("sessionBean", usuario);
			return "views/index?faces-redirect=true";
		} catch (Exception e) {
			Mensaje.crearMensajeWARN(e.getMessage());
			return "";
		}
	}

	/**
	 * Permite deslogearse del sistema
	 * 
	 * @return
	 */
	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.invalidate();
		institucion = null;
		setMenu(new ArrayList<Menu>());
		setUsuario(null);
		setContrasena(null);
		setInstitucion(null);
		setEstudiante(null);
		return "/index?faces-redirect=true";
	}

	/**
	 * M�todo para verifiar la existencia de la sesi�n
	 * 
	 * @param rol
	 *            de usuario
	 * @return Clase Usuario
	 */
	public String verificarSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		LogginBean log = (LogginBean) session.getAttribute("logginBean");
		String user = log.getUsuario();
		if (user == null || user.isEmpty()) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/SisConcursoColegios/faces/index.xhtml");
			} catch (IOException ex) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, ex
								.getMessage(), null));
			}
			return null;
		} else {
			return user;
		}
	}

	/**
	 * Metodo para download archivos
	 */
	public void DownloadFile() {
		InputStream stream = ((ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream("/resources/docs/Reglamento-Olimpiada Ciencias INNOPOLIS 17-12-2015.pdf");
		file = new DefaultStreamedContent(stream, "application/pdf",
				"Reglamento-Olimpiada Ciencias INNOPOLIS 17-12-2015.pdf");
	}

	public void DownloadFile2() {
		InputStream stream2 = ((ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext())
				.getResourceAsStream("/resources/docs/pasos de inscripcion.pdf");
		file2 = new DefaultStreamedContent(stream2, "application/pdf",
				"Pasos de Inscripci�n.pdf");

	}

	/**
	 * Metodo para descargar formato de envio de estudiantes e intituciones
	 */
	public void DownloadFile3() {
		try {
			InputStream stream3 = ((ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext())
					.getResourceAsStream("/resources/docs/FormatoRegistroEstudiantes.xlsx");
			file3 = new DefaultStreamedContent(
					stream3,
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
					"Formato Registro Estudiantes.xlsx");
		} catch (Exception e) {
			System.out.println("Descarga de Archivo Cancelado");
		}

	}

	/**
	 * Metodo de envio a otra pagina web
	 * 
	 * @throws Exception
	 */
	public void redirect() throws Exception {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		externalContext
				.redirect("http://www.ciudadyachay.com/es/eventos/olimpiada-de-ciencias-innopolis");
	}

	/**
	 * Metodo para culacular el tiempo entre 2 fechas
	 */
	public void calculoTiempo() {
		if (t_par != 0L) {
			long t_actual = new Date().getTime();
			if (t_par >= t_actual) {
				long t_total = t_par - t_actual;

				dia = t_total / (24 * 60 * 60 * 1000);
				hora = t_total / (60 * 60 * 1000);
				while (hora >= 24) {
					hora = hora - 24;
				}
				minuto = t_total / (60 * 1000);
				while (minuto >= 60) {
					minuto = minuto - 60;
				}
				segundo = t_total / 1000;
				while (segundo >= 60) {
					segundo = segundo - 60;
				}
				time = "Dias: " + dia + "\n Horas: " + hora + "\n Minutos: "
						+ minuto + "\n Segundos: " + segundo;
			} else {
				time = "Dias: 0 \n Horas: 0 \n Minutos: 0 \n Segundos: 0";
			}
		}
	}

}
