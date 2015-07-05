package posl.engine.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used at a class level to provide a named mapping
 * for the internally identified Commands
 * 
 * A class with a namespace of "roger" and a method annotated with the Command of "wilco"
 * could access the command in this way
 * 
 * roger.wilco
 * 
 * @author je bailey
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Namespace {
	String value();
}
