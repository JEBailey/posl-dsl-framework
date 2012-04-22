package posl.lang.java;

import posl.engine.core.Context;
import posl.engine.spi.PoslImpl;
import posl.lang.JavaCommands;

public class JavaImpl implements PoslImpl {

	@Override
	public String[] getRequires() {
		return new String[]{"posl.lang.core"};
	}

	@Override
	public String getName() {
		return "posl.lang.java";
	}

	@Override
	public void visit(Context context) {
		context.load(JavaCommands.class);
	}

}
