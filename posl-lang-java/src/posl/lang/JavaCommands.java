package posl.lang;


import java.lang.reflect.Proxy;
import java.util.ArrayList;

import posl.engine.Interpreter;
import posl.engine.annotation.ArgumentResolver;
import posl.engine.annotation.Command;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.resolvers.Classic;
import posl.engine.resolvers.ScopeDefault;
import posl.engine.type.Atom;
import posl.engine.type.MultiLineStatement;
import posl.engine.type.PList;
import posl.engine.type.Statement;
import posl.java.Java;
import posl.java.JavaInvocationHandler;

public class JavaCommands {

	
	@Command("java")
	@ArgumentResolver(Classic.class)
	public static Object ceateJava(Scope scope, Statement statement)
			throws PoslException, ClassNotFoundException {
		if(scope.containsKey(statement.get(1).toString())){
			ArrayList<Object> derivedArgs = new ArrayList<Object>();
			derivedArgs.add(statement.get(0));
			derivedArgs.add(scope.get(statement.get(1).toString()));
			Statement newArgs = new Statement(derivedArgs);
			return new Java(scope, newArgs);
		}
		return new Java(scope, statement);
	}
	
	@Command("importJava")
	@ArgumentResolver(Classic.class)
	public static void alias(Scope scope, Statement args) throws Exception {
		String []pathElements = args.get(1).toString().split("[.]");
		String className = pathElements[pathElements.length - 1];
		scope.put(className, new Atom(args.get(1).toString()));
	}
	
	@Command("proxy")
	@ArgumentResolver(ScopeDefault.class)
	public static Object proxy(final Scope scope, PList klassNames, MultiLineStatement statement)
			throws Exception, PoslException {
		Scope child = scope.createChildScope();
		Interpreter.processList(child, statement);
		Object proxy = null;
		Class<?>[] klasses = new Class[klassNames.size()];
		int index = 0;
		for (Object object : klassNames) {
			if (!(object instanceof String)) {
				return new Error("expected identifier, found "
						+ object.toString() + "\n");
			}
			klasses[index++] = Class.forName((String)object);
		}
		proxy = Proxy.newProxyInstance(JavaCommands.class.getClassLoader(),
				klasses,new JavaInvocationHandler(child));
		return proxy;
	}

}
