package store;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;
import primitivas.Punto;

public class PGMRead {
	Vector <Integer> pgm = new Vector<Integer>();
	private final File fFile;
	private int max=0;
	private int pixelPto=2;
	private int pts=30;
	private int incX = 120; //100;
	private int incY = 300; //290;
	private int incZ = 265000;
	
	 public void cargar(ide.ListaFiguras listaFiguras) throws FileNotFoundException {
		 this.processLineByLine();
		 this.print(listaFiguras);
	 }
	 public PGMRead(String aFileName, int z){
		 this.incZ=z;
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
		 while( scanner.hasNext() ){
			 String t = scanner.next();
		     if(t.startsWith("#") || t.startsWith("P")) {
		    	 break;
	    	 }
		     else {
		    	 addVal(t);	
	    	 }
	     }
		 scanner.close();
	 }
	private void addVal(String t) {
		pgm.add(Integer.valueOf(t));
		if((pgm.lastElement()>max)&&(pgm.size()>3))
			max = pgm.lastElement();
	}	

	private void print(ide.ListaFiguras listaFiguras) {
		Vector<Punto> p = new Vector<Punto>();
		//System.out.println(pgm.get(0)+"x"+pgm.get(1));
		//System.out.println("Color: "+pgm.get(2)+" :: "+max);
		//System.out.println("Total: "+(pgm.size()-3));
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
					p.add(new Punto(incX+xant*pixelPto, incY+yant*pixelPto, incZ+zant*pts));
					p.add(new Punto(incX+x*pixelPto, incY+y*pixelPto, incZ+pgm.get(pos+3)*pts));
				  
					xant=x; 
					yant=y;
					zant=pgm.get(pos+3);
				}
				else {
					pos =(((y+1)*pgm.get(0))-1-x);
				  
					p.add(new Punto(incX+xant*pixelPto, incY+yant*pixelPto, incZ+zant*pts));
					p.add(new Punto(incX+(pgm.get(0)-x-1)*pixelPto, incY+y*pixelPto,incZ+ pgm.get(pos+3)*pts));
				  
					xant=pgm.get(0)-x-1; 
					yant=y;
					zant=pgm.get(pos+3);
				}
				listaFiguras.insertarFigura(2, false, p);
			}
			izqAder=izqAder?false:true;
		}
	}
}