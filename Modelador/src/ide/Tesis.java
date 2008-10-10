package ide;

import primitivas.Figuras;
import primitivas.Plot;
import primitivas.Punto;
import primitivas.Polilinea;
import serial.Comunicacion;
import store.LecturaEscritura;
import store.PGMRead;

import org.eclipse.swt.widgets.FileDialog;
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

import java.io.FileNotFoundException;
import java.io.IOException;
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
public class Tesis {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Label labelInfo = null;
	private Composite compositePrincipal = null;
	private Canvas canvas = null;
	private Image imageBuffer;
	private Composite compositeBarras = null;
	private TipoFigura2 tipoFigura = null;
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
	
	private boolean okPort= false;
	private String comPort = "COM4";
	int []M = new int[4];
	private Comunicacion comuni;  //  @jve:decl-index=0:
	private boolean redibujar = false;
	private boolean reiniciarPts = false;
	boolean primera=true;
	private int z;
	private Menu submenu1 = null;
	private Menu submenu2 = null;
	private double incRobot = 0.12;
	private LecturaEscritura le = new LecturaEscritura();  //  @jve:decl-index=0:
	private PGMRead pgm;  //  @jve:decl-index=0:
	boolean sigFig = false;
	boolean levantar=false;
	private int arriba=150000;
	private int retardo1=0;
	double xAnt, yAnt;
	private boolean importar=false;
	
	private void crearListenerRobotizar() {
		Listener listener3 = new Listener() {						
			public void handleEvent(Event e) {					
				switch(e.type) {
					case SWT.MouseDown:
						if(okPort)
							robotizar(listaFiguras.figuras);
						else
							mostrarMSG(DEF.errorNoConexion,DEF.error);
						break;
				}				
			}
		};
		listaFiguras.buttonRobotizar.addListener(SWT.MouseDown, listener3);
	}
	
	
	
