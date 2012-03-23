package posl.lang.function;

import posl.engine.core.Context;
import posl.engine.spi.PoslImpl;
import posl.lang.CoreFunction;

public class FunctionImpl implements PoslImpl {

	@Override
	public String[] getRequires() {
		return new String[]{};
	}

	@Override
	public String getName() {
		return "posl.lang.function";
	}

	@Override
	public void visit(Context context) {
		context.load(CoreFunction.class);
	}

}
