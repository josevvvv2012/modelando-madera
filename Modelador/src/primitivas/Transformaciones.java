package primitivas;

import ide.DEF;

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
	
	
	public void perspectiva() {
		double xc, yc, zc;
		xc=yc=zc=2;
		m = new double[][]{
				{-zc,0,0,0},
				{0,-zc,0,0},
				{xc,yc,0,1},
				{0,0,0,-zc }
			};
		
		//System.out.println("antes: "+punto.getX()+", "+punto.getY()+", "+punto.getZ());
		rotacion(45, DEF.vistaZX);		
		//System.out.println("rot B: "+punto.getX()+", "+punto.getY()+", "+punto.getZ());
		rotacion(35.264, DEF.vistaZY);		
		//System.out.println("rot A: "+punto.getX()+", "+punto.getY()+", "+punto.getZ());
	}
	
	public void actualizar() {
		punto.setX(x[0]);
		punto.setY(x[1]);	
		punto.setZ(x[2]);
	}
	public Transformaciones(Punto p) {
		punto = p;
		b = new double[] {punto.getX(), punto.getY(), punto.getZ(), 1};
	}
	public void setPunto(Punto p) {
		punto = p;
	}
	public Punto getPunto() {
		return punto;
	}
	public void traslacion(double tx, double ty, double tz) {
		m = new double[][]{
						{1,0,0,tx},
						{0,1,0,ty},
						{0,0,1,tz},
						{0,0,0,1 }
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
	

	
	
	public void escalamiento(double sx, double sy, double sz) {
		m = new double[][]{
						{sx,0, 0, 0},
						{0, sy,0, 0},
						{0, 0,sz, 0},
						{0, 0, 0, 1}
					};

		x = productoMatricial(m, b);
		actualizar();
	}		

	public void rotacion(double theta, int eje) {
		
		double [][]m2=null;
		double cosAng = Math.cos(theta*(Math.PI/180));
		double sinAng = Math.sin(theta*(Math.PI/180));
		switch(eje) {
			case DEF.vistaZY:
			m2 = new double[][]{
					{1,			0,			0,		0},
					{0,	   cosAng,    sinAng, 		0},
					{0,	   -sinAng,     cosAng,		0},
					{0, 		0,			0,		1}
				};
			break;
			case DEF.vistaZX:
				m2 = new double[][]{
						{cosAng,	0,     -sinAng, 		0},
						{0,			1,			0,		0},
						
						{sinAng,	0,     cosAng,		0},
						{0, 		0,			0,		1}
					};
				break;
			case DEF.vistaYX:
				m2 = new double[][]{
						{cosAng,  -sinAng,		0, 		0},
						{sinAng,   cosAng,		0,		0},
						{0,		 		0,		1,		0},
						{0, 		0,			0,		1}
					};
				break;
		}
		
		
		
	/*	
		= new double[][]{
						{Math.cos(theta*(Math.PI/180)),  -Math.sin(theta*(Math.PI/180)), 0},
						{Math.sin(theta*(Math.PI/180)),   Math.cos(theta*(Math.PI/180)),0},
						{0, 0, 1 }
					};
*/
		double []x2 = productoMatricialDouble(m2, b);
		x = new double[x2.length];		
		x[0] = (int)x2[0];		
		x[1] = (int)x2[1];	
		x[2] = (int)x2[2];
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
