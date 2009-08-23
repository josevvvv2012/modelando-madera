package ide;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;

public class Ayuda {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Browser buscadorAyuda = null;
	private Button bAyuda = null;

	/**
	 * This method initializes buscadorAyuda	
	 *
	 */
	private void createBuscadorAyuda() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
		buscadorAyuda = new Browser(sShell, SWT.NONE);
		buscadorAyuda.setLayoutData(gridData);
		//buscadorAyuda.setText("ayuda\\inicio.html");
		buscadorAyuda.setUrl("modelando-madera.googlecode.com");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		Display display = Display.getDefault();
		Ayuda thisClass = new Ayuda();
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
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		sShell = new Shell();
		sShell.setText(DEF.mAyuda);
		createBuscadorAyuda();
		sShell.setSize(new Point(474, 515));
		sShell.setLayout(new GridLayout());
		bAyuda = new Button(sShell, SWT.NONE);
		bAyuda.setText(DEF.bCerrar);
		bAyuda.setLayoutData(gridData1);
		bAyuda.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				sShell.close();
			}
		});
	}
	
	public Ayuda() {
		
	}
	public Ayuda(Shell parent) {
		createSShell();
		sShell.open();
		  
	}

}
