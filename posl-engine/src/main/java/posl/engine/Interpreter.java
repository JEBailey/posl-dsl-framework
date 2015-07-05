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
import java.util.List;

import posl.engine.api.StatementProvider;
import posl.engine.api.StatementProviderVisitor;
import posl.engine.api.Executable;
import posl.engine.api.LexUtil;
import posl.engine.api.Parser;
import posl.engine.core.Context;
import posl.engine.core.Scope;
import posl.engine.error.PoslException;
import posl.engine.type.Statement;


/**
 * Initiates the process of turning a
 * string of text into an executable structure based
 * on information supplied in the Context
 * 
 * 
 * @author jason bailey
 */
public class Interpreter {

	private Interpreter() {
	}

	/**
	 * Processes the content of the file within the provided context
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
		Parser parser = context.getParser();
		Scope scope = context.getScope();
		String data = LexUtil.toString(stream);
		
		parser.process(data, context.lexemes);
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
		Parser parser = context.getParser();
		Scope scope = context.getScope();
		Object result = null;
		parser.process(string, context.lexemes);
		while (parser.hasNext()) {
			result = process(scope, parser.next());
		}
		return result;
	}


	
	/**
	 * 
	 * 
	 * @param scope provides the variables which the statement will be executed within
	 * @param statement
	 * @return
	 * @throws PoslException
	 */
	public static Object process(final Scope scope, StatementProvider statement) throws PoslException {
		return statement.accept(new StatementProviderVisitor() {
			
			@Override
			public Object evaluate(List<Statement> statements) throws PoslException {
				Object result = null;
				for (Statement statement : statements) {
					result = process(scope, statement);
				}
				return result;
			}
			
			@Override
			public Object evaluate(Statement statement) throws PoslException {
				Object token  = scope.getValue(statement.get(0));
				if (token instanceof Executable) {
					token = ((Executable) token).execute(scope, statement);
				} else if (statement.size() > 1){
					//token = getExecutable(token).execute(scope, statement);
				}
				return token;
			}
		});
	}

}
