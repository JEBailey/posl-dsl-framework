package posl.engine.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to define a method that will be executed once,
 * the result of that method will be placed as the value 
 * for the provided key.
 * 
 * 
 * @author bailey
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Primitive {
	String value();
}
