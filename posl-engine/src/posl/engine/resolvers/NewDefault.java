package posl.engine.resolvers;

import java.lang.annotation.Annotation;

import posl.engine.annotation.Optional;
import posl.engine.api.AArgumentHandler;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public class NewDefault extends AArgumentHandler {

	@Override
	public Object[] render(Scope scope, Statement tokens) throws PoslException {
		Object[] arguments = new Object[info.length];
		for (int i = 0; i < info.length;){
			try {
				arguments[i] = scope.get(params[i],tokens.get(i+1));
			} catch (IndexOutOfBoundsException ioobe){
				loop : {
					for (Annotation annotation:annotations[i]){
						if (annotation instanceof Optional){
						    break loop;	
						}
					}
					throw new PoslException(tokens.getLineNumber(),"incorrect number of arguments");
				}
			}
		}
		return arguments;
	}

}
