/*
 * Reference.java
 *
 * Created on September 26, 2003, 9:16 AM
 */

package posl.engine.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import posl.engine.Interpreter;
import posl.engine.error.PoslException;
import posl.engine.type.Atom;
import posl.engine.type.Reference;
import posl.engine.type.SingleStatement;

/**
 * 
 * @author JE Bailey
 */
public class Scope {

	/**
	 * parent scope
	 */
	private Scope enclosingScope;
	
	/**
	 * objects existing in the current scope
	 */
	private Map<String,Object>content;

	// private static Logger log = Logger.getLogger(Scope.class.getName());

	public Scope() {
		content = new ConcurrentHashMap<String,Object>();
	}

	private Scope(Scope scope) {
		this();
		enclosingScope = scope;
	}
	
	/**
	 * Wrapper class over the default String key, Object value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Object put(Atom key, Object value) {
		return put(key.toString(), value);
	}

	/**
	 * Place the key value mapping into the backing
	 * Map
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Object put(String key, Object value) {
		return content.put(key, value);
	}
	
	public Scope getRootScope(){
		if (enclosingScope != null){
			return enclosingScope.getRootScope();
		}
		return this;
	}

	public Object update(Atom key, Object value) throws Exception {
		return update(key.toString(), value);
	}


	public Scope createChildScope() {
		return new Scope(this);
	}
	

	/**
	 * Returns the associated object or the
	 * @throws PoslException 
	 * 
	 */
	public Object getValue(final Object object) throws PoslException {
		Object response = object;
		if (response instanceof Atom){
			response = get(object.toString());
			if (response == null){//TODO
				throw new PoslException(-1,"Null reference for "+object.toString());
			}
		}
	    if (response instanceof SingleStatement){
			response = Interpreter.process(this,(SingleStatement)object);
		}
		return response;
	}

	/*
	 * Take a object and if it's in the system map, converts it, or returns the
	 * string value
	 */
	public String getString(Object object) throws PoslException {
		return getValue(object).toString();
	}

	/**
	 * Possible Types that are being inputed
	 * Atom - return the object that the Atom represents
	 * 
	 * 
	 * 
	 * @param requestedType
	 * @param object
	 * @return
	 * @throws PoslException
	 */
	public Object get(Type requestedType, Object object) throws PoslException {
		if (requestedType instanceof ParameterizedType){
			return get(((ParameterizedType) requestedType).getRawType(),object);
		}
		
		if (requestedType == Reference.class){
			return new Reference(object,this);
		}
		
		final Class<? extends Object> klass = object.getClass();
		
		if (klass == SingleStatement.class){
			return get(requestedType,Interpreter.process(this,(SingleStatement)object));
		}
		
		if (klass == Atom.class) {
			if (requestedType != Atom.class){
				return get(requestedType,this.getValue(object));
			}
		}
		
		if (((Class<?>)requestedType).isAssignableFrom(klass)){
			return object;
		}
		
		System.out.println("request type is "+requestedType.toString()+" object is "+object);
		return null;
	}


	public boolean containsKey(Object key) {
		boolean result = content.containsKey(key);
		if (!result){
			if (enclosingScope != null){
				return enclosingScope.containsKey(key);
			}
		}
		return result;
	}

	public boolean containsValue(Object value) {
		boolean result = content.containsValue(value);
		if (!result){
			if (enclosingScope != null){
				return enclosingScope.containsValue(value);
			}
		}
		return result;
	}

	public Object get(String key) {
		Object object = content.get(key);
		if (object == null) {
			if (enclosingScope != null){
				object = enclosingScope.get(key);				
			}
		}
		return object;
	}

	public Object remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object update(String key,Object value)  {
		if (content.containsKey(key)){
			put(key,value);
		} else {
			if (enclosingScope != null){
				enclosingScope.update(key, value);				
			} else {
				throw new RuntimeException("unable to update value that's not defined");
			}
		}
		return value;
	}

	public Set<String> keySet() {
		return content.keySet();
	}

}
