package posl.engine.resolvers;

import java.lang.annotation.Annotation;

import posl.engine.annotation.Optional;
import posl.engine.api.IArgumentHandler;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public class ScopeDefault extends IArgumentHandler {

	@Override
	public Object[] render(Scope scope, Statement tokens) throws PoslException {
		int index = 1; // var for tokens
		Object[] arguments = new Object[params.length];
		arguments[0] = scope;
		if(tokens.size() < requiredParameters()){ 
			throw new PoslException(tokens.getLineNumber(),"Wrong number of arguments");
		}
		for (int i = 1; i < tokens.size(); ++i){
			arguments[i] = scope.get(params[i],tokens.get(index++));
		}
		return arguments;
	}

	private int requiredParameters() {
		int required = 0;
		for(int i =0 ; i < annotations.length; i++){
			if(!isOptional(annotations[i])){
				required++;
			}
		}
		return required;
	}

	private boolean isOptional(Annotation[] annotations) {
		for(Annotation anno : annotations){
			if(anno instanceof Optional){
				return true;
			}
		}
		return false;
	}
	
	
}
