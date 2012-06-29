package posl.engine.token;

import java.util.Stack;

import posl.engine.api.IStatement;
import posl.engine.api.IToken;
import posl.engine.type.Statement;

public class EOL implements IToken {

	@Override
	public boolean consume(IStatement statement, Stack<IStatement> statements,
			Stack<Character> charStack) {
		if (!statement.isMultiLine()){
			if (statement.notEmpty()){
				statements.add((Statement)statement);
			}
			statement = new Statement(statement.endLineNumber()+1);
		} else {
			statement.addEol();
		}
		return true;
	}

}
