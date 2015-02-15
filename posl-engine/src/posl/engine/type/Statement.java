package posl.engine.type;

import java.util.Collection;
import java.util.LinkedList;

import posl.engine.api.StatementProvider;
import posl.engine.api.Collector;
import posl.engine.api.StatementProviderVisitor;
import posl.engine.error.PoslException;

public class Statement implements Collector, StatementProvider {
	
	private LinkedList<Object> content = new LinkedList<Object>();
	private int startPos;
	private int endPos;
	
	public Statement(int startPos){
		this.startPos = startPos;
	}
	
	
	
	public Statement(Collection<? extends Object> list) {
		content = new LinkedList<Object>(list);
	}
	
	public boolean notEmpty(){
		return !content.isEmpty();
	}

	public Statement subList(int arg0, int arg1){
		return new Statement(content.subList(arg0, arg1));
	}
	
	public String errorString() {
		return "at pos :" + toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
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
	public boolean isFinished() {
		return !content.isEmpty();
	}

	public int startPos() {
		return startPos;
	}

	public int endPos() {
		return endPos;
	}

	@Override
	public Object get() {
		return this;
	}



	@Override
	public Object accept(StatementProviderVisitor visitor) throws PoslException {
		return visitor.evaluate(this);
	}
	

}
