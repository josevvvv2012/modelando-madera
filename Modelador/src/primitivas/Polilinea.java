package primitivas;
import java.util.Hashtable;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public  class Polilinea {
	//int []maxs ;
	//int []mins ;
	int miny, maxy;	
	Hashtable<Integer, Integer> maxs = new Hashtable<Integer, Integer>();
	Hashtable<Integer, Integer> mins = new Hashtable<Integer, Integer>();
	public Polilinea(Plot plot, Punto a, Punto b) {
		miny=10000000;
		maxy=0;
		Bresenham(plot, a.getX(), a.getY(), b.getX(), b.getY());
		//DDA(plot, a, b);
	}
	
	public void agregarLinea(Plot plot, Punto a, Punto b) {
		Bresenham(plot, a.getX(), a.getY(), b.getX(), b.getY());
	}
	
	public void rellenarPolilinea(Plot plot) {
		for(int i=miny; i<maxy; i++) {
			plot.linea(new Punto(mins.get(i), i), new Punto(maxs.get(i), i));
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
			if(maxs.containsKey(y)) {
				if(maxs.get(y)<x) {
					maxs.put(y,x);
					
				}
				if(mins.get(y)>x) {
					mins.put(y,x);
					
				}			
			}
			else {
				maxs.put(y, x);
				mins.put(y, x);
			}
			if(miny>y)
				miny=y;
			if(maxy<y)
				maxy=y;						
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
}

