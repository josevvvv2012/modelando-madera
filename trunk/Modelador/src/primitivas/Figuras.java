package primitivas;

import java.util.Vector;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Figuras {
	public int tipoFig;
	public  boolean relleno;
	public  Vector<Punto> puntos;
	private String []figs  = {"null", "Punto",
			  "Linea",
			  "Polilinea",
			  "Circulo",
			  "Elipse",
			  "Bezier",
			  "Relleno",
			  "Recortar"
	};
	
	@SuppressWarnings("unchecked")
	public Figuras(int tipo, boolean relle, Vector<Punto> p) {
		this.tipoFig = tipo;
		this.relleno = relle;	
		this.puntos = (Vector<Punto>)p.clone();
		
	}
	
	public String getText() {
		return figs[this.tipoFig];
	}
	
	public void setPuntos(Vector<Punto> p) {
		this.puntos = p;
	}
	public void insertarPunto(Punto p) {
		puntos.add(p);
	}
}
