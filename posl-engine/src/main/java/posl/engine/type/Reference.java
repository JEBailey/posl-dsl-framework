package posl.engine.type;

import posl.engine.Interpreter;
import posl.engine.api.StatementProvider;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;

/**
 * Represents the relationship between an Object and it's un-evaluated value.
 * This provides access to an item without having it evaluated.
 * 
 * 
 * @author je bailey
 */
public class Reference {

	
	private Object key;

	private Scope scope;

	public Reference(Object key, Scope scope) {
		this.key = key;
		this.scope = scope;
	}

	public Object getKey() {
		return key;
	}

	@SuppressWarnings("unchecked")
	public <R> R getValue(Class<R> klass) throws PoslException {
		return (R) scope.get(klass, key);
	}

	public Object getValue() {
		return scope.get(key.toString());
	}

	public void setValue(Object value) {
		this.scope.put(key.toString(), value);
	}
			
	/**
	 * Inserts a new value into the original Scope for the key that this
	 * reference is associated with
	 * 
	 * @param value
	 * @return inserted value
	 */
	public Object updateValue(Object value) {
		return this.scope.update(key.toString(), value);
	}

	/**
	 * Inserts a new value into the current Scope for the key that this
	 * reference is associated with. If the original key is associated with a
	 * parent Scope then this will create a new key value pair in the currently
	 * active Scope
	 * 
	 * @param value
	 * @return inserted value
	 */
	public Object put(Object value) {
		return scope.put(key.toString(), value);
	}

	public void createChildScope() {
		this.scope = this.scope.createChildScope();
	}

	public Object evaluate() throws PoslException {
		Object result = scope.getValue(key);
		if (result instanceof StatementProvider) {
			return Interpreter.process(scope, (StatementProvider) result);
		}
		return result;
	}

}
