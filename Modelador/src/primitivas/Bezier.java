package primitivas;

import java.util.Vector;

/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Bezier extends Primitiva{
	double maxPts;
	public Bezier(Plot plot, Vector<Punto> puntos, int pts) {
		this.plot = plot;
		this.z = puntos.get(0).getZ();
		if(pts==1)
			maxPts=500;
		else
			maxPts = pts;
		iniciarLimites();
		graficar(puntos);
		
		//if(plot==null) {
			
			double iter=Math.abs(limX[1]-limX[0])<Math.abs(limY[1]-limY[0])?Math.abs(limX[1]-limX[0]):Math.abs(limY[1]-limY[0]);
			//System.out.println("Iteraciones: "+iter);
			for(double ix =1; ix<iter; ix+=10) {
				//System.out.println("Iteraciones: "+ix);
				graficar(getPts(puntos, ix));	
			}
			
		//}
		
		
	}
	public Bezier(Plot plot, Punto a, Punto b, Punto c, Punto d, int pts) {
		this.plot = plot;
		this.z = a.getZ();
		maxPts=pts;
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
			grafPto(new Punto(x1,y1));
			//plot.linea(new Punto((int)x1, (int)y1), new Punto((int)x2, (int)y2), 1);											
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
		
		double []dest = new double[2];
		for(double i=1; i<maxPts; i++) {
			DeCasteljau(dest, i/maxPts, p1, p2, p3, p4);
			grafPto(new Punto(dest[0], dest[1]));
		}		
	}
	
	
	
	public void graficar(Vector<Punto> v) {
		double step= 1/maxPts;
		double Xant, Yant;
		double t = step;
		//System.out.println("t="+step);
		double[] Pxi, Pyi;
		Pxi = new double[v.size()];
		Pyi = new double[v.size()];
		double X, Y;	
		for(int i=0; i<v.size(); i++) {
			Pxi[i]=v.get(i).getX();
			Pyi[i]=v.get(i).getY();
		}
		Xant = -1000;
		Yant = -1000;
		 //for (int k = 1; k < 2; k++){			   
			 while(t<=1) {
				 for (int j = Pxi.length-1; j > 0; j--)
					    for (int i = 0; i < j; i++){
					     Pxi[i] = (1-t)*Pxi[i] + t*Pxi[i+1];
					     Pyi[i] = (1-t)*Pyi[i] + t*Pyi[i+1];}

					   X = Pxi[0];  Y = Pyi[0];
					   if(Xant!=-1000&&Yant!=-1000)
						   linea(Xant, Yant, X, Y, 20);
					   Xant = X;
					   Yant = Y;
					   //grafPto(new Punto(X,Y, z));
					   
					   t += step;
				 
			// }
		}
	}
	
	public void linea( double x0, double y0, double x1, double y1, int n)
	{
	   int i;
	   double inc_lambda = 1.0 / (n - 1.0);

	   for(i=0; i < n; i++) {
		   grafPto(new Punto(x0 + (i * inc_lambda) * (x1 - x0),y0 + (i * inc_lambda) * (y1 - y0)));
		   /*
	      x[i] = x0 + (i * inc_lambda) * (x1 - x0);
	      y[i] = y0 + (i * inc_lambda) * (y1 - y0);*/
	   }
	}

}
