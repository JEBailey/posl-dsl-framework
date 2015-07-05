package posl.engine.api;

import java.util.Stack;

public interface Token {

	/**
	 * Encapsulates the logic to determine how to apply the token data
	 * to the incoming structures.
	 * 
	 * @param statement current Collector which is consuming tokens
	 * @param statements stack of Collectors which represents nesting
	 * @param charStack used for to look for current bounding representation
	 * @return the collector to be used for the next Token
	 */
	public Collector consume(Collector statement,
			Stack<Collector> statements, Stack<Character> charStack);

	/**
	 * Allows us to categorize the token information into one of the
	 * broad categories defined within the TokenVisitor API
	 * 
	 * provides a way to define extra functionality.
	 * 
	 * @param visitor to visit
	 */
	public void accept(TokenVisitor visitor);

	public int getStartOffset();

	public int getEndOffset();

	public void setMeta(Object meta);

	public Object getMeta();

	public abstract String getString();

}