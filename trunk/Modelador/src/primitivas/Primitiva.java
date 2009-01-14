package primitivas;

import java.util.Vector;

public abstract class Primitiva {
	
	Vector<Punto> v1 = new Vector<Punto>();
	Vector<Punto> v2 = new Vector<Punto>();
	Vector<Punto> v3 = new Vector<Punto>();
	Vector<Punto> v4 = new Vector<Punto>();
	
	Vector <Punto> coordenadas= new Vector<Punto>();
	
	public double[] limX = new double[2];
	public double[] limY = new double[2];
	Plot plot;
	double z;
	double inc=1;
	double ptosPixel=4;
	double previoX=0;
	double previoY=0;
	
	public void iniciarLimites() {
		limX[0]=1000;
		limX[1]=-limX[0];
		
		limY[0]=1000;
		limY[1]=-limY[0];
	}
	public void modLim(double x, double y) {
		if(limX[0]>x) 
			limX[0]=x;
		if(limX[1]<x)
			limX[1]=x;
		
		if(limY[0]>y) 
			limY[0]=y;
		if(limY[1]<y)
			limY[1]=y;
	}
	public void mostrarLim() {
		System.out.println("X {"+limX[0]+", "+limX[1]+"}");
		System.out.println("Y {"+limY[0]+", "+limY[1]+"}");
	}
	public double getMidX() {
		return (limX[0]+limX[1])/2;
	}
	public double getMidY() {
		return (limY[0]+limY[1])/2;
	}
	
	public Vector<Punto> getPts(Vector<Punto> orig, double inc) {
		Vector<Punto> p = new Vector<Punto>();
		double tempX;
		double tempY;
		for(int i=0; i<orig.size(); i++) {
			tempX = orig.get(i).getX()<getMidX()?orig.get(i).getX()+inc:orig.get(i).getX()-inc;
			if(orig.get(i).getX()<getMidX()) {
				if(tempX>getMidX())
					tempX=getMidX();
			}
			else
				if(tempX<getMidX())
					tempX=getMidX();
			
			tempY = orig.get(i).getY()<getMidY()?orig.get(i).getY()+inc:orig.get(i).getY()-inc;
			if(orig.get(i).getY()<getMidY()) {
				if(tempY>getMidY())
					tempY=getMidY();
			}
			else
				if(tempY<getMidY())
					tempY=getMidY();
			
			p.add(new Punto(tempX, tempY, orig.get(i).getZ()));
			
		}
		
		return p;
	}
	
	public void grafPto(Punto p) {	
		previoX = p.getX();
		previoY= p.getY();
		modLim(p.getX(), p.getY());
		if(plot instanceof Plot){			
			plot.pixel(p);	
		}		
		else  {			
			coordenadas.add(p);
		}

			
	}
	public Punto getCoordenadas(int index) {
		return coordenadas.get(index);
	}
	
	public int getSizeCoordenadas() {
		return coordenadas.size();
	}
	public void insertarVectores(double x0, double y0, double x, double y) {
		v1.add(new Punto( (x0+x)   ,   (y0+y))  );
		v2.add(new Punto( (x0+x)   ,   (y0-y))  );			
		v3.add(new Punto( (x0-x)   ,   (y0-y))  ); 
		v4.add(new Punto( (x0-x)   ,   (y0+y))  );
	}
	public void iniciarVectores() {
		v1 = new Vector<Punto>();
		v2 = new Vector<Punto>();
		v3 = new Vector<Punto>();
		v4 = new Vector<Punto>();
	}
	public void graficarVectores() {
		for(int i=0; i<v1.size(); i++)
			grafPto(v1.get(i));
	
		for(int i=0; i<v2.size(); i++)
			grafPto(v2.get(i));
		
		for(int i=0; i<v3.size(); i++)
			grafPto(v3.get(i));
		
		for(int i=0; i<v4.size(); i++)
			grafPto(v4.get(i));
	}
	
	public double dist(double x0, double y0, double x1, double y1) {
		double x2 = Math.pow(x0 - x1, 2);
		double y2 = Math.pow(y0 - y1, 2);
		return Math.sqrt(x2+y2);
		
	}
}
