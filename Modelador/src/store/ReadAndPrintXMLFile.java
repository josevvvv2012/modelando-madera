package store;

import java.io.File;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public class ReadAndPrintXMLFile{

    public static void main (String argv []){
    try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("file.xml"));

            // normalize text representation
            doc.getDocumentElement ().normalize ();      

            
            NodeList listFiguras = doc.getElementsByTagName("fig");            
            Element firstNameElement ;
            NodeList firstNameList;
            Element firstFiguraElement;
            Node firstFiguraNode ;
            
            System.out.println("Numero de figuras: " + listFiguras.getLength());
            for(int s=0; s<listFiguras.getLength() ; s++){
            	firstFiguraNode = listFiguras.item(s);            	
            	System.out.println("tipo: "+firstFiguraNode.getAttributes().getNamedItem("name").getNodeValue());
            	if(firstFiguraNode.getNodeType() == Node.ELEMENT_NODE){
            		firstFiguraElement = (Element)firstFiguraNode;
            		firstNameList = firstFiguraElement.getElementsByTagName("*");            		
            		for(int i=0; i<firstNameList.getLength(); i++) {
            			firstNameElement = (Element)firstNameList.item(i);
            			NodeList textFNList = firstNameElement.getChildNodes();                      
                        	System.out.println("\tFigura "+i+": " + 
                                    ((Node)textFNList.item(0)).getNodeValue().trim());
                        
            			
            		}                    
                    
                   
                    

            	}
            }
            
            
            NodeList listOfPersons = doc.getElementsByTagName("nombre");
            int totalPersons = listFiguras.getLength();
            


        }catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        	t.printStackTrace ();
        }
        //System.exit (0);

    }//end of main


}