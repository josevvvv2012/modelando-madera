package contornos;

import java.awt.image.BufferedImage;


import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.layout.GridData;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;
public class Contornos extends Composite {

	private Button bCargar = null;
	private Canvas canvas = null;
	private Image imageBuffer;
	
	
	public Contornos(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.horizontalAlignment = GridData.FILL;
		bCargar = new Button(this, SWT.NONE);
		bCargar.setText("Cargar");
		bCargar.setLayoutData(gridData);
		bCargar.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
				x();
			}
		});
		createCanvas();
		setSize(new Point(320, 270));
		setLayout(new GridLayout());
	}
	public void x() {
		  ImageData imgData = new ImageData("d:\\circuito.jpg");
		//create the detector
		CannyEdgeDetector detector = new CannyEdgeDetector();

		//adjust its parameters as desired
		detector.setLowThreshold(0.5f);
		detector.setHighThreshold(1f);

		//apply it to an image
		
		//detector.setSourceImage(toBufferedImage(new java.awt.Image()));
		
		detector.setSourceImage(convertToAWT(imgData));
		detector.process();
		BufferedImage edges = detector.getEdgesImage();
		imageBuffer = new Image(canvas.getDisplay(), convertToSWT(edges));
		canvas.redraw();
	}

	/**
	 * This method initializes canvas	
	 *
	 */
	private void createCanvas() {
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.grabExcessVerticalSpace = true;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.FILL;
		canvas = new Canvas(this, SWT.NONE);
		canvas.setLayoutData(gridData1);
		
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(imageBuffer, 0, 0);
			}
		}
	);
	
	imageBuffer = new Image(canvas.getDisplay(), 1200, 800);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	static ImageData convertToSWT(BufferedImage bufferedImage) {
	    if (bufferedImage.getColorModel() instanceof DirectColorModel) {
	      DirectColorModel colorModel = (DirectColorModel) bufferedImage
	          .getColorModel();
	      PaletteData palette = new PaletteData(colorModel.getRedMask(),
	          colorModel.getGreenMask(), colorModel.getBlueMask());
	      ImageData data = new ImageData(bufferedImage.getWidth(),
	          bufferedImage.getHeight(), colorModel.getPixelSize(),
	          palette);
	      WritableRaster raster = bufferedImage.getRaster();
	      int[] pixelArray = new int[3];
	      for (int y = 0; y < data.height; y++) {
	        for (int x = 0; x < data.width; x++) {
	          raster.getPixel(x, y, pixelArray);
	          int pixel = palette.getPixel(new RGB(pixelArray[0],
	              pixelArray[1], pixelArray[2]));
	          data.setPixel(x, y, pixel);
	        }
	      }
	      return data;
	    } else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
	      IndexColorModel colorModel = (IndexColorModel) bufferedImage
	          .getColorModel();
	      int size = colorModel.getMapSize();
	      byte[] reds = new byte[size];
	      byte[] greens = new byte[size];
	      byte[] blues = new byte[size];
	      colorModel.getReds(reds);
	      colorModel.getGreens(greens);
	      colorModel.getBlues(blues);
	      RGB[] rgbs = new RGB[size];
	      for (int i = 0; i < rgbs.length; i++) {
	        rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF,
	            blues[i] & 0xFF);
	      }
	      PaletteData palette = new PaletteData(rgbs);
	      ImageData data = new ImageData(bufferedImage.getWidth(),
	          bufferedImage.getHeight(), colorModel.getPixelSize(),
	          palette);
	      data.transparentPixel = colorModel.getTransparentPixel();
	      WritableRaster raster = bufferedImage.getRaster();
	      int[] pixelArray = new int[1];
	      for (int y = 0; y < data.height; y++) {
	        for (int x = 0; x < data.width; x++) {
	          raster.getPixel(x, y, pixelArray);
	          data.setPixel(x, y, pixelArray[0]);
	        }
	      }
	      return data;
	    }
	    return null;
	  }
	
	
	
	
	static BufferedImage convertToAWT(ImageData data) {
	    ColorModel colorModel = null;
	    PaletteData palette = data.palette;
	    if (palette.isDirect) {
	      colorModel = new DirectColorModel(data.depth, palette.redMask,
	          palette.greenMask, palette.blueMask);
	      BufferedImage bufferedImage = new BufferedImage(colorModel,
	          colorModel.createCompatibleWritableRaster(data.width,
	              data.height), false, null);
	      
	      WritableRaster raster = bufferedImage.getRaster();
	      int[] pixelArray = new int[3];
	      for (int y = 0; y < data.height; y++) {
	        for (int x = 0; x < data.width; x++) {
	          int pixel = data.getPixel(x, y);
	          RGB rgb = palette.getRGB(pixel);
	          pixelArray[0] = rgb.red;
	          pixelArray[1] = rgb.green;
	          pixelArray[2] = rgb.blue;
	          raster.setPixels(x, y, 1, 1, pixelArray);
	        }
	      }
	      return bufferedImage;
	    } else {
	      RGB[] rgbs = palette.getRGBs();
	      byte[] red = new byte[rgbs.length];
	      byte[] green = new byte[rgbs.length];
	      byte[] blue = new byte[rgbs.length];
	      for (int i = 0; i < rgbs.length; i++) {
	        RGB rgb = rgbs[i];
	        red[i] = (byte) rgb.red;
	        green[i] = (byte) rgb.green;
	        blue[i] = (byte) rgb.blue;
	      }
	      if (data.transparentPixel != -1) {
	        colorModel = new IndexColorModel(data.depth, rgbs.length, red,
	            green, blue, data.transparentPixel);
	      } else {
	        colorModel = new IndexColorModel(data.depth, rgbs.length, red,
	            green, blue);
	      }
	      BufferedImage bufferedImage = new BufferedImage(colorModel,
	          colorModel.createCompatibleWritableRaster(data.width,
	              data.height), false, null);
	      WritableRaster raster = bufferedImage.getRaster();
	      int[] pixelArray = new int[1];
	      for (int y = 0; y < data.height; y++) {
	        for (int x = 0; x < data.width; x++) {
	          int pixel = data.getPixel(x, y);
	          pixelArray[0] = pixel;
	          raster.setPixel(x, y, pixelArray);
	        }
	      }
	      return bufferedImage;
	    }
	    
	}
	    
	    static  BufferedImage toBufferedImage(java.awt.Image image) {
	        if (image instanceof BufferedImage) {
	            return (BufferedImage)image;
	        }
	    
	        // This code ensures that all the pixels in the image are loaded
	        image = new  ImageIcon(image).getImage();
	    
	        // Determine if the image has transparent pixels; for this method's
	        // implementation, see e661 Determining If an Image Has Transparent Pixels
	        boolean hasAlpha = hasAlpha(image);
	    
	        // Create a buffered image with a format that's compatible with the screen
	        BufferedImage bimage = null;
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        try {
	            // Determine the type of transparency of the new buffered image
	            int transparency = Transparency.OPAQUE;
	            if (hasAlpha) {
	                transparency = Transparency.BITMASK;
	            }
	    
	            // Create the buffered image
	            GraphicsDevice gs = ge.getDefaultScreenDevice();
	            GraphicsConfiguration gc = gs.getDefaultConfiguration();
	            bimage = gc.createCompatibleImage(
	                image.getWidth(null), image.getHeight(null), transparency);
	        } catch (HeadlessException e) {
	            // The system does not have a screen
	        }
	    
	        if (bimage == null) {
	            // Create a buffered image using the default color model
	            int type = BufferedImage.TYPE_INT_RGB;
	            if (hasAlpha) {
	                type = BufferedImage.TYPE_INT_ARGB;
	            }
	            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
	        }
	    
	        // Copy image to buffered image
	        Graphics g = bimage.createGraphics();
	    
	        // Paint the image onto the buffered image
	        g.drawImage(image, 0, 0, null);
	        g.dispose();
	    
	        return bimage;
	    }
	    
	 

	
	 public static boolean hasAlpha(java.awt.Image image) {
	        // If buffered image, the color model is readily available
	        if (image instanceof BufferedImage) {
	            BufferedImage bimage = (BufferedImage)image;
	            return bimage.getColorModel().hasAlpha();
	        }
	    
	        // Use a pixel grabber to retrieve the image's color model;
	        // grabbing a single pixel is usually sufficient
	         PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
	        try {
	            pg.grabPixels();
	        } catch (InterruptedException e) {
	        }
	    
	        // Get the image's color model
	        ColorModel cm = pg.getColorModel();
	        return cm.hasAlpha();
	    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
