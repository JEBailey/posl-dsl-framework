package posl.engine.api;

public interface Container {
	
	boolean add(Object token);

	boolean addEol();
	
	Object get();
}
