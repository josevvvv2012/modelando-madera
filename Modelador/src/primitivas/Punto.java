package primitivas;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Punto {
	private double x;
	private double y;
	private double z;
	
	public Punto(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Punto(double x, double y) {
		this.x = x;
		this.y = y;
	}

	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public double distancia(Punto b) {
		
		return Math.sqrt(Math.pow((x-b.getX()), 2)+ Math.pow((y-b.getY()), 2));
	}

}
