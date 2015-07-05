package posl.lang;

import posl.engine.annotation.Command;
import posl.engine.type.Reference;

public class Var {

	@Command("=")
	public static Object let(Reference key, Object value) throws Exception {
		return key.updateValue(value);
	}
	
	@Command("var")
	public static Object set(Reference key, Object value) throws Exception {
		Object prior = key.put(value);
		if (prior != null){
			key.put(prior);
			throw new Exception("value already defined");
		}
		return value;
	}

}
