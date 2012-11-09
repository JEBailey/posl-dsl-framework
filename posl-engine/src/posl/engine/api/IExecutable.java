package posl.engine.api;

import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.SingleStatement;

public interface IExecutable {

	Object execute(Scope scope, SingleStatement tokens) throws PoslException;

}