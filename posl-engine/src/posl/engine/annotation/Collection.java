package posl.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Collects the remaining parameters into the given collection
 * 
 * 
 * @author je bailey
 *
 */
@Target(ElementType.PARAMETER)
public @interface Collection {
}
