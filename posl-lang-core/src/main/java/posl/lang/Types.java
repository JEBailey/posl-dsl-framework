package posl.lang;

import posl.engine.annotation.Command;

public class Types {
	
	@Command("int")
	public static Number run(Number number) throws Exception {
		return number.intValue();
	}
	
	@Command("object")
	public static Object object() throws Exception {
		return new Object();
	}
	
}
