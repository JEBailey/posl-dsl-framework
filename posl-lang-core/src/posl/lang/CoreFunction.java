package posl.lang;

import posl.engine.annotation.Command;
import posl.engine.core.Scope;
import posl.engine.type.Atom;
import posl.engine.type.MultiLineStatement;
import posl.engine.type.PList;
import posl.lang.executable.Function;

public class CoreFunction {
	

	// executable creation commands
	@Command("function")
	public static Object run(Scope scope, Atom functionName, PList argList,
			MultiLineStatement functionBody) {
		for (Object object : argList) {
			if (!(object instanceof Atom)) {
				return new Error("expected identifier, found "
						+ object.toString() + "\n");
			}
		}
		Function func = new Function(argList, functionBody, scope);
		scope.put(functionName, func);
		return func;
	}


}
