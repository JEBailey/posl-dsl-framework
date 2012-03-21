package posl.editorkit;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import posl.engine.api.ILexer;
import posl.engine.core.Lexer;
import posl.engine.core.Parser;
import posl.engine.token.Token;

public class DocumentLexer implements ILexer {

	static Logger log = Logger.getLogger(Parser.class.getName());

	ILexer lexer = null;
	public List<Token> tokens = null;

	private Stack<Token> charStack = new Stack<Token>();

	public DocumentLexer() {
		lexer = new Lexer();
		tokens = new ArrayList<Token>();
	}

	@Override
	public void tokenize(InputStream is) {
		lexer.tokenize(is);
	}

	@Override
	public boolean hasMore() {
		return false;
	}

	@Override
	public Token next() {
		return null;
	}

	@Override
	public void tokenize(Reader reader) {
		lexer.tokenize(reader);
	}

	@Override
	public List<Token> getTokens() {
		tokens = new ArrayList<Token>();
		parse();
		return tokens;
	}

	private void parse() {
		boolean command = true;
		Token comparable = null;
		while (lexer.hasMore()) {
			Token token = lexer.next();
			DocAttributes attr = new DocAttributes();
			token.setAttribute(attr);
			if (log.isLoggable(Level.FINE)) {
				log.fine("receiving token " + token);
			}

			switch (token.type) {
			case GRAMMAR:
				switch (token.getCharValue()) {
				case '[':
					command = true;
					charStack.push(token);
					break;
				case '(':
					charStack.push(token);
					break;
				case '{':
					command = true;
					charStack.push(token);
					break;
				case ')':
					comparable = null;
					if (!charStack.empty()) {
						comparable = charStack.pop();
						if (comparable.getCharValue() == '('){
							attr.setToken(comparable);
							((DocAttributes)comparable.getAttribute()).setToken(token);
						}
					} 
					break;
				case ']':
					comparable = null;
					if (!charStack.empty()) {
						comparable = charStack.pop();
						if (comparable.getCharValue() == '['){
							attr.setToken(comparable);
							((DocAttributes)comparable.getAttribute()).setToken(token);
						}
					} 
					break;
				case '}':
					comparable = null;
					if (!charStack.empty()) {
						comparable = charStack.pop();
						if (comparable.getCharValue() == '{'){
							attr.setToken(comparable);
							((DocAttributes)comparable.getAttribute()).setToken(token);
						}
					}
				}
				break;
			case EOL:
				command = true;
				break;
			case ATOM:
				if (command){
					attr.setCommand(true);
					command = false;
				}
				break;
			default:
				break;
			}
			tokens.add(token);
		}

	}

}
