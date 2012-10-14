package posl.logo.lib;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;


public class Turtle {

	public Turtle(BufferedImage image) {
		graphics = (Graphics2D)image.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF); 
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		graphics.setColor(Color.black);
	}

	private double x;
	private double y;
	//private double z;
	private boolean pendown = true;
	
	private Graphics2D graphics;

	private Headings heading = new Headings();
	
	public double forward(double number){
		double endX   = x - number * Math.sin(heading.getRadians());
		double endY   = y - number * Math.cos(heading.getRadians());
		if (pendown){
			graphics.drawLine((int)x, (int)y, (int)endX, (int)endY);			
		}
		x = endX;
		y = endY;
		return number;
	}
	
	
	public Double back(double number){
		double endX   = x + number * Math.sin(heading.getRadians());
		double endY   = y + number * Math.cos(heading.getRadians());
		if (pendown) {
			graphics.drawLine((int)x, (int)y, (int)endX, (int)endY);
		}
		x = endX;
		y = endY;
		return number;
	}
	
	public void drawLineTo(Point2D point){
		if (pendown) {
			graphics.drawLine((int)x, (int)y, (int)point.getX(), (int)point.getY());
		}
		x = point.getX();
		y = point.getY();
	}


	public Object right(double number){
		heading.modifyDegrees(- number);
		return number;
	}
	

	public Object left(double number){
		heading.modifyDegrees(number);
		return number;
	}
	
	public Point2D getPos(){
		return new Point2D.Double(x, y);
	}
	
	public double setX(double x){
		this.x = x;
		return x;
	}
	
	public double getX(){
		return x;
	}
	
	public double setY(double y){
		this.y = y;
		return y;
	}
	
	public double getY(){
		return y;
	}
	
	public boolean setPenDown(boolean status){
		return pendown = status;
	}
	
	public Graphics2D getGraphics() {
		return graphics;
	}

	class Headings {
		
		private double degrees;
		private double radians;
		
		public double getRadians() {
			return radians;
		}
		public void setDegrees(double degrees) {
			this.degrees = degrees;
			radians = Math.toRadians(degrees);
		}
		
		public void modifyDegrees(double change){
			degrees = (degrees + change) % 360;
			radians = Math.toRadians(degrees);
		}
		
		
		
	}

}
