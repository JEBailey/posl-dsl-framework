package posl.engine.api;

import java.util.Stack;

public abstract class BasicToken implements Token {
	
	protected int startPos;
	protected int endPos;
	
	protected Object value;
	protected Object meta;
	
	/* (non-Javadoc)
	 * @see posl.engine.api.Token#consume(posl.engine.api.Aggregator, java.util.Stack, java.util.Stack)
	 */
	@Override
	public abstract Collector consume(Collector statement, Stack<Collector> statements ,Stack<Character> charStack);
	
	/* (non-Javadoc)
	 * @see posl.engine.api.Token#accept(posl.engine.api.TokenVisitor)
	 */
	@Override
	public abstract void accept(TokenVisitor visitor);
	
	/* (non-Javadoc)
	 * @see posl.engine.api.Token#getStartOffset()
	 */
	@Override
	public int getStartOffset(){
		return startPos;
	}

	/* (non-Javadoc)
	 * @see posl.engine.api.Token#getEndOffset()
	 */
	@Override
	public int getEndOffset(){
		return endPos;
	}
	
	/* (non-Javadoc)
	 * @see posl.engine.api.Token#setMeta(java.lang.Object)
	 */
	@Override
	public void setMeta(Object meta){
		this.meta = meta;
	}
    
	/* (non-Javadoc)
	 * @see posl.engine.api.Token#getMeta()
	 */
	@Override
	public Object getMeta(){
		return meta;
	}
	
	/* (non-Javadoc)
	 * @see posl.engine.api.Token#getString()
	 */
	@Override
	public String getString(){
		return (String)value;
	}
	
	@Override
	public String toString(){
		return value.toString();
	}
}
