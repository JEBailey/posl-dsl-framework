package posl.logo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javax.swing.SwingWorker;

import posl.engine.Interpreter;
import posl.engine.annotation.Command;
import posl.engine.core.Context;
import posl.engine.error.PoslException;
import posl.engine.provider.PoslProvider;
import posl.engine.type.Reference;
import posl.logo.lib.Turtle;

public class LogoWorker extends SwingWorker<BufferedImage, BufferedImage> {

	private Random random = new Random(System.currentTimeMillis());

	private String text;

	// image size
	private int width = 800;
	private int height = 600;

	// turtle stuff
	private Turtle turtle;
	// private int turtleIndex;

	// command based values
	private BufferedImage bimage;

	private Graphics2D outerImage;

	public LogoWorker(String text, BufferedImage outerImage) {
		super();
		this.text = text;
		this.outerImage = (Graphics2D) outerImage.getGraphics();
	}

	@Override
	protected BufferedImage doInBackground() throws Exception {
		Context context = null;
		try {
			context = PoslProvider.getContext("posl.logo");
			context.load(this);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		// configure image to be used
		bimage = ImageUtil.getBufferedImage(width, height);

		turtle = new Turtle(bimage);
		turtle.setX(height);

		try {
			Interpreter.process(context,
					new ByteArrayInputStream(text.getBytes()));
		} catch (PoslException e) {
			this.firePropertyChange("error", null, e);
			System.out.println(e.toString());
		}
		publish(bimage);
		return bimage;
	}

	@Override
	protected void process(List<BufferedImage> chunks) {
		if (chunks.size() > 0) {
			BufferedImage image = chunks.get(chunks.size() - 1);
			this.outerImage.drawImage(image, 0, 0, null);
		}
	}

	// POSL Commands that are specific to Logo

	@Command("pendown")
	public void pendown() {
		turtle.setPenDown(true);
	}

	@Command("pause")
	public void pause(Number pause) {
		try {
			publish(bimage);
			Thread.sleep(pause.longValue());
		} catch (InterruptedException e) {
			// whatever
		}
	}

	@Command("penup")
	public void penup() {
		turtle.setPenDown(false);
	}

	@Command("repeat")
	public Object repeat(Number repeatNumber, Reference statements)
			throws PoslException {
		int number = repeatNumber.intValue();
		statements.createChildScope();
		for (int x = 0; x < number; ++x) {
			statements.evaluate();
		}
		return new Object();
	}

	@Command("forward")
	public Object forward(Number number) {
		return turtle.forward(number.doubleValue());
	}

	@Command("back")
	public Object back(Number number) {
		return turtle.back(number.doubleValue());
	}

	@Command("right")
	public Object right(Number number) {
		return turtle.right(number.doubleValue());
	}

	@Command("left")
	public Object left(Number number) {
		return turtle.left(number.doubleValue());
	}

	/**
	 * Fills a color in the image with a different color.
	 * 
	 * @param x
	 *            x coordinate of starting point.
	 * @param y
	 *            y coordinate of starting point.
	 * @param targetColor
	 *            color we want to replace.
	 * @param replacementColor
	 *            the color which is used as the replacement.
	 * @param image
	 *            the image where we fill the color.
	 */
	public void floodFill(Point point, int targetColor, int replacementColor) {
		Queue<Point> queue = new LinkedList<Point>();
		boolean[][] queued = new boolean[bimage.getWidth()][bimage.getHeight()];
		queue.add(point);
		while (!queue.isEmpty()) {
			Point p = queue.remove();
			int x = p.x;
			int y = p.y;
			if (bimage.getRGB(x, y) == targetColor) {
				bimage.setRGB(x, y, replacementColor);
				if (x > 0 && x < bimage.getWidth() - 1 && y > 0
						&& y < bimage.getHeight() - 1) {
					if (!queued[x + 1][y]) {
						queue.add(new Point(x + 1, y));
					}
					if (!queued[x - 1][y]) {
						queue.add(new Point(x - 1, y));
					}
					if (!queued[x][y + 1]) {
						queue.add(new Point(x, y + 1));
					}
					if (!queued[x][y - 1]) {
						queue.add(new Point(x, y - 1));
					}
				}
			}
		}
		queue = null;
	}

	@Command("flood")
	public void flood(Number xDelta, Number yDelta, Color replacementColor) {
		int x = (int) turtle.getPos().getX() - xDelta.intValue();
		int y = (int) turtle.getPos().getY() - yDelta.intValue();
		int targetColor = bimage.getRGB(x, y);
		floodFill(new Point(x, y), targetColor, replacementColor.getRGB());
	}

	@Command("paint")
	public void paint() {
		publish(bimage);
	}

	@Command("pencolor")
	public Object color(Color value) {
		turtle.getGraphics().setColor(value);
		return value;
	}

	@Command("line_width")
	public Object lineWidth(Number number) {
		turtle.getGraphics().setStroke(new BasicStroke(number.floatValue()));
		return number;
	}

	@Command("center_width")
	public Object centerWidth() {
		return turtle.setX(width / 2);
	}

	@Command("center_height")
	public Object centerHeight() {
		return turtle.setY(height / 2);
	}

	@Command("home")
	public void home() {
		turtle.setX(0);
		turtle.setY(height);
	}

	@Command("clear")
	public void clear() {
		turtle.getGraphics().setBackground(Color.white);
		turtle.getGraphics().clearRect(0, 0, width, height);
	}

	@Command("getpos")
	public Point2D pos() {
		return turtle.getPos();
	}

	@Command("setpos")
	public void setpos(Object object) {
		turtle.drawLineTo((Point2D) object);
	}

	@Command("newpos")
	public Point2D newPosition(Number x, Number y) {
		return new Point2D.Double(x.doubleValue(), y.doubleValue());
	}

	@Command("rand")
	public Object rand(Number number) {
		return random.nextInt(number.intValue());
	}

}
