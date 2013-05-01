package posl.engine.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import posl.engine.api.Executable;
import posl.engine.error.PoslException;
import posl.engine.type.SingleStatement;

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
		} catch (PoslException e1) {
			e1.push(tokens.startPos(), "in method "+tokens.get(0).toString());
			throw e1;
		} catch (InvocationTargetException ite) {
			// Any exception which occurs in the proxied method
			// will result in an InvocationTargetException
			PoslException exception = null;
			try {
				exception = (PoslException)ite.getTargetException();
			} catch (Exception e){
				exception = new PoslException(tokens.startPos(),ite.getCause().toString());
			}
			exception.push(tokens.startPos(),  "in command '"+tokens.get(0)+"'");
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
