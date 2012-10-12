package posl.engine.lexeme;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Stack;

import posl.engine.api.ALexeme;
import posl.engine.api.IStatement;
import posl.engine.api.IToken;
import posl.engine.api.TokenVisitor;
import posl.engine.core.PoslStream;

public class Numbers extends ALexeme {
	
	private static NumberFormat nf = NumberFormat.getInstance();

	@Override
	public boolean consume(List<IToken> tokens, PoslStream ps) {
		// numbers
		if (isDigit(ps.val())
				|| (ps.val() == '-' && isDigit(ps.LA(1)))) {
			if (ps.val() == '0' && ps.LA(1) == 'x') {
				return processHexCode(tokens,ps);
			}
			return processNumber(tokens,ps);
		}
		return false;
	}

	private  boolean processNumber(List<IToken> tokens, PoslStream ps) {
		ps.mark();
		ps.pop();
		consumeDigits(ps);
		// at this point we have defined all positive whole numbers
		// check for float
		if (ps.val() == '.') {
			ps.pop();
			consumeDigits(ps);
			if (ps.val() == 'e' || ps.val() == 'E') {
				int priorMark = ps.getMark();
				ps.mark();
				ps.pop();
				if (isDigit(ps.val()) || (ps.val() == '-' || ps.val() == '+')) {
					ps.pop();
					consumeDigits(ps);
				} else {
					//TODO:handle this properly
					//tokens.add(new Inner(ps.getSubString(), ps.getMark()));
					//tokens.add(Token.NUMBER(ps.getSubString(),ps.getMark()));
					ps.reset(priorMark);
				}
			}
		}
		String numText = ps.getSubString();
		tokens.add(new Inner(numText,ps.getMark(),numText.length()));
		return true;
	}


	private boolean processHexCode(List<IToken> tokens, PoslStream ps) {
		// skip the first 0x
		ps.pop();
		ps.pop();
		ps.mark();
		while (isDigit(ps.val()) || isHex(ps.val())) {
			ps.pop();
		}
		String numText = ps.getSubString();
		tokens.add(new Inner(String.valueOf(Integer.parseInt(numText, 16)), ps.getMark() - 2,numText.length()+2));
		return true;
	}
	
	private class Inner extends IToken {

		public Inner(String value, int startPos, int length) {
			this.value = value;
			this.startPos = startPos;
			this.endPos = startPos + length;
		}
		
		
		@Override
		public IStatement consume(IStatement statement, Stack<IStatement> statements,
				Stack<Character> charStack) {
			try {
				statement.addObject(nf.parse(value));
			} catch (ParseException e) {
				statement.addObject(new Error("bad number format"));
			}
			return statement;
		}
		
		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitNumbers(this);
		}
		
	}


}
