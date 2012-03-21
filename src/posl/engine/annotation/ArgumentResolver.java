package posl.engine.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import posl.engine.api.IArgumentHandler;

@Retention(RetentionPolicy.RUNTIME)
public @interface ArgumentResolver {
	Class<? extends IArgumentHandler> value();
}
