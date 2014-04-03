package posl.engine.api;

/**
 * Simple interface to track types of Statements.
 * 
 * 
 * 
 * @author jebailey
 *
 */
public interface Statement {
	
	Object accept(StatementVisitor visitor);

}
