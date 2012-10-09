package posl.editorkit;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import posl.engine.api.ILexer;
import posl.engine.api.IToken;
import posl.engine.api.TokenVisitor;
import posl.engine.core.Lexer;
import posl.engine.core.Parser;


public class DocumentLexer implements ILexer {

	static Logger log = Logger.getLogger(Parser.class.getName());

	ILexer lexer = null;
	public List<IToken> ITokens = null;

	private Stack<IToken> charStack = new Stack<IToken>();

	public DocumentLexer() {
		lexer = new Lexer();
		ITokens = new ArrayList<IToken>();
	}

	@Override
	public void tokenize(InputStream is) {
		lexer.tokenize(is);
	}

	@Override
	public IToken next() {
		return null;
	}


	private void parse() {
		boolean command = true;
		IToken comparable = null;
		while (lexer.hasMore()) {
			IToken IToken = lexer.next();
			DocAttributes attr = new DocAttributes();
			IToken.setMetaInformation(attr);
			if (log.isLoggable(Level.FINE)) {
				log.fine("receiving IToken " + IToken);
			}

			switch (IToken.type) {
			
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
			ITokens.add(IToken);
		}

	}
	
	class UIVisitor implements TokenVisitor {

		@Override
		public void visitComments(IToken iToken) {		
		}

		@Override
		public void visitEol(IToken iToken) {			
		}

		@Override
		public void visitGrammar(IToken iToken) {
			
			switch (IToken.getCharValue()) {
			case '[':
				command = true;
				charStack.push(IToken);
				break;
			case '(':
				charStack.push(IToken);
				break;
			case '{':
				command = true;
				charStack.push(IToken);
				break;
			case ')':
				comparable = null;
				if (!charStack.empty()) {
					comparable = charStack.pop();
					if (comparable.getCharValue() == '('){
						attr.setToken(comparable);
						((DocAttributes)comparable.getMetaInformation()).setToken(IToken);
					}
				} 
				break;
			case ']':
				comparable = null;
				if (!charStack.empty()) {
					comparable = charStack.pop();
					if (comparable.getCharValue() == '['){
						attr.setToken(comparable);
						((DocAttributes)comparable.getAttribute()).setToken(IToken);
					}
				} 
				break;
			case '}':
				comparable = null;
				if (!charStack.empty()) {
					comparable = charStack.pop();
					if (comparable.getCharValue() == '{'){
						attr.setToken(comparable);
						((DocAttributes)comparable.getAttribute()).setIToken(IToken);
					}
				}
			}
			break;
		}

		@Override
		public void visitIdentifier(IToken iToken) {
			
		}

		@Override
		public void visitNumbers(IToken iToken) {			
		}

		@Override
		public void visitQuote(IToken iToken) {			
		}

		@Override
		public void visitWhitespace(IToken iToken) {			
		}
		
	}

}