package posl.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * When annotating a method parameter, you can optionally identify the last
 * parameter with a Collection annotation and all additional arguments passed
 * into the method from the script will be passed into a
 * collection of the listed type.
 * 
 * @author je bailey
 * 
 */
@Target(ElementType.PARAMETER)
public @interface Collection {
}
