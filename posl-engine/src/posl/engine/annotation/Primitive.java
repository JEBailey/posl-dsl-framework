package posl.engine.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * 
 * 
 * @author bailey
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Primitive {
	String value();
}
