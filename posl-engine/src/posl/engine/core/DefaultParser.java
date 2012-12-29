/*
 * LineFactory.java
 *
 * Created on October 10, 2003, 5:33 PM
 */

package posl.engine.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Stack;
import java.util.logging.Logger;

import posl.engine.api.Container;
import posl.engine.api.Lexer;
import posl.engine.api.Parser;
import posl.engine.api.Statement;
import posl.engine.error.PoslException;
import posl.engine.type.SingleStatement;

/**
 * 
 * 
 * @author je bailey
 * 
 */
public class DefaultParser implements Parser {

	private Lexer lexer;
	
	private static Logger log = Logger.getLogger(DefaultParser.class.getName());

	private Stack<Container> statements = new Stack<Container>();

	private Container statement;
	
	private Stack<Character> charStack = new Stack<Character>();

	public DefaultParser() {
		lexer = new DefaultLexer();
		statement = new SingleStatement();
	}
	

	@Override
	public void process(InputStream is) throws PoslException {
		lexer.tokenize(is);
		while (lexer.hasNext()) {			
			statement = lexer.next().consume(statement, statements, charStack);
		}
	}
	
	/* (non-Javadoc)
	 * @see po.IParser#process(java.lang.String)
	 */
	@Override
	public void process(String string) throws PoslException {
		process(new ByteArrayInputStream(string.getBytes()));
	}

	/* (non-Javadoc)
	 * @see po.IParser#getStatement()
	 */
	@Override
	public SingleStatement next() {
		return (SingleStatement)statements.remove(0);
	}

	/* (non-Javadoc)
	 * @see po.IParser#hasMore()
	 */
	@Override
	public boolean hasNext() {
		return !statements.isEmpty();
	}
	
	/* (non-Javadoc)
	 * @see po.IParser#complete()
	 */
	@Override
	public boolean complete() {
		return charStack.isEmpty();
	}


	@Override
	public void remove() {
		// TODO Auto-generated method stub
		// not implemented 
	}
}
