package posl.editorkit;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

import posl.engine.api.Lexer;
import posl.engine.api.Token;
import posl.engine.api.TokenVisitor;
import posl.engine.core.Context;
import posl.engine.core.DefaultLexer;
import posl.engine.core.DefaultParser;
import posl.engine.provider.PoslProvider;

/**
 * The document lexer is used instead of the original lexer, to focus on the structure
 * of the code and how that will be displayed in the view.
 * 
 * 
 * @author jason bailey
 *
 */
public class DocumentLexer {

	static Logger log = Logger.getLogger(DefaultParser.class.getName());

	private Lexer lexer = null;
	private List<Token> tokens = null;
	private UIVisitor visitor = new UIVisitor();

	private Context context;

	public DocumentLexer(String contentType) {
		context = PoslProvider.getContext("brainfuck");
	}
	

	public void tokenize(Reader reader) {
		lexer = new DefaultLexer();
		tokens = new ArrayList<Token>();
	    lexer.tokenize(reader,context.lexemes);
	    parse();
	}
	
	private void parse(){
		while(lexer.hasNext()){
			Token next = lexer.next();
			next.setMeta(new DocAttributes());
			next.accept(visitor);
			tokens.add(next);
		}
	}
	
	public List<Token> getTokens(){
		return tokens;
	}

	private class UIVisitor implements TokenVisitor {

		boolean command = true;
		Token comparable = null;

		private Stack<Token> charStack = new Stack<Token>();

		@Override
		public void visitComments(Token token) {
		}

		@Override
		public void visitEol(Token token) {
			command = true;
		}
		

		@Override
		public void visitGrammar(Token token) {
			char ch = token.getString().charAt(0);
			switch (ch) {
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
					if (comparable.getString().charAt(0) == '(') {
						((DocAttributes) token.getMeta()).setPairedToken(comparable);
						((DocAttributes) comparable.getMeta()).setPairedToken(token);
					}
				}
				break;
			case ']':
				comparable = null;
				if (!charStack.empty()) {
					comparable = charStack.pop();
					if (comparable.getString().charAt(0) == '[') {
						((DocAttributes) token.getMeta()).setPairedToken(comparable);
						((DocAttributes) comparable.getMeta()).setPairedToken(token);
					}
				}
				break;
			case '}':
				comparable = null;
				if (!charStack.empty()) {
					comparable = charStack.pop();
					if (comparable.getString().charAt(0) == '{') {
						((DocAttributes) token.getMeta()).setPairedToken(comparable);
						((DocAttributes) comparable.getMeta()).setPairedToken(token);
					}
				}
			}
		}

		@Override
		public void visitIdentifier(Token token) {
			if (command) {
				((DocAttributes) token.getMeta()).setCommand(true);
				command = false;
			}
		}

		@Override
		public void visitNumbers(Token token) {
		}

		@Override
		public void visitQuote(Token token) {
		}

		@Override
		public void visitWhitespace(Token token) {
		}

	}


}
