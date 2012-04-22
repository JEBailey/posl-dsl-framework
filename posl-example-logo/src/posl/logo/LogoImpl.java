package posl.logo;

import posl.engine.core.Context;
import posl.engine.spi.PoslImpl;
import posl.logo.impl.Colors;

public class LogoImpl implements PoslImpl {

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
