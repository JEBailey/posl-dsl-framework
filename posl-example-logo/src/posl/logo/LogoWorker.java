package posl.logo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;
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
	private BufferedImage offscreenImage;

	private Graphics2D onscreenGraphics;

	public LogoWorker(String text, BufferedImage outerImage) {
		super();
		this.text = text;
		this.onscreenGraphics = (Graphics2D) outerImage.getGraphics();
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
		offscreenImage = ImageUtil.getBufferedImage(width, height);

		turtle = new Turtle(offscreenImage);
		turtle.setX(height);

		try {
			Interpreter.process(context,
					new ByteArrayInputStream(text.getBytes()));
		} catch (PoslException e) {
			this.firePropertyChange("error", null, e);
			System.out.println(e.toString());
		}

		doPublish(offscreenImage);
		return offscreenImage;
	}

	private static final Object mutex = new Object();

	@Override
	protected void process(List<BufferedImage> chunks) {
		if (chunks.size() > 0) {
			BufferedImage image = chunks.get(chunks.size() - 1);
			synchronized (mutex) {
				this.onscreenGraphics.drawImage(image, 0, 0, null);
			}
		}
	}

	private void doPublish(BufferedImage image) {
		synchronized (mutex) {
			publish(image);
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
			doPublish(offscreenImage);
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
	public Double forward(Number number) {
		return turtle.forward(number.doubleValue());
	}

	@Command("back")
	public Double back(Number number) {
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

	@Command("ellipse")
	public void ellipse(Number width, Number height) {
		double x = turtle.getX();
		double y = turtle.getY();
		turtle.getGraphics().draw(
				new Ellipse2D.Double(x, y, width.doubleValue(), height
						.doubleValue()));
	}

	@Command("filledEllipse")
	public void fellipse(Number width, Number height) {
		double x = turtle.getX() - (width.doubleValue()/2);
		double y = turtle.getY() - (height.doubleValue()/2);
		turtle.getGraphics().fill(
				new Ellipse2D.Double(x, y, width.doubleValue(), height
						.doubleValue()));
	}
	
	@Command("paint")
	public void paint() {
		doPublish(offscreenImage);
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

	@Command("center")
	public void center() {
		turtle.setX(width / 2);
	}

	@Command("bottom")
	public void bottom() {
		turtle.setY(height);
	}

	@Command("middle")
	public void middle() {
		turtle.setY(height / 2);
	}

	@Command("home")
	public void home() {
		clear();
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

	@Command("getx")
	public Number getx() {
		return turtle.getX();
	}

	@Command("gety")
	public Number gety() {
		return turtle.getY();
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
	public Number rand(Number number) {
		return random.nextInt(number.intValue());
	}

	@Command("arc")
	public void arc(double r, double angle1, double angle2) {
		if (r < 0)
			throw new RuntimeException("arc radius can't be negative");
		while (angle2 < angle1) {
			angle2 += 360;
		}
		double xs = turtle.getX();
		double ys = turtle.getY();
		double ws = 2 * r;
		double hs = 2 * r;
		turtle.getGraphics().draw(
				new Arc2D.Double(xs - ws / 2, ys - hs / 2, ws, hs, angle1,
						angle2 - angle1, Arc2D.OPEN));
	}
	
	@Command("circle")
	public void circle(Number height, Number width) {
		turtle.getGraphics().drawOval((int)turtle.getX(),(int)turtle.getY(), width.intValue(), height.intValue());
	}

	@Command("fill")
	public void fill(Number xSeed, Number ySeed, Color col) {
		int x = (int)turtle.getX() + xSeed.intValue();
		int y = (int)turtle.getY() - ySeed.intValue();
		int width = offscreenImage.getWidth();
		int height = offscreenImage.getHeight();

		DataBufferInt data = (DataBufferInt) (offscreenImage.getRaster().getDataBuffer());
		int[] pixels = data.getData();

		if (x >= 0 && x < width && y >= 0 && y < height) {
			int oldColor = pixels[y * width + x];
			int fillColor = col.getRGB();
			if (oldColor != fillColor) {
				floodIt(pixels, x, y, width, height, oldColor, fillColor);
			}
		}
	}

	private void floodIt(int[] pixels, int x, int y, int width, int height,
			int oldColor, int fillColor) {

		int[] point = new int[] { x, y };
		LinkedList<int[]> points = new LinkedList<int[]>();
		points.addFirst(point);

		while (!points.isEmpty()) {
			point = points.remove();

			x = point[0];
			y = point[1];
			int xrow = x;

			int yp = y * width;
			int ypp = yp + width;
			int ypm = yp - width;

			do {
				pixels[xrow + yp] = fillColor;
				xrow++;
			} while (xrow < width && pixels[xrow + y * width] == oldColor);

			int xl = x;
			do {
				pixels[xl + yp] = fillColor;
				xl--;
			} while (xl >= 0 && pixels[xl + y * width] == oldColor);

			xrow--;
			xl++;

			boolean upLine = false;
			boolean downLine = false;

			for (int xi = xl; xi <= xrow; xi++) {
				upLine = (y > 0 && pixels[xi + ypm] == oldColor && !upLine);
				if (upLine){
					points.addFirst(new int[] { xi, y - 1 });
				}
				downLine = (y < height - 1 && pixels[xi + ypp] == oldColor && !downLine);
				if (downLine){
					points.addFirst(new int[] { xi, y + 1 });
				}
			}
		}
	}

}
