package posl.engine.api;

import posl.engine.error.PoslException;

/**
 * 
 * 
 * @author jebailey
 *
 */
public interface StatementProvider {

	Object accept(StatementProviderVisitor visitor) throws PoslException;
}
