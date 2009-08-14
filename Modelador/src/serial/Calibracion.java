package serial;

import java.io.IOException;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

import serial.Comunicacion;

public class Calibracion {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Label label = null;
	private Button radioButton = null;
	private Button radioButton1 = null;
	private Button radioButton2 = null;
	private Button radioButton3 = null;
	private Text text0 = null;
	private Text text1 = null;
	private Text text2 = null;
	private Text text3 = null;
	private Button buttonMas = null;
	private Button buttonMenos = null;
	private Text textInc = null;
	private int m = 0;
	private int []motores = new int[]{5500, 5250, 3000, 20000};
	
	private Comunicacion comuni = new Comunicacion();  //  @jve:decl-index=0:
	private Text textRetorno = null;
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
		Calibracion thisClass = new Calibracion();
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
		try {
			comuni.connect("COM5");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
		GridData gridData22 = new GridData();
		gridData22.grabExcessHorizontalSpace = true;
		gridData22.verticalAlignment = GridData.CENTER;
		gridData22.horizontalAlignment = GridData.FILL;
		GridData gridData12 = new GridData();
		gridData12.grabExcessHorizontalSpace = true;
		gridData12.verticalAlignment = GridData.CENTER;
		gridData12.horizontalAlignment = GridData.FILL;
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData21 = new GridData();
		gridData21.horizontalAlignment = GridData.FILL;
		gridData21.verticalAlignment = GridData.CENTER;
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.FILL;
		gridData11.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalSpan = 3;
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.CENTER;
		gridData2.grabExcessHorizontalSpace = false;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.horizontalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		sShell = new Shell();
		sShell.setText("Shell");
		sShell.setLayout(gridLayout);
		sShell.setSize(new Point(300, 231));
		label = new Label(sShell, SWT.NONE);
		label.setText("Calibracion");
		label.setLayoutData(gridData2);
		radioButton = new Button(sShell, SWT.RADIO);
		radioButton.setText("Motor 0");
		radioButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				m=0;
			}
		});
		text0 = new Text(sShell, SWT.BORDER);
		text0.setText("5500");
		text0.setLayoutData(gridData4);
		Label filler4 = new Label(sShell, SWT.NONE);
		radioButton1 = new Button(sShell, SWT.RADIO);
		radioButton1.setText("Motor 1");
		radioButton1
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						m=1;
					}
				});
		text1 = new Text(sShell, SWT.BORDER);
		text1.setText("5250");
		text1.setLayoutData(gridData3);
		Label filler3 = new Label(sShell, SWT.NONE);
		radioButton2 = new Button(sShell, SWT.RADIO);
		radioButton2.setText("Motor 2");
		radioButton2
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						m=2;
					}
				});
		text2 = new Text(sShell, SWT.BORDER);
		text2.setText("30000");
		text2.setLayoutData(gridData21);
		Label filler1 = new Label(sShell, SWT.NONE);
		radioButton3 = new Button(sShell, SWT.RADIO);
		radioButton3.setText("Motor 3");
		radioButton3
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						m=3;
					}
				});
		text3 = new Text(sShell, SWT.BORDER);
		text3.setText("200000");
		text3.setLayoutData(gridData11);
		Label filler = new Label(sShell, SWT.NONE);
		buttonMas = new Button(sShell, SWT.NONE);
		buttonMas.setText("Mas");
		buttonMas.setLayoutData(gridData);
		buttonMas.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				motores[m] +=Integer.valueOf(textInc.getText());
				actualizarCampos();
				
			}
		});
		buttonMenos = new Button(sShell, SWT.NONE);
		buttonMenos.setText("Menos");
		buttonMenos.setLayoutData(gridData1);
		buttonMenos.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				motores[m] -=Integer.valueOf(textInc.getText());
				actualizarCampos();
			}
		});
		textInc = new Text(sShell, SWT.BORDER);
		textInc.setText("10");
		textInc.setLayoutData(gridData12);
		Label filler2 = new Label(sShell, SWT.NONE);
		Label filler5 = new Label(sShell, SWT.NONE);
		textRetorno = new Text(sShell, SWT.BORDER);
		textRetorno.setLayoutData(gridData22);
	}
	
	private void actualizarCampos() {
		textRetorno.setText("NO ACTUALIZO");
		text0.setText(String.valueOf(motores[0]));
		text1.setText(String.valueOf(motores[1]));
		text2.setText(String.valueOf(motores[2]));
		text3.setText(String.valueOf(motores[3]));
		try {			
				textRetorno.setText(comuni.escribe("W"+motores[0]+" "+motores[1]+" "+motores[2]+" "+motores[3]+"\n"));
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
