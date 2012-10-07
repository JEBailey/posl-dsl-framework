package posl.engine.spi;

import posl.engine.core.Context;

public interface PoslImpl {
	
	/**
	 * returns an array of required libraries
	 * 
	 * @return
	 */
	public String[] getRequires();
	
	/**
	 * returns the library identifier
	 * 
	 * @return
	 */
	public String getName();
	
	/**
	 * Updates the context with this libraries
	 * executables
	 * 
	 * @param context
	 */
	public void visit(Context context);

}
