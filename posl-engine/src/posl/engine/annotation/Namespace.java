package posl.engine.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Namespace provides a subcontext of stateful data
 * that may be passed and used to perform executions
 * 
 * 
 * @author je bailey
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Namespace {
	String value();
}
