package posl.engine.lexeme;

import java.util.List;

import posl.engine.api.ALexeme;
import posl.engine.core.PoslStream;
import posl.engine.token.Token;

public class QuoteString extends ALexeme {

	@Override
	public boolean consume(List<Token> tokens, PoslStream ps) {
		// string
		if (ps.val() == '"') {
			return processQuote(tokens,ps);
		}
		return false;
	}
	
	private boolean processQuote(List<Token> tokens, PoslStream ps) {
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
			tokens.add(Token.STRING(sb.toString(), ps.getMark()));;
		} else {
			ps.reset();
			ps.pop();
			tokens.add(Token.WORD("\"", ps.getMark()));
		}
		return true;

	}

}
