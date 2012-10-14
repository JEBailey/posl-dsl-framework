package posl.engine.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public class Resolver  {
	
protected Type[] params;
	
	protected Annotation[][] annotations;
	
	protected ParameterInfo[] info;
	
	
	public void populate(Method method){
		this.params = method.getGenericParameterTypes();
		this.annotations = method.getParameterAnnotations();
		info = new ParameterInfo[params.length];
		for (int i = 0;i < params.length ; i++){
			info[i] = new ParameterInfo(params[i], annotations[i]);
		}
	}
	

	/*
	 * So the key here is that we have a number of
	 * 
	 * (non-Javadoc)
	 * 
	 * @see posl.engine.api.AArgumentHandler#render(posl.engine.core.Scope,
	 * posl.engine.type.Statement)
	 */
	public Object[] render(Scope scope, Statement tokens) throws PoslException {
		// This is the argument array that will be passed in the method call
		Object[] arguments = new Object[info.length];
		// we're going to loop through the parameter information
		// i is the index for the parameter information
		// t is the index for the passed in tokens.
		int tokenIndex = 1;
		int length = tokens.size() - 1; // length of tokens passed in
		for (int i = 0; i < info.length; ++i) {
			ParameterInfo param = info[i];
			// first check to see if we've ran out of arguments
			if (tokenIndex > length) {
				if (!param.isOptional()) {
					throw new PoslException(tokens.startLineNumber(),
							"incorrect number of arguments");
				}
			} else {
				// we have enough arguments. do we need them?
				arguments[i] = param.render(scope, tokens, tokenIndex);
			}
			// do we increment?
			tokenIndex += param.incr();
		}
		return arguments;
	}

}
