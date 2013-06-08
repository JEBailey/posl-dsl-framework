package posl.engine.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * Command is used to identify a method that will be
 * wrapped by a MethodProxy executable
 * 
 * @author je bailey
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	String value();
}
