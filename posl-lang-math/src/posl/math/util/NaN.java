package posl.math.util;


public class NaN extends Number {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9219557329628620160L;

	public static final NaN INSTANCE = new NaN();

	private NaN() {
	}
	
	@Override
	public double doubleValue() {
		// TODO Auto-generated method stub
		return Double.NaN;
	}

	@Override
	public float floatValue() {
		// TODO Auto-generated method stub
		return Float.NaN;
	}

	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public long longValue() {
		// TODO Auto-generated method stub
		return -1;
	}

}
