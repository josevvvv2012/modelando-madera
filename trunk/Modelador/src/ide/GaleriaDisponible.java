package ide;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Text;
import java.util.Vector;
import java.util.Hashtable;

public class GaleriaDisponible extends Composite {

	
	private Button radioButton = null;
	private Button radioButton1 = null;
	private Hashtable<String, Integer> hGaleria = new Hashtable<String, Integer>();
	private int oGaleria=8;
	Vector <Button>p = new Vector<Button>();  //  @jve:decl-index=0:
	public GaleriaDisponible(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {		
		GridLayout gridLayout = new GridLayout();
		this.setLayout(gridLayout);
		setSize(new Point(300, 200));
		cargarGaleria();
		
	}
	
	private void cargarGaleria() {
		for(int i=0; i<10; i++) {
			p.add(new Button(this, SWT.RADIO));
			p.get(i).setText(i+"...");
			hGaleria.put( p.get(i).getText(), oGaleria++);
		}
		for(int i=0; i<p.size(); i++) {		
			p.get(i).addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					System.out.println(hGaleria.get(((Button)e.widget).getText()));
				}
			});
		}
	
		
	}

}
