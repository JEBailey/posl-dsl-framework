package posl.engine.api;

import java.util.Stack;

public interface IToken {
	
	
	public abstract IStatement  consume(IStatement statement, Stack<IStatement> statements ,Stack<Character> charStack);

}
