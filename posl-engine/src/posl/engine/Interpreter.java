/*
 * Interpertor.java
 *
 * Created on April 14, 2003, 8:03 PM
 */

package posl.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import posl.engine.api.IExecutable;
import posl.engine.api.IParser;
import posl.engine.core.Context;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.MultiLineStatement;
import posl.engine.type.Statement;

/**
 *
 */

/**
 * 
 * @author jason bailey
 */
public class Interpreter {

	/**
	 * Generates an interpreter with a blank scope.
	 */
	private Interpreter() {
	}

	/**
	 * 
	 * 
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws PoslException
	 */
	public static Object process(Context context, File file)
			throws FileNotFoundException, PoslException {
		return process(context, new FileInputStream(file));
	}

	/**
	 * 
	 * 
	 * @param stream
	 * @return
	 * @throws PoslException
	 */
	public static Object process(Context context, InputStream stream)
			throws PoslException {
		
		IParser parser = context.getParser();
		Scope scope = context.getScope();

		parser.process(stream);
		Object result = null;
		while (parser.hasNext()) {
			Statement statement = parser.next();
			result = process(scope, statement);
		}
		return result;
	}

	/**
	 * 
	 * 
	 * @param string
	 * @return
	 * @throws PoslException
	 */
	public static Object process(Context context, String string)
			throws PoslException {
		IParser parser = context.getParser();
		Scope scope = context.getScope();
		Object result = null;
		parser.process(string);
		while (parser.hasNext()) {
			result = process(scope, parser.next());
		}
		return result;
	}

	/**
	 * 
	 * 
	 * @param scope
	 * @param statements
	 * @return
	 * @throws PoslException
	 */
	public static Object processList(Scope scope, MultiLineStatement statements) throws PoslException {
		Object result = null;
		for (Statement statement : statements) {
			result = process(scope, statement);
		}
		return result;
	}

	/**
	 * 
	 * 
	 * @param scope
	 * @param statement
	 * @return
	 * @throws PoslException
	 */
	public static Object process(Scope scope, Statement statement) throws PoslException {
		Object token  = scope.getValue(statement.get(0));
		if (token instanceof IExecutable) {
			token = ((IExecutable) token).execute(scope, statement);
		}
		return token;

	}

}
