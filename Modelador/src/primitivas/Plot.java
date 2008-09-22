package primitivas;
import java.util.Vector;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Plot {
	public GC gc;
	public Image id;
	
	public Plot(GC gc) {
		this.gc = gc;
		
	}
	
	public int getPixel(int x, int y) {
		
		return id.getImageData().getPixel(x, y);
		
	}
	public Plot(GC gc, Image id, org.eclipse.swt.graphics.Color backColor, org.eclipse.swt.graphics.Color foreColor) {
		this.gc = gc;
		this.id = id;
		this.gc.setForeground(foreColor);
		this.gc.setBackground(backColor);
	}
	
	
	public Plot(GC gc,  org.eclipse.swt.graphics.Color backColor, org.eclipse.swt.graphics.Color foreColor) {
		this.gc = gc;
		this.gc.setForeground(foreColor);
		this.gc.setBackground(backColor);
	}
	public void pixel(int x, int y) {
		gc.drawPoint(x,y);
	}
	public void pixel(Punto punto) {		
		gc.drawPoint((int)punto.getX(), (int)punto.getY());
	}
	public void dispose() {
		gc.dispose();		
	}
	
	public void linea(Punto a, Punto b) {
		new Linea(this, a, b);
	}	
	
	public void bezier(Punto a, Punto b, Punto c, Punto d, int pts) {
		new Bezier(this, a, b, c, d, pts);
	}
	
	public void bezier(Vector<Punto> p, int pts) {
		new Bezier(this, p, pts);
		
	}
	
	public void relleno(Punto a) {
		new relleno(this, a);
	}
	public void circulo(Punto a, Punto b, boolean relleno, double inc) {	
		
		double dx = (a.getX()-b.getX());
		double dy = (a.getY()-b.getY());
		double r = (int)Math.sqrt(dx*dx+dy*dy);		
		//new Circulo(this, a, r, relleno, inc);
		
		new Elipse(this, a, r, r, relleno, inc);
		
	}
	
	public void elipse(Punto a, Punto b, boolean relleno, double inc) {
		
		double dx = Math.abs(a.getX()-b.getX());
		double dy = Math.abs(a.getY()-b.getY());
				
		if((dx!=0)&&(dy!=0))
			new Elipse(this, a, dx, dy, relleno, inc);
		
	}
	
	public void setFondo(org.eclipse.swt.graphics.Color color) {
		this.gc.setBackground(color);
	}
	public void setFrente(org.eclipse.swt.graphics.Color color) {
		this.gc.setForeground(color);
	}
	
	@SuppressWarnings("deprecation")
	public void xOr(boolean xor) {
		this.gc.setXORMode(xor);		
	}
}
