package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;

import posl.engine.api.ALexeme;
import posl.engine.api.IStatement;
import posl.engine.api.IToken;
import posl.engine.core.PoslStream;
import posl.engine.type.Statement;

public class WhiteSpace extends ALexeme {

	@Override
	public boolean consume(List<IToken> tokens, PoslStream wrapper) {
		while ((wrapper.val()> 0) && (wrapper.val()<= ' ')) {
			if (wrapper.val()== '\n') {
				tokens.add(new Inner());
			}
			wrapper.pop();
			return true;
		}
		return false;
	}
	
	private class Inner implements IToken {
		@Override
		public IStatement consume(IStatement statement, Stack<IStatement> statements,
				Stack<Character> charStack) {
			if (!statement.isMultiLine()){
				if (statement.notEmpty()){
					statements.add((Statement)statement);
				}
				return new Statement(statement.endLineNumber()+1);
			} else {
				statement.addEol();
				return statement;
			}
		}
	}

}
