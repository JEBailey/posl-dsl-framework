package posl.engine.type;

import java.util.Iterator;
import java.util.LinkedList;

import posl.engine.api.IStatement;

public class MultiLineStatement implements IStatement, Iterable<Statement> {
	
	private LinkedList<Statement> content = new LinkedList<Statement>();
	
	private Statement statement;
	public int lineNumber = -1;

	private boolean isMultiLine;
	
	public MultiLineStatement(int lineNumber){
		this(lineNumber,true);
	}
	
	public MultiLineStatement(int lineNumber,boolean isMultiLine){
		statement = new Statement(lineNumber, false);
		this.lineNumber = lineNumber;
		this.isMultiLine = isMultiLine;
	}

	public boolean addObject(Object object, int lnumber){
		if (!(object instanceof EOL)){
			return statement.add(object);
		}
		if (statement.notEmpty()){
			content.add(statement);
			this.statement = new Statement(lnumber);
		}
		return true;
	}
	
	public boolean notEmpty(){
		return statement.notEmpty();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (isMultiLine){
			sb.append("{\n");
		}
		for (Object object:content){
			sb.append(object.toString());
			sb.append('\n');
		}
		if (isMultiLine){
			sb.append('}');
		}
		return sb.toString();
	}

	@Override
	public Iterator<Statement> iterator() {
		return content.iterator();
	}

	@Override
	public boolean isMultiLine() {
		return isMultiLine;
	}
	
	

}
