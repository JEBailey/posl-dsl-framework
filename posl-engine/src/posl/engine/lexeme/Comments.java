package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;

import posl.engine.api.Container;
import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.api.TokenVisitor;
import posl.engine.core.PoslStream;

public class Comments extends Lexeme {

	@Override
	public boolean consume(List<Token> tokens, PoslStream ps) {
		// end of line comments
		if (ps.val() == '/' && ps.LA(1) == '/') {
			ps.setMark();
			while (ps.hasMore() && ps.val() != '\n') {
				ps.pop();
			}
			tokens.add(new Inner(ps));//, ps.getMark()));
			return true;
		} else 
		if (ps.val() == '/' && ps.LA(1)== '*') {
			ps.setMark();
			while (ps.hasMore()) {
				if (ps.val() == '*' && ps.LA(1)== '/') {
					ps.pop();
					ps.pop();
					break;
				}
				ps.pop();
			}
			tokens.add(new Inner(ps));
			return true;
		} 
		return false;
	}
	

	
	private class Inner extends Token {
		
		public Inner(PoslStream ps) {
			this.value = ps.getSubString();
			this.startPos = ps.getMark();
			this.endPos = ps.getPos();
		}

		@Override
		public Container consume(Container statement, Stack<Container> statements,
				Stack<Character> charStack) {
			return statement;
		}

		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitComments(this);
		}

	}

}
