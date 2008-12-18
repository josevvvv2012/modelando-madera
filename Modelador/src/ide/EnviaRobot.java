package ide;

import java.io.IOException;
import java.util.Vector;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;

import primitivas.Figuras;
import primitivas.Punto;
import serial.Comunicacion;

public class EnviaRobot extends Composite {

	private CLabel cLabel = null;
	private Label label = null;
	private Label label1 = null;
	private ProgressBar pbFiguras = null;
	private ProgressBar pbPuntos = null;
	private Label label2 = null;	
	private Vector<Figuras> figuras;  //  @jve:decl-index=0:
	private Vector <primitivas.Punto>puntos;
	private primitivas.Primitiva prim;
	private Comunicacion comuni;
	private boolean importar=false;	
	private double xAnt;
	private double yAnt;
	private double dx, dy;
	private int retardo1;
	private boolean primera=true;
	private boolean sigFig=false;
	private boolean levantar=false;
	private int []M = new int[4];
	private boolean detener= false;
	
	public void detenerEnvio() {
		detener=true;
		bEnviar.setText(DEF.bEnviaRobot);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			mostrarMSG(e.toString(), DEF.error);
		}
		pbFiguras.setSelection(0);
		pbPuntos.setSelection(0);
		
	}
	
	public Button bEnviar = null;
	public boolean enviando= false;
	public void setComuni( Comunicacion com) {
		this.comuni = com;
	}
	public void setFiguras(Vector<Figuras> fig) {
		this.figuras= fig;
	}
	public void setImportar(boolean imp) {
		this.importar = imp;
	}
	
	public EnviaRobot(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData21 = new GridData();
		gridData21.horizontalAlignment = GridData.END;
		gridData21.grabExcessHorizontalSpace = false;
		gridData21.verticalAlignment = GridData.CENTER;
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.END;
		gridData11.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.verticalAlignment = GridData.CENTER;
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.grabExcessHorizontalSpace = false;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		cLabel = new CLabel(this, SWT.NONE);
		cLabel.setText(DEF.lFiguras);
		cLabel.setLayoutData(gridData21);
		pbFiguras = new ProgressBar(this, SWT.NONE);
		pbFiguras.setLayoutData(gridData2);
		label1 = new Label(this, SWT.NONE);
		label1.setText("");
		Label filler = new Label(this, SWT.NONE);
		label = new Label(this, SWT.NONE);
		label.setText(DEF.lPunto);
		label.setLayoutData(gridData11);
		pbPuntos = new ProgressBar(this, SWT.NONE);
		pbPuntos.setLayoutData(gridData1);
		label2 = new Label(this, SWT.NONE);
		label2.setText("");
		Label filler1 = new Label(this, SWT.NONE);
		bEnviar = new Button(this, SWT.NONE);
		bEnviar.setText(DEF.bEnviaRobot);
		bEnviar.setLayoutData(gridData);
		
		bEnviar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				iniciarProcesoEnvio();
			}
		});
		
		this.setLayout(gridLayout);
		setSize(new Point(300, 200));
	}

	public void iniciarProcesoEnvio() {
		enviandoCodigos(pbPuntos, pbFiguras).start();		
	}

	  public Thread enviandoCodigos(ProgressBar pbP, ProgressBar pbF) {
		    final ProgressBar pbP1=pbP;
		    final ProgressBar pbF1=pbF;
		    final Button bSend= this.bEnviar;
		    return new Thread() {

		      public void run() {
		    	enviando = true;
		    	primera=true;
		  		sigFig=false;
		  		levantar=false;
		  				  		
		  		prim = null;
		  		final int nFig = figuras.size();
		  		for(int i=0; i<nFig&&detener==false; i++) {
		  			final int iFig = i;
		  			puntos = figuras.get(i).puntos;		  			
		  			if(i!=0) {
		  				retardo1=(int)dist(xAnt, yAnt, puntos.firstElement().getX(), puntos.firstElement().getY())*100;
		  				sigFig=true;
		  			}
		  			if(!importar||i==0) {				
		  				new cinematica.Inversa().get_angles(new Punto(puntos.firstElement().getX(), puntos.firstElement().getY(),DEF.arriba),M);
		  				mostrar("W"+M[0]+" "+M[1]+" "+M[2]+" "+M[3],1,1,  i, figuras.size());
		  				levantar=true;
		  			}
		  			
		  			
		  			switch(figuras.get(i).tipoFig) {
		  			case DEF.punto:
		  				new cinematica.Inversa().get_angles(puntos.get(0),M);				
		  				mostrar("W"+M[0]+" "+M[1]+" "+M[2]+" "+M[3],1,1,i, figuras.size());
		  				break;
		  			case DEF.linea:
		  				prim = new primitivas.Linea(null,puntos.get(0), puntos.get(1));				
		  				break;
		  			case DEF.circulo:
		  				dx = (puntos.get(0).getX()-puntos.get(1).getX());
		  				dy = (puntos.get(0).getY()-puntos.get(1).getY());
		  				int r = (int)Math.sqrt(dx*dx+dy*dy);	
		  				prim = new primitivas.Circulo(null,puntos.get(0), r, false, DEF.incRobot);
		  				break;
		  			case DEF.elipse:
		  				dx = Math.abs(puntos.get(0).getX()-puntos.get(1).getX());
		  				dy = Math.abs(puntos.get(0).getY()-puntos.get(1).getY());
		  						
		  				if((dx!=0)&&(dy!=0))
		  					prim = new primitivas.Elipse(null,puntos.get(0),dx, dy, false, DEF.incRobot);
		  				break;
		  			case DEF.bezier:
		  				prim = new primitivas.Bezier(null, puntos, 160);
		  				
		  				
		  				break;
		  			}
		  			
		  			if(prim != null)
		  			{
		  				int [] mtemp= new int[4];
		  				final int nPnt= prim.getSizeCoordenadas();
		  				for(int k=0; k<nPnt&&detener==false; k++) {
		  					final int iPnt = k;
		  					copiarM(M, mtemp);
		  					new cinematica.Inversa().get_angles(prim.getCoordenadas(k),M);
		  					if(!igualM(M, mtemp)) {
		  						
		  						getDisplay().asyncExec(new Runnable() {
							          public void run() {
							        	  
							        	  
							        	  actualizarPB(pbF1,nFig, iFig, pbP1, nPnt, iPnt);
							        	  
							        	  if((iFig==(nFig-1))&&(iPnt==(nPnt-1))) {
							        		  actualizarPB(pbF1,nFig, nFig, pbP1, nPnt, nPnt);
							        		  mostrarMSG(DEF.robotFinalizado, DEF.robotMSG);
						        			  bSend.setText(DEF.bEnviaRobot);
						        			  pbF1.setSelection(0);
							        		  pbP1.setSelection(0);

							        	  }
							        	  
							          }
							    });
		  						mostrar("W"+M[0]+" "+M[1]+" "+M[2]+" "+M[3], k, prim.getSizeCoordenadas(),  i, figuras.size());
		  					}
		  				}
		  				try {
		  				xAnt = prim.getCoordenadas(prim.getSizeCoordenadas()-1).getX();
		  				yAnt = prim.getCoordenadas(prim.getSizeCoordenadas()-1).getY();
		  				}
		  				catch (java.lang.ArrayIndexOutOfBoundsException e) {
		  					if(!importar) {
		  						mostrarMSG(e.toString(), DEF.error);
		  						
		  					}
		  				
		  				}
		  			}
		  			if(!importar) {
		  				new cinematica.Inversa().get_angles(new Punto(xAnt, yAnt,DEF.arriba),M);
		  				levantar=true;
		  				mostrar("W"+M[0]+" "+M[1]+" "+M[2]+" "+M[3],1,1, i, figuras.size());
		  			}
		  		}
		  		primera=true;

								/***
						 * 
						 */
						
						/***
						 * 
						 */
						
						
						
		  		enviando = false;
		  		detener=false;
		  		
		      }
		      
		    };
		    
		  }
	  

	private void actualizarPB(ProgressBar pbF1, int nFig, int iFig, ProgressBar pbP1, int nPnt, int iPnt ) {
		
		  if(pbP1.getMaximum()!=nPnt)
			  pbP1.setMaximum(nPnt);
		  pbP1.setSelection(iPnt);
		
		if(pbF1.getMaximum()!=nFig)
			pbF1.setMaximum(nFig);
		pbF1.setSelection(iFig);
	}
	
	
	
	
	
	
	
	private void mostrar(String str, int act, int max, int iFigura, int totalFigura) {

		//System.out.println(str);
		
		try {
	
			comuni.escribe(str+"\n");
			
		} catch (IOException e) {
			mostrarMSG(e.toString(), DEF.error);
			
		}
		
		retardo();		
	}
	
	public double dist(double x0, double y0, double x1, double y1) {
		double x2 = Math.pow(x0 - x1, 2);
		double y2 = Math.pow(y0 - y1, 2);
		return Math.sqrt(x2+y2);
		
	}	
	private void mostrarMSG(String str, String title) {
		MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_INFORMATION
	            | SWT.OK);
	        messageBox.setMessage(str);
	        messageBox.setText(title);
	        messageBox.open();	        
	}
	
	private void copiarM(int[] orig, int [] dest) {
		for(int i=0; i<orig.length; i++)
			dest[i]=orig[i];
	}
	

	private boolean igualM(int[] orig, int[] dest) {
		for(int i=0; i<orig.length; i++)
			if(orig[i]!=dest[i])
				return false;
		return true;
	}	
	private void retardo() {
		try {
			if(levantar) {
				Thread.sleep(3500);
				levantar=false;
			}
			if(sigFig) {
				//labelInfo.setText("Retardo ="+retardo1);
				Thread.sleep(retardo1);
				
				sigFig=false;
			}
			if(primera) {
				Thread.sleep(8999);
				primera=false;
			}
			else {
				Thread.sleep(4);
			}
		} catch (InterruptedException e) {
			mostrarMSG(e.toString(), DEF.error);
		}
	}
	
	
}
