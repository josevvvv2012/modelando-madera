package primitivas;
import ide.DEF;

import java.util.Vector;

import javax.swing.text.StyledEditorKit.ForegroundAction;

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
	public void selecColor() {
		this.gc.setForeground(DEF.colorSelec);
	}
	public void unselecColor() {
		this.gc.setForeground(DEF.frenteColor);
	}
	
	public void pixel(int x, int y) {
		gc.drawPoint(x,y);
	}
	public void pixel(Punto punto, int vista) {
		switch(vista) {
			case DEF.vistaYX:
				gc.drawPoint((int)punto.getX(), (int)punto.getY());
				break;
			case DEF.vistaZX:
				gc.drawPoint((int)punto.getX(), (int)punto.getZ());
				break;
			case DEF.vistaZY:
				gc.drawPoint((int)punto.getY(), (int)punto.getZ());
				break;
			case DEF.vistaPerspectiva:
				primitivas.Transformaciones t = new Transformaciones(punto);
				t.perspectiva();				
				gc.drawPoint((int)t.getPunto().getX(), (int)t.getPunto().getY());
				break;
		}
		
	}
	public void dispose() {
		gc.dispose();		
	}
	
	public void linea(Punto a, Punto b, int vista ){
		new Linea(this, a, b, vista);
	}	
	

	
	public void bezier(Vector<Punto> p, int pts, int vista) {
		new Bezier(this, p, pts, vista);
		
	}
	
	public void relleno(Punto a) {
		new relleno(this, a);
	}
	public void circulo(Punto a, Punto b, boolean relleno, int vista) {	
		
		double dx = (a.getX()-b.getX());
		double dy = (a.getY()-b.getY());
		double r = (int)Math.sqrt(dx*dx+dy*dy);		
		//new Circulo(this, a, r, relleno, inc);
		
		int vista2=1;
		
		if(a.getX()==b.getX())
			vista2 =DEF.vistaZY;
		if(a.getY()==b.getY())
			vista2 =DEF.vistaZX;
		if(a.getZ()==b.getZ())
			vista2 =DEF.vistaYX;
			
		switch(vista2) {
			case DEF.vistaYX: dx = (a.getX()-b.getX()); dy = (a.getY()-b.getY());break;
			case DEF.vistaZX: dx = (a.getX()-b.getX()); dy = (a.getZ()-b.getZ());break;
			case DEF.vistaZY: dx = (a.getY()-b.getY()); dy = (a.getZ()-b.getZ());break;
		}
		
		
		new Elipse(this, a, r, r, relleno, vista, vista2);
		
	}
	
	public void elipse(Punto a, Punto b, boolean relleno, int vista) {
		
		double dx = Math.abs(a.getX()-b.getX());
		double dy = Math.abs(a.getY()-b.getY());
		int vista2=1;
	
		if(a.getX()==b.getX())
			vista2 =DEF.vistaZY;
		if(a.getY()==b.getY())
			vista2 =DEF.vistaZX;
		if(a.getZ()==b.getZ())
			vista2 =DEF.vistaYX;
			
		switch(vista2) {
			case DEF.vistaYX: dx = Math.abs(a.getX()-b.getX()); dy = Math.abs(a.getY()-b.getY()); break;
			case DEF.vistaZX: dx = Math.abs(a.getX()-b.getX()); dy = Math.abs(a.getZ()-b.getZ()); break;
			case DEF.vistaZY: dx = Math.abs(a.getY()-b.getY()); dy = Math.abs(a.getZ()-b.getZ()); break;
		}
		
				
		if((dx!=0)&&(dy!=0))
			new Elipse(this, a, dx, dy, relleno, vista,vista2);
		
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
