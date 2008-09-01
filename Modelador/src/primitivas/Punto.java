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
	private int z;
	
	public Punto(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
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
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public double distancia(Punto b) {
		
		return Math.sqrt(Math.pow((x-b.getX()), 2)+ Math.pow((y-b.getY()), 2));
	}

}
