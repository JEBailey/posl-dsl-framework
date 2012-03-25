package posl.lang;

import posl.engine.core.Context;
import posl.engine.spi.PoslImpl;

public class VarImpl implements PoslImpl {

	@Override
	public String[] getRequires() {
		return new String[]{};
	}

	@Override
	public String getName() {
		return "posl.lang.var";
	}

	@Override
	public void visit(Context context) {
		context.load(Var.class);
	}

}
