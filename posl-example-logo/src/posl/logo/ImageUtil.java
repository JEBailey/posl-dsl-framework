package posl.logo;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class ImageUtil {
	
	public static BufferedImage getBufferedImage(int width,int height){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gs.getDefaultConfiguration();
		
		BufferedImage bimage = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		bimage.setAccelerationPriority(1);
		return bimage;
	}

}
