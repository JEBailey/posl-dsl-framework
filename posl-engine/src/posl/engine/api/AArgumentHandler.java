package posl.engine.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import posl.engine.core.ParameterInfo;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public abstract class AArgumentHandler {
	
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
	
	
	public abstract Object[] render(Scope scope, Statement tokens) throws PoslException;

}
