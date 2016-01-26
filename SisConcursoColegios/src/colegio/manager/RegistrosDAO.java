package colegio.manager;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import colegio.controller.generic.Funciones;
import colegio.model.entidades.ColEstudiante;
import colegio.model.entidades.ColInstitucion;
import colegio.model.entidades.ColInstitucionesSenescyt;
import colegio.model.entidades.ColOpcionesRespuesta;
import colegio.model.entidades.ColParametro;
import colegio.model.entidades.ColPregunta;
/**
 * Clase MatriculasDAO permite manejar el HibernateDAO en conveniencia a la
 * gestion de matricula y reservas
 * 
 * @author Juan Carlos Est�vez Hidalgo
 * @version 1.0
 *
 */

public class RegistrosDAO {

	// Campos de la clase
	private ManagerDAO manager;

	/**
	 * Constructor para la utilizacion de metodos de la clase HibernateDAO
	 * 
	 * @param manager
	 *            El parametro manager inicializa la utilizacion de la clase
	 *            HIbernateDAO y todos sus metodos
	 */
	public RegistrosDAO() {
		manager = new ManagerDAO();
		
	}// Cierre del Constructor

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Creaci�n de metodos para el manejo de la tabla ColInstitucion
	 * 
	 */

	/**
	 * Metodo para listar todas los existentes
	 * 
	 * @return La lista de todas encontradas
	 */
	@SuppressWarnings("unchecked")
	public List<ColInstitucion> findAllInstituciones() {
		try{
		return manager.findAll(ColInstitucion.class);}
		catch (Exception e){
			System.out.println("error fndAllInstituciones");
			e.printStackTrace();
			return null;
		}
	}// Cierre del metodo

	/**
	 * Metodo para obtener un Dato mediante un ID
	 * 
	 * @param id_per
	 *            Tipo integer de busqueda
	 * @return El objeto Movimiento encontrado mediante el ID
	 */
	public ColInstitucion InstitucionByID(Integer id_ins) throws Exception {
		return (ColInstitucion) manager
				.findById(ColInstitucion.class, id_ins);
	}// Cierre del metodo

	/**
	 * Metodo para ingresar un Dato a la base de datos
	 * 
	 * @param zona
	 * @param provincia
	 * @param nombre
	 * @param direccion
	 * @param telefono
	 * @param correo
	 * @param amie
	 * @throws Exception
	 */
	public void insertarInstitucion(String zona, String provincia,
			String nombre, String direccion, String telefono, String correo,
			String amie, String rep_nombres, String rep_apellidos,
			String rep_cedula, String rep_correo, String rep_telefono,
			String rep_cargo, String coo_nombres, String coo_apellidos,
			String coo_cedula, String coo_cargo, String coo_telefono,
			String coo_correo) throws Exception {
		try {
			ColInstitucion ins = new ColInstitucion();

			// Datos de Entidad
			ins.setInsZona(zona);
			ins.setInsProvincia(provincia);
			ins.setInsNombre(nombre);
			ins.setInsDireccion(direccion);
			ins.setInsTelefono(telefono);
			ins.setInsCorreo(correo);
			ins.setInsAmie(amie);
			ins.setInsFecha(new Timestamp(new Date().getTime()));
			ins.setInsEstado("Pendiente");

			// Datos de Representante
			ins.setInsRepNombres(rep_nombres);
			ins.setInsRepApellidos(rep_apellidos);
			ins.setInsRepCargo(rep_cargo);
			ins.setInsRepCedula(rep_cedula);
			ins.setInsRepCorreo(rep_correo);
			ins.setInsRepTelefono(rep_telefono);

			// Datos de Coordinador
			ins.setInsCooApellidos(coo_apellidos);
			ins.setInsCooCargo(coo_cargo);
			ins.setInsCooCedula(coo_cedula);
			ins.setInsCooCorreo(coo_correo);
			ins.setInsCooNombres(coo_nombres);
			ins.setInsCooTelefono(coo_telefono);
			ins.setInsCooClave(Funciones.randomString(8));

			manager.insertar(ins);
			System.out.println("Bien_insertar_Institucion");
		} catch (Exception e) {
			System.out.println("Error_insertar_Institucion");
			System.out.println(e);
			e.printStackTrace();
		}

	}// Cierre del metodo

