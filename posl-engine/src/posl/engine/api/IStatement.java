package posl.engine.api;

public interface IStatement {
	
	Object accept(StatementVisitor visitor);

}
