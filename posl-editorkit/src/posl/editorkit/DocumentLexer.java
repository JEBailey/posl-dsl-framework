package posl.editorkit;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

import posl.editorkit.DocAttributes.style;
import posl.engine.api.ILexer;
import posl.engine.api.Token;
import posl.engine.api.TokenVisitor;
import posl.engine.core.Lexer;
import posl.engine.core.Parser;

public class DocumentLexer {

	static Logger log = Logger.getLogger(Parser.class.getName());

	private ILexer lexer = null;
	private List<Token> tokens = null;
	private UIVisitor visitor = new UIVisitor();

	public DocumentLexer() {
	}
	

	public void tokenize(Reader reader) {
		lexer = new Lexer();
		tokens = new ArrayList<Token>();
	    lexer.tokenize(reader);
	    parse();
	}
	
	private void parse(){
		while(lexer.hasNext()){
			Token next = lexer.next();
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
		DocAttributes attr = new DocAttributes();
		private Stack<Token> charStack = new Stack<Token>();

		@Override
		public void visitComments(Token token) {
			setDoc(token);
			attr.setStyle(style.COMMENTS);
		}

		@Override
		public void visitEol(Token token) {
			setDoc(token);
			command = true;
		}
		

		@Override
		public void visitGrammar(Token token) {
			setDoc(token);
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
						attr.setPairedToken(comparable);
						((DocAttributes) comparable.getMeta()).setPairedToken(token);
					}
				}
				break;
			case ']':
				comparable = null;
				if (!charStack.empty()) {
					comparable = charStack.pop();
					if (comparable.getString().charAt(0) == '[') {
						attr.setPairedToken(comparable);
						((DocAttributes) comparable.getMeta()).setPairedToken(token);
					}
				}
				break;
			case '}':
				comparable = null;
				if (!charStack.empty()) {
					comparable = charStack.pop();
					if (comparable.getString().charAt(0) == '{') {
						attr.setPairedToken(comparable);
						((DocAttributes) comparable.getMeta()).setPairedToken(token);
					}
				}
			}
			attr.setStyle(style.GRAMMAR);
		}

		private void setDoc(Token token) {
			attr = new DocAttributes();
			token.setMeta(attr);
		}

		@Override
		public void visitIdentifier(Token token) {
			setDoc(token);
			if (command) {
				attr.setCommand(true);
				command = false;
			}
			attr.setStyle(style.INDENTIFIER);
		}

		@Override
		public void visitNumbers(Token token) {
			setDoc(token);
			attr.setStyle(style.NUMBER);
		}

		@Override
		public void visitQuote(Token token) {
			setDoc(token);
			attr.setStyle(style.STRING);
		}

		@Override
		public void visitWhitespace(Token token) {
			setDoc(token);
		}

	}


}
