package posl.lang.math;

import posl.engine.core.Context;
import posl.engine.spi.PoslImpl;
import posl.lang.CoreMath;
import posl.lang.Relational;

public class MathImpl implements PoslImpl {

	@Override
	public String[] getRequires() {
		return new String[]{};
	}

	@Override
	public String getName() {
		return "posl.lang.math";
	}

	@Override
	public void visit(Context context) {
		context.load(CoreMath.class);
		context.load(Relational.class);
	}

}
