package primitivas;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Transformaciones {
	private Punto punto;
	private int [][]m;
	private int []b;
	private int []x;
	
	
	public void actualizar() {
		punto.setX(x[0]);
		punto.setY(x[1]);		
	}
	public Transformaciones(Punto p) {
		punto = p;
		b = new int[] {punto.getX(), punto.getY(), 1};
	}
	public void setPunto(Punto p) {
		punto = p;
	}
	public Punto getPunto() {
		return punto;
	}
	public void traslacion(int tx, int ty) {
		m = new int[][]{
						{1,0,tx},
						{0,1,ty},
						{0,0,1 }
					};

		x = productoMatricial(m, b);
		actualizar();
	}
	
	public void reflexion() {
		m = new int[][]{
						{1, 0,0},
						{0,-1,0},
						{0, 0,1 }
					};

		x = productoMatricial(m, b);
		actualizar();
	}
	

	
	
	public void escalamiento(int sx, int sy) {
		m = new int[][]{
						{sx,0, 0},
						{0, sy,0},
						{0, 0, 1 }
					};

		x = productoMatricial(m, b);
		actualizar();
	}		

	public void rotacion(int theta) {
		
		double [][]m2 = new double[][]{
						{Math.cos(theta*(Math.PI/180)),  -Math.sin(theta*(Math.PI/180)), 0},
						{Math.sin(theta*(Math.PI/180)),   Math.cos(theta*(Math.PI/180)),0},
						{0, 0, 1 }
					};

		double []x2 = productoMatricialDouble(m2, b);
		x = new int[x2.length];		
		x[0] = (int)x2[0];		
		x[1] = (int)x2[1];		
		actualizar();
	}		
	
	public int []productoMatricial(int [][]m, int []b) {
		int []x;
		x = new int[b.length];
		for(int i=0; i<b.length; i++) {
			x[i]=0;
			for(int j=0; j<b.length; j++) {
				x[i]+=m[i][j]*b[j];
			}
		}
		
		return x;
	}
	
	public double []productoMatricialDouble(double [][]m, int []b) {
		double []x;
		x = new double[b.length];
		for(int i=0; i<b.length; i++) {
			x[i]=0;
			for(int j=0; j<b.length; j++) {
				x[i]+=m[i][j]*b[j];
			}			
		}
		
		return x;
	}

}
