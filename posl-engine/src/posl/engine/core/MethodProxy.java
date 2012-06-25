package posl.engine.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import posl.engine.annotation.ArgumentResolver;
import posl.engine.api.AArgumentHandler;
import posl.engine.api.IExecutable;
import posl.engine.error.PoslException;
import posl.engine.resolvers.NewDefault;
import posl.engine.type.Statement;

public class MethodProxy implements IExecutable {

	private Method method;

	private Object object;
	
	private AArgumentHandler resolver;	

	public MethodProxy(Method method, Object object) {
		this.method = method;
		this.object = object;
		try {
			if (method.isAnnotationPresent(ArgumentResolver.class)) {
				resolver = method.getAnnotation(ArgumentResolver.class).value().newInstance();
			} else {
				resolver = new NewDefault();
			}
			resolver.populate(method);
		} catch (Exception e) {
			assert false : e;
		}
	}

	@Override
	public Object execute(Scope argumentScope, Statement tokens) throws PoslException {
		try {
			//Define the argument array that we will be passing to the method executor
			Object[] arguments = resolver.render(argumentScope, tokens);
			return method.invoke(object, arguments);
		} catch (PoslException e1) {
			e1.push(tokens.getLineNumber(), "in method "+tokens.get(0).toString());
			throw e1;
		} catch (IllegalAccessException e) {
			throw new PoslException(tokens.getLineNumber(),e.toString());
		} catch (IllegalArgumentException e) {
			throw new PoslException(tokens.getLineNumber(),e.toString());
		} catch (InvocationTargetException ite) {
			// Any exception which occurs in the proxied method
			// will result in an InvocationTargetException
			PoslException exception = null;
			try {
				exception = (PoslException)ite.getTargetException();
			} catch (Exception e){
				exception = new PoslException(tokens.getLineNumber(),ite.getCause().toString());
			}
			exception.push(tokens.getLineNumber(),  "in command '"+tokens.get(0)+"'");
			throw exception;
		}
	}
	
	@Override
	public String toString() {
		return "COMMAND :: " + super.toString();
	}

}
