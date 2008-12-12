package contornos;

import java.awt.Frame;
import java.awt.Panel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;

public class Contornos2 extends Frame {

	private static final long serialVersionUID = 1L;
	private Panel panel = null;

	/**
	 * This method initializes panel	
	 * 	
	 * @return java.awt.Panel	
	 */
	private Panel getPanel() {
		if (panel == null) {
			panel = new Panel();
			panel.setLayout(new GridBagLayout());
		}
		
		return panel;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * This is the default constructor
	 */
	public Contornos2() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setTitle("Frame");

		this.add(getPanel(), BorderLayout.CENTER);
		
	}

}