	/**
	 * Metodo para editar una entidad
	 * 
	 * @param ins_id
	 * @param ins_estado
	 */
	public void editarInstitucion(Integer ins_id, String ins_estado) {
		try {
			ColInstitucion ins = this.InstitucionByID(ins_id);
			ins.setInsEstado(ins_estado);
			manager.actualizar(ins);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Creaci�n de metodos para el manejo de la tabla ColInstitucionesSenescyt
	 * 
	 */

	/**
	 * Metodo para listar todas los existentes
	 * 
	 * @return La lista de todas encontradas
	 */
	@SuppressWarnings("unchecked")
	public List<ColInstitucionesSenescyt> findAllInstitucionesS() throws Exception {
		return manager.findAll(ColInstitucionesSenescyt.class);
	}// Cierre del metodo

	/**
	 * Metodo para obtener un Dato mediante un ID
	 * 
	 * @param id_per
	 *            Tipo integer de busqueda
	 * @return El objeto Movimiento encontrado mediante el ID
	 */
	public ColInstitucionesSenescyt InstitucionSByID(String id_amie)
			throws Exception {
		return (ColInstitucionesSenescyt) manager.findById(
				ColInstitucionesSenescyt.class, id_amie);
	}// Cierre del metodo

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Creaci�n de metodos para el manejo de la tabla ColEstudiantes
	 * 
	 */

	/**
	 * Metodo para listar todas los existentes
	 * 
	 * @return La lista de todas encontradas
	 */
	@SuppressWarnings("unchecked")
	public List<ColEstudiante> findAllEstudiantes() {
		return manager.findAll(ColEstudiante.class);
	}// Cierre del metodo

	/**
	 * Metodo para listar todas los existentes
	 * 
	 * @return La lista de todas encontradas
	 */
	@SuppressWarnings("unchecked")
	public List<ColEstudiante> findAllEstudiantesXID(Integer id_ins) {
		List<ColEstudiante> c=new ArrayList<>();
		List<ColEstudiante> le= manager.findAll(ColEstudiante.class);
		for (ColEstudiante col : le) {
			if (col.getColInstitucion().getInsId()==id_ins){
				c.add(col);
			}
		}
		return c;
	}// Cierre del metodo

	/**
	 * Metodo para listar todas los existentes
	 * 
	 * @return La lista de todas encontradas
	 */
	@SuppressWarnings("unchecked")
	public List<ColEstudiante> findAllEstudiantesActivos(String estado) {
		return manager.findWhere(ColEstudiante.class, "est_estado='" + estado
				+ "'", null);
	}// Cierre del metodo

	/**
	 * Metodo para obtener un Dato mediante un ID
	 * 
	 * @param id_per
	 *            Tipo integer de busqueda
	 * @return El objeto Movimiento encontrado mediante el ID
	 */
	public ColEstudiante EstudianteByID(Integer est_id) throws Exception {
		return (ColEstudiante) manager.findById(ColEstudiante.class, est_id);
	}// Cierre del metodo

	/**
	 * Metodo para ingresar un Dato a la base de datos
	 * 
	 * @param nombres
	 * @param apellidos
	 * @param cedula
	 * @param area
	 * @param telefono
	 * @param correo
	 */
	public void insertarEstudiante(String nombres, String apellidos,
			String cedula, String area, String telefono, String correo,
			Integer id_ins) throws Exception {
		try {
			ColEstudiante est = new ColEstudiante();
			ColInstitucion ins = new ColInstitucion();
			est.setEstNombres(nombres);
			est.setEstApellidos(apellidos);
			est.setEstCedula(cedula);
			est.setEstCorreo(correo);
			est.setEstArea(area);
			est.setEstTelefono(telefono);
			est.setEstEstado("A");
			ins = this.InstitucionByID(id_ins);
			est.setColInstitucion(ins);
			est.setEstClave(Funciones.randomString(5));
			manager.insertar(est);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Metodo para editar una entidad
	 * 
	 * @param ins_id
	 * @param ins_estado
	 */
	public void editarEstudiante(Integer est_id, String est_estado,
			Date est_fechai, Date est_fechaf) {
		try {
			ColEstudiante est = this.EstudianteByID(est_id);
			est.setEstFechaIni(new Timestamp(est_fechai.getTime()));
			est.setEstFechaFin(new Timestamp(est_fechaf.getTime()));
			est.setEstEstado(est_estado);
			manager.actualizar(est);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void eliminarEstudiante(Integer id_est) {
		try {
			manager.eliminar(ColEstudiante.class, id_est);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Creaci�n de metodos para el manejo de la tabla ColParametro
	 * 
	 */

	/**
	 * Metodo para listar todas los existentes
	 * 
	 * @return La lista de todas encontradas
	 */
	@SuppressWarnings("unchecked")
	public List<ColParametro> findAllParametros() {
		return manager.findAll(ColParametro.class);
	}// Cierre del metodo

	/**
	 * Metodo para obtener un Dato mediante un ID
	 * 
	 * @param id_per
	 *            Tipo integer de busqueda
	 * @return El objeto Movimiento encontrado mediante el ID
	 */
	public ColParametro ParametroByID(Integer par_id) throws Exception {
		return (ColParametro) manager.findById(ColParametro.class, par_id);
	}// Cierre del metodo

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Creaci�n de metodos para el manejo de la tabla ColPreguntas
	 * 
	 */

	/**
	 * Metodo para listar todas los existentes
	 * 
	 * @return La lista de todas encontradas
	 */
	@SuppressWarnings("unchecked")
	public List<ColPregunta> findAllPreguntas() {
		return manager.findAllAleatorioP(ColPregunta.class);
//		return manager.findAllAleatorioP(ColPregunta.class);
	}// Cierre del metodo

	/**
	 * Metodo para obtener un Dato mediante un ID
	 * 
	 * @param id_per
	 *            Tipo integer de busqueda
	 * @return El objeto encontrado mediante el ID
	 */
	public ColPregunta PreguntaByID(Integer id_pre) throws Exception {
		return (ColPregunta) manager.findById(ColPregunta.class, id_pre);
	}// Cierre del metodo

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Creaci�n de metodos para el manejo de la tabla ColOpcionesRespuestas
	 * 
	 */

	/**
	 * Metodo para listar todas los existentes
	 * 
	 * @return La lista de todas encontradas
	 */
	@SuppressWarnings("unchecked")
	public List<ColOpcionesRespuesta> findAllOpciones()  throws Exception{
		return manager.findAll(ColOpcionesRespuesta.class);
	}// Cierre del metodo

	/**
	 * Metodo para obtener un Dato mediante un ID
	 * 
	 * @param id_per
	 *            Tipo integer de busqueda
	 * @return El objeto encontrado mediante el ID
	 */
	public ColOpcionesRespuesta OpcionesByID(Integer id_pre) throws Exception {
		return (ColOpcionesRespuesta) manager.findById(
				ColOpcionesRespuesta.class, id_pre);
	}// Cierre del metodo
}
