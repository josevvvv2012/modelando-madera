package CreateObject;

import java.io.*;
import java.util.Vector;
import java.util.*;

public class CreateObject {

	
  static Vector<String> vertices = new Vector<String>();
  public static void main (String[] args) throws IOException {

    BufferedReader fin   = new BufferedReader(new FileReader("teapot.txt"));  // *** change

    File outputFile = new File("output.txt");
    
   BufferedWriter fout = new BufferedWriter(new FileWriter(outputFile));
   
   

    
    
    String line;
    StringTokenizer st;
    String temp="";
    line = fin.readLine();
    line = line.trim();
    int numVerts = Integer.parseInt(line);
    System.out.println ("Verts: "+numVerts);
    fout.write(""+numVerts);
    fout.newLine();

    for (int i=0; i<numVerts; i++) {
      line = fin.readLine();
      st = new StringTokenizer(line);
      st.nextToken();
      temp =""+st.nextToken()+"  "+st.nextToken()+"  "+st.nextToken(); 
      fout.write(temp);
      vertices.add(temp);
      fout.newLine();
    }
    for(int i=0; i<vertices.size(); i++) {
    	System.out.println(i+": " +vertices.get(i).toString());
    }
    System.exit(0);

    line = fin.readLine();
    line = line.trim();
    int numSurfs = Integer.parseInt(line);
    System.out.println ("Surfs: "+numSurfs);    
    fout.write(numSurfs);
    fout.newLine();


    for (int i=0; i<numSurfs; i++) {
      line = fin.readLine();
      line = line.trim();
      int numPolys = Integer.parseInt(line);
      System.out.println ("Polys: "+numPolys); 
      fout.write(numPolys);
      fout.newLine();

int actualPolys=0;      
      for (int j=0; j<numPolys; j++) {
        line = fin.readLine();
        st = new StringTokenizer(line);
        int v = Integer.parseInt(st.nextToken());
        int p0 = Integer.parseInt(st.nextToken())-1;
        int p1 = Integer.parseInt(st.nextToken())-1;
        int p2 = Integer.parseInt(st.nextToken())-1;
        System.out.println(v+" "+p0+" "+p1+" "+p2);
        int p3 = 0;
        if (v==4) {
          p3 = Integer.parseInt(st.nextToken())-1;
          System.out.println(p3);
        }
        actualPolys++;
        fout.write(p0+" "+p1+" "+p2);
        fout.newLine();
        if (v==4) {
          actualPolys++;
          fout.write(p0+" "+p2+" "+p3);          
          fout.newLine();
        }
      }

    System.out.println ("Actual Polys: "+actualPolys); 
    }

    
    fin.close();
    fout.close();
  }

}
