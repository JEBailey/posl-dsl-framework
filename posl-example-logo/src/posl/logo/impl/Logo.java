package posl.logo.impl;

import posl.engine.core.Context;
import posl.engine.spi.PoslImpl;
import posl.logo.lib.Colors;

public class Logo implements PoslImpl {

	@Override
	public String[] getRequires() {
		return new String[]{"posl.lang.core"};
	}

	@Override
	public String getName() {
		return "posl.logo";
	}

	@Override
	public void visit(Context context) {
		context.load(Colors.class);
	}

}
