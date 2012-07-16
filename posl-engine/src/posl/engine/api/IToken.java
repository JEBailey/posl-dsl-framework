package posl.engine.api;

import java.util.Stack;

public interface IToken {
	
	IStatement  consume(IStatement statement, Stack<IStatement> statements ,Stack<Character> charStack);
	
	int length();

	int getStartOffset();

}
