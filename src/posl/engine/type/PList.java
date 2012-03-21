package posl.engine.type;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import posl.engine.api.IStatement;

public class PList extends LinkedList<Object> implements List<Object>, IStatement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PList(Collection<? extends Object> arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	public boolean addObject(Object object, int lineNumber){
		return super.add(object);
	}
	
	public boolean notEmpty(){
		return !isEmpty();
	}

	@Override
	public PList subList(int arg0, int arg1){
		return new PList(super.subList(arg0, arg1));
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append('(');
		for (Object object:this){
			sb.append(' ');
			sb.append(object.toString());
		}
		sb.append(' ');
		sb.append(')');
		return sb.toString();
	}

	@Override
	public boolean isMultiLine() {
		return false;
	}

}
