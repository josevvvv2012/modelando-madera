package ide;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import primitivas.Figuras;
import primitivas.Punto;
import primitivas.Recorte;
import primitivas.Transformaciones;
import serial.Comunicacion;

import java.io.IOException;
import java.util.Timer;
import java.util.Vector;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.custom.CLabel;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class ListaFiguras extends Composite {

	private Label label = null;
	private List listFigura = null;
	private Label label1 = null;
	private Combo comboFigura = null;
	private Label label2 = null;
	private Text textValorX = null;
	public Button bFijar = null;
	public Vector<Figuras> figuras = new Vector<Figuras>();  //  @jve:decl-index=0:
	private Text textValorY = null;
	private Button buttonEliminar = null;
	private Button bEscalar = null;
	private Transformaciones trans;
	private Button bRotar = null;
	private Label label6 = null;
	private Recorte rec=new Recorte();  //  @jve:decl-index=0:
	private Button bReflejar = null;
	private Button bRellenar = null;
	private Text textValorZ = null;
	public Button buttonRobotizar = null;
	private Comunicacion comuni = new Comunicacion();  //  @jve:decl-index=0:
	double incRobot = 0.1;
	private Button buttonDuplicar = null;
	private Button buttonIzq = null;
	private Button buttonDer = null;
	private Button buttonUp = null;
	private Button buttonDown = null;
	private Spinner spinnerEscala = null;
	private Spinner spinnerRotar = null;
	public void insertarFigura(int tipo, boolean relleno, Vector<Punto> p) {
		if(tipo==8) {
			recortar(new double[] {p.get(0).getX(), p.get(0).getY(), p.get(1).getX(), p.get(1).getY()});
		}
		else {

			figuras.add(new Figuras(tipo,relleno, p));
			listFigura.add( figuras.get(figuras.size()-1).getText() );
		}
		
	}

	public void limpiarFiguras() {
		figuras.removeAllElements();
		comboFigura.removeAll();
		listFigura.removeAll();
	}
	
	public void limpiarCampos() {
		textValorX.setText("");
		textValorY.setText("");
		textValorZ.setText("");
	}
	public void swap(double []lista) {
		double t= lista[0];
		lista[0]=lista[2];
		lista[2]=t;
		
		t=lista[1];
		lista[1]=lista[3];
		lista[3]=t;		
	}
	public void recortar(double []ventana) {
		double []temp;
		double []linea;
		int c1,c2;
		boolean borrar;
		boolean redraw=false;
		System.out.println("Ventana  \t\t\t ("+ventana[0]+", "+ventana[1]+") -> ("+ventana[2]+", "+ventana[3]+").");
		for(int i=0; i< figuras.size(); i++){			
			if(figuras.get(i).tipoFig==2) {
				borrar = false;				
				linea = new double[]{figuras.get(i).puntos.get(0).getX(),figuras.get(i).puntos.get(0).getY(),figuras.get(i).puntos.get(1).getX(),figuras.get(i).puntos.get(1).getY()};
				c1=0;				
				System.out.println("linea Modificando\t\t ("+linea[0]+", "+linea[1]+") -> ("+linea[2]+", "+linea[3]+").");
				if(linea[0]>linea[2]) {
					swap(linea);
					System.out.println("linea (swap) \t\t\t("+linea[0]+", "+linea[1]+") -> ("+linea[2]+", "+linea[3]+").");					
				}
				if( rec.puntosDentro(linea, ventana)!=2 ) {
					if(rec.puntosDentroRango(linea, ventana)) {						
						temp=rec.recorteHoriz(linea, ventana[0]);
						if(temp[0]!=-1) {
							linea[0]=temp[0];
							linea[1]=temp[1];
						} else{
							c1++;
						}
						temp=rec.recorteHoriz(linea, ventana[2]);
						if(temp[0]!=-1) {
							linea[2]=temp[0];
							linea[3]=temp[1];
						} else{
							c1++;
						}
						
						//if(c1!=2) {
						if(true) {
							System.out.println("linea (rec 1) \t\t\t("+linea[0]+", "+linea[1]+") -> ("+linea[2]+", "+linea[3]+").");
							if(linea[1]>linea[3]) {
								swap(linea);
								System.out.println("linea (swap) \t\t\t("+linea[0]+", "+linea[1]+") -> ("+linea[2]+", "+linea[3]+").");
							}
								
							c2=0;
							
							
							if(rec.puntosDentro(linea, ventana)!=2) {
						
								temp=rec.recorteVert(linea, ventana[1]);
								if(temp[0]!=-1) {
									linea[0]=temp[0];
									linea[1]=temp[1];									
								}
								else {
									c2++;
								}
								temp=rec.recorteVert(linea, ventana[3]);
								if(temp[0]!=-1) {
									linea[2]=temp[0];
									linea[3]=temp[1];									
								}else {
									c2++;
								}	
								if((c2!=2)||(c1!=2)) {
									redraw=true;
									figuras.get(i).puntos.get(0).setX(linea[0]);
									figuras.get(i).puntos.get(0).setY(linea[1]);
									figuras.get(i).puntos.get(1).setX(linea[2]);
									figuras.get(i).puntos.get(1).setY(linea[3]);
									
								}else {
									borrar=true;
								}
								
							}
							else {								
								redraw=true;
								figuras.get(i).puntos.get(0).setX(linea[0]);
								figuras.get(i).puntos.get(0).setY(linea[1]);
								figuras.get(i).puntos.get(1).setX(linea[2]);
								figuras.get(i).puntos.get(1).setY(linea[3]);
							}																					
						}else {
							borrar=true;
						}												
					} else {
						borrar=true;
					}
				}
				if(rec.puntosDentro(linea, ventana)==2 ) {
					redraw=true;
					figuras.get(i).puntos.get(0).setX(linea[0]);
					figuras.get(i).puntos.get(0).setY(linea[1]);
					figuras.get(i).puntos.get(1).setX(linea[2]);
					figuras.get(i).puntos.get(1).setY(linea[3]);
				}else 
				
				if(borrar) {
					figuras.remove(i);
					listFigura.remove(i);
					comboFigura.removeAll();
					redraw=true;
					i=i-1;
				}				
			
			}
		
		}
		if(redraw)
			this.bFijar.notifyListeners(SWT.MouseDown, null);
	}

	public void fijarPunto(int c) {	
		figuras.get(listFigura.getSelectionIndex()).puntos.get(c).setX(Double.valueOf(textValorX.getText()));
		figuras.get(listFigura.getSelectionIndex()).puntos.get(c).setY(Double.valueOf(textValorY.getText()));	
		this.bFijar.notifyListeners(SWT.MouseDown, null);
	}

	public void escalar(double sx, double sy) {
		
		
		double ix, iy;
		ix = figuras.get(listFigura.getSelectionIndex()).puntos.get(0).getX();
		iy = figuras.get(listFigura.getSelectionIndex()).puntos.get(0).getY();
		
		
		
		for(int i=0; i<figuras.get(listFigura.getSelectionIndex()).puntos.size(); i++) {
			
			figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setX(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getX()-ix);
			figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setY(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getY()-iy);
			
			
			trans = new Transformaciones(figuras.get(listFigura.getSelectionIndex()).puntos.get(i));
			trans.escalamiento(sx, sy);
			
			figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setX(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getX()+ix);
			figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setY(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getY()+iy);
		}
		this.bFijar.notifyListeners(SWT.MouseDown, null);
	}
	public void trasladar(double tx, double ty) {
		
		for(int i=0; i<figuras.get(listFigura.getSelectionIndex()).puntos.size(); i++) {			
			trans = new Transformaciones(figuras.get(listFigura.getSelectionIndex()).puntos.get(i));
			trans.traslacion(tx, ty);		
		}
		this.bFijar.notifyListeners(SWT.MouseDown, null);
	}
	
	public void rotar(double theta) {
		
		double ix, iy;
		ix = figuras.get(listFigura.getSelectionIndex()).puntos.get(0).getX();
		iy = figuras.get(listFigura.getSelectionIndex()).puntos.get(0).getY();
		
		for(int i=0; i<figuras.get(listFigura.getSelectionIndex()).puntos.size(); i++) {
			
			figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setX(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getX()-ix);
			figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setY(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getY()-iy);
			
			trans = new Transformaciones(figuras.get(listFigura.getSelectionIndex()).puntos.get(i));
			trans.rotacion(theta);
			
			figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setX(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getX()+ix);
			figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setY(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getY()+iy);
			
		}
		this.bFijar.notifyListeners(SWT.MouseDown, null);
	}
	
	
	public void reflejar() {
		
		double ix, iy;
		ix = figuras.get(listFigura.getSelectionIndex()).puntos.get(0).getX();
		iy = figuras.get(listFigura.getSelectionIndex()).puntos.get(0).getY();
		
		for(int i=0; i<figuras.get(listFigura.getSelectionIndex()).puntos.size(); i++) {
			
			figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setX(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getX()-ix);
			figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setY(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getY()-iy);
			
			trans = new Transformaciones(figuras.get(listFigura.getSelectionIndex()).puntos.get(i));
			trans.reflexion();
			
			figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setX(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getX()+ix);
			figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setY(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getY()+iy);
			
		}
		this.bFijar.notifyListeners(SWT.MouseDown, null);
	}
	
	private void mostrarPunto(int a) {
		Vector <Punto>p = figuras.get(listFigura.getSelectionIndex()).puntos;
		textValorX.setText(""+p.get(a).getX() );
		textValorY.setText(""+p.get(a).getY() );
		textValorZ.setText(""+p.get(a).getZ() );
	}
	

	public ListaFiguras(Composite parent, int style) {
		super(parent, style);
		initialize();					
	}

	private void recargarPuntos(int a) {
		comboFigura.removeAll();
		Vector <Punto>p = figuras.get(a).puntos;
		for(int i=0; i< p.size(); i++) {
			comboFigura.add( String.valueOf(i)   );
		}
		limpiarCampos();
		
	}
	/**
	 * 
	 */
	private void initialize() {
		
		
		
		
		
		GridData gridData151 = new GridData();
		gridData151.horizontalAlignment = GridData.BEGINNING;
		gridData151.grabExcessHorizontalSpace = true;
		gridData151.verticalAlignment = GridData.CENTER;
		GridData gridData9 = new GridData();
		gridData9.horizontalAlignment = GridData.CENTER;
		gridData9.verticalAlignment = GridData.CENTER;
		GridData gridData8 = new GridData();
		gridData8.horizontalAlignment = GridData.CENTER;
		gridData8.verticalAlignment = GridData.CENTER;
		GridData gridData33 = new GridData();
		gridData33.horizontalAlignment = GridData.CENTER;
		gridData33.horizontalSpan = 2;
		gridData33.verticalAlignment = GridData.CENTER;
		GridData gridData24 = new GridData();
		gridData24.horizontalAlignment = GridData.END;
		gridData24.grabExcessHorizontalSpace = true;
		gridData24.verticalAlignment = GridData.CENTER;
		GridData gridData16 = new GridData();
		gridData16.verticalAlignment = GridData.CENTER;
		gridData16.grabExcessHorizontalSpace = false;
		gridData16.horizontalSpan = 2;
		gridData16.horizontalAlignment = GridData.CENTER;
		GridData gridData15 = new GridData();
		gridData15.horizontalAlignment = GridData.FILL;
		gridData15.grabExcessHorizontalSpace = false;
		gridData15.grabExcessVerticalSpace = false;
		gridData15.horizontalSpan = 2;
		gridData15.verticalAlignment = GridData.CENTER;
		GridData gridData14 = new GridData();
		gridData14.horizontalAlignment = GridData.FILL;
		gridData14.grabExcessHorizontalSpace = true;
		gridData14.horizontalSpan = 2;
		gridData14.verticalAlignment = GridData.CENTER;
		GridData gridData23 = new GridData();
		gridData23.grabExcessHorizontalSpace = true;
		gridData23.horizontalAlignment = GridData.FILL;
		gridData23.horizontalSpan = 2;
		gridData23.verticalAlignment = GridData.CENTER;
		GridData gridData13 = new GridData();
		gridData13.horizontalAlignment = GridData.FILL;
		gridData13.grabExcessHorizontalSpace = true;
		gridData13.horizontalSpan = 2;
		gridData13.verticalAlignment = GridData.CENTER;
		GridData gridData12 = new GridData();
		gridData12.horizontalAlignment = GridData.FILL;
		gridData12.grabExcessHorizontalSpace = true;
		gridData12.horizontalSpan = 2;
		gridData12.verticalAlignment = GridData.CENTER;
		GridData gridData6 = new GridData();
		gridData6.grabExcessHorizontalSpace = true;
		gridData6.verticalAlignment = GridData.CENTER;
		gridData6.horizontalAlignment = GridData.FILL;
		GridData gridData22 = new GridData();
		gridData22.horizontalAlignment = GridData.FILL;
		gridData22.grabExcessHorizontalSpace = true;
		gridData22.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.verticalAlignment = GridData.CENTER;
		gridData2.horizontalSpan = 2;
		gridData2.horizontalAlignment = GridData.FILL;
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.FILL;
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.horizontalSpan = 2;
		gridData5.verticalAlignment = GridData.CENTER;
		GridData gridData41 = new GridData();
		gridData41.horizontalAlignment = GridData.FILL;
		gridData41.grabExcessHorizontalSpace = true;
		gridData41.horizontalSpan = 2;
		gridData41.verticalAlignment = GridData.CENTER;
		GridData gridData31 = new GridData();
		gridData31.horizontalAlignment = GridData.END;
		gridData31.verticalAlignment = GridData.CENTER;
		GridData gridData21 = new GridData();
		gridData21.horizontalAlignment = GridData.END;
		gridData21.verticalAlignment = GridData.CENTER;
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.END;
		gridData11.verticalAlignment = GridData.CENTER;
		GridData gridData4 = new GridData();
		gridData4.verticalAlignment = GridData.CENTER;
		gridData4.grabExcessHorizontalSpace = true;
		gridData4.horizontalSpan = 2;
		gridData4.horizontalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 4;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
		label = new Label(this, SWT.NONE);
		label.setText(DEF.lFiguras);
		label.setLayoutData(gridData31);
		listFigura = new List(this, SWT.V_SCROLL);
		listFigura.setLayoutData(gridData);
		Label filler15 = new Label(this, SWT.NONE);
		buttonEliminar = new Button(this, SWT.NONE);
		buttonEliminar.setText(DEF.bEliminar);
		buttonEliminar.setLayoutData(gridData2);
		buttonDuplicar = new Button(this, SWT.NONE);
		buttonDuplicar.setText(DEF.bDuplicar);
		buttonDuplicar.setLayoutData(gridData15);
		Label filler16 = new Label(this, SWT.NONE);
		buttonUp = new Button(this, SWT.NONE);
		buttonUp.setText(DEF.bUp);
		buttonUp.setLayoutData(gridData16);
		bReflejar = new Button(this, SWT.NONE);
		buttonUp.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				try {
					trasladar(0,-1);
				}
				catch( java.lang.ArrayIndexOutOfBoundsException ex2) {	
					errorMsg(DEF.error, DEF.errorNoFiguraSel);
				}
			}
		});		
		Label filler4 = new Label(this, SWT.NONE);
		buttonIzq = new Button(this, SWT.NONE);
		buttonIzq.setText(DEF.bIzq);
		buttonIzq.setLayoutData(gridData24);
		buttonIzq.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				try {
					trasladar(-1,0);
				}
				catch( java.lang.ArrayIndexOutOfBoundsException ex2) {	
					errorMsg(DEF.error, DEF.errorNoFiguraSel);
				}
			}
		});
		buttonDer = new Button(this, SWT.NONE);
		buttonDer.setText(DEF.bDer);
		buttonDer.setLayoutData(gridData151);
		spinnerEscala = new Spinner(this, SWT.NONE);
		spinnerEscala.setDigits(2);
		spinnerEscala.setMinimum(-10000);
		spinnerEscala.setMaximum(10000);
		spinnerEscala.setSelection(100);
		spinnerEscala.setLayoutData(gridData9);
		buttonDer.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				try {
					trasladar(1,0);
				}
				catch( java.lang.ArrayIndexOutOfBoundsException ex2) {	
					errorMsg(DEF.error, DEF.errorNoFiguraSel);
				}
			}
		});
		bEscalar = new Button(this, SWT.NONE);
		Label filler3 = new Label(this, SWT.NONE);
		buttonDown = new Button(this, SWT.NONE);
		buttonDown.setText(DEF.bDown);
		buttonDown.setLayoutData(gridData33);
		buttonDown.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				try {
					trasladar(0,1);
				}
				catch( java.lang.ArrayIndexOutOfBoundsException ex2) {	
					errorMsg(DEF.error, DEF.errorNoFiguraSel);
				}
			}
		});
		spinnerRotar = new Spinner(this, SWT.NONE);
		spinnerRotar.setDigits(2);
		spinnerRotar.setMinimum(-36000);
		spinnerRotar.setMaximum(36000);
		spinnerRotar.setLayoutData(gridData8);
		bRotar = new Button(this, SWT.NONE);
		listFigura.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				try {
					recargarPuntos(listFigura.getSelectionIndex());
				}catch( java.lang.ArrayIndexOutOfBoundsException ex2) {	
					errorMsg(DEF.error, DEF.errorNoFiguraSel);
				}
			}
		});
		label1 = new Label(this, SWT.NONE);
		label1.setText("Punto");
		label1.setLayoutData(gridData21);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 5;
		this.setLayout(gridLayout);
		createComboFigura();
		this.setSize(new Point(300, 407));
		label2 = new Label(this, SWT.NONE);
		label2.setText(DEF.lValor);
		label2.setLayoutData(gridData11);
		textValorX = new Text(this, SWT.BORDER);
		textValorX.setLayoutData(gridData4);
		textValorY = new Text(this, SWT.BORDER);
		textValorY.setLayoutData(gridData5);
		Label filler1 = new Label(this, SWT.NONE);
		textValorZ = new Text(this, SWT.BORDER | SWT.CENTER);
		textValorZ.setLayoutData(gridData23);
		bFijar = new Button(this, SWT.NONE);
		bFijar.setText(DEF.bFijar);
		bFijar.setLayoutData(gridData41);
		label6 = new Label(this, SWT.NONE);
		label6.setText("");
		buttonRobotizar = new Button(this, SWT.NONE);
		buttonRobotizar.setText(DEF.mHerramientasRobotizar);
		buttonRobotizar.setLayoutData(gridData14);		
		bRellenar = new Button(this, SWT.NONE);
		bRellenar.setText("Rellenar/Limpiar");
		bRellenar.setLayoutData(gridData13);
		buttonDuplicar
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						try {
							figuras.add(figuras.get(listFigura.getSelectionIndex()).copiar());							
							listFigura.add( figuras.get(figuras.size()-1).getText() );	
						}
						catch( java.lang.ArrayIndexOutOfBoundsException ex2) {			
							errorMsg(DEF.error, DEF.errorNoFiguraSel);
						}
						
						
					}
				});
		bFijar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				try {
					fijarPunto(comboFigura.getSelectionIndex());									
				}catch(java.lang.NumberFormatException ex) {	
					errorMsg(DEF.error, DEF.errorFormatoConv);
				}
				catch( java.lang.ArrayIndexOutOfBoundsException ex2) {			
					errorMsg(DEF.error, DEF.errorNoFiguraSel);
				}
			}
		});
		buttonEliminar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						try {
							figuras.remove(listFigura.getSelectionIndex());
							listFigura.remove(listFigura.getSelectionIndex());
							comboFigura.removeAll();
							limpiarCampos();							
							bFijar.notifyListeners(SWT.MouseDown, null);
						}
						catch(java.lang.NumberFormatException ex) {		
							errorMsg(DEF.error, DEF.errorFormatoConv);
						}
						catch( java.lang.ArrayIndexOutOfBoundsException ex2) {	
							errorMsg(DEF.error, DEF.errorNoFiguraSel);
						}
					}
				});
		
			
		bEscalar.setText(DEF.bEscalar);
		bEscalar.setLayoutData(gridData22);
		bEscalar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/escalar.png")));
		bRotar.setText(DEF.bRotar);
		bRotar.setLayoutData(gridData6);
		bRotar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/rotar.png")));
		bReflejar.setText(DEF.bReflejar);
		bReflejar.setLayoutData(gridData12);
		bReflejar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/reflejar.png")));
		bRellenar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				try {
					if(figuras.get(listFigura.getSelectionIndex()).relleno)
						figuras.get(listFigura.getSelectionIndex()).relleno = false;
					else
						figuras.get(listFigura.getSelectionIndex()).relleno = true;
					
				}catch( java.lang.ArrayIndexOutOfBoundsException ex2) {
					errorMsg(DEF.error, DEF.errorNoFiguraSel);
				}
			
				
				bFijar.notifyListeners(SWT.MouseDown, null);
				//System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
			}
		});
		bReflejar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				
				try {
					reflejar();
				}catch(java.lang.NumberFormatException ex) {
					errorMsg(DEF.error, DEF.errorFormatoConv);
				}
				catch( java.lang.ArrayIndexOutOfBoundsException ex2) {
					errorMsg(DEF.error, DEF.errorNoFiguraSel);
				}
				
			}
		});
		bRotar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				try {
					rotar(Double.valueOf(spinnerRotar.getText()));
				}catch(java.lang.NumberFormatException ex) {
					errorMsg(DEF.error, DEF.errorFormatoConv);
				}
				catch( java.lang.ArrayIndexOutOfBoundsException ex2) {
					errorMsg(DEF.error, DEF.errorNoFiguraSel);
				}
			}
		});
		bEscalar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				try {
					escalar(Double.valueOf(spinnerEscala.getText()),Double.valueOf(spinnerEscala.getText()));
				}catch(java.lang.NumberFormatException ex) {
					errorMsg(DEF.error, DEF.errorFormatoConv);
				}
				catch( java.lang.ArrayIndexOutOfBoundsException ex2) {
					errorMsg(DEF.error, DEF.errorNoFiguraSel);
				}
			}
		});
	}
	public void errorMsg(String titulo, String msg) {
		MessageBox m=new MessageBox(this.getShell(), SWT.ICON_ERROR);
		m.setText(titulo);
		m.setMessage(msg);
		m.open();
	}
	/**
	 * This method initializes comboFigura	
	 *
	 */
	private void createComboFigura() {
		GridData gridData3 = new GridData();
		gridData3.horizontalSpan = 4;
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;
		gridData3.grabExcessHorizontalSpace = true;
		comboFigura = new Combo(this, SWT.NONE);
		comboFigura.setLayoutData(gridData3);
		comboFigura.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {						
						mostrarPunto(comboFigura.getSelectionIndex());
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
	}
	
	private void robotizar() {
		Vector <primitivas.Punto>puntos;
		int comandos=0;
		int []M = new int[4];
		double dx, dy;
		primitivas.Primitiva prim = null;
		for(int i=0; i<figuras.size(); i++) {
			
			puntos = figuras.get(i).puntos;
			switch(figuras.get(i).tipoFig) {
			case 1:
				new cinematica.Inversa().get_angles(puntos.get(0),M);				
				mostrar("W"+M[0]+" "+M[1]+" "+M[2]+" "+M[3]+".");
				break;
			case 2:
				prim = new primitivas.Linea(null,puntos.get(0), puntos.get(1), incRobot);				
				break;
			case 4:
				dx = (puntos.get(0).getX()-puntos.get(1).getX());
				dy = (puntos.get(0).getY()-puntos.get(1).getY());
				int r = (int)Math.sqrt(dx*dx+dy*dy);	
				prim = new primitivas.Circulo(null,puntos.get(0), r, false, incRobot);
				break;
			case 5:
				dx = Math.abs(puntos.get(0).getX()-puntos.get(1).getX());
				dy = Math.abs(puntos.get(0).getY()-puntos.get(1).getY());
						
			//	if((dx!=0)&&(dy!=0))
				//	prim = new primitivas.Elipse(null,puntos.get(0),dx, dy, false);
				break;
			case 6:
				//prim = new primitivas.Bezier(null, puntos.get(0), puntos.get(1), puntos.get(2), puntos.get(3), 10000);
				prim = new primitivas.Bezier(null, puntos, 10000);
				break;
			}
			
			if(prim != null)
				for(int k=0; k<prim.getSizeCoordenadas(); k++) {					
					new cinematica.Inversa().get_angles(prim.getCoordenadas(k),M);
					
					mostrar("W"+M[0]+" "+M[1]+" "+M[2]+" "+M[3]);
					comandos++;
				}						
		}		
	}
	
	private void retardo() {
		try {
			Thread.sleep(9);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void mostrar(String str) {					
		try {
			comuni.escribe(str+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		retardo();
		
	}


}  //  @jve:decl-index=0:visual-constraint="10,10" 

