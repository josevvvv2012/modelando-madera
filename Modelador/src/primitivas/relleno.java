package primitivas;

import java.util.Hashtable;
import org.eclipse.swt.graphics.ImageData; 

/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public  class relleno {
	Plot p;
	ImageData imageBuffer;
	int iteraciones;
	int maxX = 300;
	int maxY = 300;
	private Hashtable<Double, Integer> ht = new Hashtable<Double, Integer>();
	
	public relleno(Plot plot, Punto a) {				
		this.p = plot;
		iteraciones =0;
		imageBuffer = p.gc.getGCData().image.getImageData();
		Semilla(a.getX(), a.getY());
		//SemillaOriginal(a.getX(), a.getY(),0);
		
		System.out.println("Iteraciones = "+iteraciones);
		
	}
	
	public void SemillaOriginal(int x, int y, int opc) {
		System.out.println("pixel -> "+x+", "+y);
		iteraciones++;
		if(imageBuffer.getPixel(x, y)==0) {
			
			p.pixel(x,y);
			
			
			SemillaOriginal(x+1, y, 2);
			SemillaOriginal(x-1, y, 1);
			SemillaOriginal(x, y+1, 3);
			SemillaOriginal(x, y-1, 4);					
			
		}
		else {
			imageBuffer = p.gc.getGCData().image.getImageData();
		}				
	}
	

	
	public void Semilla(double x, double y) {
		iteraciones++;
		System.out.println("pixel -> "+x+", "+y);
		System.out.println("iteraciones= "+iteraciones+"cta= "+ht.size());
		Double dbl;
		
		if(imageBuffer.getPixel((int)x, (int)y)==0) {
			p.pixel((int)x,(int)y);
			dbl=(double)x;
			dbl+=(double)y/1000;
			
			if(!ht.containsKey( dbl+1 ) ) {
				ht.put(dbl+1, 1);
				Semilla(x+1, y);
			}
			
			if(!ht.containsKey( dbl-1 ) ) {
				ht.put(dbl-1, 1);
				Semilla(x-1, y);
			}
						
			if(!ht.containsKey( dbl+((double)1/1000) ) ) {
				ht.put(dbl+((double)1/1000), 1);
				Semilla(x, y+1);
			}
			
			if(!ht.containsKey( dbl-((double)1/1000) ) ) {
				ht.put(dbl-((double)1/1000), 1-1);
				Semilla(x, y-1);
			}
			
		
			
					
		}
		else {
			imageBuffer = p.gc.getGCData().image.getImageData();
			//ht.clear();
		}
	}
	
	
	
}
