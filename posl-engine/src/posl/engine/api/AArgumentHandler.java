package posl.engine.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public abstract class AArgumentHandler {
	
	protected Type[] params;
	
	protected Annotation[][] annotations;
	
	
	public void populate(Method method){
		this.params = method.getGenericParameterTypes();
		this.annotations = method.getParameterAnnotations();
	}
	
	
	public abstract Object[] render(Scope scope, Statement tokens) throws PoslException;

}
