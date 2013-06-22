package posl.engine.type;

import java.util.LinkedList;
import java.util.List;

import posl.engine.api.Collector;

/**
 * Converts the set of tokens into a List object
 * 
 * @author je bailey
 */
public class ListContainer implements Collector {

	private List<Object>content;

	public ListContainer() {
		content = new LinkedList<Object>();
	}

	
	public boolean add(Object object){
		return content.add(object);
	}


	@Override
	public boolean finish() {
		return false;
	}

	@Override
	public Object get() {
		return content;
	}

}
