package posl.engine.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import posl.engine.annotation.Property;
import posl.engine.annotation.Optional;
import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public class ParameterInfo {

	public enum State {
		NORMAL, OPTIONAL, CONTEXT_PROPERTY, SCOPE, COLLECTION
	};

	private Type type;

	private State state = State.NORMAL;

	// represents the amount that we should update the command line index
	private int increment = 1;

	private Object parameter;

	public ParameterInfo(Type param, Annotation[] annotations) {
		this.type = param;
		for (Annotation annotation : annotations) {
			if (annotation instanceof Optional) {
				state = State.OPTIONAL;
			} else if (annotation instanceof Property) {
				increment = 0;
				state = State.CONTEXT_PROPERTY;
				this.parameter = ((Property) annotation).value();
			}
		}
		if (param == Scope.class) {
			state = State.SCOPE;
			increment = 0;
		}
	}

	public int incr() {
		return increment;
	}

	public boolean isOptional() {
		return state == State.OPTIONAL;
	}

	public Object render(Scope scope, Statement statement, int tokenIndex)
			throws PoslException {
		switch (state) {
		case NORMAL:
		case OPTIONAL:
			return scope.get(type, statement.get(tokenIndex));
		case CONTEXT_PROPERTY:
			return scope.get((String) parameter);
		case SCOPE:
			return scope;
			// Collection is a special case which takes a paramatized Collection
			// object and
			// adds all the remaining arguments into that collection
		case COLLECTION:
			try {
				Object list = type.getClass().newInstance();
				ParameterizedType parameterizedType = (ParameterizedType) type
						.getClass().getGenericSuperclass();
				Type generic = parameterizedType.getActualTypeArguments()[0];
				Method add = Collection.class.getDeclaredMethod("add",
						Object.class);
				for (int index = tokenIndex; index < statement.size(); ++index) {
					add.invoke(list, scope.get(generic, statement.get(index)));
				}
			} catch (Exception e) {
				throw new PoslException(statement.startLineNumber(),
						"failed to get COLLECTION");
			}

		}
		return null;
	}

}
