package posl.lang;

import java.util.LinkedList;
import java.util.List;

import posl.engine.annotation.Command;
import posl.engine.error.PoslException;
import posl.engine.type.Reference;


public class CoreList {

	@Command("empty?")
	public static Boolean empty(List<?> list) throws Exception {
		return list.isEmpty();
	}

	
	@Command("pop")
	public static Object pop(List<?> list) throws Exception {
		return list.remove(0);
	}
	
	@Command("push")
	public static List<Object> push(List<?> list, Object token) throws Exception {
		List<Object> reply = new LinkedList<Object>(list);
		reply.add(0, token);
		return reply;
	}
	
	
	
	@Command("append")
	public static List<?> append(List<Object> list, Object token) throws Exception {
		list = new LinkedList<Object>(list);
		list.add(token);
		return list;
	}
	
	@Command("rest")
	public static  List<?> rest(List<Object> list) throws Exception {
		return list.subList(1, list.size());
	}
	
	@Command("tail")
	public static Object tail(List<?> list) throws Exception {
		return list.get(list.size() - 1);
	}
	
	@Command("head")
	public static Object head(List<?> list) throws Exception {
		return list.get(0);
	}
	
	@Command("cat")
	public static Object cat(List<Object> list, Reference key) throws PoslException {
		list = new LinkedList<Object>(list);
		list.add(0,key);
		return list;
	}
	
	@Command("nth")
	public static Object nth(List<?> list, Number n){
		return list.get(n.intValue());
	}
}
