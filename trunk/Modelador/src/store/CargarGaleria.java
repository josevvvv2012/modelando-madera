package store;

import java.io.*;
import java.util.Vector;

public class CargarGaleria {
	Vector <String> galeria = new Vector<String>();
	public String sDirectorio = "galeria";
	public String ext = ".xml";
	public void cargarDirectorio() {
		galeria = new Vector<String>();
		File directorio  = new File(sDirectorio);
		if(directorio.isDirectory()) {
			
			String[] archivos = directorio.list();
			for(int i=0; i<archivos.length; i++) {
				if((new File(sDirectorio+"/"+archivos[i])).isFile()&&archivos[i].endsWith(ext)) {
					galeria.add(archivos[i].substring(0, archivos[i].length()-ext.length()));
					
				}
			}
		}		
	}
	public int getSize() {
		return galeria.size();
	}
	public String getGaleria(int index) {
		return galeria.get(index);
	}
    	public static void main(String args[])  {
		CargarGaleria cg = new CargarGaleria();
		cg.cargarDirectorio();
		for(int i=0; i<cg.getSize(); i++) {
			System.out.println(cg.getGaleria(i));
		}
	}
}
