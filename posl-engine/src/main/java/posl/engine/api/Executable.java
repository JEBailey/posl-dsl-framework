package posl.engine.api;

import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.Statement;

/**
 * Designates an object that performs work in the script environment.
 * In processing a statement, the assumption is that an executable is the first
 * object to be encountered.
 * 
 * @author je bailey
 *
 */
public interface Executable {

	Object execute(Scope scope, Statement statement) throws PoslException;

}