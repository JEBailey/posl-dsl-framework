package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;

import posl.engine.api.BasicToken;
import posl.engine.api.Collector;
import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.api.TokenVisitor;
import posl.engine.core.Stream;
import posl.engine.type.SingleStatement;

public class Eol implements Lexeme {

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
	
	private class Inner extends BasicToken {
		
		public Inner(Stream ps){
			this.value = "\n";
			this.startPos = ps.getMark();
			this.endPos = ps.getMark();
		}
		
		public Collector consume(Collector statement, Stack<Collector> statements,
				Stack<Character> charStack) {
			if (statement.finish()){
				statements.add(statement);
				statement = new SingleStatement(startPos);
			}
			return statement;
		}
		
		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitEol(this);
		}

	}

}
