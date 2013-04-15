package posl.engine.type;

import java.util.LinkedList;
import java.util.List;

import posl.engine.api.Container;
import posl.engine.api.Statement;
import posl.engine.api.StatementVisitor;

public class MultiLineStatement implements Statement, Container {

	private List<SingleStatement> statements = new LinkedList<SingleStatement>();

	private SingleStatement statement;
	
	public int startLineNumber;
	public int endLineNumber;

	public MultiLineStatement() {
		statement = new SingleStatement();
	}

	public boolean add(Object object) {
		return statement.add(object);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for (SingleStatement statement : statements) {
			sb.append(statement.toString());
			sb.append('\n');
		}
		sb.append('}');
		return sb.toString();
	}

	public List<SingleStatement> get(){
		return statements;
	}
	

	@Override
	public boolean addEol() {
		if (statement.notEmpty()) {
			statements.add(statement);
			this.statement = new SingleStatement();
		}
		return false;
	}

	@Override
	public Object accept(StatementVisitor visitor) {
		return visitor.visit((List<SingleStatement>)statements);
	}

}
