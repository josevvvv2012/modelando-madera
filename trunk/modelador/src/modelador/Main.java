package modelador;
//import javax.media.opengl.GLCanvas;

import javax.media.opengl.GL;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLDrawableFactory;
import javax.media.opengl.glu.GLU;


import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.graphics.Image;


import org.eclipse.swt.widgets.Label;

public class Main {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Composite CompositePrincipal = null;
	private Composite CompositeBarra = null;
	private Menu MenuBarPrincipal = null;
	private Menu Archivo = null;
	private Menu Edicion = null;
	private Menu Ayuda = null;
	private Menu Herramientas = null;
	//private Canvas Canvas = null;
	private GLCanvas Canvas = null;
	private Label tEstado = null;
	private double[] pila = new double[10];
	private int args=0;
	/**
	 * This method initializes CompositePrincipal	
	 *
	 */
	private void createCompositePrincipal() {
		GridData gridData21 = new GridData();
		gridData21.horizontalAlignment = GridData.FILL;
		gridData21.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 1;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
		CompositePrincipal = new Composite(sShell, SWT.NONE);
		createCanvas();
		CompositePrincipal.setLayout(gridLayout1);
		CompositePrincipal.setLayoutData(gridData);
		tEstado = new Label(CompositePrincipal, SWT.NONE);
		tEstado.setText("");
		tEstado.setLayoutData(gridData21);
	}

	/**
	 * This method initializes CompositeBarra	
	 *
	 */
	private void createCompositeBarra() {
		GridData gridData1 = new GridData();
		gridData1.grabExcessVerticalSpace = false;
		gridData1.horizontalAlignment = GridData.CENTER;
		gridData1.verticalAlignment = GridData.FILL;
		gridData1.heightHint = -1;
		gridData1.widthHint = 190;
		gridData1.grabExcessHorizontalSpace = false;
		//CompositeBarra = new Composite(sShell, SWT.NONE);
		CompositeBarra = new Barras(sShell, SWT.NONE);
		CompositeBarra.setLayout(new FillLayout());
		CompositeBarra.setLayoutData(gridData1);
	}

