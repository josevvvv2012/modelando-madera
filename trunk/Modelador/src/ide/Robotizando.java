package ide;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;

public class Robotizando {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Label label = null;
	private Label label1 = null;
	private Label label2 = null;
	private Label label3 = null;
	private Label label4 = null;
	private ProgressBar pbFiguras = null;
	private Label label5 = null;
	private ProgressBar pbPuntos = null;
	private Label label6 = null;
	private Button bCancelar = null;
	private Label label7 = null;
	
	
	private int nFiguras = 10;
	private int nPuntos = 100;
	
	private int iFiguras=0;
	private int iPuntos=0;
	private Button buttonIniciar = null;
	private boolean cancelar=true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Display display = Display.getDefault();
		Robotizando thisClass = new Robotizando();
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
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = GridData.FILL;
		gridData11.grabExcessHorizontalSpace = true;
		gridData11.verticalAlignment = GridData.CENTER;
		GridData gridData5 = new GridData();
		gridData5.horizontalAlignment = GridData.FILL;
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.horizontalSpan = 2;
		gridData5.verticalAlignment = GridData.CENTER;
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.END;
		gridData4.grabExcessHorizontalSpace = true;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.END;
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.grabExcessHorizontalSpace = true;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		sShell = new Shell();
		sShell.setText("Shell");
		sShell.setLayout(gridLayout);
		sShell.setSize(new Point(305, 248));
		label = new Label(sShell, SWT.NONE);
		label.setText("");
		Label filler2 = new Label(sShell, SWT.NONE);
		label1 = new Label(sShell, SWT.CENTER);
		label1.setText(DEF.lRobotEnviando);
		label1.setLayoutData(gridData5);
		label2 = new Label(sShell, SWT.NONE);
		label2.setText("");
		Label filler = new Label(sShell, SWT.NONE);
		label3 = new Label(sShell, SWT.NONE);
		label3.setText(DEF.lRobotFigura);
		label3.setLayoutData(gridData3);
		pbFiguras = new ProgressBar(sShell, SWT.NONE);
		pbFiguras.setLayoutData(gridData2);
		label4 = new Label(sShell, SWT.NONE);
		label4.setText("");
		Label filler3 = new Label(sShell, SWT.NONE);
		label5 = new Label(sShell, SWT.NONE);
		label5.setText(DEF.lRobotPunto);
		label5.setLayoutData(gridData4);
		pbPuntos = new ProgressBar(sShell, SWT.NONE);
		pbPuntos.setLayoutData(gridData1);
		label6 = new Label(sShell, SWT.NONE);
		label6.setText("");
		Label filler4 = new Label(sShell, SWT.NONE);
		buttonIniciar = new Button(sShell, SWT.NONE);
		buttonIniciar.setText(DEF.bIniciar);
		buttonIniciar.setLayoutData(gridData11);
		buttonIniciar
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						cancelar=false;
						m();
						/*
						Thread t = new Thread(new Runnable() {
							public void run()
							{
								m();
							}
						});
						t.start();
						//m();
						 * */
					}
				});
		bCancelar = new Button(sShell, SWT.NONE);
		bCancelar.setText(DEF.bCancelar);
		bCancelar.setLayoutData(gridData);
		bCancelar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				cancelar=true;
				//actualizarPB(nFiguras, iFiguras++, nPuntos, iPuntos++);
			}
		});
		label7 = new Label(sShell, SWT.NONE);
		label7.setText("");
	}

	private void m() {
		for(int i=0; i<10; i++) {
			for(int j=0; j<50; j++) {
				if(cancelar==true)
					return;
				actualizarPB(9, i, 49, j);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
	
			}
		}
	}
	private void actualizarPB(int nFig, int iFig, int nPnt, int iPnt ) {
		if(pbPuntos.getMaximum()!=nPnt)
			pbPuntos.setMaximum(nPnt);
		pbPuntos.setSelection(iPnt);
		
		if(pbPuntos.getMaximum()!=nFig)
			pbFiguras.setMaximum(nFig);
		pbFiguras.setSelection(iFig);
	}
}
