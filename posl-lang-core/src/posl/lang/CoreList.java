package posl.lang;

import java.util.List;

import posl.engine.annotation.ArgumentResolver;
import posl.engine.annotation.Command;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.resolvers.ScopeDefault;
import posl.engine.type.PList;


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
	public static PList push(List<?> list, Object token) throws Exception {
		PList reply = new PList(list);
		reply.push(token);
		return reply;
	}
	
	
	
	@Command("append")
	public static List<?> append(PList list, Object token) throws Exception {
		list = new PList(list);
		list.addLast(token);
		return list;
	}
	
	@Command("rest")
	public static  List<?> rest(PList list) throws Exception {
		return list.subList(1, list.size());
	}
	
	@Command("tail")
	public static Object tail(List<?> list) throws Exception {
		return list.get(list.size() - 1);
	}
	
	@Command("head")
	public static Object head(PList list) throws Exception {
		return list.peek();
	}
	
	@Command("cat")
	@ArgumentResolver(ScopeDefault.class)
	public static Object cat(Scope scope, PList list, Object key) throws PoslException {
		list = new PList(list);
		list.push(scope.get(Object.class,key));
		return list;
	}
	
	@Command("nth")
	public static Object nth(List<?> list, Number n){
		return list.get(n.intValue());
	}
}
