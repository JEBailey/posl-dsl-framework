package posl.engine.type;

import java.util.Iterator;
import java.util.LinkedList;

import posl.engine.api.IStatement;

public class MultiLineStatement implements IStatement, Iterable<Statement> {

	private LinkedList<Statement> statements = new LinkedList<Statement>();

	private Statement statement;
	public int startLineNumber;
	public int endLineNumber;

	public MultiLineStatement(int lineNumber) {
		super();
		statement = new Statement(lineNumber);
		this.startLineNumber = lineNumber;
	}

	public boolean addObject(Object object) {
		return statement.add(object);
	}

	public boolean notEmpty() {
		return statement.notEmpty();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\n");
		for (Statement statement : statements) {
			sb.append(statement.toString());
			sb.append('\n');
		}
		sb.append('}');
		return sb.toString();
	}

	@Override
	public Iterator<Statement> iterator() {
		return statements.iterator();
	}

	@Override
	public boolean isMultiLine() {
		return true;
	}

	@Override
	public void addEol() {
		if (statement.notEmpty()) {
			statements.add(statement);
			this.statement = new Statement(incrLineNumber());
		}
	}

	@Override
	public int startLineNumber() {
		return startLineNumber;
	}

	@Override
	public int endLineNumber() {
		return endLineNumber;
	}

	@Override
	public int incrLineNumber() {
		return ++endLineNumber;
	}

}
