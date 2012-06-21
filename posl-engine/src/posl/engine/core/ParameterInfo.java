package posl.engine.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import posl.engine.annotation.ContextProperty;
import posl.engine.annotation.Optional;
import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public class ParameterInfo {

	public enum State { NORMAL, OPTIONAL, CONTEXT_PROPERTY, SCOPE};
	
	private Type type;
	
	private State state = State.NORMAL;
	
	//represents the amount that we should update the command line index
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
		if (param == Scope.class){
			state = State.SCOPE;
			increment = 0;
		}
	}
	
	public int incr() {
		return increment;
	}

	public boolean isOptional() {
		return state == State.OPTIONAL;
	}

	public Object render(Scope scope, Statement statement, int tokenIndex) throws PoslException {
		switch (state){
		case NORMAL:
		case OPTIONAL:
			return scope.get(type, statement.get(tokenIndex));
		case CONTEXT_PROPERTY:
			return scope.get(parameter);
		case SCOPE:
			return scope;
		}
		return null;
	}
	
	
	
	
	
	
	
}
