package posl.engine.type;

import java.util.LinkedList;

import posl.engine.api.IStatement;

public class Statement implements IStatement {

	private int lineNumber = -1;
	
	private LinkedList<Object> content = new LinkedList<Object>();

	private boolean isMultiLine;
	
	public Statement(int lineNumber){
		this(lineNumber,false);
	}
	
	public Statement(int lineNumber, boolean isMultiLine){
		this.lineNumber = lineNumber;
		this.isMultiLine = isMultiLine;
	}
	
	public boolean addObject(Object object, int lineNumber){
		return content.add(object);
	}
	
	public boolean notEmpty(){
		return !content.isEmpty();
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
	
	public int getLineNumber(){
		return lineNumber;
	}

	@Override
	public boolean isMultiLine() {
		return isMultiLine;
	}

	public boolean add(Object object) {
		return content.add(object);
	}

	public Object get(int i) {
		return content.get(i);
	}

	public int size() {
		return content.size();
	}

	public void push(Atom atom) {
		content.push(atom);
	}
	

}
