package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;

import posl.engine.api.ALexeme;
import posl.engine.api.IStatement;
import posl.engine.api.IToken;
import posl.engine.core.PoslStream;
import posl.engine.type.Statement;

public class Eol extends ALexeme {

	@Override
	public boolean consume(List<IToken> tokens, PoslStream ps) {
		if (ps.val() == -1){
			tokens.add(new Inner());
			return false;
		}
		while (ps.val() == '\n'){
			ps.pop();
			return tokens.add(new Inner());
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

		@Override
		public int length() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getStartOffset() {
			// TODO Auto-generated method stub
			return 0;
		}
	}

}