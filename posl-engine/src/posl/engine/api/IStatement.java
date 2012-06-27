package posl.engine.api;


public interface IStatement {
	
	boolean addObject(Object token, int lineNumber);
	
	boolean notEmpty();
	
	boolean isMultiLine();

	void addEol(int lnumber);

}
