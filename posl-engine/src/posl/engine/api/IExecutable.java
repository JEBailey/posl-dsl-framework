package posl.engine.api;

import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public interface IExecutable {

	public abstract Object execute(Scope scope, Statement tokens) throws PoslException;

}