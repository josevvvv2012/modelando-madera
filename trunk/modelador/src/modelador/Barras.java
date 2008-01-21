package modelador;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class Barras extends Composite {
	public Barras(Composite parent, int style) {
		
		super(parent, style);
		initialize();
	}
	private void initialize() {
		
		this.setLayout(new FillLayout());
		ExpandBar bar = new ExpandBar (this, SWT.V_SCROLL);
		
		Composite tipoFigura = new TipoFigura(bar, SWT.NONE);
		ExpandItem item0 = new ExpandItem (bar, SWT.NONE, 0);		
		item0.setText("Tipos de linea");
		item0.setHeight(tipoFigura.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item0.setExpanded(true);
		item0.setControl(tipoFigura);	
		
		ExpandItem item1 = new ExpandItem (bar, SWT.NONE, 0);	
		Composite expandLayer = new Layers(bar, SWT.NONE);
		item1.setText("Capas");
		item1.setExpanded(true);
		item1.setHeight(expandLayer.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item1.setControl(expandLayer);
		
		ExpandItem item2 = new ExpandItem (bar, SWT.NONE, 0);	
		Composite expandViews = new Views(bar, SWT.NONE);
		item2.setText("Vistas");
		item2.setExpanded(true);
		item2.setHeight(expandViews.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item2.setControl(expandViews);
		
		
		bar.setSpacing(8);	
	}
}
