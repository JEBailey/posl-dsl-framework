package posl.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

import posl.engine.api.Executable;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.Atom;
import posl.engine.type.SingleStatement;

public class JavaInvocationHandler implements InvocationHandler {

	private Scope scope;
	
	public JavaInvocationHandler(Scope scope) {
		super();
		this.scope = scope;
	}
	/**
	 * Executed in one of two ways
	 * The first is as a proxy to a java object
	 * 
	 * The second is as an IExecutable which essentially
	 * mimics the NameSpace functionality but does so in a closure
	 * type way
	 * 
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String name = method.getName();
		if (name.equals("execute")){
			return execute((Scope)args[0],(SingleStatement)args[1]);
		}
		SingleStatement statement = new SingleStatement(Arrays.asList(args));
		statement.push(new Atom(name));
		return execute(scope,statement);
	}

	//TODO: Handle java invocations differently from IEXecutable invocations
	/**
	 * replicates the functionality found within the Namespace
	 * command. 
	 * 
	 * 
	 * 
	 * @param scope
	 * @param tokens
	 * @return
	 * @throws PoslException
	 */
	private Object execute(Scope scope, SingleStatement tokens) throws PoslException {
		Object token = null;
		try {
			token = tokens.get(0);
			token = this.scope.getValue(token);
		} catch (PoslException e) {
			//e.printStackTrace(); // it's possible that we haven't mapped all functions. That's ok
		}
		if (token != null) {
			if (token instanceof Executable) {
				token = ((Executable)token).execute(scope, tokens);
			}
		}
		return token;
	}


}
