package store;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;

import primitivas.Punto;

public class PGMRead {
	Vector <Integer> pgm = new Vector<Integer>();
	private final File fFile;
	private int max=0;
	
	 public static void main(String... aArgs) throws FileNotFoundException {
		    PGMRead parser = new PGMRead("./sample.pgm");
		    parser.processLineByLine();		
		    parser.print(null);
	  }
	 public void cargar(ide.ListaFiguras listaFiguras) throws FileNotFoundException {
		 this.processLineByLine();
		 this.print(listaFiguras);
	 }
		  public PGMRead(String aFileName){
		    fFile = new File(aFileName);  
		  }

		  public final void processLineByLine() throws FileNotFoundException {
		    Scanner scanner = new Scanner(fFile);		    
		    try {
		      while ( scanner.hasNextLine() ){
		        processLine( scanner.nextLine() );
		      }
		    }
		    finally {
		      scanner.close();
		    }
		  }
		  
		  
		  protected void processLine(String aLine){
		   
		    Scanner scanner = new Scanner(aLine);
		    //scanner.useDelimiter(" ");
		    
		    while( scanner.hasNext() ){
		    	String t = scanner.next();
		    	if(t.startsWith("#") || t.startsWith("P")) {
		    		break;
		    	}
		    	else {
		    		addVal(t);
		    		//log(t);	
		    	}		    			      
		    }
		    
		    scanner.close();
		  }
		  
		  
		  
		 
		  private void addVal(String t) {
			  pgm.add(Integer.valueOf(t));
			  
			  if((pgm.lastElement()>max)&&(pgm.size()>3))
				  max = pgm.lastElement();
		  }
		  private void get() {
			  Vector<Punto> p = new Vector<Punto>();
			  int pos;
			  for(int y=0; y<pgm.get(1); y++) {
				  for(int x=0; x<pgm.get(0); x++) {
					  pos =(x+(y*pgm.get(0)));
					  System.out.print(pgm.get(pos+3)+"  ");
				  }
				  System.out.print("\n");
			  }
			  
		  }
		  private void print(ide.ListaFiguras listaFiguras) {			  
			  Vector<Punto> p = new Vector<Punto>();
			  System.out.println(pgm.get(0)+"x"+pgm.get(1));
			  System.out.println("Color: "+pgm.get(2)+" :: "+max);
			  System.out.println("Total: "+(pgm.size()-3));
			  int pos;
			  boolean izqAder=true;			  
			  int xant, yant, zant;
			  zant=pgm.get(3);
			  xant=0;
			  yant=0;
			  for(int y=0; y<pgm.get(1); y++) {
				  
				  for(int x=0; x<pgm.get(0); x++) {
					  p = new Vector<Punto>();
					  if(izqAder) {
						  pos =(x+(y*pgm.get(0)));
						  //System.out.print(pgm.get(pos+3)+"  ");
						  
						  p.add(new Punto(xant, yant, zant));
						  p.add(new Punto(x, y, pgm.get(pos+3)));
						  
						  //System.out.print("L("+xant+", "+yant+", "+zant+"  ->  "+x+", "+y+", "+pgm.get(pos+3)+") ");
						  xant=x; 
						  yant=y;
						  zant=pgm.get(pos+3);						  
					  }
					  else {
						  pos =(((y+1)*pgm.get(0))-1-x);
						 // System.out.print(pgm.get(pos+3)+"  ");
						  
						  p.add(new Punto(xant, yant, zant));
						  p.add(new Punto((pgm.get(0)-x-1), y, pgm.get(pos+3)));
						  
						  //System.out.print("L("+xant+", "+yant+", "+zant+"  ->  "+(pgm.get(0)-x-1)+", "+y+", "+pgm.get(pos+3)+") ");
						  xant=pgm.get(0)-x-1; 
						  yant=y;
						  zant=pgm.get(pos+3);	
					  }
					  listaFiguras.insertarFigura(2, false, p);
				  }
				  izqAder=izqAder?false:true;
				  
				  System.out.print("\n");
			  }			 
		  }
}
