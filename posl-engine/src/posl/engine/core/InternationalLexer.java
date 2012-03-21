package posl.engine.core;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import posl.engine.api.ILexer;
import posl.engine.token.Token;

public class InternationalLexer implements ILexer {

	// list of tokens to be returned
	protected List<Token> tokens;

	private StreamWrapper wrapper;

	private int startOfToken;

	/**
	 * 
	 * 
	 */
	public void tokenize(InputStream is) {
		wrapper = new StreamWrapper(is);
		tokens =  new ArrayList<Token>();
		tokenize();
		wrapper = null;
	}
	
	public void tokenize(Reader reader) {
		wrapper = new StreamWrapper(reader);
		tokens =  new ArrayList<Token>();
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
			while (Character.isWhitespace(val())) {
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
			if (Character.isDigit(val()) || (val() == '-' && Character.isDigit(LA(1)))) {
				processNumber();
			} else
			// atom/id
			if (Character.isAlphabetic(val())) {
				processWord();
			} else
			// string
			if (val() == '"') {
				processQuote();
			} else
			// special
			if (isSpecial(val())) {
				processSpecial();
			} else
			{
				if (val() > 0) {
					tokens.add(Token.GRAMMAR((char)pop(),pos() -1));
				}
			}

		}
		tokens.add(Token.EOL(pos()));
	}

	private void processEolComment() {
		StringBuilder sb = new StringBuilder();
		startOfToken = pos();
		while (wrapper.hasMore() && val() != '\n') {
			sb.appendCodePoint(pop());
		}
		tokens.add(Token.COMMENT(sb.toString(), startOfToken));
	}

	private void processMultiLineComment() {
		StringBuilder sb = new StringBuilder();
		startOfToken = pos();
		while (wrapper.hasMore()) {
			if (val() == '*' && LA(1) == '/') {
				sb.appendCodePoint(pop());
				sb.appendCodePoint(pop());
				break;
			}
			sb.appendCodePoint(pop());
		}

		tokens.add(Token.COMMENT(sb.toString(), startOfToken));
	}

	private void processQuote() {
		StringBuilder sb = new StringBuilder();
		startOfToken = pos();
		wrapper.mark();
		pop();
		while (wrapper.hasMore() && val() != '"') {
			if (val() == '\\') {
				pop();
				switch (val()) {
				case 'a':
					sb.appendCodePoint(0x7);
					break;
				case 'b':
					sb.appendCodePoint('\b');
					break;
				case 'f':
					sb.appendCodePoint(0xC);
					break;
				case 'n':
					sb.appendCodePoint('\n');
					break;
				case 'r':
					sb.appendCodePoint('\r');
					break;
				case 't':
					sb.append('\t');
					break;
				case 'v':
					sb.append(0xB);
					break;
				default:
					sb.appendCodePoint(val());
				}// end switch
				pop();
			} else {
				sb.appendCodePoint(pop());
			}

		}// end while
		if (val() == '"') {
			pop();
			tokens.add(Token.STRING(sb.toString(),startOfToken));
		} else {
			wrapper.reset();
			pop();
			tokens.add(Token.WORD("\"",startOfToken));
		}

	}

	private void processNumber() {
		StringBuilder sb = new StringBuilder();
		startOfToken = pos();
		sb.appendCodePoint(pop());
		consumeDigits(sb);
		// at this point we have defined all positive whole numbers
		// check for float
		if (val() == '.') {
			sb.appendCodePoint(pop());
			consumeDigits(sb);
			wrapper.mark();
			if (val() == 'e' || val() == 'E') {
				sb.appendCodePoint(pop());
				if (Character.isDigit(val()) || (val() == '-' || val() == '+')) {
					sb.appendCodePoint(pop());
					consumeDigits(sb);
				} else {
					tokens.add(Token.NUMBER(sb.substring(0, wrapper.getMark()),startOfToken));
					wrapper.reset();
				}
			}
		}
		
		tokens.add(Token.NUMBER(sb.toString(),startOfToken));
	}

	private void consumeDigits(StringBuilder sb) {
		while (Character.isDigit(val())) {
			sb.appendCodePoint(pop());
		}
	}

	private void processWord() {
		StringBuilder sb = new StringBuilder();
		startOfToken = pos();
		sb.appendCodePoint(pop());
		while (Character.isJavaIdentifierPart(val())) {
			sb.appendCodePoint(pop());
		}
		tokens.add(Token.WORD(sb.toString(),startOfToken));
	}

	private void processSpecial() {
		StringBuilder sb = new StringBuilder();
		startOfToken = pos();
		sb.appendCodePoint(pop());
		while (isSpecial(val())) {
			sb.appendCodePoint(pop());
		}
		tokens.add(Token.WORD(sb.toString(),startOfToken));
	}

	private boolean isSpecial(int value) {
		int type = Character.getType(value);
		return (type == Character.OTHER_PUNCTUATION || type == Character.MATH_SYMBOL || type == Character.DASH_PUNCTUATION);
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
	
	public List<Token> getTokens(){
		return tokens;
	}

	public int LA(int offset) {
		return wrapper.LA(offset);
	}

	public int val() {
		return wrapper.val();
	}

	public int pop() {
		return wrapper.pop();
	}

	private int pos() {
		return wrapper.pos();
	}



}
