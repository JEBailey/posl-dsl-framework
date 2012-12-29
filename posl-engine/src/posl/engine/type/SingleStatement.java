package posl.engine.type;

import java.util.Collection;
import java.util.LinkedList;

import posl.engine.api.Container;
import posl.engine.api.Statement;
import posl.engine.api.StatementVisitor;

public class SingleStatement implements Statement, Container {

	private int startLineNumber;
	private int endLineNumber;
	
	private LinkedList<Object> content = new LinkedList<Object>();
	
	public SingleStatement(){
	}
	
	public SingleStatement(Collection<? extends Object> list) {
		content = new LinkedList<Object>(list);
	}
	
	public boolean notEmpty(){
		return !content.isEmpty();
	}

	public SingleStatement subList(int arg0, int arg1){
		return new SingleStatement(content.subList(arg0, arg1));
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
	public boolean addEol() {
		return !content.isEmpty();
		
	}

	public int startPos() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int endPos() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object get() {
		return this;
	}

	@Override
	public Object accept(StatementVisitor visitor) {
		return visitor.visit(this);
	}
	

}
