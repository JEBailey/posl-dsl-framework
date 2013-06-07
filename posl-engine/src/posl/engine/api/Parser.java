package posl.engine.api;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import posl.engine.error.PoslException;
import posl.engine.type.SingleStatement;

public interface Parser extends Iterator<SingleStatement> {

	public abstract void process(InputStream is, List<Lexeme> lexemes) throws PoslException;

	public abstract void process(String string, List<Lexeme> lexemes) throws PoslException;

	public abstract boolean complete();

}