	private void crearListenerB_Fijar() {
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
									plot.circulo(p.get(i).puntos.firstElement(), p.get(i).puntos.lastElement(), p.get(i).relleno, 1);
									break;
								case 5:
									plot.elipse(p.get(i).puntos.firstElement(), p.get(i).puntos.lastElement(), p.get(i).relleno, 1);
									break;
								case 6:
									//plot.bezier(p.get(i).puntos.get(0), p.get(i).puntos.get(1), p.get(i).puntos.get(2), p.get(i).puntos.get(3), 1000);
									plot.bezier(p.get(i).puntos, 1);
									
									
									
									
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
	
	private void crearListenerCanvas() {
		Listener listener = new Listener() {						
			public void handleEvent(Event e) {					
				tipoFigura.getTipo();
				z = tipoFigura.getZ();
				switch(e.type) {
					case SWT.MouseDown:												
						switch(e.button) {
							case clickIzq:											
								switch(tipoFigura.getTipo()) {
								case -1:									
									le.leer(tipoFigura.getArchivoGaleria(), listaFiguras, tipoFigura.getZ());									
									listaFiguras.bFijar.notifyListeners(SWT.MouseDown, null);
						        	canvas.redraw();
						        	
									break;
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
										plot.circulo(ps.get(0), ps.get(1), tipoFigura.getFill(),1);
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
										plot.elipse(ps.get(0), ps.get(1), tipoFigura.getFill(), 1);
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
									else /*if (ps.size()>-3)*/{
										plot.linea(ps.get(ps.size()-1), new Punto(e.x, e.y));
										ps.add(new Punto(e.x, e.y, z));
										redibujar=true;
									}
									/*
									else  if (ps.size()==-3){
										ps.add(new Punto(e.x, e.y, z));
										plot.xOr(true);										
										for(int i=1; i< ps.size(); i++) {
											plot.linea(ps.get(i-1), ps.get(i),1);															
										}
										plot.xOr(false);																				
										plot.bezier(ps.get(0), ps.get(1), ps.get(2), ps.get(3), 1000);										
										reiniciarPts=true;
										redibujar = true;
									}
									*/
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
										
										double x1, x2, y1, y2;
										x1=ps.get(0).getX()<ps.get(1).getX()?ps.get(0).getX():ps.get(1).getX();
										x2=ps.get(0).getX()>ps.get(1).getX()?ps.get(0).getX():ps.get(1).getX();
										y1=ps.get(0).getY()<ps.get(1).getY()?ps.get(0).getY():ps.get(1).getY();
										y2=ps.get(0).getY()>ps.get(1).getY()?ps.get(0).getY():ps.get(1).getY();
										
										plot.xOr(true);
										plot.gc.drawRectangle((int)x1,(int)y1, (int)(x2-x1), (int)(y2-y1));										
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
										//poli.graficar();	
										poli.rellenarPolilinea(plot);
											
									}																		
									reiniciarPts=true;
									redibujar=true;
									break;
								case 6:
									ps.add(new Punto(e.x, e.y, z));
									plot.xOr(true);										
									for(int i=1; i< ps.size(); i++) {
										plot.linea(ps.get(i-1), ps.get(i));															
									}
									plot.xOr(false);
									
									plot.bezier(ps, 1);
									
									/*
									double ix, iy;																		
									Vector<Punto> psNuevo = new Vector<Punto>();
									Transformaciones trans;
									//psNuevo.add(new Punto(ps.get(0).getX()-ix, ps.get(0).getY()-iy));
									//trans.escalamiento(.9, .9);
									
									ix = ps.get(0).getX();
									iy = ps.get(0).getY();
									psNuevo = (Vector<Punto>) ps.clone();
									for(double ik =.01;ik<1; ik+=.01) {
										//psNuevo = (Vector<Punto>) ps.clone();
										for(int ii=0; ii<ps.size(); ii++) {											
											//psNuevo.add(new Punto(ps.get(ii).getX()-ix, ps.get(ii).getY()-iy));
											psNuevo.get(ii).setX(psNuevo.get(ii).getX()-ix);
											psNuevo.get(ii).setY(psNuevo.get(ii).getY()-iy);
											trans = new Transformaciones(psNuevo.get(ii));
											trans.escalamiento(1-ik, 1-ik);
											psNuevo.get(ii).setX(psNuevo.get(ii).getX()+ix);
											psNuevo.get(ii).setY(psNuevo.get(ii).getY()+iy);
											//System.out.println(ps.get(ii).getXML(1));
											//p2 =  (Vector<Punto>)p.get(i).puntos.clone();											
										}
										System.out.println("Ejecutando: "+ik);										
										plot.bezier(psNuevo,1);
									}									
									*/
									reiniciarPts=true;
									redibujar = true;
									break;
								}
								break;							
						}
						break;

					case SWT.MouseMove:						
						plot.xOr(true);					
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
								plot.circulo(ps.get(0), pAnt, false, 1);						
								pAnt = new Punto(e.x, e.y, z);
								plot.circulo(ps.get(0), pAnt, false,1);
								redibujar=true;
							}						
							break;							
						case 5:
							if(ps.size()>0) {						
								plot.elipse(ps.get(0), pAnt, false, 1);						
								pAnt = new Punto(e.x, e.y, z);
								plot.elipse(ps.get(0), pAnt, false, 1);
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
								double x1, x2, y1, y2;
								x1=ps.get(0).getX()<pAnt.getX()?ps.get(0).getX():pAnt.getX();
								x2=ps.get(0).getX()>pAnt.getX()?ps.get(0).getX():pAnt.getX();
								y1=ps.get(0).getY()<pAnt.getY()?ps.get(0).getY():pAnt.getY();
								y2=ps.get(0).getY()>pAnt.getY()?ps.get(0).getY():pAnt.getY();
								plot.gc.drawRectangle((int)x1,(int)y1, (int)(x2-x1), (int)(y2-y1));

								pAnt = new Punto(e.x, e.y, z);
								x1=ps.get(0).getX()<pAnt.getX()?ps.get(0).getX():pAnt.getX();
								x2=ps.get(0).getX()>pAnt.getX()?ps.get(0).getX():pAnt.getX();
								y1=ps.get(0).getY()<pAnt.getY()?ps.get(0).getY():pAnt.getY();
								y2=ps.get(0).getY()>pAnt.getY()?ps.get(0).getY():pAnt.getY();
								plot.gc.drawRectangle((int)x1,(int)y1, (int)(x2-x1), (int)(y2-y1));

								redibujar=true;
							}								
							break;		
							
						}
						plot.xOr(false);						
						new cinematica.Inversa().get_angles(new Punto(e.x,e.y,z), M);
						labelInfo.setText("("+e.x+", "+e.y+", "+z+")" 
								+"  -> W"+M[0]+" "+M[1]+" "+M[2]+" "+M[3]);
												
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
		gridData2.widthHint = 300;
		
		
		GridData gridData3 = new GridData();
		gridData3.grabExcessVerticalSpace = true;
		gridData3.verticalAlignment = GridData.FILL;
		gridData3.horizontalAlignment = GridData.BEGINNING;
		
		compositeBarras = new Composite(sShell, SWT.NONE);
		compositeBarras.setLayout(new FillLayout());
		compositeBarras.setLayoutData(gridData2);
		
		ExpandBar bar = new ExpandBar (compositeBarras, SWT.V_SCROLL);
			
			tipoFigura = new TipoFigura2(bar, SWT.NONE);
			ExpandItem item0 = new ExpandItem (bar, SWT.NONE, 0);				
			item0.setText(DEF.tPrimitivasBasicas);
			//item0.setHeight(tipoFigura.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item0.setHeight(250);
			item0.setExpanded(true);
			item0.setControl(tipoFigura);
			
			
			listaFiguras = new ListaFiguras(bar, SWT.NONE);
			ExpandItem item1 = new ExpandItem (bar, SWT.NONE, 1);		
			item1.setText(DEF.tFigurasProyecto);
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
		Tesis thisClass = new Tesis();
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
		sShell.setText(".:: Roberto Loaeza Valerio ::.");
		sShell.setMaximized(true);
		createCompositePrincipal();
		sShell.setLayout(gridLayout1);
		sShell.setSize(new Point(959, 662));
		createCompositeBarras();
		menuBar = new Menu(sShell, SWT.BAR);
		MenuItem submenuItemArchivo = new MenuItem(menuBar, SWT.CASCADE);
		submenuItemArchivo.setText(DEF.mArchivo);
		MenuItem submenuItemHerramientas = new MenuItem(menuBar, SWT.CASCADE);
		submenuItemHerramientas.setText(DEF.mHerramientas);
		MenuItem submenuItemAyuda = new MenuItem(menuBar, SWT.CASCADE);
		submenuItemAyuda.setText(DEF.mAyuda);
		submenu2 = new Menu(submenuItemAyuda);
		MenuItem pushAcercaDe = new MenuItem(submenu2, SWT.PUSH);
		pushAcercaDe.setText(DEF.mAyudaAcercaDe);
		pushAcercaDe
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						mostrarMSG("Elaborado por:\n\tRoberto Loaeza Valerio",DEF.mAyudaAcercaDe);
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
		submenuItemAyuda.setMenu(submenu2);
		submenu1 = new Menu(submenuItemHerramientas);
		MenuItem pushActivarRobot = new MenuItem(submenu1, SWT.PUSH);
		pushActivarRobot.setText(DEF.mHerramientasActivarRobot);
		MenuItem pushRobotizar = new MenuItem(submenu1, SWT.PUSH);
		pushRobotizar.setText(DEF.mHerramientasRobotizar);
		pushRobotizar
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						if(okPort)
							robotizar(listaFiguras.figuras);
						else
							mostrarMSG(DEF.errorNoConexion,DEF.error);
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
		pushActivarRobot
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						if(inicializarPuerto()&&!okPort)
							okPort=true;
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
		submenuItemHerramientas.setMenu(submenu1);
		submenu = new Menu(submenuItemArchivo);
		MenuItem pushNuevo = new MenuItem(submenu, SWT.PUSH);
		pushNuevo.setText(DEF.mArchivoNuevo);
		pushNuevo.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/new.png")));
		pushNuevo.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				archivoNuevo();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		MenuItem pushAbrir = new MenuItem(submenu, SWT.PUSH);
		pushAbrir.setText(DEF.mArchivoAbrir);
		pushAbrir.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/open.png")));
		MenuItem pushImportar = new MenuItem(submenu, SWT.PUSH);
		pushImportar.setText(DEF.mArchivoImportar);
		pushImportar
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						archivoImportar(sShell);
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
		pushAbrir.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				archivoAbrir(sShell);
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		MenuItem pushCerrar = new MenuItem(submenu, SWT.PUSH);
		pushCerrar.setText(DEF.mArchivoCerrar);
		pushCerrar.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				archivoCerrar();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		MenuItem pushGuardarComo = new MenuItem(submenu, SWT.PUSH);
		pushGuardarComo.setText(DEF.mArchivoGuardarComo);
		pushGuardarComo.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/saveas.png")));
		pushGuardarComo
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						archivoGuardarComo(sShell);
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
		MenuItem pushSalir = new MenuItem(submenu, SWT.PUSH);
		pushSalir.setText(DEF.mArchivoSalir);
		pushSalir.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/exit.png")));
		pushSalir.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(pedirConfirmacion(DEF.pSalir, DEF.mArchivoSalir)==SWT.YES) 
					System.exit(0);				 
			}
			
		});
		submenuItemArchivo.setMenu(submenu);
		sShell.setMenuBar(menuBar);
		labelInfo = new Label(sShell, SWT.NONE);
		labelInfo.setText("Punto ( 0, 0, 0 )");
		labelInfo.setLayoutData(gridData1);
		
		crearListenerCanvas();
		crearListenerB_Fijar();
		crearListenerRobotizar();				
	}
	private boolean inicializarPuerto() {
		comuni = new Comunicacion();  
		try {
			comuni.connect(comPort);
		} catch (Exception e) {
			mostrarMSG(DEF.errorConexionPuerto+e.toString(), DEF.error);
			return false;
		}
		return true;
	}
	
	private void robotizar(Vector<Figuras> figuras) {
		Vector <primitivas.Punto>puntos;		
		
		double dx, dy;
		primera=true;
		sigFig=false;
		levantar=false;
		primitivas.Primitiva prim = null;
		for(int i=0; i<figuras.size(); i++) {
			
			puntos = figuras.get(i).puntos;
			
			if(i!=0) {
				retardo1=(int)dist(xAnt, yAnt, puntos.firstElement().getX(), puntos.firstElement().getY())*100;
				sigFig=true;
			}
			if(!importar||i==0) {				
				new cinematica.Inversa().get_angles(new Punto(puntos.firstElement().getX(), puntos.firstElement().getY(),arriba),M);
				mostrar("W"+M[0]+" "+M[1]+" "+M[2]+" "+M[3],1,1,  i, figuras.size());
				levantar=true;
			}
			
			
			switch(figuras.get(i).tipoFig) {
			case 1:
				new cinematica.Inversa().get_angles(puntos.get(0),M);				
				mostrar("W"+M[0]+" "+M[1]+" "+M[2]+" "+M[3],1,1,i, figuras.size());
				break;
			case 2:
				prim = new primitivas.Linea(null,puntos.get(0), puntos.get(1));				
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
						
				if((dx!=0)&&(dy!=0))
					prim = new primitivas.Elipse(null,puntos.get(0),dx, dy, false, incRobot);
				break;
			case 6:
				prim = new primitivas.Bezier(null, puntos, 160);
				
				
				break;
			}
			
			if(prim != null)
			{
				int [] mtemp= new int[4];
				for(int k=0; k<prim.getSizeCoordenadas(); k++) {					
					copiarM(M, mtemp);
					new cinematica.Inversa().get_angles(prim.getCoordenadas(k),M);
					if(!igualM(M, mtemp))
						mostrar("W"+M[0]+" "+M[1]+" "+M[2]+" "+M[3], k, prim.getSizeCoordenadas(),  i, figuras.size());
				}
				try {
				xAnt = prim.getCoordenadas(prim.getSizeCoordenadas()-1).getX();
				yAnt = prim.getCoordenadas(prim.getSizeCoordenadas()-1).getY();
				}
				catch (java.lang.ArrayIndexOutOfBoundsException e) {
					mostrarMSG(e.toString(), DEF.error);
				}
			}
			if(!importar) {
				new cinematica.Inversa().get_angles(new Punto(xAnt, yAnt,arriba),M);
				levantar=true;
				mostrar("W"+M[0]+" "+M[1]+" "+M[2]+" "+M[3],1,1, i, figuras.size());
			}
		}
		primera=true;
	}
	
	private boolean igualM(int[] orig, int[] dest) {
		for(int i=0; i<orig.length; i++)
			if(orig[i]!=dest[i])
				return false;
		return true;
	}
	private void copiarM(int[] orig, int [] dest) {
		for(int i=0; i<orig.length; i++)
			dest[i]=orig[i];
	}
	private void retardo() {
		try {
			if(levantar) {
				Thread.sleep(3500);
				levantar=false;
			}
			if(sigFig) {
				labelInfo.setText("Retardo ="+retardo1);
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
	
	private void mostrar(String str, int act, int max, int iFigura, int totalFigura) {
		labelInfo.setText("Ejecutando figura "+(iFigura+1)+" de "+totalFigura+".   Enviando: "+act+" de "+max+" instrucciones");
		try {
			comuni.escribe(str+"\n");
			
		} catch (IOException e) {
			mostrarMSG(e.toString(), DEF.error);
			
		}
		retardo();		
	}
	private void archivoCerrar() {
		if(pedirConfirmacion(DEF.pCerrar, DEF.mArchivoCerrar)==SWT.YES) {
			listaFiguras.limpiarFiguras();
			listaFiguras.bFijar.notifyListeners(SWT.MouseDown, null);	
		}
		
	}
	private void archivoNuevo() {
		if(pedirConfirmacion(DEF.pCerrar, DEF.mArchivoCerrar)==SWT.YES) {
			listaFiguras.limpiarFiguras();
			listaFiguras.bFijar.notifyListeners(SWT.MouseDown, null);
			importar=false;
		}
	}
	private void archivoAbrir(Shell s) {
		 FileDialog fd = new FileDialog(s, SWT.OPEN);
	        fd.setText(DEF.mArchivoAbrir);
	        fd.setFilterPath("/");
	        String[] filterExt = { "*"+DEF.ext, "*.*" };
	        fd.setFilterExtensions(filterExt);
	        String selected = fd.open();
	        if(selected!=null) {
	        	listaFiguras.limpiarFiguras();
	        	le.leer(selected, listaFiguras, -1);	        	
	        	listaFiguras.bFijar.notifyListeners(SWT.MouseDown, null);
	        	canvas.redraw();
	        }
	}
	private void archivoImportar(Shell s) {
		 FileDialog fd = new FileDialog(s, SWT.OPEN);
	        fd.setText(DEF.mArchivoAbrir);
	        fd.setFilterPath("/");
	        String[] filterExt = { "*"+DEF.extpgm, "*.*" };
	        fd.setFilterExtensions(filterExt);
	        String selected = fd.open();
	        if(selected!=null) {
	        	listaFiguras.limpiarFiguras();
	        	
	        	pgm = new PGMRead(selected,tipoFigura.getZ());
	        	try {
					pgm.cargar(listaFiguras);
					importar=true;
				} catch (FileNotFoundException e) {
					mostrarMSG(e.toString(), DEF.error);					
				}	        	
	        	listaFiguras.bFijar.notifyListeners(SWT.MouseDown, null);
	        	canvas.redraw();
	        }
	}
	
	private void archivoGuardarComo(Shell s) {
		 FileDialog fd = new FileDialog(s, SWT.SAVE);
	        fd.setText(DEF.mArchivoGuardarComo);	      
	        String[] filterExt = {"*"+DEF.ext};
	        fd.setFilterExtensions(filterExt);
	        String selected = fd.open();
	        if(selected!= null)
	        	guardar(selected, listaFiguras.figuras);
	}
	
	
	
	private boolean guardar(String str, Vector<Figuras> figuras) {
		
		try {
			le.escribe(str, figuras);
		} catch (IOException e) {
			mostrarMSG(e.toString(), DEF.error);
		}
		
				
		return true;
	}

	private int pedirConfirmacion(String str, String title) {
		MessageBox messageBox = new MessageBox(sShell, SWT.ICON_QUESTION
	            | SWT.YES | SWT.NO);
	        messageBox.setMessage(str);
	        messageBox.setText(title);
	        return messageBox.open();
	        
	}
	private void mostrarMSG(String str, String title) {
		MessageBox messageBox = new MessageBox(sShell, SWT.ICON_INFORMATION
	            | SWT.OK);
	        messageBox.setMessage(str);
	        messageBox.setText(title);
	        messageBox.open();	        
	}
	public double dist(double x0, double y0, double x1, double y1) {
		double x2 = Math.pow(x0 - x1, 2);
		double y2 = Math.pow(y0 - y1, 2);
		return Math.sqrt(x2+y2);
		
	}

}

