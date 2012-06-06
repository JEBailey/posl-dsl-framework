package posl.engine.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import posl.engine.api.AArgumentHandler;

@Retention(RetentionPolicy.RUNTIME)
public @interface ArgumentResolver {
	Class<? extends AArgumentHandler> value();
}
