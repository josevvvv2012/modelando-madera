package store;

import java.io.File;
import org.w3c.dom.Document;
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
            System.out.println ("Raiz=" + 
                 doc.getDocumentElement().getNodeName());

            NodeList listFiguras = doc.getElementsByTagName("fig");
            System.out.println("Numero de figuras : " + listFiguras.getLength());
            for(int s=0; s<listFiguras.getLength() ; s++){
            	Node firstFiguraNode = listFiguras.item(s);
            	if(firstFiguraNode.getNodeType() == Node.ELEMENT_NODE){
            		Element firstFiguraElement = (Element)firstFiguraNode;
            		
            		
            		NodeList firstNameList = firstFiguraElement.getElementsByTagName("x1");
                    Element firstNameElement = (Element)firstNameList.item(0);

                    NodeList textFNList = firstNameElement.getChildNodes();
                    System.out.println("Figura x1 : " + 
                           ((Node)textFNList.item(0)).getNodeValue().trim());


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