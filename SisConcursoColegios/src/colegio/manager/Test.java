/**
 * 
 */
package colegio.manager;


import colegio.controller.generic.Funciones;



/**
 * @author jestevez
 *
 */
public class Test {
	
	
	public Test() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Funciones.sendMail("olimpiadasdeciencia@yachay.gob.ec",
					"juank20097@yopmail.com",
					"Notificaci�n de Olimpiadas de Ciencias", "hola");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//	RegistrosDAO r = new RegistrosDAO();
//	List<COL_Instituciones_Senescyt> c= new ArrayList<COL_Instituciones_Senescyt>();
//	c=r.findAllInstitucionesS();
//	System.out.println(c.size());
//	
//	COL_Instituciones_Senescyt z = new COL_Instituciones_Senescyt();
//	try {
//		z=r.InstitucionSByID("01H00076");
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
		
//	System.out.println(Funciones.validadorDeCedula("1001551785"));
//	System.out.println(z.getSen_nombre());
//	System.out.println(h.Activado());
	
	
//	l=r.findAllEstudiantesActivos('A');
//	l=r.findAllEstudiantesXID(14);
//	System.out.println(l.size());
//	System.out.println(Funciones.conseguirMAC());
		
//	COL_Parametros p=r.ParametroByNombre("institucion");
//	System.out.println(p.isPar_valor());
//	System.out.println(p.isPar_entidad());
	}

}
