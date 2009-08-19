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
import primitivas.Primitiva;
import primitivas.Punto;
import primitivas.Recorte;
import primitivas.Transformaciones;
import serial.Comunicacion;
import java.io.IOException;
import java.util.Vector;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Spinner;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class ListaFiguras extends Composite {

	private Label label = null;
	public List listFigura = null;
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
	private int opcVisual=1;
	
	private Vector<DeshacerRehacer> deshacer=new Vector<DeshacerRehacer>();  //  @jve:decl-index=0:
	private Vector<DeshacerRehacer> rehacer=new Vector<DeshacerRehacer>();  //  @jve:decl-index=0:
	
	private int id=0;
	public void setOpcVisual(int opcVisual) {
		this.opcVisual = opcVisual;
	}

	public void insertarFigura(int tipo, boolean relleno, Vector<Punto> p) {
			figuras.add(new Figuras(++id, tipo,relleno, p));
			listFigura.add( figuras.get(figuras.size()-1).getText() );
			deshacer.add(new DeshacerRehacer(id, DEF.oInsertar, new Punto(1,1,1)));
	}
	
	public void eliminar(int index) {
		figuras.remove(index);
		listFigura.remove(index);
	}
	
	public int getIndex(int id) {
		for(int i=0; i<figuras.size(); i++) {
			if(figuras.get(i).id==id)
				return i;
		}
		return -1;
	}
	
	public void reHacer(){
		if(rehacer.size()!=0) {
			
			
			DeshacerRehacer p = rehacer.get(rehacer.size()-1);
			int index= getIndex(p.id);
			double ix, iy, iz;
			switch(p.operacion) {		
				case DEF.oEliminar:
					
					deshacer.add(new DeshacerRehacer(p.id, DEF.oInsertar, new Punto(1,1,1)));
					figuras.add(new Figuras(p.id, p.p.tipoFig, p.p.relleno, p.p.puntos));
					listFigura.add( figuras.get(figuras.size()-1).getText() );
					
					rehacer.remove(rehacer.size()-1);
					break;
				case DEF.oInsertar:
					deshacer.add(new DeshacerRehacer(figuras.get(index).id, DEF.oEliminar, figuras.get(index)));
					figuras.remove(index);
					listFigura.remove(index);
					rehacer.remove(rehacer.size()-1);
					break;
					
				case DEF.oTrasladar:
					deshacer.add(new DeshacerRehacer(p.id,DEF.oTrasladar, p.p2));
					for(int i=0; i<figuras.get(index).puntos.size(); i++) {			
						trans = new Transformaciones(figuras.get(index).puntos.get(i));			
						trans.traslacion(p.p2.getX(), p.p2.getY(), p.p2.getZ());		
					}			
					rehacer.remove(rehacer.size()-1);
					
					break;
				case DEF.oEscalar:
					
					ix = figuras.get(index).puntos.get(0).getX();
					iy = figuras.get(index).puntos.get(0).getY();
					iz = figuras.get(index).puntos.get(0).getZ();
					deshacer.add(new DeshacerRehacer(p.id,DEF.oEscalar, p.p2));
					for(int i=0; i<figuras.get(index).puntos.size(); i++) {
	
						trans = new Transformaciones(figuras.get(index).puntos.get(i));
						trans.traslacion(-ix, -iy, -iz);
						
						trans = new Transformaciones(figuras.get(index).puntos.get(i));
						
						trans.escalamiento(p.p2.getX(), p.p2.getY(), p.p2.getZ());
						
						trans = new Transformaciones(figuras.get(index).puntos.get(i));
						trans.traslacion(ix,iy, iz);
						
						
					}					
					rehacer.remove(rehacer.size()-1);
					break;		
				case DEF.oRotar:
					ix = figuras.get(index).puntos.get(0).getX();
					iy = figuras.get(index).puntos.get(0).getY();
					iz = figuras.get(index).puntos.get(0).getZ();	
					deshacer.add(new DeshacerRehacer(figuras.get(index).id, DEF.oRotar, p.theta, p.vista));
					for(int i=0; i<figuras.get(index).puntos.size(); i++) {
										
						trans = new Transformaciones(figuras.get(index).puntos.get(i));
						trans.traslacion(-ix, -iy, -iz);		
						
						trans = new Transformaciones(figuras.get(index).puntos.get(i));
						trans.rotacion(p.theta, p.vista);
						
						trans = new Transformaciones(figuras.get(index).puntos.get(i));
						trans.traslacion(ix, iy, iz);
					
					}
					
					rehacer.remove(rehacer.size()-1);
					break;
				case DEF.oFijar:
					
					deshacer.add(new DeshacerRehacer(figuras.get(index).id, DEF.oFijar, new Punto(figuras.get(index).puntos.get(p.punto).getX(), figuras.get(index).puntos.get(p.punto).getY(),figuras.get(index).puntos.get(p.punto).getZ()), p.punto));
					figuras.get(index).puntos.get(p.punto).setX(p.p2.getX());
					figuras.get(index).puntos.get(p.punto).setY(p.p2.getY());
					figuras.get(index).puntos.get(p.punto).setZ(p.p2.getZ());
					rehacer.remove(rehacer.size()-1);
					break;
				
				
			}
	
			comboFigura.removeAll();
			limpiarCampos();							
			bFijar.notifyListeners(SWT.MouseDown, null);
		}
	}
	
	public void desHacer(){

		if(deshacer.size()!=0) {
			
			
			DeshacerRehacer p = deshacer.get(deshacer.size()-1);
			int index= getIndex(p.id);
			double ix, iy, iz;
			switch(p.operacion) {
				case DEF.oInsertar:
						rehacer.add(new DeshacerRehacer(p.id,DEF.oEliminar, figuras.get(index)));
						figuras.remove(index);
						listFigura.remove(index);	
						deshacer.remove(deshacer.size()-1);
						
					break;
				case DEF.oEliminar:
					
					figuras.add(new Figuras(p.id, p.p.tipoFig, p.p.relleno, p.p.puntos));
					listFigura.add( figuras.get(figuras.size()-1).getText() );
					rehacer.add(new DeshacerRehacer(p.id,DEF.oInsertar, figuras.get(figuras.size()-1)));
					deshacer.remove(deshacer.size()-1);
					break;
				case DEF.oTrasladar:
					rehacer.add(new DeshacerRehacer(p.id,DEF.oTrasladar, p.p2));
					for(int i=0; i<figuras.get(index).puntos.size(); i++) {			
						trans = new Transformaciones(figuras.get(index).puntos.get(i));			
						trans.traslacion(-p.p2.getX(), -p.p2.getY(), -p.p2.getZ());		
					}			
					deshacer.remove(deshacer.size()-1);
					
					break;
					
				case DEF.oEscalar:
					
					ix = figuras.get(index).puntos.get(0).getX();
					iy = figuras.get(index).puntos.get(0).getY();
					iz = figuras.get(index).puntos.get(0).getZ();
					rehacer.add(new DeshacerRehacer(p.id,DEF.oEscalar, p.p2));
					for(int i=0; i<figuras.get(index).puntos.size(); i++) {
	
						trans = new Transformaciones(figuras.get(index).puntos.get(i));
						trans.traslacion(-ix, -iy, -iz);
						
						trans = new Transformaciones(figuras.get(index).puntos.get(i));
						
						trans.escalamiento(1/p.p2.getX(), 1/p.p2.getY(), 1/p.p2.getZ());
						
						trans = new Transformaciones(figuras.get(index).puntos.get(i));
						trans.traslacion(ix,iy, iz);
						
						
					}					
					deshacer.remove(deshacer.size()-1);
					break;
				case DEF.oRotar:
					ix = figuras.get(index).puntos.get(0).getX();
					iy = figuras.get(index).puntos.get(0).getY();
					iz = figuras.get(index).puntos.get(0).getZ();	
					rehacer.add(new DeshacerRehacer(figuras.get(index).id, DEF.oRotar, p.theta, p.vista));
					for(int i=0; i<figuras.get(index).puntos.size(); i++) {
										
						trans = new Transformaciones(figuras.get(index).puntos.get(i));
						trans.traslacion(-ix, -iy, -iz);		
						
						trans = new Transformaciones(figuras.get(index).puntos.get(i));
						trans.rotacion(-p.theta, p.vista);
						
						trans = new Transformaciones(figuras.get(index).puntos.get(i));
						trans.traslacion(ix, iy, iz);
					
					}
					
					deshacer.remove(deshacer.size()-1);
					break;
					
				case DEF.oFijar:
					rehacer.add(new DeshacerRehacer(figuras.get(index).id, DEF.oFijar, new Punto(figuras.get(index).puntos.get(p.punto).getX(), figuras.get(index).puntos.get(p.punto).getY(),figuras.get(index).puntos.get(p.punto).getZ()), p.punto));
					figuras.get(index).puntos.get(p.punto).setX(p.p2.getX());
					figuras.get(index).puntos.get(p.punto).setY(p.p2.getY());
					figuras.get(index).puntos.get(p.punto).setZ(p.p2.getZ());
					deshacer.remove(deshacer.size()-1);
					break;
			}
	
			comboFigura.removeAll();
			limpiarCampos();							
			bFijar.notifyListeners(SWT.MouseDown, null);
		}
	}
	

	public void limpiarFiguras() {
		figuras.removeAllElements();
		comboFigura.removeAll();
		listFigura.removeAll();
		deshacer.removeAllElements();
		rehacer.removeAllElements();
		id=0;
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
	
	public void fijarPunto(int c) {	
		
		deshacer.add(new DeshacerRehacer(figuras.get(listFigura.getSelectionIndex()).id, DEF.oFijar, new Punto(figuras.get(listFigura.getSelectionIndex()).puntos.get(c).getX(),figuras.get(listFigura.getSelectionIndex()).puntos.get(c).getY(),figuras.get(listFigura.getSelectionIndex()).puntos.get(c).getZ()), c));		
		figuras.get(listFigura.getSelectionIndex()).puntos.get(c).setX(Double.valueOf(textValorX.getText()));
		figuras.get(listFigura.getSelectionIndex()).puntos.get(c).setY(Double.valueOf(textValorY.getText()));
		figuras.get(listFigura.getSelectionIndex()).puntos.get(c).setZ(Double.valueOf(textValorZ.getText()));
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
		listFigura = new List(this, SWT.V_SCROLL | SWT.MULTI);
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
					
					int []indices = listFigura.getSelectionIndices();
					for(int i=0; i<indices.length; i++) {
						trasladar(1, indices[i]);	
					}
						
					
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
					int []indices = listFigura.getSelectionIndices();
					for(int i=0; i<indices.length; i++) {
						trasladar(3, indices[i]);	
					}
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
					int []indices = listFigura.getSelectionIndices();
					for(int i=0; i<indices.length; i++) {
						trasladar(4, indices[i]);	
					}
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
					
					int []indices = listFigura.getSelectionIndices();
					for(int i=0; i<indices.length; i++) {
						trasladar(2, indices[i]);	
					}	
					
					
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
		listFigura.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				bFijar.notifyListeners(SWT.MouseDown, null);
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
						bDuplicar();
						
						
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
						bEliminar();
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
					int []indices = listFigura.getSelectionIndices();
					for(int i=0; i<indices.length; i++) {
						rotar(Double.valueOf(spinnerRotar.getText()), indices[i]);	
					}
					
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
					int []indices = listFigura.getSelectionIndices();
					for(int i=0; i<indices.length; i++) {
						escalar(Double.valueOf(spinnerEscala.getText()),Double.valueOf(spinnerEscala.getText()), indices[i]);
					}
				}catch(java.lang.NumberFormatException ex) {
					errorMsg(DEF.error, DEF.errorFormatoConv);
				}
				catch( java.lang.ArrayIndexOutOfBoundsException ex2) {					
					errorMsg(DEF.error, DEF.errorNoFiguraSel);
				}
			}
		});
	}
	public void bDuplicar() {
		try {
			int []indices = listFigura.getSelectionIndices();
			for(int i=0; i<indices.length; i++) {
				//figuras.add(figuras.get(indices[i]).copiar());							
				//listFigura.add( figuras.get(figuras.size()-1).getText() );
				//figuras.get(figuras.size()-1).id = id++;
				insertarFigura(figuras.get(indices[i]).tipoFig, figuras.get(indices[i]).relleno, figuras.get(indices[i]).puntos);
			}
			//figuras.add(figuras.get(listFigura.getSelectionIndex()).copiar());							
			//listFigura.add( figuras.get(figuras.size()-1).getText() );	
		}
		catch( java.lang.ArrayIndexOutOfBoundsException ex2) {			
			errorMsg(DEF.error, DEF.errorNoFiguraSel);
		}
	}
	public void bEliminar() {
		try {							
			while(listFigura.getSelectionIndex()!=-1) {		
				deshacer.add(new DeshacerRehacer(figuras.get(listFigura.getSelectionIndex()).id, DEF.oEliminar, figuras.get(listFigura.getSelectionIndex())));
				eliminar(listFigura.getSelectionIndex());
				//figuras.remove(listFigura.getSelectionIndex());
				//listFigura.remove(listFigura.getSelectionIndex());	
			}							
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
	
	
	
	public void trasladar(int opc, int index) {
		int tx, ty, tz;
		tx = ty = tz = 0;
		switch(opcVisual) {
			case DEF.vistaYX:
				switch(opc) {
					case 1: ty=-1; break;
					case 2: ty=1;  break;
					case 3: tx=-1; break;
					case 4: tx=1;  break;
				}
				break;
			case DEF.vistaZX:
				switch(opc) {
					case 1: tz=-1; break;
					case 2: tz=1;  break;
					case 3: tx=-1; break;
					case 4: tx=1;  break;
				}
				break;
			case DEF.vistaZY:
				switch(opc) {
				case 1: tz=-1; break;
				case 2: tz=1;  break;
				case 3: ty=-1; break;
				case 4: ty=1;  break;
				}
				break;		
		}
		
		for(int i=0; i<figuras.get(index).puntos.size(); i++) {			
			trans = new Transformaciones(figuras.get(index).puntos.get(i));			
			trans.traslacion(tx, ty, tz);		
		}
	
		deshacer.add(new DeshacerRehacer(figuras.get(index).id, DEF.oTrasladar, new Punto(tx,ty,tz)));
		this.bFijar.notifyListeners(SWT.MouseDown, null);
	}
	
	public void rotar(double theta, int index) {
		
		double ix, iy, iz;
		ix = figuras.get(index).puntos.get(0).getX();
		iy = figuras.get(index).puntos.get(0).getY();
		iz = figuras.get(index).puntos.get(0).getZ();
		deshacer.add(new DeshacerRehacer(figuras.get(index).id, DEF.oRotar, theta, opcVisual));
		for(int i=0; i<figuras.get(index).puntos.size(); i++) {
			
			//figuras.get(index).puntos.get(i).setX(figuras.get(index).puntos.get(i).getX()-ix);
			//figuras.get(index).puntos.get(i).setY(figuras.get(index).puntos.get(i).getY()-iy);
			//figuras.get(index).puntos.get(i).setZ(figuras.get(index).puntos.get(i).getZ()-iz);
			trans = new Transformaciones(figuras.get(index).puntos.get(i));
			trans.traslacion(-ix, -iy, -iz);		
			
			trans = new Transformaciones(figuras.get(index).puntos.get(i));
			trans.rotacion(theta, opcVisual);
			
			trans = new Transformaciones(figuras.get(index).puntos.get(i));
			trans.traslacion(ix, iy, iz);
			//figuras.get(index).puntos.get(i).setX(figuras.get(index).puntos.get(i).getX()+ix);
			//figuras.get(index).puntos.get(i).setY(figuras.get(index).puntos.get(i).getY()+iy);
			//figuras.get(index).puntos.get(i).setZ(figuras.get(index).puntos.get(i).getZ()+iz);
			
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
	public void escalar(double sx, double sy, int index) {
		
		
		double ix, iy, iz;
		ix = figuras.get(index).puntos.get(0).getX();
		iy = figuras.get(index).puntos.get(0).getY();
		iz = figuras.get(index).puntos.get(0).getZ();
		
		switch(opcVisual) {
			case DEF.vistaYX: deshacer.add(new DeshacerRehacer(figuras.get(index).id, DEF.oEscalar, new Punto(sx,sy,1))); break;
			case DEF.vistaZX: deshacer.add(new DeshacerRehacer(figuras.get(index).id, DEF.oEscalar, new Punto(sx,1,sy))); break;
			case DEF.vistaZY: deshacer.add(new DeshacerRehacer(figuras.get(index).id, DEF.oEscalar, new Punto(1,sx,sy))); break;		
		}
		
		for(int i=0; i<figuras.get(listFigura.getSelectionIndex()).puntos.size(); i++) {
			
			//figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setX(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getX()-ix);
			//figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setY(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getY()-iy);
			//figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setZ(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getZ()-iz);
			trans = new Transformaciones(figuras.get(index).puntos.get(i));
			trans.traslacion(-ix, -iy, -iz);
			
			trans = new Transformaciones(figuras.get(index).puntos.get(i));
			switch(opcVisual) {
				case DEF.vistaYX: trans.escalamiento(sx, sy, 1); break;
				case DEF.vistaZX: trans.escalamiento(sx, 1, sy); break;
				case DEF.vistaZY: trans.escalamiento(1, sx, sy); break;		
			}
			trans = new Transformaciones(figuras.get(index).puntos.get(i));
			trans.traslacion(ix,iy, iz);
			
			//figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setX(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getX()+ix);
			//figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setY(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getY()+iy);
			//figuras.get(listFigura.getSelectionIndex()).puntos.get(i).setZ(figuras.get(listFigura.getSelectionIndex()).puntos.get(i).getZ()+iz);
		}
		this.bFijar.notifyListeners(SWT.MouseDown, null);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10" 

