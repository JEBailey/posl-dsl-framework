package posl.engine.api;

import java.io.InputStream;

import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public interface IParser {

	public abstract void process(InputStream is) throws PoslException;

	public abstract void process(String string) throws PoslException;

	public abstract Statement getStatement();

	public abstract boolean hasMore();

	public abstract boolean complete();

}