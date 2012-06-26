package posl.engine.type;

import posl.engine.Interpreter;
import posl.engine.api.IStatement;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
/**
 * Reference class provides an encapsulated view of a word(atom) and it's representation
 * The purpose of this is to provide an encapsulated method of retrieving a value without
 * having to do multiple argument resolvers
 * 
 * 
 * @author jbailey
 *
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
	
	public Object evaluate() throws PoslException{
		Object result = scope.getValue(key);
		if (result instanceof IStatement){
			if (((IStatement)result).isMultiLine()){
				return Interpreter.processList(scope, (MultiLineStatement)result);
			} else {
				return Interpreter.process(scope, (Statement)result);
			}
		}
		return result;
	}


}
