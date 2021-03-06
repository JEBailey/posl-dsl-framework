package posl.engine.api;

import java.util.Iterator;
import java.util.List;

import posl.engine.error.PoslException;
import posl.engine.type.Statement;

/**
 * Converts a string into a series of Statements as defined by the Lexemes
 * that are passed in.
 * 
 * @author je bailey
 *
 */
public interface Parser extends Iterator<Statement> {
	
	public abstract void process(CharSequence string, List<Lexeme> lexemes) throws PoslException;

	public abstract boolean complete();

}