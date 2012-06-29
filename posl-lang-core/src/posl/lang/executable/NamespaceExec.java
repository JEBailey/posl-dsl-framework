package posl.lang.executable;

import posl.engine.Interpreter;
import posl.engine.api.IExecutable;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.MultiLineStatement;
import posl.engine.type.Statement;

public class NamespaceExec implements IExecutable {

	/**
	 * Parent scope. When the actual method block runs, it will run within a
	 * child of this scope.
	 */
	private Scope runtimeScope;
	

	public NamespaceExec(Scope scope,MultiLineStatement statements) throws PoslException {
		runtimeScope = scope.createChildScope();
		Interpreter.processList(runtimeScope,statements);
	}
	
	public Object processStatements(MultiLineStatement statements) throws PoslException{
		return Interpreter.processList(runtimeScope,statements);
	}
	
	public Object processStatement(Statement statement) throws PoslException{
		return Interpreter.process(runtimeScope,statement);
	}
	
	public void put(String key,Object value){
		this.runtimeScope.put(key, value);
	}

	@Override
	public Object execute(Scope callingScope, Statement tokens) throws PoslException {
		try {
			/*Object token = runtimeScope.getValue(tokens.get(1));
			Statement subList = tokens.subList(1,tokens.size());
			if (token instanceof IExecutable) {
				token = ((IExecutable)token).execute(callingScope, subList);
			}
			return token;*/
			Statement subList = tokens.subList(1,tokens.size());
			return Interpreter.process(runtimeScope, subList);
		} catch (PoslException e) {
			throw e.push(tokens.startLineNumber(), "in namespace "+tokens.get(0).toString());
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
