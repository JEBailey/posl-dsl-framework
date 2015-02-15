package posl.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Identifies a method that will be
 * wrapped by a MethodProxy executable
 * 
 * @author je bailey
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) 
public @interface Command {
	String value();
}
