package posl.lang.math;

import posl.engine.util.NaN;

/**
 * 
 * NaN Implmenetation
 * 
 * @author jason bailey
 */
public class NaNMath extends NumberMath {
	
	public static final NaNMath INSTANCE = new NaNMath();

	private NaNMath() {
	}

	@Override
	protected Number absImpl(Number number) {
		return NaN.INSTANCE;
	}

	@Override
	public Number addImpl(Number left, Number right) {
		return NaN.INSTANCE;
	}

	@Override
	public int compareToImpl(Number left, Number right) {
		return -1;
	}

	@Override
	public Number divideImpl(Number left, Number right) {
		return NaN.INSTANCE;
	}

	@Override
	public Number multiplyImpl(Number left, Number right) {
		return NaN.INSTANCE;
	}

	@Override
	public Number subtractImpl(Number left, Number right) {
		return NaN.INSTANCE;
	}

	@Override
	protected Number unaryMinusImpl(Number left) {
		return NaN.INSTANCE;
	}

}
