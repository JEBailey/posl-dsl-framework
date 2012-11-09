package posl.java;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import posl.engine.api.IExecutable;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.SingleStatement;

public class JavaMethod implements IExecutable {

	private Object reference;
	private List<Method> methods = new LinkedList<Method>();

	public JavaMethod(Object object, Method method) {
		this.reference = object;
		methods.add(method);
	}
	
	public void addMethod(Method method){
		methods.add(method);
	}

	@Override
	public Object execute(Scope scope, SingleStatement tokens) {
		Method method = null;		
		Object[] args = new Object[tokens.size()];
		for (int i = 0 ; i < args.length;++i){
			try {
				args[i] = scope.getValue(tokens.get(i));
			} catch (PoslException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(args[i] instanceof Java){
				Java j = (Java)args[i];
				args[i] = j.getObject();
			}
		}
		method = appropriateMethod(args);

		try {
			Object reply = method.invoke(reference, args);
			if(reply == null) {
				return null;
			} else if(reply instanceof Number || reply instanceof String){
				return reply;
			} else {
				return new Java(reply);
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}

	}

	private Method appropriateMethod(Object[] args) {
		if(methods.size() == 1){
			return methods.get(0);
		} else {
			for(Method m : methods){
				if(sameParameters(m, args)){
					return m;
				}
			}
			throw new RuntimeException("Could not find method for arguments");
		}
	}

	private boolean sameParameters(Method m, Object[] args) {
		Type[] paramTypes = m.getGenericParameterTypes();
		return args.length == paramTypes.length;
	}

}
