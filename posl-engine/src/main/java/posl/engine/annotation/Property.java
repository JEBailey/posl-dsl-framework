package posl.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The parameter will be populated from the context rather than a value passed
 * in from the script. The key for the Scope is the name of the parameter or the
 * value given in the parameter.
 * 
 * @author jebailey
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Property {
	String value();
}
