package ide;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

public class Vistas extends Composite {

	private Button bFrente = null;
	private Button bArriba = null;
	private Button bLado = null;
	private Button bOpenGL = null;

	public int opcion=1;
	public Vistas(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.grabExcessVerticalSpace = true;
		gridData3.verticalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.grabExcessVerticalSpace = true;
		gridData2.verticalAlignment = GridData.FILL;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.grabExcessVerticalSpace = true;
		gridData1.verticalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		bFrente = new Button(this, SWT.TOGGLE);
		bFrente.setText("Frente");
		bFrente.setLayoutData(gridData);
		bFrente.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				desactivarTodo(2);
				
			}
		});
		bArriba = new Button(this, SWT.TOGGLE);
		bArriba.setText("Arriba");		
		bArriba.setLayoutData(gridData2);
		bArriba.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				desactivarTodo(1);
			}
		});
		bLado = new Button(this, SWT.TOGGLE);
		bLado.setText("Lateral");
		bLado.setLayoutData(gridData1);
		bLado.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				desactivarTodo(3);
			}
		});
		bOpenGL = new Button(this, SWT.TOGGLE);
		bOpenGL.setText("OpenGL");
		bOpenGL.setLayoutData(gridData3);
		bOpenGL.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				desactivarTodo(4);
			}
		});
		this.setLayout(gridLayout);
		setSize(new Point(300, 200));
		desactivarTodo(1);
	}
	
	private void desactivarTodo(int opc) {
		bLado.setSelection(false);
		bArriba.setSelection(false);
		bFrente.setSelection(false);
		bOpenGL.setSelection(false);
		switch(opc) {
			case 1:
				bArriba.setSelection(true);
				break;
			case 2:
				bFrente.setSelection(true);
				break;
			case 3:
				bLado.setSelection(true);
				break;
			case 4:
				bOpenGL.setSelection(true);
				break;	
		}
		opcion = opc;
		
	}

}
