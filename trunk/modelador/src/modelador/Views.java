package modelador;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;

public class Views extends Composite {

	private Button radioButtonTop = null;
	private Button radioButtonFront = null;
	private Button radioButton3D = null;

	public Views(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		radioButtonTop = new Button(this, SWT.RADIO);
		radioButtonTop.setText("Arriba");
		radioButtonFront = new Button(this, SWT.RADIO);
		radioButtonFront.setText("Frente");
		radioButton3D = new Button(this, SWT.RADIO);
		radioButton3D.setText("3D");
		setSize(new Point(300, 200));
		setLayout(new GridLayout());
	}

}
