package posl.lang.executable;

import posl.engine.Interpreter;
import posl.engine.api.IExecutable;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.MultiLineStatement;
import posl.engine.type.SingleStatement;

public class NamespaceExec implements IExecutable {

	/**
	 * Parent scope. When the actual method block runs, it will run within a
	 * child of this scope.
	 */
	private Scope runtimeScope;
	

	public NamespaceExec(Scope scope,MultiLineStatement statements) throws PoslException {
		runtimeScope = scope.createChildScope();
		Interpreter.process(runtimeScope,statements.get());
	}
	
	public Object processStatements(MultiLineStatement statements) throws PoslException{
		return Interpreter.process(runtimeScope,statements.get());
	}
	
	public Object processStatement(SingleStatement statement) throws PoslException{
		return Interpreter.process(runtimeScope,statement);
	}
	
	public void put(String key,Object value){
		this.runtimeScope.put(key, value);
	}

	@Override
	public Object execute(Scope callingScope, SingleStatement tokens) throws PoslException {
		try {
			SingleStatement subList = tokens.subList(1,tokens.size());
			return Interpreter.process(runtimeScope, subList);
		} catch (PoslException e) {
			throw e.push(tokens.startPos(), "in namespace "+tokens.get(0).toString());
		}
	}

	public boolean containsKey(String key) {
		return runtimeScope.containsKey(key);
	}
	
	@Override
	public String toString() {
		return "NAMESPACE :: " + super.toString();
	}

}
