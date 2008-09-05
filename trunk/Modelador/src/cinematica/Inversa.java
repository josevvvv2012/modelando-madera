package cinematica;

import primitivas.Punto;

public class Inversa {
	
	double Res_M[] = new double[]{
			(360.0 / 800.0) * (8.0 / 44.0) * (8.0 / 60.0) * (Math.PI / 180.0),		// en rad	1 u		= 0.010909	grados
			(360.0 / 800.0) * (8.0 / 32.0) * (8.0 / 36.0) * (Math.PI / 180.0),  	// en rad	1 u		= 0.010909	grados
			(360.0 / 2000.0) * (1.0 / 19.7) * (8.0 / 20.0) * (Math.PI / 180.0),  	// en rad 	1 u    	= 0.003654 	grados
			(1.0 / 2000.0) * (1.0 / 6.3) * (25.4 / 11.0)							// en mm 	1000 u 	= 0.18326 	mm
		};
	
	int Def_M[]  = new int[]{
			   5000, 
			   5350, 
			   42500,
			   150000
		  };
	
	double longs[]= new double[] {
		200.0,			// mm
		200				// mm
	};
	
	double teta_global = 90.0*(Math.PI/180.0);

	public void get_angles(Punto p, int M[]){
		get_angles(p.getX(), p.getY(), M);
		M[3]=p.getZ();
	}
	
	public void get_angles(double x, double y, int M[])
	{
		double x2 = Math.pow(x, 2);
		double y2 = Math.pow(y, 2);
		double l1 = Math.pow(longs[0], 2);
		double l2 = Math.pow(longs[1], 2);		
		double angs[] = new double[3];
		double beta, phi;
		   	   
		beta = Math.asin( y / ( Math.sqrt (x2 + y2) ) );
		if (x < 0) 
			beta = Math.PI - beta;

		phi = Math.acos ( (x2 + y2 + l1 - l2) /( 2 * longs[0] * Math.sqrt(x2 + y2) ) );
	   	   
		angs[0] =beta - phi;		
		angs[1]= Math.acos( (x2 + y2 - l1 - l2 )/ (2 * longs[0] * longs[1]) );		
		angs[2]= (teta_global - angs[0] - angs[1]);
		
		M[0] = (int) (angs[0] / Res_M[0]) + Def_M[0]; 
		M[1] = (int) (angs[1] / Res_M[1]) + Def_M[1];
		M[2] = (int) (angs[2] / Res_M[2]) + Def_M[2];
		M[3] = Def_M[3];	   
	}
		 	
	
	
    public static void main ( String[] args )
    {
    	int m[] = new int[4];
    	int x = 1;
    	int y = 2;
    	
    	new Inversa().get_angles(x, y, m);
    	
    	System.out.print("W");
    	for(int i=0; i<m.length; i++) {
    		System.out.print(m[i]+" ");
    	}    	
    	
    }
}
