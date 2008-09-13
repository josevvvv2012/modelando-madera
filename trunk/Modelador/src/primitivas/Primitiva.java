package primitivas;

import java.util.Vector;

public abstract class Primitiva {
	
	Vector<Punto> v1 = new Vector<Punto>();
	Vector<Punto> v2 = new Vector<Punto>();
	Vector<Punto> v3 = new Vector<Punto>();
	Vector<Punto> v4 = new Vector<Punto>();
	
	Vector <Punto> coordenadas= new Vector<Punto>();
	Plot plot;
	double z;
	double inc=1;
	
	
	public void grafPto(Punto p) {	
		System.out.println("("+p.getX()+", "+p.getY()+")");
		
		if(plot instanceof Plot){			
			plot.pixel(p);	
		}		
		else {
			p.setZ( z );
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
}
