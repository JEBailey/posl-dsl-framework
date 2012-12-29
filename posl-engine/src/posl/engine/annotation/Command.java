package posl.engine.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * Command is used to identify a method as an executable from the script
 * The value passed to Command is the value used to call this method
 * from the script.
 * 
 * @author je bailey
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	String value();
}
