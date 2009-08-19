package ide;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;

public class Preferencias extends Composite {

	private CLabel cLabel = null;
	private Label label = null;
	private CLabel cLabel1 = null;
	private Text text = null;
	private CLabel cLabel2 = null;
	private Button checkBox = null;
	private Button bOK = null;
	private Button bCancel = null;

	public Preferencias(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData31 = new GridData();
		gridData31.horizontalAlignment = GridData.FILL;
		gridData31.grabExcessHorizontalSpace = true;
		gridData31.verticalAlignment = GridData.CENTER;
		GridData gridData21 = new GridData();
		gridData21.horizontalAlignment = GridData.FILL;
		gridData21.grabExcessHorizontalSpace = true;
		gridData21.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.verticalAlignment = GridData.CENTER;
		gridData3.horizontalSpan = 2;
		gridData3.horizontalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.END;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.END;
		gridData1.verticalAlignment = GridData.CENTER;
		GridData gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.grabExcessHorizontalSpace = true;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		cLabel = new CLabel(this, SWT.CENTER);
		cLabel.setText("Preferencias del sistema");
		cLabel.setLayoutData(gridData);
		label = new Label(this, SWT.NONE);
		label.setText("");
		Label filler = new Label(this, SWT.NONE);
		Label filler4 = new Label(this, SWT.NONE);
		cLabel1 = new CLabel(this, SWT.NONE);
		cLabel1.setText("Puerto");
		cLabel1.setLayoutData(gridData1);
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(gridData3);
		cLabel2 = new CLabel(this, SWT.NONE);
		cLabel2.setText("Fondo Negro");
		cLabel2.setLayoutData(gridData2);
		checkBox = new Button(this, SWT.CHECK);
		Label filler2 = new Label(this, SWT.NONE);
		Label filler1 = new Label(this, SWT.NONE);
		bOK = new Button(this, SWT.NONE);
		bOK.setText("Aceptar");
		bOK.setLayoutData(gridData21);
		bOK.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				
				return;
			}
		});
		bCancel = new Button(this, SWT.NONE);
		bCancel.setText("Cancelar");
		bCancel.setLayoutData(gridData31);
		this.setLayout(gridLayout);
		this.setSize(new Point(300, 142));
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
