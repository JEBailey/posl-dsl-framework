package posl.engine.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Namespace is used at a class level to provide a named mapping
 * for the internally identified Commands
 * 
 * 
 * @author je bailey
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Namespace {
	String value();
}
