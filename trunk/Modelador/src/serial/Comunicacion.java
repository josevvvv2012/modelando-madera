package serial;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Comunicacion {
	
	InputStream in;
	OutputStream out;

	public Comunicacion() {
		super();
	}
	
	public Comunicacion(String arg) throws Exception {
		connect(arg);				
	}
	public void connect (String nombrePuerto) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(nombrePuerto);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: El puerto está en uso");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(115200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
            }
            else
            {
                System.out.println("Error: Solo soporta puerto serial.");
            }
        }
		
	}
	public void cerrar() {
		in = null;
		out = null;		
	}

	public String leer() throws IOException {
		 byte[] buffer = new byte[1024];
		 int len = in.read(buffer);
		 return new String(buffer,0,len);
	}
	public String escribe(String str) throws IOException {
		
		for(int i=0; i<str.length(); i++) {
			out.write((int)str.charAt(i));
		}
		return ""; /*leer();*/
	}
	
    public static void main ( String[] args )
    {
    	Comunicacion comuni = new Comunicacion();
    	try {
			comuni.connect("COM4");
			comuni.escribe("hola mundo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fin");
    }

}
