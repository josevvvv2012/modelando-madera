package primitivas;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Recorte {

	public Recorte() {
		
	}
	
	
	public double []recorteHoriz(double []linea, double max_min) {
		if(Math.abs(linea[0]-linea[2])<2)
			return new double[]{-1,-1};
		double x = (linea[0]+linea[2])/2;
		double y = (linea[1]+linea[3])/2;
		
		if(x<max_min)
			return recorteHoriz(new double[]{x,y, linea[2], linea[3]}, max_min);
		if(x>max_min)
			return recorteHoriz(new double[]{linea[0],linea[1], x, y}, max_min);
		else
			return new double[]{x,y};
	}
	public double []recorteVert(double []linea, double max_min) {
		if(Math.abs(linea[1]-linea[3])<2)
			return new double[]{-1,-1};
		double x = (linea[0]+linea[2])/2;
		double y = (linea[1]+linea[3])/2;
		
		if(y<max_min)
			return recorteVert(new double[]{x,y, linea[2], linea[3]}, max_min);
		if(y>max_min)
			return recorteVert(new double[]{linea[0],linea[1], x, y}, max_min);
		else
			return new double[]{x,y};
	}
	
	public  int puntosDentro(double []linea, double []ventana) {
		int cont=0;
		if((linea[0]>=ventana[0])&&(linea[0]<=ventana[2])) {
			if((linea[1]>=ventana[1])&&(linea[1]<=ventana[3])) {
				cont++;	
			}			
		}
		if((linea[2]>=ventana[0])&&(linea[2]<=ventana[2])) {
			if((linea[3]>ventana[1])&&(linea[3]<ventana[3])) {
				cont++;	
			}			
		}
		return cont;
	}
	public  boolean  puntosDentroRango(double []linea, double []ventana) {
		if((linea[0]>=ventana[0])&&(linea[0]<=ventana[2])) {			
			return true;
		}
		if((linea[1]>=ventana[1])&&(linea[1]<=ventana[3])) {			
			return true;
		}
		
		if((linea[2]>ventana[0])&&(linea[2]<ventana[2])) {			
			return true;
		}
		if((linea[3]>ventana[1])&&(linea[3]<ventana[3])) {			
			return true;
		}
		
		return false;
	}
	
	
}
