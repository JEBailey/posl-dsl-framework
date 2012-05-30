package posl.engine.api;

import java.util.Stack;

public interface IToken {
	
	boolean consume(IStatement statement, Stack<IStatement> statements ,Stack<Character> charStack);

}
