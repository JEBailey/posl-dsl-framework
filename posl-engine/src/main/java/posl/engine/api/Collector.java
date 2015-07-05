package posl.engine.api;

/**
 * Class that gathers a sequence of tokens into a collection of tokens.
 * 
 * @author je bailey
 *
 */
public interface Collector {
	
	/**
	 *
	 * @param token to be added to the aggregate
	 * @return add was successful
	 */
	boolean add(Object token);

	/**
	 * There are times when the Token process believes the collector
	 * has reached an end point (EOS,EOL)
	 * 
	 * This provides an indicator as to whether the token creates a new
	 * collector or continues to use the existing one
	 * 
	 * @return true if the Collector is done Collecting
	 */
	boolean isFinished();
	
	/**
	 * This returns the object that the collector is collecting into.
	 * This could the collector itself or a new object
	 * 
	 * @return object which encompasses the supplied tokens
	 */
	Object get();
}
