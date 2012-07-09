package posl.engine.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import posl.engine.api.IExecutable;
import posl.engine.error.PoslException;
import posl.engine.resolvers.NewDefault;
import posl.engine.type.Statement;

public class MethodProxy implements IExecutable {

	private Method method;

	private Object object;
	
	private NewDefault resolver = new NewDefault();	

	public MethodProxy(Method method, Object object) {
		this.method = method;
		this.object = object;
		resolver.populate(method);
	}

	@Override
	public Object execute(Scope argumentScope, Statement tokens) throws PoslException {
		try {
			return method.invoke(object, resolver.render(argumentScope, tokens));
		} catch (PoslException e1) {
			e1.push(tokens.startLineNumber(), "in method "+tokens.get(0).toString());
			throw e1;
		} catch (InvocationTargetException ite) {
			// Any exception which occurs in the proxied method
			// will result in an InvocationTargetException
			PoslException exception = null;
			try {
				exception = (PoslException)ite.getTargetException();
			} catch (Exception e){
				exception = new PoslException(tokens.startLineNumber(),ite.getCause().toString());
			}
			exception.push(tokens.startLineNumber(),  "in command '"+tokens.get(0)+"'");
			throw exception;
		} catch (IllegalAccessException e) {
			throw new PoslException(tokens.startLineNumber(),e.toString());
		} 
	}
	
	@Override
	public String toString() {
		return "COMMAND :: " + super.toString();
	}

}
