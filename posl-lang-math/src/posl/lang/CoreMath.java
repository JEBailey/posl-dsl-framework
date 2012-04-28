package posl.lang;

import posl.engine.annotation.Command;
import posl.engine.annotation.Primitive;
import posl.engine.error.PoslException;
import posl.engine.type.Reference;
import posl.lang.math.NumberMath;

public class CoreMath {

	@Command("+")
	public static Object add(Number left, Number right) throws Exception {
		return NumberMath.add(left, right);
	}

	@Command("++")
	public static Object increment(Reference key)
			throws PoslException {
		Number value = key.getValue(Number.class);
		return key.updateValue(NumberMath.add(value, 1));
	}

	@Command("--")
	public static Object decrement(Reference key) throws PoslException {
		Number value = key.getValue(Number.class);
		return key.updateValue(NumberMath.subtract(value, 1));
	}

	@Command("/")
	public static Object divide(Number left, Number right) {
		return NumberMath.divide(left, right);
	}

	@Command("*")
	public static Object multiply(Number left, Number right) {
		return NumberMath.multiply(left, right);
	}

	@Command("-")
	public static Object subtract(Number left, Number right) {
		return NumberMath.subtract(left, right);
	}

	@Command("mod")
	public static Object mod(Number left, Number right) {
		return NumberMath.mod(left, right);
	}
	
	@Command("cos")
	public static Number mod(Number number) {
		return Math.cos(number.doubleValue());
	}
	
	@Command("sin")
	public static Number sin(Number number) {
		return Math.sin(number.doubleValue());
	}
	
	@Primitive("PI")
	public static Number PI() {
		return Math.PI;
	}
	
	@Primitive("E")
	public static Number E() {
		return Math.E;
	}

}
