package ide;

import ide.TipoFigura;
import primitivas.Figuras;
import primitivas.Plot;
import primitivas.Punto;
import primitivas.Polilinea;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridData;
import java.util.Vector;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FillLayout;

/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class Paint {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Label labelInfo = null;
	private Composite compositePrincipal = null;
	private Canvas canvas = null;
	private Image imageBuffer;
	private Composite compositeBarras = null;
	private TipoFigura tipoFigura = null;
	private ListaFiguras listaFiguras = null;
	private Plot plot = null;  //  @jve:decl-index=0:
	private Punto pAnt = null;
	private Vector<Punto> ps=new Vector<Punto>();  //  @jve:decl-index=0:
	private Menu menuBar = null;
	private Menu submenu = null;
	private Color negro= new Color(Display.getCurrent(), 0, 0,0);
	private Color blanco= new Color(Display.getCurrent(), 255, 255,255);	
	private final int clickIzq = 1;
//	private final int clickMed = 2;
//	private final int clickDer = 3;
	private boolean redibujar = false;
	private boolean reiniciarPts = false;
	private int z;
	
	private void crearListener2() {
		Listener listener2 = new Listener() {						
			public void handleEvent(Event e) {					
				Vector<Figuras> p = listaFiguras.figuras;
				switch(e.type) {
					case SWT.MouseDown:												
						plot.gc.fillRectangle(0, 0, 1200, 800);
						for(int i=0; i<p.size(); i++) {														
							switch(p.get(i).tipoFig) {
								case 1:
									plot.pixel(p.get(i).puntos.firstElement());
									break;
								case 2:
									plot.linea(p.get(i).puntos.get(0), p.get(i).puntos.get(1));
									break;
								case 3:
									
									Polilinea poli = new Polilinea(plot, p.get(i).puntos.get(0), p.get(i).puntos.get(1));
									for(int j=1; j<p.get(i).puntos.size()-1; j++) {
										poli.agregarLinea(p.get(i).puntos.get(j), p.get(i).puntos.get(j+1));
									}
									poli.agregarLinea(p.get(i).puntos.firstElement(), p.get(i).puntos.lastElement());
									if(p.get(i).relleno)
										poli.rellenarPolilinea(plot);
									break;
								case 4:
									plot.circulo(p.get(i).puntos.firstElement(), p.get(i).puntos.lastElement(), p.get(i).relleno);
									break;
								case 5:
									plot.elipse(p.get(i).puntos.firstElement(), p.get(i).puntos.lastElement(), p.get(i).relleno);
									break;
								case 6:
									plot.bezier(p.get(i).puntos.get(0), p.get(i).puntos.get(1), p.get(i).puntos.get(2), p.get(i).puntos.get(3));
									break;
								case 7:
									plot.relleno(p.get(i).puntos.get(0));
									break;
							}
						}
						canvas.redraw();
						break;
				}				
			}
		};
		listaFiguras.bFijar.addListener(SWT.MouseDown, listener2);
	}
	
	private void crearListener() {
		Listener listener = new Listener() {						
			public void handleEvent(Event e) {					
				tipoFigura.getTipo();
				z = tipoFigura.getZ();
				switch(e.type) {
					case SWT.MouseDown:												
						switch(e.button) {
							case clickIzq:				
								z = tipoFigura.getZ();
								switch(tipoFigura.getTipo()) {
								case 1:
									ps.removeAllElements();		
									ps.add(new Punto(e.x, e.y, z));
									plot.pixel(ps.get(0));									
									reiniciarPts=true;
									redibujar=true;
									break;
								case 2:			
									if(ps.size()==0) {
										pAnt = new Punto(e.x, e.y, z);
										ps.add(new Punto(e.x, e.y, z));
										redibujar=true;
									}
									else {
										ps.add(new Punto(e.x, e.y, z));
										plot.linea(ps.get(0), ps.get(1));										
										reiniciarPts=true;
										redibujar=true;
									}						
									break;
								case 3:			
									if(ps.size()==0) {
										pAnt = new Punto(e.x, e.y, z);
										ps.add(new Punto(e.x, e.y, z));
										redibujar=true;
									}
									else {
										plot.linea(ps.get(ps.size()-1), new Punto(e.x, e.y));
										ps.add(new Punto(e.x, e.y, z));
										redibujar=true;
									}						
									break;
								case 4:			
									if(ps.size()==0) {
										pAnt = new Punto(e.x, e.y, z);
										ps.add(new Punto(e.x, e.y, z));
										redibujar=true;
									}
									else {
										ps.add(new Punto(e.x, e.y, z));
										plot.circulo(ps.get(0), ps.get(1), tipoFigura.getFill());
										reiniciarPts=true;
										redibujar=true;
									}						
									break;
								case 5:			
									if(ps.size()==0) {
										pAnt = new Punto(e.x, e.y, z);
										ps.add(new Punto(e.x, e.y, z));
										redibujar=true;
									}
									else {
										ps.add(new Punto(e.x, e.y, z));
										plot.elipse(ps.get(0), ps.get(1), tipoFigura.getFill());
										reiniciarPts=true;
										redibujar=true;
									}						
									break;
								case 6:
									if(ps.size()==0) {
										pAnt = new Punto(e.x, e.y, z);
										ps.add(new Punto(e.x, e.y, z));
										redibujar=true;
									}
									else if (ps.size()<3){
										plot.linea(ps.get(ps.size()-1), new Punto(e.x, e.y));
										ps.add(new Punto(e.x, e.y, z));
										redibujar=true;
									}
									else  if (ps.size()==3){
										ps.add(new Punto(e.x, e.y, z));
										plot.xOr(true);										
										for(int i=1; i< ps.size(); i++) {
											plot.linea(ps.get(i-1), ps.get(i));															
										}
										plot.xOr(false);																				
										plot.bezier(ps.get(0), ps.get(1), ps.get(2), ps.get(3));										
										reiniciarPts=true;
										redibujar = true;
									}
									break;
								case 7:
									MessageBox m=new MessageBox(sShell, SWT.ICON_INFORMATION);
									m.setText("Aviso");
									m.setMessage("Puede que tarde mucho esta implementacion :(");
									m.open();						
									ps.add(new Punto(e.x, e.y, z));
									plot.relleno(ps.firstElement());
									reiniciarPts=true;
									redibujar=true;
									break;
								case 8:			
									if(ps.size()==0) {
										pAnt = new Punto(e.x, e.y, z);
										ps.add(new Punto(e.x, e.y, z));
										redibujar=true;
									}
									else {
										ps.add(new Punto(e.x, e.y, z));
										
										int x1, x2, y1, y2;
										x1=ps.get(0).getX()<ps.get(1).getX()?ps.get(0).getX():ps.get(1).getX();
										x2=ps.get(0).getX()>ps.get(1).getX()?ps.get(0).getX():ps.get(1).getX();
										y1=ps.get(0).getY()<ps.get(1).getY()?ps.get(0).getY():ps.get(1).getY();
										y2=ps.get(0).getY()>ps.get(1).getY()?ps.get(0).getY():ps.get(1).getY();
										
										plot.xOr(true);
										plot.gc.drawRectangle(x1,y1, x2-x1, y2-y1);										
										ps.get(0).setX(x1);
										ps.get(1).setX(x2);
										ps.get(0).setY(y1);
										ps.get(1).setY(y2);
										plot.xOr(false);																				
										reiniciarPts=true;
										redibujar=true;
									}						
									break;
								}								
								break;							
						}
						break;
					case SWT.MouseDoubleClick:
						switch(e.button) {
							case clickIzq:
								z = tipoFigura.getZ();
								switch(tipoFigura.getTipo()) {
								case 3:
									ps.add(new Punto(e.x, e.y, z));
									plot.linea(ps.get(0), ps.lastElement());
									
									if(tipoFigura.getFill()) {
										Polilinea poli = new Polilinea(plot, ps.get(0), ps.get(1));
										for(int j=1; j<ps.size()-1; j++) {
											poli.agregarLinea( ps.get(j), ps.get(j+1));
										}
										poli.agregarLinea(ps.firstElement(), ps.lastElement());
										poli.rellenarPolilinea(plot);
											
									}
									
									
									reiniciarPts=true;
									redibujar=true;
									break;
								}
								break;							
						}
						break;

					case SWT.MouseMove:						
						plot.xOr(true);
						z = tipoFigura.getZ();
						switch(tipoFigura.getTipo()) {						
						case 2:
							if(ps.size()>0) {								
								plot.linea(ps.get(0), pAnt);
								pAnt = new Punto(e.x, e.y, z);
								plot.linea(ps.get(0), pAnt);
								redibujar=true;
							}								
							break;							
						case 3:
							if(ps.size()>0) {							
								plot.linea(ps.get(ps.size()-1), pAnt);
								pAnt = new Punto(e.x, e.y, z);
								plot.linea(ps.get(ps.size()-1), pAnt);
								redibujar=true;
							}								
							break;							
						case 4:
							if(ps.size()>0) {							
								plot.circulo(ps.get(0), pAnt, false);						
								pAnt = new Punto(e.x, e.y, z);
								plot.circulo(ps.get(0), pAnt, false);
								redibujar=true;
							}						
							break;							
						case 5:
							if(ps.size()>0) {						
								plot.elipse(ps.get(0), pAnt, false);						
								pAnt = new Punto(e.x, e.y, z);
								plot.elipse(ps.get(0), pAnt, false);
								redibujar=true;
							}						
							break;
						case 6:
							if(ps.size()>0) {							
								plot.linea(ps.get(ps.size()-1), pAnt);
								pAnt = new Punto(e.x, e.y, z);
								plot.linea(ps.get(ps.size()-1), pAnt);
								redibujar=true;
							}
							break;
						case 8:
							if(ps.size()>0) {		
								int x1, x2, y1, y2;
								x1=ps.get(0).getX()<pAnt.getX()?ps.get(0).getX():pAnt.getX();
								x2=ps.get(0).getX()>pAnt.getX()?ps.get(0).getX():pAnt.getX();
								y1=ps.get(0).getY()<pAnt.getY()?ps.get(0).getY():pAnt.getY();
								y2=ps.get(0).getY()>pAnt.getY()?ps.get(0).getY():pAnt.getY();
								plot.gc.drawRectangle(x1,y1, x2-x1, y2-y1);

								pAnt = new Punto(e.x, e.y, z);
								x1=ps.get(0).getX()<pAnt.getX()?ps.get(0).getX():pAnt.getX();
								x2=ps.get(0).getX()>pAnt.getX()?ps.get(0).getX():pAnt.getX();
								y1=ps.get(0).getY()<pAnt.getY()?ps.get(0).getY():pAnt.getY();
								y2=ps.get(0).getY()>pAnt.getY()?ps.get(0).getY():pAnt.getY();
								plot.gc.drawRectangle(x1,y1, x2-x1, y2-y1);

								redibujar=true;
							}								
							break;		
							
						}
						plot.xOr(false);						
						labelInfo.setText("("+e.x+", "+e.y+", "+z+")");
												
						break;
				}
				if(reiniciarPts) {
					listaFiguras.insertarFigura(tipoFigura.getTipo(), tipoFigura.getFill(), ps);
					ps.removeAllElements();					
					reiniciarPts=false;
				}
				if(redibujar) {
					canvas.redraw();
					redibujar=false;
				}
			}
		};
		canvas.addListener(SWT.MouseDown, listener);
		canvas.addListener(SWT.MouseMove, listener);
		canvas.addListener(SWT.MouseDoubleClick, listener);
		
		
	}
	
	private void createCompositePrincipal() {
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.FILL;
		gridData11.grabExcessHorizontalSpace = true;
		gridData11.grabExcessVerticalSpace = true;
		gridData11.verticalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
		compositePrincipal = new Composite(sShell, SWT.NONE);
		compositePrincipal.setLayoutData(gridData);
		compositePrincipal.setLayout(gridLayout);
		
		canvas = new Canvas(compositePrincipal, SWT.NONE);
		
		
		canvas.setBackground(negro);
		canvas.setForeground(blanco);
		canvas.setLayoutData(gridData11);

		canvas.addPaintListener(new PaintListener() {
				public void paintControl(PaintEvent e) {
					e.gc.drawImage(imageBuffer, 0, 0);
				}
			}
		);
		
		imageBuffer = new Image(canvas.getDisplay(), 1200, 800);
		plot = new Plot(new GC(imageBuffer), imageBuffer,  negro, blanco);
		
		plot.gc.fillRectangle(0, 0, 1200, 800);
		canvas.redraw();
		
	}
	

	/**
	 * This method initializes compositeBarras	
	 *
	 */
	private void createCompositeBarras() {
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.BEGINNING;
		gridData2.grabExcessVerticalSpace = true;
		gridData2.verticalSpan = 2;
		gridData2.verticalAlignment = GridData.FILL;
		gridData2.heightHint = -1;
		gridData2.widthHint = 260;
		
		
		GridData gridData3 = new GridData();
		gridData3.grabExcessVerticalSpace = true;
		gridData3.verticalAlignment = GridData.FILL;
		gridData3.horizontalAlignment = GridData.BEGINNING;
		
		compositeBarras = new Composite(sShell, SWT.NONE);
		compositeBarras.setLayout(new FillLayout());
		compositeBarras.setLayoutData(gridData2);
		
		ExpandBar bar = new ExpandBar (compositeBarras, SWT.V_SCROLL);
			
			tipoFigura = new TipoFigura(bar, SWT.NONE);
			ExpandItem item0 = new ExpandItem (bar, SWT.NONE, 0);				
			item0.setText("Primitivas");
			item0.setHeight(tipoFigura.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item0.setExpanded(true);
			item0.setControl(tipoFigura);
			
			
			listaFiguras = new ListaFiguras(bar, SWT.NONE);
			ExpandItem item1 = new ExpandItem (bar, SWT.NONE, 1);		
			item1.setText("Figuras");
			item1.setHeight(listaFiguras.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item1.setExpanded(true);
			item1.setControl(listaFiguras);
			
			
			
		
		bar.setSpacing(8);		
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Paint thisClass = new Paint();
		thisClass.createSShell();
		thisClass.sShell.open();

		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 2;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.CENTER;
		sShell = new Shell();
		sShell.setText("Algoritmos de Graficacion  .:: Roberto Loaeza Valerio ::.");
		createCompositePrincipal();
		sShell.setLayout(gridLayout1);
		sShell.setSize(new Point(959, 662));
		createCompositeBarras();
		menuBar = new Menu(sShell, SWT.BAR);
		MenuItem submenuItemArchivo = new MenuItem(menuBar, SWT.CASCADE);
		submenuItemArchivo.setText("Archivo");
		submenu = new Menu(submenuItemArchivo);
		MenuItem pushSalir = new MenuItem(submenu, SWT.PUSH);
		pushSalir.setText("Salir");
		pushSalir.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.exit(1);
			}
		});
		submenuItemArchivo.setMenu(submenu);
		sShell.setMenuBar(menuBar);
		labelInfo = new Label(sShell, SWT.NONE);
		labelInfo.setText("Punto ( 0, 0 )");
		labelInfo.setLayoutData(gridData1);
		
		crearListener();
		crearListener2();
	}

}

