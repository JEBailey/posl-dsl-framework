package posl.engine.api;


public interface IStatement {
	
	boolean addObject(Object token);
	
	boolean notEmpty();
	
	boolean isMultiLine();

	void addEol(int lnumber);

}
