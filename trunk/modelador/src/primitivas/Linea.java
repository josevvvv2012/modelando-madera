package primitivas;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public  class Linea {
	int []pts ;
	public Linea(Plot plot, Punto a, Punto b, int med_X) {
		Bresenham(plot, a.getX(), a.getY(), b.getX(), b.getY(), med_X);
		//DDA(plot, a, b);
	}
	public Linea(Plot plot, Punto a, Punto b) {
		Bresenham(plot, a.getX(), a.getY(), b.getX(), b.getY());
		//DDA(plot, a, b);
	}
	public void DDA(Plot plot, Punto a, Punto b) {				
		int dx = b.getX()-a.getX();
		int dy = b.getY()-a.getY();		
		int k =(dx>dy)?Math.abs(dx):Math.abs(dy);
		
		float x=a.getX();
		float y=a.getY();
		float xinc= (float)dx/k;
		float yinc= (float)dy/k;
				
		
		plot.pixel(a);
		
		for(int i=0; i<k; i++) {
			x=x+xinc;
			y=y+yinc;
			plot.pixel(new Punto(Math.round(x), Math.round(y)));			
		}		
	}
	
	
	public void Bresenham(Plot plot, int x1, int y1, int x2, int y2)
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
			plot.pixel(new Punto(x, y));
			
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


	public void Bresenham(Plot plot, int x1, int y1, int x2, int y2, int med_X)
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
			plot.pixel(new Punto(x, y));
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

