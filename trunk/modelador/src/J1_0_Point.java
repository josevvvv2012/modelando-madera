/***********************************************************
 * Created on 2004-2-20
 * @author Jim X. Chen: draw a point
 * Modified 2007-9-8 for the new JOGL system
 */
import java.awt.*;
import javax.media.opengl.*;
import funciones.Figuras2D;
//import net.java.games.jogl.*;


public class J1_0_Point extends Frame implements
    GLEventListener {

  static int HEIGHT = 600, WIDTH = 600;
  static GL gl; //interface to OpenGL
  static GLCanvas canvas; // drawable in a frame
  static GLCapabilities capabilities;

  public J1_0_Point() {


    capabilities = new GLCapabilities();
    canvas = new GLCanvas(); 

    canvas.addGLEventListener(this);


    add(canvas, BorderLayout.CENTER);


    gl = canvas.getGL();
  }


  public static void main(String[] args) {
    J1_0_Point frame = new J1_0_Point();


    frame.setSize(WIDTH, HEIGHT);
    frame.setVisible(true);
  }



  public void init(GLAutoDrawable drawable) {


    gl.glColor3f(1.0f, 0.0f, 0.0f);
  }



  public void reshape(
      GLAutoDrawable drawable,
      int x,
      int y,
      int width,
      int height) {

      WIDTH = width; 
      HEIGHT = height;


    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glOrtho(0, width, 0, height, -1.0, 1.0);
  }



  public void display(GLAutoDrawable drawable) {

    //8. specify to draw a point
    //gl.glPointSize(10);
	  Figuras2D fig = new Figuras2D();
	  fig.line(gl, 0, 0, WIDTH/2, HEIGHT/2);
	  /*
    gl.glBegin(GL.GL_LINES);
    gl.glVertex2i(WIDTH/2, HEIGHT/2);
    gl.glVertex2i(WIDTH/2+10, HEIGHT/2+10);
    gl.glEnd();
    */
  }


  // called if display mode or device are changed
  public void displayChanged(
      GLAutoDrawable drawable,
      boolean modeChanged,
      boolean deviceChanged) {
  }
}
