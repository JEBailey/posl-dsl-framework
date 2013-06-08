package posl.engine.api;

import java.util.Stack;

public interface Token {

	/**
	 * represents an encapsulation of functionality,
	 * 
	 * @param statement current Aggregator which is consuming tokens
	 * @param statements stack of aggregators to indicate nesting
	 * @param charStack used for to look for current bounding representation
	 * @return either the aggregator that was passed in or a potential new aggregator
	 */
	public Aggregator consume(Aggregator statement,
			Stack<Aggregator> statements, Stack<Character> charStack);

	public void accept(TokenVisitor visitor);

	public int getStartOffset();

	public int getEndOffset();

	public void setMeta(Object meta);

	public Object getMeta();

	public abstract String getString();

}