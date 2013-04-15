package posl.lang;

import java.util.List;

import posl.engine.annotation.Command;
import posl.engine.core.Scope;
import posl.engine.type.MultiLineStatement;
import posl.engine.type.Reference;
import posl.lang.executable.Function;

public class CoreFunction {
	
	// executable creation commands
	@Command("function")
	public static Object run(Scope scope, Reference functionName, List<Object> argList,
			MultiLineStatement functionBody) {
		functionName.put(new Function(argList, functionBody, scope));
		return functionName.getValue();
	}


}
