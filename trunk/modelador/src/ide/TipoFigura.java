package ide;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.GridData;
/**
 * 
 * @author Roberto Loaeza Valerio
 * @version 1
 *
 */
public class TipoFigura extends Composite {

	private Button Linea = null;
	private Button Circulo = null;
	private Button Bezier = null;
	private Button Punto = null;
	
	private int tipo;
	private boolean fill=false;
	private Button PoliLinea = null;
	private Button Elipse = null;
	private Button relleno = null;
	private Button bRecortar = null;
	private Button bRellenado = null;
	
	public int getTipo() {
		return tipo;
	}

	public boolean getFill() {
		return fill;
	}
	public TipoFigura(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.horizontalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		Punto = new Button(this, SWT.RADIO | SWT.LEFT);
		Punto.setText("         Punto");
		Punto.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/punto.png")));
		Punto.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = 1;
			}
		});
		Linea = new Button(this, SWT.RADIO);
		Linea.setText("        Linea");
		Linea.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/linea.png")));
		Linea.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = 2;
			}
		});
		
		PoliLinea = new Button(this, SWT.RADIO);
		PoliLinea.setText("       Polilinea");
		PoliLinea.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/poligono.png")));
		PoliLinea.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = 3;
			}
		});
		
		Circulo = new Button(this, SWT.RADIO);
		Circulo.setText("       Circulo");
		Circulo.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/circulo.png")));
		Circulo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = 4;
			}
		});
		
		Elipse = new Button(this, SWT.RADIO);
		Elipse.setText("    Elipse");
		Elipse.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/elipse.png")));
		Elipse.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = 5;
			}
		});
		
		Bezier = new Button(this, SWT.RADIO);
		Bezier.setText("      Bezier");
		Bezier.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/bezier.png")));
		bRecortar = new Button(this, SWT.RADIO);
		bRecortar.setText("    Recortar Lineas");
		bRecortar.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/recorte.png")));
		relleno = new Button(this, SWT.RADIO);
		relleno.setText("        Rellenar");
		relleno.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("/imgs/rellenar.png")));
		bRellenado = new Button(this, SWT.TOGGLE);
		bRellenado.setText("Fig's Rellenas");
		bRellenado.setLayoutData(gridData);
		
		bRellenado.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(fill)
					fill = false;
				else fill = true;				
			}
		});
		bRecortar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = 8;
			}
		});
		relleno.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = 7;
			}
		});
		Bezier.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = 6;
			}
		});
		this.setLayout(gridLayout);
		setSize(new Point(300, 282));
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
