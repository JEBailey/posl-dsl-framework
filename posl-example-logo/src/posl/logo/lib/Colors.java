package posl.logo.lib;

import java.awt.Color;

import posl.engine.annotation.Command;
import posl.engine.annotation.Primitive;

public class Colors {

	@Command("color")
	public static Color color(Number red, Number green, Number blue){
		int r1 = red.intValue() % 256;
		int g1 = green.intValue();
		int b1 = blue.intValue();
		return new Color(r1,g1,b1);
	}
	
	@Primitive("red")
	public static Color red(){
		return Color.red;
	}
	
	@Primitive("gray")
	public static Color gray(){
		return Color.gray;
	}
	
	@Primitive("green")
	public static Color green(){
		return Color.green;
	}
	
	@Primitive("black")
	public static Color black(){
		return Color.black;
	}
	
	@Primitive("blue")
	public static Color blue(){
		return Color.blue;
	}
	
	@Primitive("cyan")
	public static Color cyan(){
		return Color.cyan;
	}
	
	@Primitive("darkGray")
	public static Color darkGray(){
		return Color.darkGray;
	}
	
	@Primitive("lightGray")
	public static Color lightGray(){
		return Color.lightGray;
	}
	
	@Primitive("magenta")
	public static Color magenta(){
		return Color.magenta;
	}
	
	@Primitive("orange")
	public static Color orange(){
		return Color.orange;
	}
	
	@Primitive("pink")
	public static Color pink(){
		return Color.pink;
	}
	
	@Primitive("white")
	public static Color white(){
		return Color.white;
	}
	
	@Primitive("yellow")
	public static Color yellow(){
		return Color.yellow;
	}
	
}
