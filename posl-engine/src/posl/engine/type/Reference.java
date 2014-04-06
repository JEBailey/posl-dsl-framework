package posl.engine.type;

import posl.engine.Interpreter;
import posl.engine.api.StatementProvider;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
/**
 * Represents the relationship between an Object and it's un-evaluated value.
 * This provides a method of lazy evaluation. 
 * 
 * 
 * @author jbailey
 */
public class Reference {
	
	private Object key;
	
	private Scope scope;

	public Reference(Object key, Scope scope) {
		this.key = key;
		this.scope = scope;
	}
	
	public Object getKey(){
		return key;
	}
	
	@SuppressWarnings("unchecked")
	public <R>R getValue(Class<R> klass) throws PoslException{
		return (R)scope.get(klass, key);
	}
	
	public Object getValue() {
		return scope.get(key.toString());
	}
	
	public void setValue(Object value){
		this.scope.put(key.toString(), value);
	}
	
	public Object updateValue(Object value) {
		return this.scope.update(key.toString(), value);
	}
	
	public Object put(Object value){
		return scope.put(key.toString(), value);
	}
	
	public void createChildScope(){
		this.scope = this.scope.createChildScope();
	}
	
	public Object evaluate() throws PoslException {
		Object result = scope.getValue(key);
		if (result instanceof StatementProvider){
			return Interpreter.process(scope, (StatementProvider)result);
		}
		return result;
	}

}
