package posl.engine.lexeme;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import posl.engine.api.Lexeme;
import posl.engine.api.Token;

public class WhiteSpace implements Lexeme {

	// custom white space identifier as we don't want to capture EOL's
	Pattern pattern = Pattern.compile("[ \\t\\x0B\\f]+");
	
	Matcher matcher;
	
	CharSequence cachedStream;

	@Override
	public int consume(List<Token> tokens, CharSequence ps, int offset) {
		if (ps != cachedStream){
			matcher = pattern.matcher(ps);
			cachedStream = ps;
		}
		int totalCaptured = 0;
		matcher.region(offset, ps.length());
		if (matcher.lookingAt()) {
			String s = matcher.group();
			totalCaptured += s.length();
		}
		return totalCaptured;
	}

}
