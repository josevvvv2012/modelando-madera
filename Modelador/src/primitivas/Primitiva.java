package primitivas;

import java.util.Vector;

public abstract class Primitiva {

	Vector <Punto> coordenadas= new Vector<Punto>();
	
	public void grafPto(Punto p) {
		coordenadas.add(p);
	}
	public Punto getCoordenadas(int index) {
		return coordenadas.get(index);
	}
	
	public int getSizeCoordenadas() {
		return coordenadas.size();
	}
}
