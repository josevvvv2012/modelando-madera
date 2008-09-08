package primitivas;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Elipse extends Primitiva{
	public Elipse(Plot plot, Punto orig,int rx, int ry, boolean relleno) {
		this.plot = plot;
		puntoMedio(orig, rx, ry, relleno);
	}
	public void puntoMedio(Punto orig,int rx, int ry, boolean relleno) {
		int ry2=ry*ry;
		int rx2=rx*rx;
		int x=0;
		int y=ry;
		int p1k=ry2-rx2*ry+(rx2/4);
		int p2k=0;
		boolean px=true;
		while(y>0) {
			if( ((2*ry2*x))<(2*rx2*y)) {
				x++;
				if(p1k<0) {
					p1k=p1k+2*(x)*ry2+ry2;
				}
				else {
					y--;
					p1k=p1k+2*ry2*x-2*rx2*y+ry2;
				}	
			}
			else {
				if(px) {
					p2k=ry2*(x+1/2)*(x+1/2)+rx2*(y-1)*(y-1)-rx2*ry2;
					px=false;
				}
				y--;
				if(p2k<0) {
					x++;
					p2k=p2k+2*ry2*x-2*rx2*y+rx2;
				}
				else {
					p2k=p2k-2*rx2*y+rx2;
				}
				
				
			}
			grafPto(new Punto(orig.getX()+x,orig.getY()+y));
			grafPto(new Punto(orig.getX()-x,orig.getY()+y));
			grafPto(new Punto(orig.getX()-x,orig.getY()-y));
			grafPto(new Punto(orig.getX()+x,orig.getY()-y));
			if(relleno) {
				plot.linea(new Punto(orig.getX()+x,orig.getY()+y), new Punto(orig.getX()-x,orig.getY()+y));
				plot.linea(new Punto(orig.getX()-x,orig.getY()-y),new Punto(orig.getX()+x,orig.getY()-y));
				
			}
		}						
	}
}
