package posl.engine.core;

import java.util.Map;

import posl.engine.api.Executable;
import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public class ObjectWrapper implements Executable {
	
	private static Map<String,Object>cache;

	private ObjectWrapper() {
	}
	
	public static ObjectWrapper get(Object instance){
		return null;
	}

	@Override
	public Object execute(Scope scope, Statement statement)
			throws PoslException {
		// TODO Auto-generated method stub
		return null;
	}

}
