package posl.lang.executable;

import posl.engine.Interpreter;
import posl.engine.api.Executable;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.MultiLineStatement;
import posl.engine.type.Statement;

/**
 * Creates a Scope and populates with a set of statements. 
 * 
 * 
 * @author jebailey
 *
 */
public class NamespaceExec implements Executable {

	/**
	 * Namespace scope.
	 */
	private Scope runtimeScope;

	/**
	 * Create a new Scope and populate
	 * 
	 * @param scope parent scope
	 * @param statements to be evaluated
	 * @throws PoslException
	 */
	public NamespaceExec(Scope scope, MultiLineStatement statements)
			throws PoslException {
		runtimeScope = scope.createChildScope();
		Interpreter.process(runtimeScope, statements);
	}

	/**
	 * 
	 * 
	 * 
	 * @param statements to be evaluated
	 * @return
	 * @throws PoslException
	 */
	public Object processStatements(MultiLineStatement statements)
			throws PoslException {
		return Interpreter.process(runtimeScope, statements);
	}

	public Object processStatement(Statement statement) throws PoslException {
		return Interpreter.process(runtimeScope, statement);
	}

	public void put(String key, Object value) {
		this.runtimeScope.put(key, value);
	}

	@Override
	public Object execute(Scope callingScope, Statement tokens)
			throws PoslException {
		try {
			Object token  = runtimeScope.getValue(tokens.get(1));
			if (token instanceof Executable) {
				token = ((Executable) token).execute(callingScope, tokens.subList(1, tokens.size()));
			}
			return token;
		} catch (PoslException e) {
			throw e.push(tokens.startPos(), "in namespace "
					+ tokens.get(0).toString());
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
