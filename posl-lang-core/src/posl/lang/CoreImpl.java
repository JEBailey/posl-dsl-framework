package posl.lang;

import posl.engine.core.Context;
import posl.engine.spi.PoslImpl;

public class CoreImpl implements PoslImpl {

	@Override
	public String[] getRequires() {
		return new String[]{};
	}

	@Override
	public String getName() {
		return "posl.lang.core";
	}

	@Override
	public void visit(Context context) {
		context.load(CoreLangFeatures.class);
		context.load(CoreList.class);
		context.load(Types.class);
		context.load(Var.class);
		context.load(CoreFunction.class);
		context.load(CoreMath.class);
		context.load(Relational.class);
	}

}
