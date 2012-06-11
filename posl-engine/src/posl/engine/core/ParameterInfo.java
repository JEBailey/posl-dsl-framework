package posl.engine.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import posl.engine.annotation.Optional;

public class ParameterInfo {

	public static int OPTIONAL = 0x1;
	public static int PARAMETER = 0x1 << 1;
	
	private Type type;
	
	private int state;
	
	private boolean increment = true;
	
	private Annotation parameter;
	
	public ParameterInfo(Type param,Annotation[] annotations) {
		this.type = param;
		for (Annotation annotation:annotations){
			if (annotation instanceof Optional){
				state = state & OPTIONAL;
			} else
			if (annotation instanceof ParameterInfo){
				increment = false;
				state = state & PARAMETER;
				this.parameter = annotation;
			}
		}
		if (type instanceof Context){
			increment = false;
		}
	}
	
	public boolean incr() {
		return increment;
	}

	public boolean isOptional() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object render(Scope scope, Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
}
