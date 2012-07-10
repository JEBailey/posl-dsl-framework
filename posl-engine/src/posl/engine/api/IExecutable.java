package posl.engine.api;

import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public interface IExecutable {

	Object execute(Scope scope, Statement tokens) throws PoslException;

}