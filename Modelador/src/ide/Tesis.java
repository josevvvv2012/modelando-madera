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
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.layout.GridData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.CoolBar;


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
	private EnviaRobot enviaRobot = null;
	private Vistas vistasDiseno = null;

	private Plot plot = null;  //  @jve:decl-index=0:
	private Punto pAnt = null;
	private Punto pAnt2 = new Punto(0,0);  //  @jve:decl-index=0:
	private Vector<Punto> ps=new Vector<Punto>();  //  @jve:decl-index=0:
	private Menu menuBar = null;
	private Menu submenu = null;	
	private final int clickIzq = 1;

	
	private boolean okPort= false;
	private String comPort = "COM5";
	int []M = new int[4];
	private Comunicacion comuni;  //  @jve:decl-index=0:
	private boolean redibujar = false;
	private boolean reiniciarPts = false;
	boolean primera=true;
	private int z;
	private Menu submenu1 = null;
	private Menu submenu2 = null;	
	private LecturaEscritura le = new LecturaEscritura();  //  @jve:decl-index=0:
	private PGMRead pgm;  //  @jve:decl-index=0:
	boolean sigFig = false;
	boolean levantar=false;
	private int vistaActual=1;
	double xAnt, yAnt;
	private boolean importar=false;
	
	
	private Punto puntoActual = new Punto(1,1,1);  //  @jve:decl-index=0:
	private CoolBar coolBar = null;
	private Menu submenu3 = null;
	
	private void actualizarPuntoActual(Event e) {
		vistaActual = vistasDiseno.getOpcion();
		switch(vistaActual) {
		case DEF.vistaYX:
			puntoActual = new Punto(e.x, e.y, z);
			break;
		case DEF.vistaZX:
			puntoActual = new Punto(e.x, z, e.y);
			break;
		case DEF.vistaZY:
			puntoActual = new Punto(z, e.x, e.y);
			break;
		}
		

	}
	private Punto getPuntoActual() {
		return new Punto(puntoActual.getX(), puntoActual.getY(), puntoActual.getZ());
	}
	
	private void crearListenerRobotizar() {
		Listener listener3 = new Listener() {						
			public void handleEvent(Event e) {					
				switch(e.type) {
					case SWT.MouseDown:
						enviarRobot(true);						
						break;
				}				
			}
		};
		listaFiguras.buttonRobotizar.addListener(SWT.MouseDown, listener3);
	}
	
	
	
	private void enviarRobot(boolean menu) {
		if(okPort) {
			if(!enviaRobot.enviando) {
				enviaRobot.setFiguras(listaFiguras.figuras);
				enviaRobot.setComuni(comuni);
				enviaRobot.setImportar(importar);
				enviaRobot.bEnviar.setText(DEF.bDetieneRobot);
				if(menu) {
					enviaRobot.iniciarProcesoEnvio();
				}
			}
			else
				if(DEF.pedirConfirmacion(DEF.errorEnviando,DEF.error, sShell)==SWT.YES) {
					enviaRobot.detenerEnvio();																											
				}
		}
		else
			DEF.mostrarMSG(DEF.errorNoConexion,DEF.error, sShell);
	}
	
	private void crearListenerEnviarRobot() {
		Listener listener = new Listener() {						
			public void handleEvent(Event e) {					
				switch(e.type) {
					case SWT.MouseDown:
							enviarRobot(false);
						break;
				}				
			}
		};
		enviaRobot.bEnviar.addListener(SWT.MouseDown, listener);		
	}
	
	private void crearListenerPanel() {
		Listener listener = new Listener() {						
			public void handleEvent(Event e) {					
				switch(e.type) {
					case SWT.MouseMove:
						
						sShell.setCursor(new Cursor(sShell.getDisplay(), SWT.CURSOR_ARROW));
						break;
				}				
			}
		};
		
		compositePrincipal.addListener(SWT.MouseMove, listener);		
	}
	
	private void crearListenerB_Fijar() {
		vistaActual = vistasDiseno.getOpcion();
		Listener listener2 = new Listener() {
			
			public void handleEvent(Event e) {
				
				actualizarPuntoActual(e);
				Vector<Figuras> p = listaFiguras.figuras;
				listaFiguras.setOpcVisual(vistaActual);

				
				plotVista(plot);
				switch(e.type) {
					case SWT.MouseDown:												
						plot.gc.fillRectangle(0, 0, 1200, 800);
						
						for(int i=0; i<p.size(); i++) {
						
							if(listaFiguras.listFigura.getSelectionIndex()==i) {
								plot.selecColor();
								
								//System.out.println("Figura: "+i);
							}else {
								plot.unselecColor();
							}
							
							 
							switch(p.get(i).tipoFig) {
								case DEF.punto:
									plot.pixel(new Punto(p.get(i).puntos.firstElement().getX(),p.get(i).puntos.firstElement().getY(), p.get(i).puntos.firstElement().getZ()), vistaActual);
									break;
								case DEF.linea:
									plot.linea(p.get(i).puntos.get(0), p.get(i).puntos.get(1), vistaActual);
									break;
								case DEF.polilinea:									
									Polilinea poli = new Polilinea(plot, p.get(i).puntos.get(0), p.get(i).puntos.get(1), vistaActual);
									for(int j=1; j<p.get(i).puntos.size()-1; j++) {
										poli.agregarLinea(p.get(i).puntos.get(j), p.get(i).puntos.get(j+1));
									}
									poli.agregarLinea(p.get(i).puntos.firstElement(), p.get(i).puntos.lastElement());
									
									break;
								case DEF.circulo:
									plot.circulo(p.get(i).puntos.firstElement(), p.get(i).puntos.lastElement(), p.get(i).relleno, vistaActual);
									break;
								case DEF.elipse:
									plot.elipse(p.get(i).puntos.firstElement(), p.get(i).puntos.lastElement(), p.get(i).relleno, vistaActual);
									break;
								case DEF.bezier:								
									plot.bezier(p.get(i).puntos, 1, vistaActual);			
									break;
							}					
						}
						canvas.redraw();
						plotVista(plot);
						break;
				}				
			}
		};
		listaFiguras.bFijar.addListener(SWT.MouseDown, listener2);
		vistasDiseno.bArriba.addListener(SWT.MouseDown, listener2);
		vistasDiseno.bFrente.addListener(SWT.MouseDown, listener2);
		vistasDiseno.bLado.addListener(SWT.MouseDown, listener2);
	}
	
	private void plotVista(Plot plot) {
		int x = 20;
		int y=520;
		
		y=this.sShell.getSize().y-170;
		plot.unselecColor();
		
		plot.linea(new Punto(x, y+30, 0),new Punto(x+30, y+30, 0), DEF.vistaYX);				
		plot.linea(new Punto(x+30, y+30, 0),new Punto(x+25, y+27, 0), DEF.vistaYX);				
		plot.linea(new Punto(x+30, y+30, 0),new Punto(x+25, y+33, 0), DEF.vistaYX);
						
		plot.linea(new Punto(x, y, 0),new Punto(x, y+30, 0), DEF.vistaYX);				
		plot.linea(new Punto(x, y, 0),new Punto(x-3, y+5, 0), DEF.vistaYX);
		plot.linea(new Punto(x, y, 0),new Punto(x+3, y+5, 0), DEF.vistaYX);
		
		switch(vistaActual) {
			case DEF.vistaYX:
				plot.linea(new Punto(x, y-5, 0),new Punto(x+5, y-15, 0), DEF.vistaYX);
				plot.linea(new Punto(x+2, y-10, 0),new Punto(x-2, y-15, 0), DEF.vistaYX);
				break;
			case DEF.vistaZX:
				plot.linea(new Punto(x-4, y-5, 0),new Punto(x+4, y-15, 0), DEF.vistaYX);
				plot.linea(new Punto(x-4, y-15, 0),new Punto(x+4, y-15, 0), DEF.vistaYX);
				plot.linea(new Punto(x-4, y-5, 0),new Punto(x+4, y-5, 0), DEF.vistaYX);
				break;
			case DEF.vistaZY:
				plot.linea(new Punto(x-4, y-5, 0),new Punto(x+4, y-15, 0), DEF.vistaYX);
				plot.linea(new Punto(x-4, y-15, 0),new Punto(x+4, y-15, 0), DEF.vistaYX);
				plot.linea(new Punto(x-4, y-5, 0),new Punto(x+4, y-5, 0), DEF.vistaYX);
				break;
		}
		
		x += 37;
		y+=40;
		
		
		switch(vistaActual) {
			case DEF.vistaYX:
				plot.linea(new Punto(x-4, y-5, 0),new Punto(x+4, y-15, 0), DEF.vistaYX);
				plot.linea(new Punto(x+4, y-5, 0),new Punto(x-4, y-15, 0), DEF.vistaYX);
				
				break;
			case DEF.vistaZX:
				plot.linea(new Punto(x-4, y-5, 0),new Punto(x+4, y-15, 0), DEF.vistaYX);
				plot.linea(new Punto(x+4, y-5, 0),new Punto(x-4, y-15, 0), DEF.vistaYX);
				
				break;
			case DEF.vistaZY:
				plot.linea(new Punto(x, y-5, 0),new Punto(x+5, y-15, 0), DEF.vistaYX);
				plot.linea(new Punto(x+2, y-10, 0),new Punto(x-2, y-15, 0), DEF.vistaYX);
				break;
		}							
	}
	
	private void crearListenerCanvas() {	
		plotVista(plot);
		Listener listener = new Listener() {						
			public void handleEvent(Event e) {					
				
				tipoFigura.getTipo();
				z = tipoFigura.getZ();
				actualizarPuntoActual(e);
				
				
				switch(e.type) {
					case SWT.MouseDown:					
						
						switch(e.button) {
							case clickIzq:											
								switch(tipoFigura.getTipo()) {
								case DEF.galeria:									
									le.leer(tipoFigura.getArchivoGaleria(), listaFiguras,/* tipoFigura.getZ()*/ getPuntoActual());									
									listaFiguras.bFijar.notifyListeners(SWT.MouseDown, null);
						        	canvas.redraw();
						        	
									break;
								case DEF.punto:
									ps.removeAllElements();		
									ps.add(getPuntoActual());
									plot.pixel(ps.get(0), vistaActual);									
									reiniciarPts=true;
									redibujar=true;
									break;
								case DEF.linea:			
									if(ps.size()==0) {
										pAnt = getPuntoActual();
										ps.add(getPuntoActual());
										redibujar=true;
									}
									else {
										ps.add(getPuntoActual());
										plot.linea(ps.get(0), ps.get(1), vistaActual);										
										reiniciarPts=true;
										redibujar=true;
									}						
									break;
								case DEF.polilinea:			
									if(ps.size()==0) {
										pAnt = getPuntoActual();
										ps.add(getPuntoActual());
										redibujar=true;
									}
									else {
										plot.linea(ps.get(ps.size()-1), getPuntoActual(), vistaActual);
										ps.add(getPuntoActual());
										redibujar=true;
									}						
									break;
								case DEF.circulo:			
									if(ps.size()==0) {
										pAnt = getPuntoActual();
										ps.add(getPuntoActual());
										redibujar=true;
									}
									else {
										ps.add(getPuntoActual());
										plot.circulo(ps.get(0), ps.get(1), tipoFigura.getFill(), vistaActual);
										reiniciarPts=true;
										redibujar=true;
									}						
									break;
								case DEF.elipse:			
									if(ps.size()==0) {
										pAnt = getPuntoActual();
										ps.add(getPuntoActual());
										redibujar=true;
									}
									else {
										ps.add(getPuntoActual());
										plot.elipse(ps.get(0), ps.get(1), tipoFigura.getFill(), vistaActual);
										reiniciarPts=true;
										redibujar=true;
									}						
									break;
								case DEF.bezier:
									if(ps.size()==0) {
										pAnt = getPuntoActual();
										ps.add(getPuntoActual());
										redibujar=true;
									}
									else /*if (ps.size()>-3)*/{
										ps.add(getPuntoActual());
										plot.linea(ps.get(ps.size()-2), ps.get(ps.size()-1), vistaActual);
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
								case DEF.polilinea:
									ps.add(getPuntoActual());
									plot.linea(ps.get(0), ps.lastElement(), vistaActual);
									
									if(tipoFigura.getFill()) {
										Polilinea poli = new Polilinea(plot, ps.get(0), ps.get(1), vistaActual);
										for(int j=1; j<ps.size()-1; j++) {
											poli.agregarLinea( ps.get(j), ps.get(j+1));
										}
										poli.agregarLinea(ps.firstElement(), ps.lastElement());
										//poli.graficar();	
										//poli.rellenarPolilinea(plot);
											
									}																		
									reiniciarPts=true;
									redibujar=true;
									break;
								case DEF.bezier:
									ps.add(getPuntoActual());
									plot.xOr(true);						
									for(int i=1; i< ps.size()-1; i++) {												
										plot.linea(ps.get(i-1), ps.get(i), vistaActual);											
									}
									plot.xOr(false);
									plot.bezier(ps, 1, vistaActual);									
									reiniciarPts=true;
									redibujar = true;
									break;
								}
								break;							
						}
						break;					
					case SWT.MouseMove:
						
						sShell.setCursor(new Cursor(sShell.getDisplay(), SWT.CURSOR_CROSS));
						plot.xOr(true);
					
						switch(tipoFigura.getTipo()) {						
						case DEF.linea:
							if(ps.size()>0) {								
								plot.linea(ps.get(0), pAnt, vistaActual);
								pAnt = getPuntoActual();
								plot.linea(ps.get(0), pAnt, vistaActual);
								redibujar=true;
							}								
							break;							
						case DEF.polilinea:
							if(ps.size()>0) {							
								plot.linea(ps.get(ps.size()-1), pAnt, vistaActual);
								pAnt = getPuntoActual();
								plot.linea(ps.get(ps.size()-1), pAnt, vistaActual);
								redibujar=true;
							}								
							break;							
						case DEF.circulo:
							if(ps.size()>0) {							
								plot.circulo(ps.get(0), pAnt, false, vistaActual
					);						
								pAnt = getPuntoActual();
								plot.circulo(ps.get(0), pAnt, false, vistaActual);
								redibujar=true;
							}						
							break;							
						case DEF.elipse:
							if(ps.size()>0) {						
								plot.elipse(ps.get(0), pAnt, false, vistaActual);						
								pAnt = getPuntoActual();
								plot.elipse(ps.get(0), pAnt, false, vistaActual);
								redibujar=true;
							}						
							break;
						case DEF.bezier:
							if(ps.size()>0) {							
								plot.linea(ps.get(ps.size()-1), pAnt, vistaActual);
								pAnt = getPuntoActual();
								plot.linea(ps.get(ps.size()-1), pAnt, vistaActual);
								redibujar=true;
							}
							break;															
						}
						plot.xOr(false);						
						new cinematica.Inversa().get_angles(getPuntoActual(), M);
						labelInfo.setText("("+puntoActual.getX()+", "+puntoActual.getY()+", "+puntoActual.getZ()+")" 
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
					plotVista(plot);
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
		
		
		canvas.setBackground(DEF.fondoColor);
		canvas.setForeground(DEF.frenteColor);
		canvas.setLayoutData(gridData11);

		canvas.addPaintListener(new PaintListener() {
				public void paintControl(PaintEvent e) {
					e.gc.drawImage(imageBuffer, 0, 0);
				}
			}
		);
		
		imageBuffer = new Image(canvas.getDisplay(), 1200, 800);
		plot = new Plot(new GC(imageBuffer), imageBuffer,  DEF.fondoColor, DEF.frenteColor);
		
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
		gridData2.widthHint = 350;
		
		
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
			item0.setText(DEF.tPrimitivas);
			item0.setHeight(tipoFigura.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			//item0.setHeight(300);
			item0.setExpanded(true);
			item0.setControl(tipoFigura);
			
			
			listaFiguras = new ListaFiguras(bar, SWT.NONE);
			ExpandItem item1 = new ExpandItem (bar, SWT.NONE, 1);		
			item1.setText(DEF.tFigurasProyecto);
			item1.setHeight(listaFiguras.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item1.setExpanded(false);
			item1.setControl(listaFiguras);
			
			
			vistasDiseno = new Vistas(bar,SWT.NONE);			
			ExpandItem item3 = new ExpandItem (bar, SWT.NONE, 2);		
			item3.setText(DEF.tVistasDiseno);
			item3.setHeight(vistasDiseno.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item3.setExpanded(true);
			item3.setControl(vistasDiseno);
			
			enviaRobot = new EnviaRobot(bar, SWT.NONE);
			ExpandItem item2 = new ExpandItem (bar, SWT.NONE, 3);		
			item2.setText(DEF.tEnviaRobot);
			item2.setHeight(enviaRobot.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item2.setExpanded(true);
			item2.setControl(enviaRobot);
			
			
		
		bar.setSpacing(8);		
	}


	/**
	 * This method initializes coolBar	
	 *
	 */
	private void createCoolBar() {
		GridData gridData4 = new GridData();
		gridData4.horizontalSpan = 2;
		gridData4.grabExcessVerticalSpace = false;
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.verticalAlignment = GridData.FILL;
		gridData4.widthHint = -1;
		gridData4.heightHint = -1;
		gridData4.grabExcessHorizontalSpace = true;
		coolBar = new CoolBar(sShell, SWT.NONE);
		coolBar.setLayoutData(gridData4);		
		ToolBar tb = new ToolBar(coolBar, SWT.FLAT);
		
		
		CoolItem item1 = new CoolItem (coolBar, SWT.NONE);
		ToolItem tNuevo = new ToolItem(tb, SWT.NONE);
		tNuevo.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/new.png")));
		tNuevo.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				archivoNuevo();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		tNuevo.setToolTipText(DEF.mArchivoNuevo);
	
		ToolItem tAbrir = new ToolItem(tb, SWT.NONE);
		tAbrir.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/open.png")));
		tAbrir.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				archivoAbrir(sShell);
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		tAbrir.setToolTipText(DEF.mArchivoAbrir);
		
		ToolItem tGuardar = new ToolItem(tb, SWT.NONE);
		tGuardar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/save.png")));
		tGuardar.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				archivoGuardarComo(sShell);
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		tGuardar.setToolTipText(DEF.mArchivoGuardar);
		
		
		ToolItem tUndo = new ToolItem(tb, SWT.NONE);
		tUndo.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/undo.png")));
		tUndo.setToolTipText(DEF.mEdicionDeshacer);
		ToolItem tRedo = new ToolItem(tb, SWT.NONE);
		tRedo.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/redo.png")));
			tRedo.setToolTipText(DEF.mEdicionRehacer);
		
		Point p = tb.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		tb.setSize(p);
		Point p2 = item1.computeSize(p.x, p.y);
		item1.setControl(tb);
		item1.setSize(p2);

		coolBar.pack ();
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
		sShell.setMaximized(false);
		createCoolBar();
		createCompositePrincipal();
		sShell.setLayout(gridLayout1);
		sShell.setSize(new Point(959, 662));
		createCompositeBarras();
		menuBar = new Menu(sShell, SWT.BAR);
		MenuItem submenuItemArchivo = new MenuItem(menuBar, SWT.CASCADE);
		submenuItemArchivo.setText(DEF.mArchivo);
		MenuItem submenuItemEdicion = new MenuItem(menuBar, SWT.CASCADE);
		submenuItemEdicion.setText(DEF.mEdicion);
		submenu3 = new Menu(submenuItemEdicion);
		MenuItem pushDeshacer = new MenuItem(submenu3, SWT.PUSH);
		pushDeshacer.setText(DEF.mEdicionDeshacer);
		pushDeshacer.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/undo.png")));
		MenuItem pushRehacer = new MenuItem(submenu3, SWT.PUSH);
		pushRehacer.setText(DEF.mEdicionRehacer);
		pushRehacer.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/redo.png")));
		MenuItem separator4 = new MenuItem(submenu3, SWT.SEPARATOR);
		MenuItem pushEliminar = new MenuItem(submenu3, SWT.PUSH);
		pushEliminar.setText(DEF.mEdicionEliminar);
		pushEliminar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/del.png")));
		MenuItem pushDuplicar = new MenuItem(submenu3, SWT.PUSH);
		pushDuplicar.setText(DEF.mEdicionDuplicar);
		pushDuplicar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/add.png")));
		MenuItem separator5 = new MenuItem(submenu3, SWT.SEPARATOR);
		MenuItem pushConfiguracion = new MenuItem(submenu3, SWT.PUSH);
		pushConfiguracion.setText(DEF.mEdicionConfiguracion);
		pushConfiguracion.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/conf.png")));
		submenuItemEdicion.setMenu(submenu3);
		MenuItem submenuItemHerramientas = new MenuItem(menuBar, SWT.CASCADE);
		submenuItemHerramientas.setText(DEF.mHerramientas);
		MenuItem submenuItemAyuda = new MenuItem(menuBar, SWT.CASCADE);
		submenuItemAyuda.setText(DEF.mAyuda);
		submenu2 = new Menu(submenuItemAyuda);
		MenuItem pushAcercaDe = new MenuItem(submenu2, SWT.PUSH);
		pushAcercaDe.setText(DEF.mAyudaAcercaDe);
		pushAcercaDe.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/help.png")));
		pushAcercaDe
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						DEF.mostrarMSG("Elaborado por:\n\tRoberto Loaeza Valerio",DEF.mAyudaAcercaDe, sShell);
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
		submenuItemAyuda.setMenu(submenu2);
		submenu1 = new Menu(submenuItemHerramientas);
		MenuItem pushActivarRobot = new MenuItem(submenu1, SWT.PUSH);
		pushActivarRobot.setText(DEF.mHerramientasActivarRobot);
		pushActivarRobot.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/run2.png")));
		MenuItem pushRobotizar = new MenuItem(submenu1, SWT.PUSH);
		pushRobotizar.setText(DEF.mHerramientasRobotizar);
		pushRobotizar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/run.png")));
		pushRobotizar
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {						
							enviarRobot(true);													
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
		MenuItem separator1 = new MenuItem(submenu, SWT.SEPARATOR);
		
		pushAbrir.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				archivoAbrir(sShell);
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		MenuItem pushCerrar = new MenuItem(submenu, SWT.PUSH);
		pushCerrar.setText(DEF.mArchivoCerrar);
		MenuItem separator = new MenuItem(submenu, SWT.SEPARATOR);
		MenuItem pushGuardar = new MenuItem(submenu, SWT.PUSH);
		pushGuardar.setText(DEF.mArchivoGuardar);
		pushGuardar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/save.png")));
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
		MenuItem separator2 = new MenuItem(submenu, SWT.SEPARATOR);
		MenuItem pushImportar = new MenuItem(submenu, SWT.PUSH);
		pushImportar.setText(DEF.mArchivoImportar);
		pushImportar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/importar.png")));
		MenuItem separator3 = new MenuItem(submenu, SWT.SEPARATOR);
		pushImportar
		.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				archivoImportar(sShell);
			}
			public void widgetDefaultSelected(
					org.eclipse.swt.events.SelectionEvent e) {
			}
		});
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
				if(DEF.pedirConfirmacion(DEF.pSalir, DEF.mArchivoSalir, sShell)==SWT.YES) 
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
		crearListenerEnviarRobot();
		crearListenerPanel();
	}
	private boolean inicializarPuerto() {
		comuni = new Comunicacion();  
		try {
			comuni.connect(comPort);
		} catch (Exception e) {
			DEF.mostrarMSG(DEF.errorConexionPuerto+e.toString(), DEF.error, sShell);
			return false;
		}
		return true;
	}
	
	
	private void archivoCerrar() {
		if(DEF.pedirConfirmacion(DEF.pCerrar, DEF.mArchivoCerrar, sShell)==SWT.YES) {
			listaFiguras.limpiarFiguras();
			listaFiguras.bFijar.notifyListeners(SWT.MouseDown, null);	
		}
		
	}
	private void archivoNuevo() {
		if(DEF.pedirConfirmacion(DEF.pCerrar, DEF.mArchivoCerrar,sShell)==SWT.YES) {
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
	        	le.leer(selected, listaFiguras, /*-1*/ null);	        	
	        	listaFiguras.bFijar.notifyListeners(SWT.MouseDown, null);
	        	canvas.redraw();
	        }
	}
	private void archivoImportar(Shell s) {
		 FileDialog fd = new FileDialog(s, SWT.OPEN);
	        fd.setText(DEF.mArchivoImportar);
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
					DEF.mostrarMSG(e.toString(), DEF.error, sShell);					
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
			DEF.mostrarMSG(e.toString(), DEF.error, sShell);
		}
		
				
		return true;
	}

	public double dist(double x0, double y0, double x1, double y1) {
		double x2 = Math.pow(x0 - x1, 2);
		double y2 = Math.pow(y0 - y1, 2);
		return Math.sqrt(x2+y2);	
	}
}

