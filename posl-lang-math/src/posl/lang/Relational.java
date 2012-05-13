package posl.lang;

import posl.engine.annotation.Command;
import posl.lang.math.NumberMath;


public class Relational {

	@Command("<")
	public static Object lt(Number left, Number right) throws Exception {
		return NumberMath.compareTo(left,right) < 0;

	}
	
	@Command(">")
	public static Object gt(Number left, Number right) throws Exception {
		return NumberMath.compareTo(left,right) > 0;
	}
	
	@Command("=")
	public static Object equal(Number left, Number right) throws Exception {
		return NumberMath.compareTo(left, right) == 0;
	}
	
	@Command(">=")
	public static Object gte(Number left, Number right) throws Exception {
		return NumberMath.compareTo(left,right) >= 0;
	}
	
	@Command("<=")
	public static Object lte(Number left, Number right) throws Exception {
		return NumberMath.compareTo(left, right) <= 0;
	}
	
	
	
	
}
