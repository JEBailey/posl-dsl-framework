package posl.engine.lexeme;

import java.util.List;

import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.core.Stream;

public class WhiteSpace implements Lexeme {

	@Override
	public boolean consume(List<Token> tokens, Stream wrapper) {
		int index = wrapper.getPos();
		while ((wrapper.val()> 0) && wrapper.val()<= ' ' && wrapper.val() != '\n') {
			wrapper.pop();
		}
		return index < wrapper.getPos();
	}

}
