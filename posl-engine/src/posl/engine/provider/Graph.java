package posl.engine.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Graph<T> {

	private Map<T, List<T>> edgesTo = new HashMap<T, List<T>>();
	private Map<T, List<T>> edgesFrom = new HashMap<T, List<T>>();
	
	public Graph() {
		super();
	}

	/**
	 * adds an association for target that comes from source
	 * 
	 * 
	 * @param target
	 * @param origin
	 */
	public void addDependency(T target, T origin) {
		getEdgesTo(target).add(origin);
		getEdgesFrom(origin).add(target);
	}

	private List<T> getEdgesTo(T target) {
		List<T> list = edgesTo.get(target);
		if (list == null) {
			list = new ArrayList<T>();
			edgesTo.put(target, list);
		}
		return list;
	}

	private List<T> getEdgesFrom(T source) {
		List<T> list = edgesFrom.get(source);
		if (list == null) {
			list = new ArrayList<T>();
			edgesFrom.put(source, list);
		}
		return list;
	}

	public List<T> getList(T startNode) {
		List<T> reply = new ArrayList<T>();
		buildList(reply, startNode);
		if (reply.size() < edgesTo.size()) {
			throw new RuntimeException("cyclic dependency occured");
		}
		return reply;
	}

	/**
	 * if the node has no more incoming edges, add it to the list
	 * 
	 * @param list
	 * @param node
	 */
	private void buildList(List<T> list, T node) {
		if (getEdgesTo(node).isEmpty()) {
			list.add(node);
			//iterator is needed here so that we can safely
			//remove the iterated node from the underlying list
			Iterator<T> it = getEdgesFrom(node).iterator();
			while(it.hasNext()){
				T target = it.next();
				it.remove();
				getEdgesTo(target).remove(node);
				buildList(list, target);
			}
		}
	}

}
