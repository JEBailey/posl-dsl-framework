package posl.lang;

import posl.engine.annotation.Command;


public class Relational {

	@Command("<")
	public static Object lt(Number left, Number right) throws Exception {
		return left.doubleValue() < right.doubleValue();

	}
	
	@Command(">")
	public static Object gt(Number left, Number right) throws Exception {
		return left.doubleValue() > right.doubleValue();
	}
	
	@Command("=")
	public static Object equal(Number left, Number right) throws Exception {
		return left.doubleValue() == right.doubleValue();
	}
	
}
