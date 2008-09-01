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
	
	
	public int []recorteHoriz(int []linea, int max_min) {
		if(Math.abs(linea[0]-linea[2])<2)
			return new int[]{-1,-1};
		int x = (linea[0]+linea[2])/2;
		int y = (linea[1]+linea[3])/2;
		
		if(x<max_min)
			return recorteHoriz(new int[]{x,y, linea[2], linea[3]}, max_min);
		if(x>max_min)
			return recorteHoriz(new int[]{linea[0],linea[1], x, y}, max_min);
		else
			return new int[]{x,y};
	}
	public int []recorteVert(int []linea, int max_min) {
		if(Math.abs(linea[1]-linea[3])<2)
			return new int[]{-1,-1};
		int x = (linea[0]+linea[2])/2;
		int y = (linea[1]+linea[3])/2;
		
		if(y<max_min)
			return recorteVert(new int[]{x,y, linea[2], linea[3]}, max_min);
		if(y>max_min)
			return recorteVert(new int[]{linea[0],linea[1], x, y}, max_min);
		else
			return new int[]{x,y};
	}
	
	public  int puntosDentro(int []linea, int []ventana) {
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
	public  boolean  puntosDentroRango(int []linea, int []ventana) {
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
