package posl.engine.api;

public interface Assembler {
	
	boolean add(Object token);

	boolean addEol();
	
	Object get();
}
