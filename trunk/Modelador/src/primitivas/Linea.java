package primitivas;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Linea extends Primitiva{
	
	public Linea(Plot plot, Punto a, Punto b, int vista) {
		this.plot = plot;
		this.vista = vista;
		this.z = a.getZ();		
		this.linea(a, b);
		
	}
	
	public void linea( Punto a, Punto b)
	{
	
	   int i;
	   int n=(int)(dist(a.getX(),a.getY(), b.getX(),b.getY())*ptosPixel);
	   n = 200;
	   double inc_lambda = 1.0 / (n - 1.0);
	   
	   for(i=0; i < n; i++) {
		   grafPto(new Punto(a.getX() + (i * inc_lambda) * (b.getX() - a.getX()),
				             a.getY() + (i * inc_lambda) * (b.getY() - a.getY()),
				             a.getZ() + (i * inc_lambda) * (b.getZ() - a.getZ())
		   					)
		   			);		
	   }			   
	}	
}

