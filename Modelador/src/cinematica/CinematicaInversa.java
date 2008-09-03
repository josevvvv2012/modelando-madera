package cinematica;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

public class CinematicaInversa {

	private Shell sShell = null;
	private CLabel cLabel = null;
	private CLabel cLabel1 = null;
	private CLabel cLabel2 = null;
	private Text textX = null;
	private Text textY = null;
	private CLabel cLabel3 = null;
	private Text textComando = null;
	private Button buttonLimpiar = null;
	private Button buttonCalcular = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* Before this is run, be sure to set up the launch configuration (Arguments->VM Arguments)
		 * for the correct SWT library path in order to run with the SWT dlls. 
		 * The dlls are located in the SWT plugin jar.  
		 * For example, on Windows the Eclipse SWT 3.1 plugin jar is:
		 *       installation_directory\plugins\org.eclipse.swt.win32_3.1.0.jar
		 */
		Display display = Display.getDefault();
		CinematicaInversa thisClass = new CinematicaInversa();
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
		GridData gridData8 = new GridData();
		gridData8.horizontalAlignment = GridData.FILL;
		gridData8.verticalAlignment = GridData.CENTER;
		GridData gridData7 = new GridData();
		gridData7.horizontalAlignment = GridData.FILL;
		gridData7.verticalAlignment = GridData.CENTER;
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = GridData.FILL;
		gridData6.verticalAlignment = GridData.CENTER;
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.FILL;
		gridData5.verticalAlignment = GridData.CENTER;
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.END;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.END;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.END;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		sShell = new Shell();
		sShell.setText("Shell");
		sShell.setLayout(gridLayout);
		sShell.setSize(new Point(300, 200));
		cLabel = new CLabel(sShell, SWT.CENTER);
		cLabel.setText("Cinematica Inversa:");
		cLabel.setLayoutData(gridData);
		cLabel1 = new CLabel(sShell, SWT.NONE);
		cLabel1.setText("x =");
		cLabel1.setLayoutData(gridData1);
		textX = new Text(sShell, SWT.BORDER);
		textX.setLayoutData(gridData4);
		cLabel2 = new CLabel(sShell, SWT.NONE);
		cLabel2.setText("y =");
		cLabel2.setLayoutData(gridData2);
		textY = new Text(sShell, SWT.BORDER);
		textY.setLayoutData(gridData5);
		buttonLimpiar = new Button(sShell, SWT.NONE);
		buttonLimpiar.setText("Limpiar");
		buttonLimpiar.setLayoutData(gridData7);
		buttonLimpiar
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						textX.setText("");
						textY.setText("");
					}
				});
		buttonCalcular = new Button(sShell, SWT.NONE);
		buttonCalcular.setText("Calcular");
		buttonCalcular.setLayoutData(gridData8);
		buttonCalcular
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						int m[] = new int[4];
						String s = "W";
				    	int x = Integer.valueOf(textX.getText());
				    	int y = Integer.valueOf(textY.getText());
				    	
				    	new Inversa().get_angles(x, y, m);				   				    	
				    	for(int i=0; i<m.length; i++) {
				    		s = s+m[i]+" ";				    		
				    	}
				    	textComando.setText(s);
					}
				});
		cLabel3 = new CLabel(sShell, SWT.NONE);
		cLabel3.setText("Comando =");
		cLabel3.setLayoutData(gridData3);
		textComando = new Text(sShell, SWT.BORDER);
		textComando.setLayoutData(gridData6);
	}

}
