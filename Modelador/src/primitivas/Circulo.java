package primitivas;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Circulo extends Primitiva{
	public Circulo(Plot plot, Punto orig,int r, boolean relleno) {
		this.plot = plot;
		Bresenham(r, orig, relleno);
	}
	public void puntoMedio(int r, Punto orig) {
		int x0=0;		
		int y0=r;
		int pk=5/4-r^2;
		while(x0<=r) {
			grafPto(new Punto( (orig.getX()+x0)   ,   (orig.getY()+y0))  );
			grafPto(new Punto( (orig.getX()-x0)   ,   (orig.getY()+y0))  );
			grafPto(new Punto( (orig.getX()-x0)   ,   (orig.getY()-y0))  );
			grafPto(new Punto( (orig.getX()+x0)   ,   (orig.getY()-y0))  );
			if(pk<0) {
				pk=pk+2*(x0+1)+1;
			}
			else {
				y0--;
				pk=pk+2*(x0+1)+1-2*y0;										
			}
			
			
			
			x0++;
			
		}		
	}
	public void Bresenham(int r, Punto orig, boolean relleno ) {
		int x = 0;
		int y = r;
		int d = 2-2*r;
		int d2, d3;
		while(y>=0) {
			
			grafPto(new Punto(orig.getX()+x, orig.getY()+y));
			grafPto(new Punto(orig.getX()+x, orig.getY()-y));
			grafPto(new Punto(orig.getX()-x, orig.getY()-y));
			grafPto(new Punto(orig.getX()-x, orig.getY()+y));
			
			
			if(relleno) {
				plot.linea(new Punto(orig.getX()+x, orig.getY()+y), new Punto(orig.getX()-x, orig.getY()+y));
				plot.linea(new Punto(orig.getX()+x, orig.getY()-y), new Punto(orig.getX()-x, orig.getY()-y));					
			}
			
		
			if(d<0) {
				d2=2*d+2*y-1;
				if(d2<0) {
					x++;
					d=d+2*x+1;
				}
				else {
					x++;
					y--;
					d=d+2*x-2*y+2;				
				}
			}
			else if(d>0){
				d3=2*d-2*x-1;
				if(d3<=0) {
					x++;
					y--;
					d=d+2*x-2*y+2;
				}
				else {
					y--;
					d=d-2*y+1;
				}				
			}
			else {
				x++;
				y--;
				d=d+2*x-2*y+2;
			}
			
		}
	}
}
