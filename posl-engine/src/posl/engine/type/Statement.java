package posl.engine.type;

import java.util.Collection;
import java.util.LinkedList;

import posl.engine.api.IStatement;

public class Statement implements IStatement {

	private int startLineNumber;
	private int endLineNumber;
	
	private LinkedList<Object> content = new LinkedList<Object>();
	
	public Statement(int lineNumber){
		this.startLineNumber = lineNumber;
	}
	
	public Statement(Collection<? extends Object> list) {
		content = new LinkedList<Object>(list);
	}
	
	public boolean addObject(Object object){
		return content.add(object);
	}
	
	public boolean notEmpty(){
		return !content.isEmpty();
	}

	public Statement subList(int arg0, int arg1){
		return new Statement(content.subList(arg0, arg1));
	}
	
	public String errorString() {
		return "at line "+ startLineNumber + ":" + toString();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Object object:content){
			sb.append(' ');
			sb.append(object.toString());
		}
		sb.append(' ');
		return sb.toString();
	}

	@Override
	public boolean isMultiLine() {
		return false;
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

	@Override
	public void addEol() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int startLineNumber() {
		return startLineNumber;
	}

	@Override
	public int endLineNumber() {
		return endLineNumber;
	}

	@Override
	public int incrLineNumber() {
		return ++endLineNumber;
	}
	

}
