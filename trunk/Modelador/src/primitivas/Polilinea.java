package primitivas;
import java.util.Hashtable;
import java.util.Vector;

/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public  class Polilinea extends Primitiva{
	//int []maxs ;
	//int []mins ;
	double miny, maxy;	
	Hashtable<Double, Double> maxs = new Hashtable<Double, Double>();
	Hashtable<Double, Double> mins = new Hashtable<Double, Double>();
	Vector <Punto> v = new Vector<Punto>();
	public Polilinea(Plot plot, Punto a, Punto b) {
		miny=10000000;
		maxy=0;
		this.plot = plot;
		this.z = a.getZ();
		v = new Vector<Punto>();
		v.add(a);
		v.add(b);
		Bresenham(a.getX(), a.getY(), b.getX(), b.getY());
		
		
		
		//DDA(plot, a, b);
	}
	
	public void graficar() {
		double maxPts = 100000;
		double step= 1/maxPts;
		double t = step;
		
		double[] Pxi, Pyi;
		Pxi = new double[v.size()];
		Pyi = new double[v.size()];
		double X, Y;
		
		System.out.println("-....");
		for(int i=0; i<v.size(); i++) {
			Pxi[i]=v.get(i).getX();
			Pyi[i]=v.get(i).getY();
		}
		 for (int k = 1; k < maxPts; k++){			   

			   for (int j = Pxi.length-1; j > 0; j--)        //  points calculation
			    for (int i = 0; i < j; i++){
			     Pxi[i] = (1-t)*Pxi[i] + t*Pxi[i+1];
			     Pyi[i] = (1-t)*Pyi[i] + t*Pyi[i+1];}

			   X = Pxi[0];  Y = Pyi[0];
			   grafPto(new Punto(X,Y, z));
			   //System.out.println("("+X+","+Y+","+t+")");
			   t += step;
			  }
	}
	
	public void agregarLinea(Punto a, Punto b) {
		v.add(b);
		//graficar();
		Bresenham(a.getX(), a.getY(), b.getX(), b.getY());
	}
	
	public void rellenarPolilinea(Plot plot) {
		for(double i=miny; i<maxy; i++) {
			plot.linea(new Punto(mins.get(i), i), new Punto(maxs.get(i), i));
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
			grafPto(new Punto(x, y));
			
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

