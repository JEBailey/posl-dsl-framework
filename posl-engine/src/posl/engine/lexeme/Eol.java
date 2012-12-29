package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;

import posl.engine.api.Container;
import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.api.TokenVisitor;
import posl.engine.core.Stream;
import posl.engine.type.SingleStatement;

public class Eol extends Lexeme {

	@Override
	public boolean consume(List<Token> tokens, Stream ps) {
		ps.setMark();
		if (ps.val() == -1){
			tokens.add(new Inner(ps));
			return false;
		}
		while (ps.val() == '\n'){
			ps.pop();
			return tokens.add(new Inner(ps));
		}
		return false;
	}
	
	private class Inner extends Token {
		
		
		
		public Inner(Stream ps){
			this.value = "\n";
			this.startPos = ps.getMark();
			this.endPos = ps.getMark();
		}
		
		public Container consume(Container statement, Stack<Container> statements,
				Stack<Character> charStack) {
			if (statement.addEol()){
				statements.add(statement);
				statement = new SingleStatement();
			}
			return statement;
		}
		
		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitEol(this);
		}

	}

}
