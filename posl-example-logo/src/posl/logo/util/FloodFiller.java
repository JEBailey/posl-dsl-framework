package posl.logo.util;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class FloodFiller {

	public static Image fill(Image img, int xSeed, int ySeed, Color col) {
		BufferedImage bi = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		bi.getGraphics().drawImage(img, 0, 0, null);
		int x = xSeed;
		int y = ySeed;
		int width = bi.getWidth();
		int height = bi.getHeight();

		DataBufferInt data = (DataBufferInt) (bi.getRaster().getDataBuffer());
		int[] pixels = data.getData();

		if (x >= 0 && x < width && y >= 0 && y < height) {

			int oldColor = pixels[y * width + x];
			int fillColor = col.getRGB();

			if (oldColor != fillColor) {
				floodIt(pixels, x, y, width, height, oldColor, fillColor);
			}
		}
		return bi;
	}

	private static void floodIt(int[] pixels, int x, int y, int width,
			int height, int oldColor, int fillColor) {

		int[] point = new int[] { x, y };
		LinkedList<int[]> points = new LinkedList<int[]>();
		points.addFirst(point);

		while (!points.isEmpty()) {
			point = points.remove();

			x = point[0];
			y = point[1];
			int xr = x;

			int yp = y * width;
			int ypp = yp + width;
			int ypm = yp - width;

			do {
				pixels[xr + yp] = fillColor;
				xr++;
			} while (xr < width && pixels[xr + y * width] == oldColor);

			int xl = x;
			do {
				pixels[xl + yp] = fillColor;
				xl--;
			} while (xl >= 0 && pixels[xl + y * width] == oldColor);

			xr--;
			xl++;

			boolean upLine = false;
			boolean downLine = false;

			for (int xi = xl; xi <= xr; xi++) {
				upLine = (y > 0 && pixels[xi + ypm] == oldColor && !upLine);
				if (upLine) {
					points.addFirst(new int[] { xi, y - 1 });	
				}
				downLine = (y < height - 1 && pixels[xi + ypp] == oldColor && !downLine);
				if (downLine) {
					points.addFirst(new int[] { xi, y + 1 });
				}
			}
		}
	}
}