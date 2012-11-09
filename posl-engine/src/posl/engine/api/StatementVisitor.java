package posl.engine.api;

import java.util.List;

import posl.engine.type.SingleStatement;

public interface StatementVisitor {
	
	Object visit(SingleStatement statement);
	
	Object visit(List<SingleStatement> statements);
	
}
