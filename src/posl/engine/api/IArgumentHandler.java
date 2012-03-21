package posl.engine.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public abstract class IArgumentHandler {
	
	public Type[] params;
	
	public Annotation[][] annotations;
	
	public abstract Object[] render(Scope scope, Statement tokens) throws PoslException;

}
