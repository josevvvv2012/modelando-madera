package primitivas;

import ide.DEF;

/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Elipse extends Primitiva{
	public Elipse(Plot plot, Punto orig,double rx, double ry, boolean relleno, int vista, int opc) {
		this.plot = plot;
		this.z = orig.getZ();
		this.vista = vista;
		double oX = orig.getX();
		double oY = orig.getY();
		double oZ = orig.getZ();	
		elipse(rx, ry, oX, oY, oZ, 1000, opc);		
	}
	
	void elipse(double radio_a, double radio_b, double oX, double oY, double oZ, int n, int opc)
	{
	    int i;
	    double inc_ang, ang;
	    inc_ang = 360.0 / n * Math.PI / 180.0;
	    for(i=0; i < n; i++) {
	        ang = inc_ang * i;	  
	    	switch(opc) {
	    		
				case DEF.vistaYX: grafPto(new Punto( oX + radio_a * Math.cos(ang), oY + radio_b * Math.sin(ang), oZ )); break;								
				case DEF.vistaZX: grafPto(new Punto( oX + radio_a * Math.cos(ang), oY, oZ + radio_b * Math.sin(ang))); break;
				case DEF.vistaZY: grafPto(new Punto( oX, oY + radio_a * Math.cos(ang), oZ + radio_b * Math.sin(ang))); break;
	    	}
	        	        
	    }
	}	
}
