package primitivas;

import ide.DEF;


/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Circulo extends Primitiva{
	
	
	
	public Circulo(Plot plot, Punto orig,double r, boolean relleno,double inc, int vista, int opc) {
		this.plot = plot;
		this.z = orig.getZ();
		this.vista = vista;
		double oX = orig.getX();
		double oY = orig.getY();
		double oZ = orig.getZ();	
		circulo(r, oX, oY, oZ, 1000, opc);
		
		
	}
	void circulo(double radio, double oX, double oY, double oZ, int n, int opc)
	{
	    int i;
	    double inc_ang, ang;
	    inc_ang = 360.0 / n * Math.PI / 180.0;
	    for(i=0; i < n; i++) {
	        ang = inc_ang * i;	  
	    	switch(opc) {
	    		
				case DEF.vistaYX: grafPto(new Punto( oX + radio * Math.cos(ang), oY + radio * Math.sin(ang), oZ )); break;								
				case DEF.vistaZX: grafPto(new Punto( oX + radio * Math.cos(ang), oY, oZ + radio * Math.sin(ang))); break;
				case DEF.vistaZY: grafPto(new Punto( oX, oY + radio * Math.cos(ang), oZ + radio * Math.sin(ang))); break;
	    	}
	        	        
	    }
	}	
	public void puntoMedio(int r, Punto orig) {
		int x0=0;		
		int y0=r;
		int pk=5/4-r^2;
		while(x0<=r) {
			insertarVectores(orig.getX(), orig.getY(),x0,y0);
			//v1.add(new Punto( (orig.getX()+x0)   ,   (orig.getY()+y0))  );
			//v2.add(new Punto( (orig.getX()+x0)   ,   (orig.getY()-y0))  );			
			//v3.add(new Punto( (orig.getX()-x0)   ,   (orig.getY()-y0))  ); 
			//v4.add(new Punto( (orig.getX()+x0)   ,   (orig.getY()+y0))  );
			
			/*
			grafPto(new Punto( (orig.getX()+x0)   ,   (orig.getY()+y0))  );
			grafPto(new Punto( (orig.getX()-x0)   ,   (orig.getY()+y0))  );
			grafPto(new Punto( (orig.getX()-x0)   ,   (orig.getY()-y0))  );
			grafPto(new Punto( (orig.getX()+x0)   ,   (orig.getY()-y0))  );
			*/
			if(pk<0) {
				pk=pk+2*(x0+1)+1;
			}
			else {
				y0-=inc;
				pk=pk+2*(x0+1)+1-2*y0;										
			}
			
			
			
			x0+=inc;
			
		}		
	}
	public void Bresenham(double r, Punto orig, boolean relleno ) {
		double x = 0;
		double y = r;
		double d = 2-2*r;
		double d2, d3;		
		while(y>=0) {
			
			insertarVectores(orig.getX(), orig.getY(),x,y);
			
			//v1.add(new Punto( (orig.getX()+x)   ,   (orig.getY()+y))  );
			//v2.add(new Punto( (orig.getX()+x)   ,   (orig.getY()-y))  );			
			//v3.add(new Punto( (orig.getX()-x)   ,   (orig.getY()-y))  ); 
			//v4.add(new Punto( (orig.getX()-x)   ,   (orig.getY()+y))  );
			/*
			grafPto(new Punto(orig.getX()+x, orig.getY()+y));
			grafPto(new Punto(orig.getX()+x, orig.getY()-y));
			grafPto(new Punto(orig.getX()-x, orig.getY()-y));
			grafPto(new Punto(orig.getX()-x, orig.getY()+y));
			
			*/
			if(relleno) {
				plot.linea(new Punto(orig.getX()+x, orig.getY()+y), new Punto(orig.getX()-x, orig.getY()+y),1 );
				plot.linea(new Punto(orig.getX()+x, orig.getY()-y), new Punto(orig.getX()-x, orig.getY()-y), 1);					
			}
			
		
			if(d<0) {
				d2=2*d+2*y-1;
				if(d2<0) {
					x+=inc;
					d=d+2*x+1;
				}
				else {
					x+=inc;
					y-=inc;
					d=d+2*x-2*y+2;				
				}
			}
			else if(d>0){
				d3=2*d-2*x-1;
				if(d3<=0) {
					x+=inc;
					y-=inc;
					d=d+2*x-2*y+2;
				}
				else {
					y-=inc;
					d=d-2*y+1;
				}				
			}
			else {
				x+=inc;
				y-=inc;
				d=d+2*x-2*y+2;
			}
			
		}
	}
}
