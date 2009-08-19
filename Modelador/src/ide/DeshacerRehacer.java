package ide;

import primitivas.Figuras;
import primitivas.Punto;


public class DeshacerRehacer {
	public int id;
	public int operacion;
	public Figuras p;
	public Punto p2;
	public int punto;
	public double theta;
	public int vista;
	
	public DeshacerRehacer(int id, int operacion, Figuras p) {
		this.id =id;
		this.operacion=operacion;
		if(p instanceof Figuras)
			this.p = p.copiar();
		else
			this.p = null;
	}
	
	public DeshacerRehacer(int id, int operacion, Punto p) {
		this.id =id;
		this.operacion=operacion;
		this.p2 = p;
	}
	
	public DeshacerRehacer(int id, int operacion, Punto p, int punto) {
		this.id =id;
		this.operacion=operacion;
		this.p2 = p;
		this.punto = punto;
	}
	
	public DeshacerRehacer(int id, int operacion, double theta, int vista) {
		this.id =id;
		this.operacion=operacion;
		this.theta = theta;
		this.vista = vista;
	}
	

}
