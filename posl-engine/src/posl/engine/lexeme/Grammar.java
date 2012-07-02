package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;

import posl.engine.api.ALexeme;
import posl.engine.api.IStatement;
import posl.engine.api.IToken;
import posl.engine.core.PoslStream;
import posl.engine.type.MultiLineStatement;
import posl.engine.type.PList;
import posl.engine.type.Statement;

public class Grammar extends ALexeme {

	@Override
	public boolean consume(List<IToken> tokens, PoslStream ps) {
		ps.mark();
		boolean grammar = false;
		switch (ps.val()){
		case '{':
		case '}':
		case '(':
		case ')':
		case '[':
		case ']':
			grammar = true;
		}
		if (grammar){
			tokens.add(new Inner((char)ps.pop()));//, ps.getMark()));
		}
		return grammar;
	}
	
	
	private class Inner implements IToken {
		char charValue;
		
		public Inner(char value) {
			this.charValue = value;
		}
		
		@Override
		public IStatement consume(IStatement statement, Stack<IStatement> statements,
				Stack<Character> charStack) {
			int lineNumber = statement.endLineNumber();
			switch (charValue) {
			case '[':
				charStack.push(']');
				statements.push(statement);
				statement = new Statement(lineNumber);
				break;
			case '(':
				charStack.push(')');
				statements.push(statement);
				statement = new PList();
				break;
			case '{':
				charStack.push('}');
				statements.push(statement);
				statement = new MultiLineStatement(lineNumber);
				break;
			case ')':
				if (!charStack.empty() && charStack.pop() == charValue){
					Object temp = statement;
					statement = statements.pop();
					statement.addObject(temp);
				} else {
					//throw new PoslException(lineNumber,"could not match parenthesis");
				}
				break;
			case ']':
				if (!charStack.empty() && charStack.pop() == charValue){
					Object temp = statement;
					statement = statements.pop();
					statement.addObject(temp);
				} else {
					//throw new PoslException(lineNumber,"could not match square bracket");
				}
				break;
			case '}':
				if (!charStack.empty() && charStack.pop() == charValue){
					statement.addEol();
					Object temp = statement;
					statement = statements.pop();
					statement.addObject(temp);
				} else {
					//throw new PoslException(lineNumber,"could not match brace");
				}
				break;
			}
			return statement;
		}
	}
	

}
