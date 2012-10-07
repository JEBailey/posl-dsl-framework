package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;

import posl.engine.api.ALexeme;
import posl.engine.api.IStatement;
import posl.engine.api.IToken;
import posl.engine.core.PoslStream;

public class QuoteString extends ALexeme {

	@Override
	public boolean consume(List<IToken> tokens, PoslStream ps) {
		// string
		if (ps.val() == '"') {
			return processQuote(tokens,ps);
		}
		return false;
	}
	
	private boolean processQuote(List<IToken> tokens, PoslStream ps) {
		StringBuilder sb = new StringBuilder();
		ps.pop();
		ps.mark();
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
	
	private class Inner implements IToken {
		private String value;
		
		private int startPos;

		public Inner(String value, int i) {
			this.value = value;
			this.startPos = i;
		}

		@Override
		public IStatement consume(IStatement statement, Stack<IStatement> statements,
				Stack<Character> charStack) {
			statement.addObject(value);
			return statement;
		}

		@Override
		public int length() {
			return value.length()+2;
		}

		@Override
		public int getStartOffset() {
			return startPos;
		}
	}

}
