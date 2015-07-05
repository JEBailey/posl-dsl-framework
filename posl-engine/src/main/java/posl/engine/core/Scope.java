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
import posl.engine.api.StatementProvider;
import posl.engine.error.PoslException;
import posl.engine.type.Atom;
import posl.engine.type.Statement;

/**
 * Provides a wrapper around an enclosed Map which represents the items
 * available to the Interpreter at a particular level
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
	private Map<String, Object> content;

	// private static Logger log = Logger.getLogger(Scope.class.getName());

	public Scope() {
		content = new ConcurrentHashMap<String, Object>();
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
	 * Place the key value mapping into the backing Map
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Object put(String key, Object value) {
		return content.put(key, value);
	}

	public Scope getRootScope() {
		if (enclosingScope != null) {
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
	 * Returns the associated object or, if the object is a statement, evaluates
	 * the statement and returns the response
	 * 
	 * @param object
	 * @return
	 * @throws PoslException
	 */
	public Object getValue(final Object object) throws PoslException {
		Object response = object;
		if (response instanceof Atom) {
			response = get(object.toString());
			if (response == null) {// TODO
				throw new PoslException(-1, "Null reference for "
						+ object.toString());
			}
		}
		if (response instanceof Statement) {
			response = Interpreter.process(this, (StatementProvider) response);
		}
		return response;
	}

	/**
	 * Resolves an object and provides it's String representation
	 */
	public String getString(Object object) throws PoslException {
		return getValue(object).toString();
	}

	/**
	 * Possible Types that are being inputed Atom - return the object that the
	 * Atom represents
	 * 
	 * 
	 * 
	 * @param requestedType
	 * @param object
	 * @return
	 * @throws PoslException
	 */
	public Object get(Type requestedType, Object object) throws PoslException {
		if (requestedType instanceof ParameterizedType) {
			return get(((ParameterizedType) requestedType).getRawType(), object);
		}

		if (object instanceof Statement) {
			return get(requestedType,
					Interpreter.process(this, (Statement) object));
		}

		if (object instanceof Atom) {
			if (requestedType != Atom.class) {
				return get(requestedType, this.getValue(object));
			}
		}

		final Class<? extends Object> klass = object.getClass();
		if (((Class<?>) requestedType).isAssignableFrom(klass)) {
			return object;
		}

		System.out.println("request type is " + requestedType.toString()
				+ " object is " + object);
		return null;
	}

	/**
	 * Attempts to locate the key object in the hierarchy of Scopes. This is a
	 * very expensive operation
	 * 
	 * @param value
	 * @return true if key is located
	 */
	public boolean containsKey(Object key) {
		boolean result = content.containsKey(key);
		if (!result) {
			if (enclosingScope != null) {
				return enclosingScope.containsKey(key);
			}
		}
		return result;
	}

	/**
	 * Attempts to locate the value object in the hierarchy of Scopes. This is a
	 * very expensive operation
	 * 
	 * @param value
	 * @return true if value is located
	 */
	public boolean containsValue(Object value) {
		boolean result = content.containsValue(value);
		if (!result) {
			if (enclosingScope != null) {
				return enclosingScope.containsValue(value);
			}
		}
		return result;
	}

	/**
	 * Attempts to locate the key at the current Scope level, if the Key is not
	 * at this level, an attempt to locate the key in the parent scope will me
	 * made
	 * 
	 * @param key
	 *            value located in the internal Map
	 * @return associated value object or `null`
	 */
	public Object get(String key) {
		Object object = content.get(key);
		if (object == null) {
			if (enclosingScope != null) {
				object = enclosingScope.get(key);
			}
		}
		return object;
	}

	public Object remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object update(String key, Object value) {
		if (content.containsKey(key)) {
			put(key, value);
		} else {
			if (enclosingScope != null) {
				enclosingScope.update(key, value);
			} else {
				throw new RuntimeException(
						"unable to update value that's not defined");
			}
		}
		return value;
	}

	public Set<String> keySet() {
		return content.keySet();
	}

}
