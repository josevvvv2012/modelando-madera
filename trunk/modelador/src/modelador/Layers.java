package modelador;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ToolItem;

public class Layers extends Composite {

	private CLabel cLabel_Layer = null;
	private Combo comboLayer = null;
	private ToolBar toolBarOpciones = null;
	private ToolBar toolBarAcciones2 = null;
	public Layers(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		cLabel_Layer = new CLabel(this, SWT.NONE);
		cLabel_Layer.setText("Capa:");
		this.setLayout(gridLayout);
		createComboLayer();
		createToolBarOpciones();
		createToolBarAcciones2();
		setSize(new Point(300, 200));
	}

	/**
	 * This method initializes comboLayer	
	 *
	 */
	private void createComboLayer() {
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.CENTER;
		comboLayer = new Combo(this, SWT.NONE);
		comboLayer.setLayoutData(gridData1);
		for(int i=0; i<10; i++) {
			comboLayer.add("Capa #"+i);
		}
	}

	/**
	 * This method initializes toolBarOpciones	
	 *
	 */
	private void createToolBarOpciones() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = false;
		gridData.horizontalSpan = 2;
		gridData.verticalAlignment = GridData.CENTER;
		toolBarOpciones = new ToolBar(this, SWT.NONE);
		toolBarOpciones.setLayoutData(gridData);
		ToolItem toolItemVisible = new ToolItem(toolBarOpciones, SWT.CHECK);
		toolItemVisible.setText("Oculto");
		ToolItem toolItemBloqueado = new ToolItem(toolBarOpciones, SWT.CHECK);
		toolItemBloqueado.setText("Bloqueado");
		toolItemBloqueado.setToolTipText("Bloquear");
		ToolItem toolItemColor = new ToolItem(toolBarOpciones, SWT.PUSH);
		toolItemColor.setText("Color");
	}

	/**
	 * This method initializes toolBarAcciones2	
	 *
	 */
	private void createToolBarAcciones2() {
		GridData gridData2 = new GridData();
		gridData2.grabExcessHorizontalSpace = false;
		gridData2.verticalAlignment = GridData.CENTER;
		gridData2.horizontalSpan = 2;
		gridData2.horizontalAlignment = GridData.FILL;
		toolBarAcciones2 = new ToolBar(this, SWT.NONE);
		toolBarAcciones2.setLayoutData(gridData2);
		ToolItem toolItemNuevo1 = new ToolItem(toolBarAcciones2, SWT.PUSH);
		toolItemNuevo1.setText("Nuevo");
		ToolItem toolItemModificar = new ToolItem(toolBarAcciones2, SWT.PUSH);
		toolItemModificar.setText("Cambiar");
		ToolItem toolItemEliminar2 = new ToolItem(toolBarAcciones2, SWT.PUSH);
		toolItemEliminar2.setText("Eliminar");
	}

}
