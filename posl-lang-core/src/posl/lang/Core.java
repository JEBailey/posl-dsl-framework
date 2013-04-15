package posl.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import posl.engine.Interpreter;
import posl.engine.annotation.Collection;
import posl.engine.annotation.Command;
import posl.engine.annotation.Property;
import posl.engine.annotation.Optional;
import posl.engine.annotation.Primitive;
import posl.engine.core.Context;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.MultiLineStatement;
import posl.engine.type.Reference;
import posl.lang.executable.NamespaceExec;

public class Core {

	// name space commands
	@Command("new")
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
	public static Object println(@Property("__outputstream") OutputStream out, @Collection List<String> args) throws PoslException, IOException {
		StringBuilder sb = new StringBuilder();
		for (String string:args) {
			sb.append(string);
			out.write(string.getBytes());
		}
		sb.append('\n');
		out.write('\n');
		return sb.toString();
	}
	
	@Command("print")
	public static Object print(@Property("__outputstream") OutputStream out, @Collection List<String> args) throws PoslException, IOException {
		StringBuilder sb = new StringBuilder();
		for (String string:args) {
			sb.append(string);
		}
		String response = sb.toString();
		out.write(response.getBytes());
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
	public static Object importC(Scope scope, String file) throws PoslException {
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
	public static String str(Reference string) throws PoslException {
		return string.getValue(String.class);
	}



}
