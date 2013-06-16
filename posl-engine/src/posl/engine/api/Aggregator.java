package posl.engine.api;

public interface Aggregator {
	
	/**
	 *
	 * @param token to be added to the aggregate
	 * @return added successfully
	 */
	boolean add(Object token);

	/**
	 * There are times when the Token process believes the aggregator
	 * has reached an end point (EOS,EOL)
	 * 
	 * This provides an indicator as to whether the token creates a new
	 * aggregator or continues to use the existing one
	 * 
	 * @return if the aggregate can be replaced
	 */
	boolean finish();
	
	/**
	 * This returns the object that the aggregator is aggretating to.
	 * This could the aggregator itself or an object within it.
	 * 
	 * @return possible new object which has aggregated the supplied tokens
	 */
	Object get();
}
