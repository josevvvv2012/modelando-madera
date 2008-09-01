package cinematica;


public class Inversa {

	static void get_angles(double x, double y, int M[])
	{
	   double Res_M0 = (360.0 / 800.0) * (8.0 / 44.0) * (8.0 / 60.0) * (Math.PI / 180.0);  	// en rad	1 u	= 0.010909	grados
	   double Res_M1 = (360.0 / 800.0) * (8.0 / 32.0) * (8.0 / 36.0) * (Math.PI / 180.0);  	// en rad	1 u 	= 0.025  	grados
	   double Res_M2 = (360.0 / 2000.0) * (1.0 / 19.7) * (8.0 / 20.0) * (Math.PI / 180.0);  	// en rad 	1 u    	= 0.003654 	grados 
	   double Res_M3 = (1.0 / 2000.0) * (1.0 / 6.3) * (25.4 / 11.0);  			// en mm 	1000 u 	= 0.18326 	mm 
	   int Def_M0 = 5000, Def_M1 = 5350, Def_M2 = 42500;
	   double l1 = 200.0, l2 = 200.0;	//en mm
	   double teta_global = 90.0*(Math.PI/180.0);

	   double ang1, ang2, ang3,  beta, phi;

	   ang2 = Math.acos( (x * x + y * y - l1 * l1 - l2 * l2)/ (2 * l1 * l2) );
	   
	   beta = Math.asin( y / ( Math.sqrt (x*x + y*y) ) );
	   if (x < 0) 
	      beta = Math.PI - beta;

	   phi = Math.acos ( (x*x + y*y + l1*l1 - l2*l2) /( 2 * l1 * Math.sqrt(x*x + y*y) ) );
	   ang1 = beta - phi;

	   ang3 = (teta_global - ang1 - ang2);
	   
	   M[0] = (int) (ang1 / Res_M0) + Def_M0; 
	   M[1] = (int) (ang2 / Res_M1) + Def_M1;
	   M[2] = (int) (ang3 / Res_M2) + Def_M2;
	   M[3] = 150000;
	}
    public static void main ( String[] args )
    {
    	int m[] = new int[4];
    	int x = 1;
    	int y = 2;    	
    	get_angles(x, y, m);
    	
    	System.out.print("W");
    	for(int i=0; i<m.length; i++) {
    		System.out.print(m[i]+" ");
    	}
    }
}
