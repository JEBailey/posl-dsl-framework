package posl.engine.lexeme;

import java.util.List;

import posl.engine.api.ALexeme;
import posl.engine.api.IToken;
import posl.engine.core.PoslStream;

public class WhiteSpace extends ALexeme {

	@Override
	public boolean consume(List<IToken> tokens, PoslStream wrapper) {
		int index = wrapper.pos();
		while ((wrapper.val()> 0) && wrapper.val()<= ' ' && wrapper.val() != '\n') {
			wrapper.pop();
		}
		return index < wrapper.pos();
	}

}
