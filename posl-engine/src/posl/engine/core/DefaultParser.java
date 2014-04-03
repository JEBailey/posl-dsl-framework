/*
 * LineFactory.java
 *
 * Created on October 10, 2003, 5:33 PM
 */

package posl.engine.core;

import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

import posl.engine.api.Collector;
import posl.engine.api.Lexeme;
import posl.engine.api.Lexer;
import posl.engine.api.Parser;
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

	private Stack<Collector> statements = new Stack<Collector>();

	private Collector statement;
	
	private Stack<Character> charStack = new Stack<Character>();


	public DefaultParser() {
		lexer = new DefaultLexer();
		statement = new SingleStatement(0);
	}
	

	@Override
	public void process(CharSequence is, List<Lexeme> lexemes) throws PoslException {
		lexer.tokenize(is, lexemes);
		while (lexer.hasNext()) {			
			statement = lexer.next().consume(statement, statements, charStack);
		}
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
		log.warning("remove not supported");
	}
}
