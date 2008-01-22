package funciones;

import javax.media.opengl.GL;


public class Figuras2D {
		
	public Figuras2D() {
		
	}
	public void drawPoint(GL gl, double x, double y) {
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2d(x, y);
		gl.glEnd();
	}
	
	public void line(GL gl, int x0, int y0, int xn, int yn) {
		int x;
		float m, y;

		m = (float)(yn-y0)/(xn-x0);
		x = x0;
		y = y0;

		while (x<xn+1) {
			drawPoint(gl, x, y);
			x++;
			y += m; 
		}
	}
}
