package posl.engine.api;

/**
 * An 
 * 
 * @author je bailey
 *
 */
public interface Collector {
	
	/**
	 *
	 * @param token to be added to the aggregate
	 * @return added successfully
	 */
	boolean add(Object token);

	/**
	 * There are times when the Token process believes the collector
	 * has reached an end point (EOS,EOL)
	 * 
	 * This provides an indicator as to whether the token creates a new
	 * collector or continues to use the existing one
	 * 
	 * @return if the aggregate can be replaced
	 */
	boolean finish();
	
	/**
	 * This returns the object that the collector is collecting to.
	 * This could the collector itself or an object within it.
	 * 
	 * @return object which encompasses the supplied tokens
	 */
	Object get();
}
