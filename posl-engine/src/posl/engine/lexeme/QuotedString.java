package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;

import posl.engine.api.Container;
import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.api.TokenVisitor;
import posl.engine.core.Stream;

public class QuotedString extends Lexeme {

	@Override
	public boolean consume(List<Token> tokens, Stream ps) {
		// string
		if (ps.val() == '"') {
			return processQuote(tokens,ps);
		}
		return false;
	}
	
	private boolean processQuote(List<Token> tokens, Stream ps) {
		StringBuilder sb = new StringBuilder();
		ps.pop();
		ps.setMark();
		while (ps.hasMore() && ps.val() != '"') {
			if (ps.val() == '\\') {
				ps.pop();
				switch (ps.val()) {
				case 'a':
					sb.append(0x7);
					break;
				case 'b':
					sb.append('\b');
					break;
				case 'f':
					sb.append((char)0xC);
					break;
				case 'n':
					sb.append('\n');
					break;
				case 'r':
					sb.append('\r');
					break;
				case 't':
					sb.append('\t');
					break;
				case 'v':
					sb.append((char)0xB);
					break;
				default:
					sb.append((char) ps.val());
				}// end switch
				ps.pop();
			} else {
				sb.append((char)ps.pop());
			}

		}// end while
		if (ps.val() == '"') {
			ps.pop();
			tokens.add(new Inner(sb.toString(), ps.getMark()));
		} else {
			ps.reset();
			return false;
		}
		return true;

	}
	
	private class Inner extends Token {

		public Inner(String value, int i) {
			this.value = value;
			this.startPos = i;
			this.endPos = i+value.length() + 2;
		}

		@Override
		public Container consume(Container statement, Stack<Container> statements,
				Stack<Character> charStack) {
			statement.add(value);
			return statement;
		}
		
		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitQuote(this);
		}


	}

}
