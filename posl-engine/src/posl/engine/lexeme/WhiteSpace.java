package posl.engine.lexeme;

import java.util.List;

import posl.engine.api.Lexeme;
import posl.engine.api.IToken;
import posl.engine.core.PoslStream;

public class WhiteSpace extends Lexeme {

	@Override
	public boolean consume(List<IToken> tokens, PoslStream wrapper) {
		int index = wrapper.getPos();
		while ((wrapper.val()> 0) && wrapper.val()<= ' ' && wrapper.val() != '\n') {
			wrapper.pop();
		}
		return index < wrapper.getPos();
	}

}
