package posl.engine.core;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import posl.engine.api.ILexer;
import posl.engine.token.Token;

public class Lexer implements ILexer {

	// list of tokens to be returned
	protected List<Token> tokens;

	private StreamWrapper wrapper;
	

	/**
	 * 
	 * 
	 */
	public void tokenize(InputStream is) {
		wrapper = new StreamWrapper(is);
		tokens = new ArrayList<Token>();
		tokenize();
		wrapper = null;
	}

	public void tokenize(Reader reader) {
		wrapper = new StreamWrapper(reader);
		tokens = new ArrayList<Token>();
		tokenize();
		wrapper = null;
	}

	/**
	 * 
	 * 
	 */
	public void tokenize() {
		while (wrapper.hasMore()) {
			// kill off some whitespace
			while ((val() > 0) && (val() <= ' ')) {
				if (val() == '\n') {
					tokens.add(Token.EOL(pos()));
				}
				pop();
			}
			// end of line comments
			if (val() == '/' && LA(1) == '/') {
				processEolComment();
			} else
			// multi-line comments
			if (val() == '/' && LA(1) == '*') {
				processMultiLineComment();
			} else
			// numbers
			if (isDigit(val()) || (val() == '-' && isDigit(LA(1)))) {
				if (val() == '0' && LA(1) == 'x') {
					processHexCode();
				}
				processNumber();
			} else
			// atom/id
			if (isAlpha(val()) || (val() == '_' && isAlpha(LA(1)))) {
				processWord();
			} else
			// special
			if (isSpecial(val())) {
				processSpecial();
			} else
			// string
			if (val() == '"') {
				processQuote();
			} else {
				if (val() > 0) {
					tokens.add(Token.GRAMMAR(pop(), pos() - 1));
				}
			}

		}
		tokens.add(Token.EOL(pos()));
	}

	// TODO
	private void processHexCode() {
		StringBuffer sb = new StringBuffer();
		mark();
		sb.append(pop());
		sb.append(pop());
		while (isDigit(val()) || isHex(val())) {
			sb.append(pop());
		}
		tokens.add(Token.NUMBER(sb.toString(), getMark()));

	}

	private void processEolComment() {
		StringBuffer sb = new StringBuffer();
		mark();
		while (wrapper.hasMore() && val() != '\n') {
			sb.append(pop());
		}
		tokens.add(Token.COMMENT(sb.toString(), getMark()));
	}

	private void processMultiLineComment() {
		StringBuffer sb = new StringBuffer();
		mark();
		while (wrapper.hasMore()) {
			if (val() == '*' && LA(1) == '/') {
				sb.append(pop());
				sb.append(pop());
				break;
			}
			sb.append(pop());
		}

		tokens.add(Token.COMMENT(sb.toString(), getMark()));
	}

	private void processQuote() {
		StringBuffer sb = new StringBuffer();
		mark();
		wrapper.doMark();
		pop();
		while (wrapper.hasMore() && val() != '"') {
			if (val() == '\\') {
				pop();
				switch (val()) {
				case 'a':
					sb.append(0x7);
					break;
				case 'b':
					sb.append('\b');
					break;
				case 'f':
					sb.append(0xC);
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
					sb.append(0xB);
					break;
				default:
					sb.append((char) val());
				}// end switch
				pop();
			} else {
				sb.append(pop());
			}

		}// end while
		if (val() == '"') {
			pop();
			tokens.add(Token.STRING(sb.toString(), getMark()));
		} else {
			wrapper.reset();
			pop();
			tokens.add(Token.WORD("\"", getMark()));
		}

	}

	private void processNumber() {
		StringBuffer sb = new StringBuffer();
		mark();
		sb.append(pop());
		consumeDigits(sb);
		// at this point we have defined all positive whole numbers
		// check for float
		if (val() == '.') {
			sb.append(pop());
			consumeDigits(sb);
			wrapper.doMark();
			if (val() == 'e' || val() == 'E') {
				sb.append(pop());
				if (isDigit(val()) || (val() == '-' || val() == '+')) {
					sb.append(pop());
					consumeDigits(sb);
				} else {
					tokens.add(Token.NUMBER(sb.substring(0, wrapper.getMark()),
							getMark()));
					wrapper.reset();
				}
			}
		}

		tokens.add(Token.NUMBER(sb.toString(), getMark()));
	}

	private void consumeDigits(StringBuffer sb) {
		while (isDigit(val())) {
			sb.append(pop());
		}
	}

	private void processWord() {
		StringBuffer sb = new StringBuffer();
		mark();
		sb.append(pop());
		while (isAlpha(val()) || isSpecial(val()) || isDigit(val())
				|| val() == '_') {
			sb.append(pop());
		}
		tokens.add(Token.WORD(sb.toString(), getMark()));
	}

	private void processSpecial() {
		StringBuffer sb = new StringBuffer();
		mark();
		sb.append(pop());
		while (isSpecial(val())) {
			sb.append(pop());
		}
		tokens.add(Token.WORD(sb.toString(), getMark()));
	}

	private boolean isSpecial(int value) {
		return ((value >= '#') && (value <= '\''))
				|| ((value >= '*') && (value <= '/'))
				|| ((value >= ':') && (value <= '@')) || value == '\\'
				|| value == '^' || value == '!';
	}

	private boolean isDigit(int value) {
		return value >= '0' && value <= '9';
	}

	private boolean isHex(int value) {
		return (value >= 'a' && value <= 'f') || (value >= 'A' && value <= 'F');
	}

	private boolean isAlpha(int value) {
		return ((value >= 'a') && (value <= 'z'))
				|| ((value >= 'A') && (value <= 'Z'));
	}

	/*
	 * private boolean isHexDigit(int value) { return isDigit(value) || (value
	 * >= 'a' && value <= 'f') || (value >= 'A' && value <= 'F'); }
	 */
	@Override
	public boolean hasMore() {
		return !tokens.isEmpty();
	}

	@Override
	public Token next() {
		if (!tokens.isEmpty()) {
			return (Token) tokens.remove(0);
		}
		return null;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public int LA(int offset) {
		return wrapper.LA(offset);
	}

	public int val() {
		return wrapper.val();
	}

	public char pop() {
		return (char) wrapper.pop();
	}

	private int pos() {
		return wrapper.pos();
	}
	
	private void mark(){
		wrapper.doMark();
	}
	
	private int getMark(){
		return wrapper.getMark();
	}

}