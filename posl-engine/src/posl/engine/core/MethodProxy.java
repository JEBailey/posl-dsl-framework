package posl.engine.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import posl.engine.api.Executable;
import posl.engine.error.PoslException;
import posl.engine.type.SingleStatement;

/**
 * MethodProxy is used to wrap an invocation of an executable that
 * is mapped to a specific java method.
 * 
 * 
 * @author je bailey
 *
 */
public class MethodProxy implements Executable {

	private Method method;

	private Object object;
	
	private Resolver resolver;	

	public MethodProxy(Method method, Object object) {
		this.method = method;
		this.object = object;
		this.resolver = new Resolver(method);
	}

	@Override
	public Object execute(Scope argumentScope, SingleStatement tokens) throws PoslException {
		try {
			return method.invoke(object, resolver.render(argumentScope, tokens));
		} catch (InvocationTargetException ite) {
			PoslException exception = new PoslException(tokens.startPos(),ite.getCause().toString());
			throw exception;
		} catch (IllegalAccessException e) {
			throw new PoslException(tokens.startPos(),e.toString());
		} 
	}
	
	@Override
	public String toString() {
		return "COMMAND :: " + super.toString();
	}

}
