package posl.engine.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import posl.engine.annotation.ContextProperty;
import posl.engine.annotation.Optional;

public class ParameterInfo {

	public enum State { NORMAL,OPTIONAL, CONTEXT_PROPERTY};
	
	private Type type;
	
	private State state = State.NORMAL;
	
	//represents whether the command line index increases
	private int increment = 1;
	
	private String parameter;
	
	public ParameterInfo(Type param,Annotation[] annotations) {
		this.type = param;
		for (Annotation annotation:annotations){
			if (annotation instanceof Optional){
				state = State.OPTIONAL;
			} else
			if (annotation instanceof ContextProperty){
				increment = 0;
				state = State.CONTEXT_PROPERTY;
				this.parameter = ((ContextProperty) annotation).value();
			}
		}
		if (type instanceof Context){
			increment = 0;
		}
	}
	
	public int incr() {
		return increment;
	}

	public boolean isOptional() {
		return state == State.OPTIONAL;
	}

	public Object render(Scope scope, Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
}
