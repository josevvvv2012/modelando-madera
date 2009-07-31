package primitivas;

import ide.DEF;

import java.util.Vector;

/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Bezier extends Primitiva{
	double maxPts;
	double Xant, Yant;
	double Xant2, Yant2;
	double x,y,z;
	int opc;
	public Bezier(Plot plot, Vector<Punto> puntos, int pts, int vista) {
		this.plot = plot;
		this.vista = vista;
		opc =1;
		this.z = puntos.get(0).getZ();
		this.x = puntos.get(0).getX();
		this.y = puntos.get(0).getY();
		if(puntos.get(0).getX()==puntos.get(1).getX())
			opc=DEF.vistaZY;
		if(puntos.get(0).getY()==puntos.get(1).getY())
			opc=DEF.vistaZX;
		if(puntos.get(0).getZ()==puntos.get(1).getZ())
			opc=DEF.vistaYX;
		
		if(pts==1)
			maxPts=500;
		else
			maxPts = pts;
		iniciarLimites();
		
		Xant2 = 1;
		Yant2 = 1;
		switch(opc) {
			case DEF.vistaYX: Xant2 = puntos.get(0).getX();	Yant2 = puntos.get(0).getY(); break;
			case DEF.vistaZY: Xant2 = puntos.get(0).getY();	Yant2 = puntos.get(0).getZ(); break;
			case DEF.vistaZX: Xant2 = puntos.get(0).getX();	Yant2 = puntos.get(0).getZ(); break;
		}		
		graficar(puntos);
	}
	public void graficar(Vector<Punto> v) {
		double step= 1/maxPts;		
		double t = step;
		double[] Pxi, Pyi;
		Pxi = new double[v.size()];
		Pyi = new double[v.size()];
		double X, Y;	
		for(int i=0; i<v.size(); i++) {	
			switch(opc) {
				case DEF.vistaYX: Pxi[i]=v.get(i).getX(); Pyi[i]=v.get(i).getY(); break;
				case DEF.vistaZY: Pxi[i]=v.get(i).getY(); Pyi[i]=v.get(i).getZ(); break;
				case DEF.vistaZX: Pxi[i]=v.get(i).getX(); Pyi[i]=v.get(i).getZ(); break;
			}
			
			
		}
		switch(opc) {
			case DEF.vistaYX: new Linea(plot, new Punto(Xant2, Yant2, z), new Punto( v.get(0).getX(), v.get(0).getY(), z), vista); Xant2 = v.lastElement().getX(); Yant2 = v.lastElement().getY(); break;
			case DEF.vistaZY: new Linea(plot, new Punto(x, Xant2, Yant2), new Punto( x, v.get(0).getY(), v.get(0).getZ()), vista); Xant2 = v.lastElement().getY(); Yant2 = v.lastElement().getZ(); break;
			case DEF.vistaZX: new Linea(plot, new Punto(Xant2, y, Yant2), new Punto( v.get(0).getX(), y, v.get(0).getZ()), vista); Xant2 = v.lastElement().getX(); Yant2 = v.lastElement().getZ(); break;
		}
		Xant = -1000;
		Yant = -1000;
		while(t<=1) {
			for (int j = Pxi.length-1; j > 0; j--)
				for (int i = 0; i < j; i++){
					Pxi[i] = (1-t)*Pxi[i] + t*Pxi[i+1];
				    Pyi[i] = (1-t)*Pyi[i] + t*Pyi[i+1];
			    }
			X = Pxi[0];  Y = Pyi[0];
			if(Xant!=-1000&&Yant!=-1000) {
				switch(opc) {
					case DEF.vistaYX: new Linea(plot, new Punto(Xant, Yant, z), new Punto(X, Y, z), vista); break;
					case DEF.vistaZY: new Linea(plot, new Punto(x, Xant, Yant), new Punto(x, X, Y), vista); break;
					case DEF.vistaZX: new Linea(plot, new Punto(Xant, y, Yant), new Punto(X, y, Y), vista); break;
				}
			}
			Xant = X;
			Yant = Y;
			t += step;
		}
	}
}
