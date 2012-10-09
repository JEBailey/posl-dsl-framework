package posl.engine.api;

import java.util.Stack;

public abstract class IToken {
	
	protected int startPos;
	protected int endPos;
	
	protected String value;
	protected Object meta;
	
	/**
	 * represents an encapsulation of functionality,
	 * 
	 * 
	 *  
	 * @param statement
	 * @param statements
	 * @param charStack
	 * @return
	 */
	public abstract IStatement  consume(IStatement statement, Stack<IStatement> statements ,Stack<Character> charStack);
	
	public abstract void accept(TokenVisitor visitor);
	
	public int getStartOffset(){
		return startPos;
	}

	int getEndOffset(){
		return endPos;
	}
	
	public void setMetaInformation(Object meta){
		this.meta = meta;
	}
    
	public Object getMetaInformation(){
		return meta;
	}
	
	public String getString(){
		return value;
	}
}
