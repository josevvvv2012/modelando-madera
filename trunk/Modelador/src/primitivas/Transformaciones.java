package primitivas;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Transformaciones {
	private Punto punto;
	private double [][]m;
	private double []b;
	private double []x;
	
	
	public void actualizar() {
		punto.setX(x[0]);
		punto.setY(x[1]);		
	}
	public Transformaciones(Punto p) {
		punto = p;
		b = new double[] {punto.getX(), punto.getY(), 1};
	}
	public void setPunto(Punto p) {
		punto = p;
	}
	public Punto getPunto() {
		return punto;
	}
	public void traslacion(double tx, double ty) {
		m = new double[][]{
						{1,0,tx},
						{0,1,ty},
						{0,0,1 }
					};

		x = productoMatricial(m, b);
		actualizar();
	}
	
	public void reflexion() {
		m = new double[][]{
						{1, 0,0},
						{0,-1,0},
						{0, 0,1 }
					};

		x = productoMatricial(m, b);
		actualizar();
	}
	

	
	
	public void escalamiento(double sx, double sy) {
		m = new double[][]{
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
		x = new double[x2.length];		
		x[0] = (int)x2[0];		
		x[1] = (int)x2[1];		
		actualizar();
	}		
	
	public double []productoMatricial(double [][]m, double []b) {
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
	
	public double []productoMatricialDouble(double [][]m, double []b) {
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
