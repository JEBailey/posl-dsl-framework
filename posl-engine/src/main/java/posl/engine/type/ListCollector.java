package posl.engine.type;

import java.util.LinkedList;
import java.util.List;

import posl.engine.api.Collector;

/**
 * Collects objects into a <code>LinkedList</code>
 * 
 * @author je bailey
 */
public class ListCollector implements Collector {

	private List<Object>content;

	public ListCollector() {
		content = new LinkedList<Object>();
	}

	
	@Override
	public boolean add(Object object){
		return content.add(object);
	}


	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public Object get() {
		return content;
	}


	@Override
	public void addEOL() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getLineNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

}
