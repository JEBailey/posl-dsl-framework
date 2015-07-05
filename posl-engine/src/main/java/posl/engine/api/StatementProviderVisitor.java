package posl.engine.api;

import java.util.List;

import posl.engine.error.PoslException;
import posl.engine.type.Statement;

public interface StatementProviderVisitor {
	
	Object evaluate(Statement statement)throws PoslException;
	
	Object evaluate(List<Statement> statements) throws PoslException;
}
