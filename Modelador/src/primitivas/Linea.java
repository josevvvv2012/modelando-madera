package primitivas;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Linea extends Primitiva{
	
	public Linea(Plot plot, Punto a, Punto b) {
		this.plot = plot;
		this.z = a.getZ();		
		this.linea(a, b);
		
	}
	
	public void linea( Punto a, Punto b)
	{
	
	   int i;
	   int n=(int)(dist(a.getX(),a.getY(), b.getX(),b.getY())*ptosPixel);
	   double inc_lambda = 1.0 / (n - 1.0);
	   
	   for(i=0; i < n; i++) {
		   grafPto(new Punto(a.getX() + (i * inc_lambda) * (b.getX() - a.getX()),
				             a.getY() + (i * inc_lambda) * (b.getY() - a.getY()),
				             a.getZ() + (i * inc_lambda) * (b.getZ() - a.getZ())
		   					)
		   			);		
	   }			   
	}
	
	public void DDA(Punto a, Punto b) {				
		double dx = b.getX()-a.getX();
		double dy = b.getY()-a.getY();		
		double k =1000;		
		double x=a.getX();
		double y=a.getY();
		double xinc= dx/k;
		double yinc= dy/k;
							
		grafPto(a);	
		
		for(int i=0; i<k; i++) {
			x=x+xinc;
			y=y+yinc;
			grafPto(new Punto(x,y));				
		}		
	}
	
	public void Bresenham(double x1, double y1, double x2, double y2)
	{	
		double x = x1;
		double y = y1;
		double temp;
		
		boolean izq_der=false;
		if(Math.abs(x1-x2)>Math.abs(y1-y2)) {
			izq_der=true;
		}
		double dx = Math.abs(x2-x1);
		double dy = Math.abs(y2-y1);		
		
		if(!izq_der) {
			temp = dx;
			dx=dy;
			dy=temp;
		}			
		double e =2*dy-dx;		
		for(double i=1; i<=dx; i+=inc) {
			grafPto(new Punto(x,y));						
			while(e>=0) {
				if(izq_der)
					y=y1>y2?y-inc:y+inc;
				else
					x=x1>x2?x-inc:x+inc;
				e=e-2*dx;
			}
			if(izq_der)
				x=x1>x2?x-inc:x+inc;	
			else
				y=y1>y2?y-inc:y+inc;
			e=e+2*dy;
		}				
	}


	public void Bresenham(int x1, int y1, int x2, int y2, int med_X)
	{	
		int x = x1;
		int y = y1;
		int temp;
		
		boolean izq_der=false;
		if(Math.abs(x1-x2)>Math.abs(y1-y2)) {
			izq_der=true;
		}
		int dx = Math.abs(x2-x1);
		int dy = Math.abs(y2-y1);		
		
		if(!izq_der) {
			temp = dx;
			dx=dy;
			dy=temp;
		}			
		int e =2*dy-dx;		
		for(int i=1; i<=dx; i++) {
			grafPto(new Punto(x,y));	
			if(med_X<x)
				plot.linea(new Punto(x, y), new Punto(med_X, y));
			else
				plot.linea( new Punto(med_X, y),new Punto(x, y));
			while(e>=0) {
				if(izq_der)
					y=y1>y2?y-1:y+1;
				else
					x=x1>x2?x-1:x+1;
				e=e-2*dx;
			}
			if(izq_der)
				x=x1>x2?x-1:x+1;	
			else
				y=y1>y2?y-1:y+1;
			e=e+2*dy;
		}				
	}
}

