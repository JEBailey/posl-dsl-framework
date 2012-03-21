package posl.engine.spi;

import posl.engine.core.Context;

public interface PoslImpl {
	
	public String[] getRequires();
	
	public String getName();
	
	public void visit(Context context);

}
