package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;

import posl.engine.api.Collector;
import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.api.TokenVisitor;
import posl.engine.core.BasicToken;
import posl.engine.core.Stream;
import posl.engine.type.Statement;

/**
 * Consumes multiple end of line indicators
 * 
 * 
 * @author jebailey
 *
 */
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
		
		@Override
		public Collector consume(Collector statement, Stack<Collector> statements,
				Stack<Character> charStack) {
			if (statement.isFinished()){
				statements.add(statement);
				statement = new Statement(startPos);
			}
			return statement;
		}
		
		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitEol(this);
		}

	}

}
