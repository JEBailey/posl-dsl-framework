package posl.engine.lexeme;

import java.util.List;

import posl.engine.api.ILexeme;
import posl.engine.core.PoslStream;
import posl.engine.token.Token;

public class WhiteSpace implements ILexeme {

	@Override
	public boolean consume(List<Token> tokens, PoslStream wrapper) {
		while ((wrapper.val()> 0) && (wrapper.val()<= ' ')) {
			if (wrapper.val()== '\n') {
				tokens.add(Token.EOL(wrapper.pos()));
			}
			wrapper.pop();
			return true;
		}
		return false;
	}

}
