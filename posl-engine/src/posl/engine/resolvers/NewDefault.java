package posl.engine.resolvers;

import java.util.Arrays;

import posl.engine.api.AArgumentHandler;
import posl.engine.core.ParameterInfo;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public class NewDefault extends AArgumentHandler {

	/*
	 * So the key here is that we have a number of
	 * 
	 * (non-Javadoc)
	 * 
	 * @see posl.engine.api.AArgumentHandler#render(posl.engine.core.Scope,
	 * posl.engine.type.Statement)
	 */
	@Override
	public Object[] render(Scope scope, Statement tokens) throws PoslException {
		// This is the argument array that will be passed in the method call
		Object[] arguments = new Object[info.length];
		// we're going to loop through the parameter information
		// i is the index for the parameter information
		// t is the index for the passed in tokens.
		int tokenIndex = 1;
		int length = tokens.size() - 1; // length of tokens passed in
		for (int i = 0; i < info.length; ++i) {
			ParameterInfo param = info[i];
			// first check to see if we've ran out of arguments
			if (tokenIndex > length) {
				if (!param.isOptional()) {
					throw new PoslException(tokens.getLineNumber(),
							"incorrect number of arguments");
				}
			} else {
				// we have enough arguments. do we need them?
				arguments[i] = param.render(scope, tokens, tokenIndex);
			}
			// do we increment?
			tokenIndex += param.incr();
		}
		return arguments;
	}

}
