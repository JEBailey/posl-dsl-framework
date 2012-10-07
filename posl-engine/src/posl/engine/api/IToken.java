package posl.engine.api;

import java.util.Stack;

public interface IToken {
	
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
	IStatement  consume(IStatement statement, Stack<IStatement> statements ,Stack<Character> charStack);
	
	int length();

	int getStartOffset();

}
