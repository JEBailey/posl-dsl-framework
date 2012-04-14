package posl.lang;

import posl.engine.annotation.Command;
import posl.engine.type.Reference;

public class Var {

	@Command("let")
	public static Object let(Reference key, Object value) throws Exception {
		return key.updateValue(value);
	}
	
	@Command("set")
	public static Object set(Reference key, Object value) throws Exception {
		Object prior = key.put(value);
		if (prior != null){
			key.put(prior);
			throw new Exception("value already defined");
		}
		return value;
	}

}
