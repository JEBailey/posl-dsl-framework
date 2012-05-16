package posl.java;

import java.lang.reflect.Field;

import posl.engine.api.IExecutable;
import posl.engine.core.Scope;
import posl.engine.type.Statement;

public class JavaField implements IExecutable {

	private Object reference;
	private Field field;
	
	public JavaField(Object object, Field field){
		this.reference = object;
		this.field = field;
	}
	
	@Override
	public Object execute(Scope scope, Statement tokens)
	{
		try {
			return field.get(reference);
		} catch (IllegalArgumentException e) {
			return new Error("incorrect arguments");
		} catch (IllegalAccessException e) {
			return new Error("illegal access");
		}
	}

}
