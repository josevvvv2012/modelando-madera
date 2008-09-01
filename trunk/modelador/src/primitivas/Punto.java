package primitivas;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Punto {
	private int x;
	private int y;
	
	public Punto(int x, int y) {
		this.x = x;
		this.y = y;
	}

	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public double distancia(Punto b) {
		
		return Math.sqrt(Math.pow((x-b.getX()), 2)+ Math.pow((y-b.getY()), 2));
	}

}
