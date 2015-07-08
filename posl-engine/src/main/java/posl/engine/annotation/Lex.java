package posl.engine.annotation;

public @interface Lex {
	Class<?> token();
	
	

	int group();
	
	boolean captureEOLs();
}