	/**
	 * This method initializes Canvas	
	 *
	 */
	private void createCanvas() {
		GridData gridData2 = new GridData();
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.FILL;
		gridData2.grabExcessVerticalSpace = true;
		//Canvas = new Canvas(CompositePrincipal, SWT.NONE);
		GLData data = new GLData ();
		data.doubleBuffer = true;
		Canvas = new GLCanvas(CompositePrincipal, SWT.NONE, data);
		Canvas.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		Canvas.setCurrent();
		final GLContext context = GLDrawableFactory.getFactory().createExternalGLContext();


		
		
		
		
		Canvas.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				if(args>3) {
					GL gl=context.getGL();				
					gl.glBegin (GL.GL_LINES);
					gl.glVertex2i (50, 200);
					gl.glVertex2i (75, 250);
					gl.glVertex2i (60, 200);
					gl.glVertex2i (85, 250);
					gl.glEnd();
					Canvas.redraw();
					args=0;
					System.out.println("dibujando");
				}
				pila[args++]=event.x;
				pila[args++]=event.y;
				
				
				tEstado.setText(getPila()+"["+event.x+", "+event.y+"]");
			}
		});
		Canvas.addListener(SWT.MouseMove, new Listener() {
			public void handleEvent(Event event) {
				
				tEstado.setText(getPila()+"["+event.x+", "+event.y+"]");
			}
		});
		
		
		
		Canvas.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				Rectangle bounds = Canvas.getBounds();
				float fAspect = (float) bounds.width / (float) bounds.height;
				Canvas.setCurrent();
				context.makeCurrent();
				GL gl = context.getGL ();
				gl.glViewport(0, 0, bounds.width, bounds.height);
				gl.glMatrixMode(GL.GL_PROJECTION);
				gl.glLoadIdentity();
				GLU glu = new GLU();
				glu.gluPerspective(45.0f, fAspect, 0.5f, 400.0f);
				gl.glMatrixMode(GL.GL_MODELVIEW);
				gl.glLoadIdentity();
				context.release();
			}
		});
		
		context.makeCurrent();
		GL gl = context.getGL ();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gl.glClearDepth(1.0);
		gl.glLineWidth(2);
		gl.glEnable(GL.GL_DEPTH_TEST);
		context.release();
		

		Canvas.setLayoutData(gridData2);
	}
	private String getPila() {
		String ret;
		ret="("+pila[0];
		for(int i=1; i<args; i++) {
			ret=ret+", "+pila[i];
		}
		ret=ret+")";
		return ret;
	}
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Main thisClass = new Main();
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
		GridLayout gridLayout = new GridLayout();
		gridLayout.horizontalSpacing = 2;
		gridLayout.numColumns = 2;
		sShell = new Shell();
		sShell.setText("Tesis: Proyecto Modelador");
		sShell.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/settings.png")));
		sShell.setLayout(gridLayout);
		createCompositePrincipal();
		createCompositeBarra();
		sShell.setSize(new Point(844, 496));
		MenuBarPrincipal = new Menu(sShell, SWT.BAR);
		MenuItem submenuArchivo = new MenuItem(MenuBarPrincipal, SWT.CASCADE);
		submenuArchivo.setText("Archivo");
		MenuItem submenuEdicion = new MenuItem(MenuBarPrincipal, SWT.CASCADE);
		submenuEdicion.setText("Edicion");
		MenuItem submenuHerramientas = new MenuItem(MenuBarPrincipal, SWT.CASCADE);
		submenuHerramientas.setText("Herramientas");
		Herramientas = new Menu(submenuHerramientas);
		MenuItem Punto = new MenuItem(Herramientas, SWT.RADIO);
		Punto.setText("Punto");
		MenuItem Linea = new MenuItem(Herramientas, SWT.RADIO);
		Linea.setText("Linea");
		MenuItem Arco = new MenuItem(Herramientas, SWT.RADIO);
		Arco.setText("Arco");
		MenuItem Circulo = new MenuItem(Herramientas, SWT.RADIO);
		Circulo.setText("Circulo");
		MenuItem Bezier = new MenuItem(Herramientas, SWT.RADIO);
		Bezier.setText("Bezier");
		submenuHerramientas.setMenu(Herramientas);
		MenuItem submenuAyuda = new MenuItem(MenuBarPrincipal, SWT.CASCADE);
		submenuAyuda.setText("Ayuda");
		Ayuda = new Menu(submenuAyuda);
		MenuItem AcercaDE = new MenuItem(Ayuda, SWT.PUSH);
		AcercaDE.setText("Acerca de...");
		AcercaDE.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/info.png")));		
		AcercaDE.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				MessageBox messageBox =   new MessageBox(sShell, SWT.OK);
				messageBox.setText("Acerca de...");
				messageBox.setMessage("Elaborado por Roberto Loaeza Valerio\n\nroberto.loaeza@gmail.com");
				messageBox.open();
			}	
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		MenuItem Tutorial = new MenuItem(Ayuda, SWT.PUSH);
		Tutorial.setText("Tutorial");
		Tutorial.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/help.png")));
		submenuAyuda.setMenu(Ayuda);
		Edicion = new Menu(submenuEdicion);
		MenuItem Deshacer = new MenuItem(Edicion, SWT.PUSH);
		Deshacer.setText("Deshacer");
		Deshacer.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/undo.png")));
		MenuItem Rehacer = new MenuItem(Edicion, SWT.PUSH);
		Rehacer.setText("Rehacer");
		Rehacer.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/redo.png")));
		MenuItem separator = new MenuItem(Edicion, SWT.SEPARATOR);
		MenuItem Cortar = new MenuItem(Edicion, SWT.PUSH);
		Cortar.setText("Cortar");
		Cortar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/cut.png")));
		MenuItem Copiar = new MenuItem(Edicion, SWT.PUSH);
		Copiar.setText("Copiar");
		Copiar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/copy.png")));
		MenuItem Pegar = new MenuItem(Edicion, SWT.PUSH);
		Pegar.setText("Pegar");
		Pegar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/paste.png")));
		MenuItem separator4 = new MenuItem(Edicion, SWT.SEPARATOR);
		MenuItem Preferencias = new MenuItem(Edicion, SWT.PUSH);
		Preferencias.setText("Preferencias");
		Preferencias.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/settings.png")));
		submenuEdicion.setMenu(Edicion);
		Archivo = new Menu(submenuArchivo);
		MenuItem Nuevo = new MenuItem(Archivo, SWT.PUSH);
		Nuevo.setText("Nuevo");
		Nuevo.setEnabled(false);
		Nuevo.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/new.png")));
		MenuItem Abrir = new MenuItem(Archivo, SWT.PUSH);
		Abrir.setText("Abrir");
		Abrir.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/open.png")));
		Abrir.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				FileDialog abrirDlg = new FileDialog(sShell, SWT.OPEN); 
				abrirDlg.open();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		MenuItem separator2 = new MenuItem(Archivo, SWT.SEPARATOR);
		MenuItem Cerrar = new MenuItem(Archivo, SWT.PUSH);
		Cerrar.setText("Cerrar");
		Cerrar.setEnabled(false);
		MenuItem separator3 = new MenuItem(Archivo, SWT.SEPARATOR);
		MenuItem Guardar = new MenuItem(Archivo, SWT.PUSH);
		Guardar.setText("Guardar");
		Guardar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/save.png")));
		Guardar.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				FileDialog saveDlg = new FileDialog(sShell, SWT.SAVE); 
				saveDlg.open();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		MenuItem GuardarComo = new MenuItem(Archivo, SWT.PUSH);
		GuardarComo.setText("Guardar Como");
		GuardarComo.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/saveas.png")));
		GuardarComo
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						FileDialog saveDlg = new FileDialog(sShell, SWT.SAVE); 
						saveDlg.open();
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
		MenuItem separator1 = new MenuItem(Archivo, SWT.SEPARATOR);
		MenuItem Salir = new MenuItem(Archivo, SWT.PUSH);
		Salir.setText("Salir");
		Salir.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/iconos/exit.png")));
		Salir.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.exit(1);
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {				
			}
		});
		submenuArchivo.setMenu(Archivo);
		sShell.setMenuBar(MenuBarPrincipal);
	}

}
