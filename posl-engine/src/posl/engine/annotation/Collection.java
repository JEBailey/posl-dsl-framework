package posl.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * When annotating a methods parameters, you can optionally identify the last
 * parameter with a Collection annotation and all additional arguments passed
 * into the method from the script implementation will be passed into a
 * collection of the listed type.
 * 
 * @author je bailey
 * 
 */
@Target(ElementType.PARAMETER)
public @interface Collection {
}
