/*
 * LineFactory.java
 *
 * Created on October 10, 2003, 5:33 PM
 */

package posl.engine.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import posl.engine.api.ILexer;
import posl.engine.api.IParser;
import posl.engine.api.IStatement;
import posl.engine.error.PoslException;
import posl.engine.token.Token;
import posl.engine.type.Atom;
import posl.engine.type.MultiLineStatement;
import posl.engine.type.PList;
import posl.engine.type.Statement;

/**
 * 
 * 
 * @author jason
 * 
 */
public class Parser implements IParser {

	private ILexer lexer;
	
	static Logger log = Logger.getLogger(Parser.class.getName());

	boolean eof = false;

	private Stack<IStatement> statements = new Stack<IStatement>();

	private IStatement statement;
	
	private Stack<Character> charStack = new Stack<Character>();

	private int lineNumber;
	/** Creates a new instance of LineFactory */
	public Parser() {
		lexer = new Lexer();
		lineNumber = 1;
		statement = new Statement(lineNumber);
	}
	
	/* (non-Javadoc)
	 * @see po.IParser#process(java.io.BufferedReader)
	 */
	@Override
	public void process(InputStream is) throws PoslException {
		lexer.tokenize(is);
		recursive();
	}
	
	/* (non-Javadoc)
	 * @see po.IParser#process(java.lang.String)
	 */
	@Override
	public void process(String string) throws PoslException {
		lexer.tokenize(new ByteArrayInputStream(string.getBytes()));
		recursive();
	}

	/* (non-Javadoc)
	 * @see po.IParser#getStatement()
	 */
	@Override
	public Statement getStatement() {
		return (Statement)statements.remove(0);
	}

	/* (non-Javadoc)
	 * @see po.IParser#hasMore()
	 */
	@Override
	public boolean hasMore() {
		return !statements.isEmpty();
	}
	
	/* (non-Javadoc)
	 * @see po.IParser#complete()
	 */
	@Override
	public boolean complete() {
		return charStack.isEmpty();
	}

	/*
	 * Note to self: '(' and ')' are lists. '{' and '}' are
	 * lazy evaluating block of code '[' and ']' are single line statements that are to be
	 * evaluated immediately
	 */

	private void recursive() throws PoslException {
		while (lexer.hasMore()) {
			retrieveLine();
		}
	}

	/*
	 * This is where most of the magic happens, the posl grammar follows a simple
	 * syntax that consists of a series of statements. A statement consists of a
	 * command and then a list of arguments.
	 *  
	 */
	private void retrieveLine() throws PoslException {
		while (lexer.hasMore()) {
			Token token = lexer.next();
			
			if (log.isLoggable(Level.FINE)) {
				log.fine("receiving token " + token);
			}
			
			switch (token.type) {
			case GRAMMAR:
				switch (token.getCharValue()) {
				case '[':
					charStack.push(']');
					statements.push(statement);
					statement = new Statement(lineNumber);
					break;
				case '(':
					charStack.push(')');
					statements.push(statement);
					statement = new PList();
					break;
				case '{':
					charStack.push('}');
					statements.push(statement);
					statement = new MultiLineStatement(lineNumber);
					break;
				case ')':
					if (!charStack.empty() && charStack.pop() == token.getCharValue()){
						Object temp = statement;
						statement = statements.pop();
						statement.addObject(temp);
					} else {
						throw new PoslException(lineNumber,"could not match parenthesis");
					}
					break;
				case ']':
					if (!charStack.empty() && charStack.pop() == token.getCharValue()){
						Object temp = statement;
						statement = statements.pop();
						statement.addObject(temp);
					} else {
						throw new PoslException(lineNumber,"could not match square bracket");
					}
					break;
				case '}':
					if (!charStack.empty() && charStack.pop() == token.getCharValue()){
						statement.addEol(lineNumber);
						Object temp = statement;
						statement = statements.pop();
						statement.addObject(temp);
					} else {
						throw new PoslException(lineNumber,"could not match brace");
					}
					break;
				}
				break;
			case EOL:
				++lineNumber;
				if (!statement.isMultiLine()){
					if (statement.notEmpty()){
						statements.add((Statement)statement);
					}
					statement = new Statement(lineNumber);
				} else {
					statement.addEol(lineNumber);
				}
				break;
			case NUMBER:
				try {
					statement.addObject(NumberFormat.getInstance().parse(token.getScanValue()));
				} catch (ParseException e) {
					statement.addObject(new Error("bad number format"));
				}
				break;
			case COMMENT:
				break;
			case ATOM:
				statement.addObject(new Atom(token.getScanValue()));
				break;
			default:
				statement.addObject(token.getString());
				break;
			}
		}
	}
}
