package primitivas;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Bezier extends Primitiva{
	public Bezier(Plot plot, Punto a, Punto b, Punto c, Punto d) {
		this.plot = plot;
		//BezierAproximacion(plot, a, b, c, d);
		BezierDeCasteljau(a, b, c, d);
	}
	
	public void BezierAproximacion(Plot plot, Punto a, Punto b, Punto c, Punto d) {
		double x1, y1, x2, y2;
		double t, k = 0.025;										
		x1 = a.getX();
		y1 = a.getY();
		for(t=k;t<=1+k;t+=k){
			x2=(a.getX()+t*(-a.getX()*3+t*(3*a.getX()-
			a.getX()*t)))+t*(3*b.getX()+t*(-6*b.getX()+
			b.getX()*3*t))+t*t*(c.getX()*3-c.getX()*3*t)+
			c.getX()*t*t*t;
			y2=(a.getY()+t*(-a.getY()*3+t*(3*a.getY()-
			a.getY()*t)))+t*(3*b.getY()+t*(-6*b.getY()+
			b.getY()*3*t))+t*t*(c.getY()*3-c.getY()*3*t)+
			d.getY()*t*t*t;
			plot.linea(new Punto((int)x1, (int)y1), new Punto((int)x2, (int)y2));											
			x1 = x2;
			y1 = y2;
		}
	}
	
	
	void interpolacion (double []dest, double []a, double []b, double t)
	{
		dest[0] = a[0] + (b[0]-a[0])*t;
		dest[1] = a[1] + (b[1]-a[1])*t; 	  
	}
	public void DeCasteljau(double []dest, double t, double []a, double []b, double []c, double []d) {
		double []ab = new double[2];
		double []bc= new double[2];
		double []cd= new double[2];
		double []abbc= new double[2];
		double []bccd= new double[2];
		interpolacion(ab, a,b,t);        
		interpolacion(bc, b,c,t);        
		interpolacion(cd, c,d,t);        
		interpolacion(abbc, ab,bc,t);    
		interpolacion(bccd, bc,cd,t);    
		interpolacion(dest, abbc,bccd,t); 
	}
	public void BezierDeCasteljau(Punto a, Punto b, Punto c, Punto d) {
		double []p1 = new double[]{a.getX(), a.getY()};
		double []p2 = new double[]{b.getX(), b.getY()};
		double []p3 = new double[]{c.getX(), c.getY()};
		double []p4 = new double[]{d.getX(), d.getY()};
		double maxPts = 1000;
		double []dest = new double[2];
		for(double i=1; i<maxPts; i++) {
			DeCasteljau(dest, i/maxPts, p1, p2, p3, p4);
			grafPto(new Punto((int)dest[0], (int)dest[1]));
		}		
	}
}
