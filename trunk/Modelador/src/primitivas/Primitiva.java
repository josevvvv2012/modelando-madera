package primitivas;

import java.util.Vector;

public abstract class Primitiva {

	Vector <Punto> coordenadas= new Vector<Punto>();
	Plot plot;
	double z;
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
}
