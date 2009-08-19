package primitivas;

import java.util.Vector;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Figuras  {
	public int tipoFig;
	public int id;
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
	
	

	public Figuras copiar() {
		Vector <Punto> p = new Vector<Punto>();
		
		for(int i=0; i<puntos.size(); i++)
			p.add(new Punto(puntos.get(i).getX(),puntos.get(i).getY(),puntos.get(i).getZ()));
		return new Figuras(-1, tipoFig, relleno, p);
		
	}
	
	@SuppressWarnings("unchecked")
	public Figuras(int id, int tipo, boolean relle, Vector<Punto> p) {
		this.id = id;
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
