package serial;

import java.io.IOException;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Slider;

import cinematica.Inversa;

public class InicializadorRobot extends Composite {

	private Label label = null;
	private Button buttonIzq = null;
	private Button buttonDer = null;
	private Button buttonBajo = null;
	private Button buttonArriba = null;
	private Label label1 = null;
	private Label label2 = null;
	private Text textSI = null;
	private Text textID = null;
	private Button buttonFixSI = null;
	private Button buttonFixID = null;
	private Slider sliderZ = null;
	private Text textAux = null;
	
	private double x=0.00;
	private double y=270.0;
	private double z=15000;
	
	private double inc = 1;	
	private int M[] = new int[4];	
	private Comunicacion comuni = new Comunicacion();
	
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
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setSize(new Point(250, 250));
		new InicializadorRobot(shell, SWT.NONE);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public InicializadorRobot(Composite parent, int style) {
		super(parent, style);
		initialize();
		try {
		//	comuni.connect("COM4");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void initialize() {
		GridData gridData12 = new GridData();
		gridData12.horizontalAlignment = GridData.FILL;
		gridData12.horizontalSpan = 4;
		gridData12.verticalAlignment = GridData.CENTER;
		GridData gridData11 = new GridData();
		gridData11.horizontalSpan = 2;
		gridData11.verticalAlignment = GridData.CENTER;
		gridData11.horizontalAlignment = GridData.FILL;
		GridData gridData10 = new GridData();
		gridData10.horizontalSpan = 2;
		gridData10.verticalAlignment = GridData.CENTER;
		gridData10.horizontalAlignment = GridData.FILL;
		GridData gridData9 = new GridData();
		gridData9.verticalSpan = 3;
		gridData9.verticalAlignment = GridData.FILL;
		gridData9.heightHint = 9;
		gridData9.horizontalAlignment = GridData.END;
		GridData gridData8 = new GridData();
		gridData8.horizontalAlignment = GridData.FILL;
		gridData8.grabExcessHorizontalSpace = false;
		gridData8.verticalAlignment = GridData.CENTER;
		GridData gridData7 = new GridData();
		gridData7.horizontalAlignment = GridData.FILL;
		gridData7.verticalAlignment = GridData.CENTER;
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = GridData.BEGINNING;
		gridData6.grabExcessHorizontalSpace = false;
		gridData6.verticalAlignment = GridData.CENTER;
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.CENTER;
		gridData5.verticalAlignment = GridData.CENTER;
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.CENTER;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.horizontalSpan = 4;
		gridData3.verticalAlignment = GridData.CENTER;
		gridData3.horizontalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.END;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.END;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		gridData.verticalAlignment = GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		label = new Label(this, SWT.CENTER);
		label.setText("Calibrador");
		label.setLayoutData(gridData3);
		Label filler3 = new Label(this, SWT.NONE);
		buttonArriba = new Button(this, SWT.NONE);
		buttonArriba.setText("/\\");
		buttonArriba.setLayoutData(gridData4);
		buttonArriba
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						y+=inc;
						calculaComando();
					}
				});
		Label filler5 = new Label(this, SWT.NONE);
		sliderZ = new Slider(this, SWT.VERTICAL);
		sliderZ.setSelection(50);
		sliderZ.setLayoutData(gridData9);
		sliderZ.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				z=sliderZ.getSelection()*480000/100;
				calculaComando();
			}
		});
		buttonIzq = new Button(this, SWT.NONE);
		buttonIzq.setText("<");
		buttonIzq.setLayoutData(gridData2);
		buttonIzq.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				x-=inc;
				calculaComando();
			}
		});
		Label filler4 = new Label(this, SWT.NONE);
		buttonDer = new Button(this, SWT.NONE);
		buttonDer.setText(">");
		buttonDer.setLayoutData(gridData6);
		buttonDer.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				x+=inc;
				calculaComando();
			}
		});
		Label filler6 = new Label(this, SWT.NONE);
		buttonBajo = new Button(this, SWT.NONE);
		buttonBajo.setText("\\/");
		buttonBajo.setLayoutData(gridData5);
		buttonBajo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				y-=inc;
				calculaComando();
			}
		});
		Label filler1 = new Label(this, SWT.NONE);
		label1 = new Label(this, SWT.NONE);
		label1.setText("SuperiorIzquierda:");
		label1.setLayoutData(gridData1);
		textSI = new Text(this, SWT.BORDER);
		textSI.setLayoutData(gridData10);
		buttonFixSI = new Button(this, SWT.NONE);
		buttonFixSI.setText("Fijar");
		buttonFixSI.setLayoutData(gridData7);
		label2 = new Label(this, SWT.NONE);
		label2.setText("InferiorDerecha:");
		label2.setLayoutData(gridData);
		textID = new Text(this, SWT.BORDER);
		textID.setLayoutData(gridData11);
		buttonFixID = new Button(this, SWT.NONE);
		buttonFixID.setText("Fijar");
		buttonFixID.setLayoutData(gridData8);
		textAux = new Text(this, SWT.BORDER);
		textAux.setLayoutData(gridData12);
		this.setLayout(gridLayout);
		this.setSize(new Point(231, 236));
	}

	private void calculaComando() {
		new Inversa().get_angles(x, y, M);
		M[3]=(int)z;
		textSI.setText(String.valueOf(x));
		textID.setText(String.valueOf(y));
		textAux.setText("W"+M[0]+" "+M[1]+" "+M[2]+" "+M[3]);
	/*	
		try {			
			comuni.escribe("W"+M[0]+" "+M[1]+" "+M[2]+" "+M[3]+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
