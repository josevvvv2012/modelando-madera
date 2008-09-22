package primitivas;

import java.util.Vector;

/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Elipse extends Primitiva{
	public Elipse(Plot plot, Punto orig,double rx, double ry, boolean relleno, double inc) {
		this.plot = plot;
		this.z = orig.getZ();
		this.inc  = inc;
		v1 = new Vector<Punto>();
		v2 = new Vector<Punto>();
		v3 = new Vector<Punto>();
		v4 = new Vector<Punto>();
		iniciarVectores();
		elipse(rx, ry, orig.getX(), orig.getY(), 1000);
		if(plot==null)
			while(rx>0&&ry>0) {
				elipse(--rx, --ry, orig.getX(), orig.getY(), 1000);		
			}
		//puntoMedio(orig, rx, ry, relleno);		
		graficarVectores();
	}
	
	void elipse(double radio_a, double radio_b, double xc, double yc, int n)
	{
	    int i;
	    double inc_ang, ang;

	    inc_ang = 360.0 / n * Math.PI / 180.0;   
	    for(i=0; i < n; i++) {
	        ang = inc_ang * i;
	        grafPto(new Punto( xc + radio_a * Math.cos(ang), yc + radio_b * Math.sin(ang) ));	        
	    }
	}

	public void puntoMedio(Punto orig,double rx, double ry, boolean relleno) {
		double ry2=ry*ry;
		double rx2=rx*rx;
		double x=0;
		double y=ry;
		double p1k=ry2-rx2*ry+(rx2/4);
		double p2k=0;
		boolean px=true;
		while(y>0) {
			if( ((2*ry2*x))<(2*rx2*y)) {
				x+=inc;
				if(p1k<0) {
					p1k=p1k+2*(x)*ry2+ry2;
				}
				else {
					y-=inc;
					p1k=p1k+2*ry2*x-2*rx2*y+ry2;
				}	
			}
			else {
				if(px) {
					p2k=ry2*(x+1/2)*(x+1/2)+rx2*(y-1)*(y-1)-rx2*ry2;
					px=false;
				}
				y-=inc;
				if(p2k<0) {
					x+=inc;
					p2k=p2k+2*ry2*x-2*rx2*y+rx2;
				}
				else {
					p2k=p2k-2*rx2*y+rx2;
				}
				
				
			}
			insertarVectores(orig.getX(), orig.getY(),x,y);
			/*
			v1.add(new Punto( (orig.getX()+x)   ,   (orig.getY()+y))  );			
			v2.add(new Punto( (orig.getX()+x)   ,   (orig.getY()-y))  );			
			v3.add(new Punto( (orig.getX()-x)   ,   (orig.getY()-y))  ); 
			v4.add(new Punto( (orig.getX()+x)   ,   (orig.getY()-y))  );
			*/
			/*
			grafPto(new Punto(orig.getX()+x,orig.getY()+y));
			grafPto(new Punto(orig.getX()-x,orig.getY()+y));
			grafPto(new Punto(orig.getX()-x,orig.getY()-y));
			grafPto(new Punto(orig.getX()+x,orig.getY()-y));
			*/
			if(relleno) {
				plot.linea(new Punto(orig.getX()+x,orig.getY()+y), new Punto(orig.getX()-x,orig.getY()+y));
				plot.linea(new Punto(orig.getX()-x,orig.getY()-y),new Punto(orig.getX()+x,orig.getY()-y));
				
			}
		}						
	}
}
