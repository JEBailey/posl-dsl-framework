package posl.engine.type;

import java.util.LinkedList;
import java.util.List;

import posl.engine.api.StatementProvider;
import posl.engine.api.Collector;
import posl.engine.api.StatementProviderVisitor;
import posl.engine.error.PoslException;

/**
 * consume a series of statements into a single object
 * 
 * 
 * @author je bailey
 *
 */
public class MultiLineStatement implements StatementProvider, Collector {

	private List<Statement> statements = new LinkedList<Statement>();

	private Statement statement;
	
	private int startPos;
	
	public MultiLineStatement() {
		statement = new Statement(0);
	}

	public boolean add(Object object) {
		return statement.add(object);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for (Statement statement : statements) {
			sb.append(statement.toString());
			sb.append('\n');
		}
		sb.append('}');
		return sb.toString();
	}

	public List<Statement> get(){
		return statements;
	}
	

	@Override
	public boolean isFinished() {
		if (statement.notEmpty()) {
			statements.add(statement);
			this.statement = new Statement(0);
		}
		return false;
	}

	@Override
	public Object accept(StatementProviderVisitor visitor) throws PoslException {
		return visitor.evaluate((List<Statement>)statements);
	}

}
