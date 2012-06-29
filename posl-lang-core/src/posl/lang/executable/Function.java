package posl.lang.executable;

import java.util.List;

import posl.engine.Interpreter;
import posl.engine.api.IExecutable;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.MultiLineStatement;
import posl.engine.type.PList;
import posl.engine.type.Statement;

public class Function implements IExecutable {

	/**
	 * Actual code that will be executed with the passed in parameters
	 */
	private MultiLineStatement statements;

	/**
	 * This is the list of word tokens that will be used as identifiers within
	 * the code block for the data that is being passed in.
	 */
	private List<?> arguments;

	/**
	 * Parent scope that the method was created in. When the actual method block
	 * runs, it will run within a child of this scope.
	 */
	private Scope scope;

	public Function(PList args, MultiLineStatement statementBlock, Scope scope) {
		this.arguments = (List<?>)args;
		this.statements = statementBlock;
		this.scope = scope;
	}

	/**
	 * The scope passed into this execution is used to obtain the specific
	 * 
	 * @param argumentScope
	 * @param callingArgs
	 * @return
	 * @throws PoslException
	 * @throws Exception
	 */
	public Object execute(Scope argumentScope, Statement callingArgs) throws PoslException {
		Scope runtimeScope = scope.createChildScope();
		if (callingArgs != null) {
			if ((callingArgs.size() - 1) != this.arguments.size()) {
				throw new PoslException(callingArgs.startLineNumber(),"incorrect number of arguments "+callingArgs.get(0).toString());
			}
			for (int i = 0; i < arguments.size(); ++i) {
				String key = arguments.get(i).toString();
				Object value = argumentScope.getValue(callingArgs.get(i + 1));
				runtimeScope.put(key, value);
			}
		}
		return Interpreter.processList(runtimeScope, statements);
	}

	@Override
	public String toString() {
		return "FUNCTION :: " + statements.toString();
	}

}
