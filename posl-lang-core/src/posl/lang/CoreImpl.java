package posl.lang;

import posl.engine.core.Context;
import posl.engine.spi.PoslImpl;

public class CoreImpl implements PoslImpl {

	@Override
	public String[] getRequires() {
		return new String[]{
				"posl.lang.math",
				"posl.lang.function"
		};
	}

	@Override
	public String getName() {
		return "posl.lang.core";
	}

	@Override
	public void visit(Context context) {
		context.load(Core.class);
		context.load(CoreList.class);
		context.load(Types.class);
		context.load(Var.class);
	}

}
