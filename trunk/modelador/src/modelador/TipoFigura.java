package modelador;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;

public class TipoFigura extends Composite {

	private Button Linea = null;
	private Button Circulo = null;
	private Button Arco = null;
	private Button Bezier = null;
	private Button Punto = null;
	
	private int tipo;
	
	public int getTipo() {
		return tipo;
	}

	public TipoFigura(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		Punto = new Button(this, SWT.RADIO);
		Punto.setText("Punto");
		Punto.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = 1;
			}
		});
		Linea = new Button(this, SWT.RADIO);
		Linea.setText("Linea");
		Linea.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = 2;
			}
		});
		Arco = new Button(this, SWT.RADIO);
		Arco.setText("Arco");
		Arco.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = 3;
			}
		});
		Circulo = new Button(this, SWT.RADIO);
		Circulo.setText("Circulo");
		Circulo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = 4;
			}
		});
		Bezier = new Button(this, SWT.RADIO);
		Bezier.setText("Bezier");
		Bezier.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tipo = 5;
			}
		});
		this.setLayout(gridLayout);
		setSize(new Point(300, 200));
	}

}
