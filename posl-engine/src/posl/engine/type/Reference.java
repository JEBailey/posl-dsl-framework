package posl.engine.type;

import java.util.List;

import posl.engine.Interpreter;
import posl.engine.api.Statement;
import posl.engine.api.StatementVisitor;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
/**
 * Reference class provides an encapsulated view of an identifier(atom) and it's unevaluated
 * value. This provides a method of lazy evaluation. 
 * 
 * 
 * @author jbailey
 */
public class Reference implements StatementVisitor {
	
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
		if (result instanceof Statement){
			return ((Statement)result).accept(this);
		}
		return result;
	}

	@Override
	public Object visit(SingleStatement statement) {
		try {
			return Interpreter.process(scope, statement);
		} catch (PoslException e){
			return e;
		}
		
	}

	@Override
	public Object visit(List<SingleStatement> statements) {
		try {
			return Interpreter.process(scope, statements);
		} catch (PoslException e){
			return e;
		}
		
	}


}
