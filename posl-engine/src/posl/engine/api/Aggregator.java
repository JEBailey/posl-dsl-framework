package posl.engine.api;

public interface Aggregator {
	
	boolean add(Object token);

	boolean addEol();
	
	Object get();
}
