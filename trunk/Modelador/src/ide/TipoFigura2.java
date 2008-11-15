package ide;

import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.List;

import store.CargarGaleria;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class TipoFigura2 extends Composite {

	private int tipo;
	private int archivoID;
	private boolean fill=false;
	
	private CTabFolder cTabFolder = null;
	private Composite Primitivas = null;
	private Composite Biblioteca = null;
	private Button rbPunto = null;
	private Button rbLinea = null;
	private Button rbPoliLinea = null;
	private Button rbCirculo = null;
	private Button rbElipse = null;
	private Button rbBezier = null;
	private Slider sZ = null;
	private CLabel cZ = null;
	private CLabel cLabel1 = null;
	private Hashtable<String, Integer> hGalerias = new Hashtable<String, Integer>();  //  @jve:decl-index=0:
	private int oGaleria=-1;
	Vector <Button>p = new Vector<Button>();  //  @jve:decl-index=0:
	private List lBiblioteca = null;
	public CargarGaleria cg = new CargarGaleria();  //  @jve:decl-index=0:
	
	
	public String getArchivoGaleria() {
		return cg.sDirectorio+"/"+cg.getGaleria(archivoID)+DEF.ext;
	}
	public int getZ() {
		return Integer.valueOf(cZ.getText());
	}
	public int getTipo() {
		return tipo;
	}

	public boolean getFill() {
		return fill;
	}
	public TipoFigura2(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.CENTER;
		gridData3.verticalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.END;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.widthHint = 40;
		gridData1.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		
		
		
		
		
		this.setLayout(gridLayout);
		createCTabFolder();
		setSize(new Point(300, 218));
		sZ = new Slider(this, SWT.VERTICAL);
		sZ.setMinimum(100000);
		sZ.setLayoutData(gridData3);
		sZ.setMaximum(450000);
		sZ.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				cZ.setText(String.valueOf(sZ.getSelection()));
			}
		});
		cLabel1 = new CLabel(this, SWT.NONE);
		cLabel1.setText(DEF.lProfundidad);
		cLabel1.setLayoutData(gridData2);
		cZ = new CLabel(this, SWT.NONE);
		cZ.setText(String.valueOf(sZ.getSelection()));
		cZ.setLayoutData(gridData1);
	}
	/**
	 * This method initializes cTabFolder	
	 *
	 */
	private void createCTabFolder() {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.widthHint = 400;
		gridData.grabExcessVerticalSpace = true;
		cTabFolder = new CTabFolder(this, SWT.MULTI);
		cTabFolder.setUnselectedCloseVisible(false);
		cTabFolder.setLayoutData(gridData);
		
		createPrimitivas();
		createBiblioteca();
		CTabItem cTabItem = new CTabItem(cTabFolder, SWT.CLOSE);
		cTabItem.setText(DEF.tPrimitivasBasicas);
		cTabItem.setControl(Primitivas);
		CTabItem cTabItem1 = new CTabItem(cTabFolder, SWT.CLOSE);
		cTabItem1.setText(DEF.tBiblioteca);
		cTabItem1.setControl(Biblioteca);
		cTabFolder.setSelection(0);
	
		//cTabFolder.setSize(200, 200);
	}
	/**
	 * This method initializes Primitivas	
	 *
	 */
	private void createPrimitivas() {
		Primitivas = new Composite(cTabFolder, SWT.NONE);
		Primitivas.setLayout(new GridLayout());
		rbPunto = new Button(Primitivas, SWT.RADIO);
		rbPunto.setText(DEF.pPunto);
		rbPunto.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/punto.png")));
		rbPunto.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = DEF.punto;
			}
		});
		rbLinea = new Button(Primitivas, SWT.RADIO);
		rbLinea.setText(DEF.pLinea);
		rbLinea.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/linea.png")));
		rbLinea.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = DEF.linea;
			}
		});
		rbPoliLinea = new Button(Primitivas, SWT.RADIO);
		rbPoliLinea.setText(DEF.pPolilinea);
		rbPoliLinea.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/poligono.png")));
		rbPoliLinea.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = DEF.polilinea;
			}
		});
		rbCirculo = new Button(Primitivas, SWT.RADIO);
		rbCirculo.setText(DEF.pCirculo);
		rbCirculo.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/circulo.png")));
		rbCirculo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = DEF.circulo;
			}
		});
		rbElipse = new Button(Primitivas, SWT.RADIO);
		rbElipse.setText(DEF.pElipse);
		rbElipse.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/elipse.png")));
		rbElipse.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = DEF.elipse;
			}
		});
		rbBezier = new Button(Primitivas, SWT.RADIO);
		rbBezier.setText(DEF.pBezier);
		rbBezier.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/bezier.png")));
		rbBezier.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = DEF.bezier;
			}
		});
	}
	/**
	 * This method initializes Biblioteca	
	 *
	 */
	private void createBiblioteca() {
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.grabExcessHorizontalSpace = true;
		gridData4.grabExcessVerticalSpace = true;
		gridData4.verticalAlignment = GridData.FILL;
		Biblioteca = new Composite(cTabFolder, SWT.NONE);
		Biblioteca.setLayout(new GridLayout());
		lBiblioteca = new List(Biblioteca, SWT.V_SCROLL);
		lBiblioteca.setLayoutData(gridData4);
		lBiblioteca.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				cargarGaleria(lBiblioteca.getSelectionIndex());
			}
		});
		cargarGaleria();
	}
	
	protected void cargarGaleria(int selectionIndex) {
		tipo = DEF.galeria;
		archivoID=selectionIndex;
		
	}
	public int getArchivoID() {
		return archivoID;
	}
	private void cargarGaleria() {
		cg.cargarDirectorio();
		
		for(int i=0; i<cg.getSize(); i++) {			
			lBiblioteca.add(cg.getGaleria(i));
			//hGaleria.put( lBiblioteca.getItem(i), oGaleria--);
		}					
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
