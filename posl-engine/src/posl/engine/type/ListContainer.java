package posl.engine.type;

import java.util.LinkedList;
import java.util.List;

import posl.engine.api.Aggregator;

public class ListContainer implements Aggregator {

	private List<Object>content;

	public ListContainer() {
		content = new LinkedList<Object>();
	}

	
	public boolean add(Object object){
		return content.add(object);
	}


	@Override
	public boolean addEol() {
		return false;
	}

	@Override
	public Object get() {
		return content;
	}

}
