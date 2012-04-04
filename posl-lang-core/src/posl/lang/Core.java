package posl.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import posl.engine.Interpreter;
import posl.engine.annotation.ArgumentResolver;
import posl.engine.annotation.Command;
import posl.engine.annotation.Optional;
import posl.engine.annotation.Primitive;
import posl.engine.core.Context;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.resolvers.Classic;
import posl.engine.resolvers.ScopeDefault;
import posl.engine.type.Atom;
import posl.engine.type.MultiLineStatement;
import posl.engine.type.Reference;
import posl.engine.type.Statement;
import posl.lang.executable.NamespaceExec;

public class Core {

	// name space commands
	@Command("new")
	@ArgumentResolver(ScopeDefault.class)
	public static Object newX(Scope scope,MultiLineStatement statementBlock)
			throws PoslException {
		return new NamespaceExec(scope.getRootScope(),statementBlock);
	}

	@Command("eval")
	public static Object eval(Reference statement) throws PoslException {
		statement.createChildScope();
		return statement.evaluate();
	}
	

	// flow control
	@Command("if")
	public static Object ifCommand(Boolean predicate,
			Reference commands,
			@Optional Reference elseCommands) throws PoslException {
		Object result = Boolean.FALSE;
		if (predicate) {
			result = commands.evaluate();
		} else {
			if (elseCommands != null) {//since it's optional
				result = elseCommands.evaluate();
			}
		}
		return result;
	}

	/**
	 * 
	 * The bang command allows us to return a command token as a normal return
	 * value. It is similar to an escape in a string, it returns the next token.
	 * 
	 * @param scope
	 * @param args
	 * @return
	 * @throws PoslException
	 */
	@Command("!")
	public static Object returnCommand(Reference reference)
			throws PoslException {
		return reference.getValue(Object.class);
	}

	@Command("while")
	public static Object whileC(Reference predicate, Reference commands) throws PoslException {
		Object result = null;
		predicate.createChildScope();
		while (predicate.getValue(Boolean.class)) {
			result = commands.evaluate();
			if (result instanceof PoslException){
				break;
			}
		}
		return result;
	}

	@Command("until")
	public static Object until(Reference predicate,
			Reference commands) throws PoslException {
		Object result = null;
		predicate.createChildScope();
		while (!predicate.getValue(Boolean.class)) {
			result = commands.evaluate();
		}
		return result;
	}

	@Command("println")
	@ArgumentResolver(Classic.class)
	public static Object println(Scope scope, Statement args) throws PoslException, IOException {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < args.size(); ++i) {
			sb.append(scope.getString(args.get(i)));
		}
		sb.append('\n');
		String response = sb.toString();
		((OutputStream)scope.getValue(new Atom("__outputstream"))).write(response.getBytes());
		return response;
	}
	
	@Command("print")
	@ArgumentResolver(Classic.class)
	public static Object print(Scope scope, Statement args) throws PoslException, IOException {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < args.size(); ++i) {
			sb.append(scope.getString(args.get(i)));
		}
		String response = sb.toString();
		((OutputStream)scope.getValue("__outputstream")).write(response.getBytes());
		return response;
	}
	
	@Primitive("true")
	public static Boolean isTrue(){
		return Boolean.TRUE;
	}
	
	@Primitive("false")
	public static Boolean isFalse(){
		return Boolean.FALSE;
	}

	// logic
	@Command("and")
	public static Object and(Boolean left, Boolean right) throws Exception {
		return left & right;
	}

	@Command("not")
	public static Object not(Boolean value) {
		return !value;
	}

	@Command("or")
	public static Object or(Boolean left, Reference right)
			throws PoslException {
		if (!left) {
			return right.getValue(Boolean.class);
		}
		return Boolean.FALSE;
	}

	// core
	@Command("import")
	@ArgumentResolver(Classic.class)
	public static Object importC(Scope scope, Statement args) throws PoslException {
		String file = scope.getString(args.get(1));
		InputStream is = ClassLoader.getSystemClassLoader().getClass()
				.getResourceAsStream(file + ".po");
		return Interpreter.process(new Context(scope),is);
	}

	@Command("readLine")
	public static String readLine() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String line = reader.readLine();
		reader.close();
		return line;
	}

	@Command("str")
	@ArgumentResolver(Classic.class)
	public static String str(Scope scope, Statement args) throws PoslException {
		return scope.getValue(args.get(1)).toString();
	}



}
