package posl.engine.api;

import java.io.InputStream;
import java.util.Iterator;

import posl.engine.error.PoslException;
import posl.engine.type.SingleStatement;

public interface IParser extends Iterator<SingleStatement> {

	public abstract void process(InputStream is) throws PoslException;

	public abstract void process(String string) throws PoslException;

	public abstract boolean complete();

}