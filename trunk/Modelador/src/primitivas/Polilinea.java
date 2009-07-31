package primitivas;
import java.util.Vector;

/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public  class Polilinea extends Primitiva{
	Vector <Punto> v = new Vector<Punto>();
	public Polilinea(Plot plot, Punto a, Punto b, int vista) {
		
		this.plot = plot;
		this.z = a.getZ();
		this.vista = vista;
		v = new Vector<Punto>();
		v.add(a);
		v.add(b);
		new Linea(plot, a, b, vista);

	}	
	public void agregarLinea(Punto a, Punto b) {
		v.add(b);
		new Linea(plot, a, b, vista);		
	}
	
	
}

