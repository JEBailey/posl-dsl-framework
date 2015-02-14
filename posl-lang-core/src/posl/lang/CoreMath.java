package posl.lang;

import java.util.List;

import posl.engine.annotation.Collection;
import posl.engine.annotation.Command;
import posl.engine.annotation.Primitive;
import posl.engine.error.PoslException;
import posl.engine.type.Reference;

public class CoreMath {

	@Command("+")
	public static Object add(Number left, Number right) throws Exception {
		return left.doubleValue() + right.doubleValue();
	}

	@Command("++")
	public static Object increment(Reference key) throws PoslException {
		Number value = key.getValue(Number.class);
		return key.updateValue(value.doubleValue() + 1);
	}

	@Command("--")
	public static Object decrement(Reference key) throws PoslException {
		Number value = key.getValue(Number.class);
		return key.updateValue(value.doubleValue() - 1);
	}

	@Command("sum")
	public static Object sum(@Collection List<Number> numbers) {
		return numbers.stream().mapToInt(x -> x.intValue()).sum();
	}

	@Command("/")
	public static Object divide(Number left, Number right) {
		return left.doubleValue() / right.doubleValue();
	}

	@Command("*")
	public static Object multiply(Number left, Number right) {
		return left.doubleValue() * right.doubleValue();
	}

	@Command("-")
	public static Object subtract(Number left, Number right) {
		return left.doubleValue() - right.doubleValue();
	}

	@Command("mod")
	public static Object mod(Number left, Number right) {
		return left.doubleValue() % right.doubleValue();
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
