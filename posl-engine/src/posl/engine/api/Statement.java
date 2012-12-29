package posl.engine.api;

public interface Statement {
	
	Object accept(StatementVisitor visitor);

}
