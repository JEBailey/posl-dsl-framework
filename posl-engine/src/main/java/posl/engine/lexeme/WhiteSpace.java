package posl.engine.lexeme;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.core.Stream;

public class WhiteSpace implements Lexeme {

	Pattern pattern = Pattern.compile("\\p{Space}");
	
	@Override
	public boolean consume(List<Token> tokens, Stream wrapper) {
		int index = wrapper.getPos();
		while ((wrapper.val()> 0) && wrapper.val()<= ' ' && wrapper.val() != '\n') {
			wrapper.pop();
		}
		return index < wrapper.getPos();
	}

	@Override
	public int consume(List<Token> tokens, CharSequence ps, int offset) {
		int totalCaptured = 0;
		Matcher matcher = pattern.matcher(ps);
		while (matcher.find(offset)) {
			String s = matcher.group();
			totalCaptured += s.length();
		}
		return totalCaptured;
	}

}
