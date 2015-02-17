package posl.engine.lexeme;

import java.util.List;
import java.util.Stack;

import posl.engine.api.Collector;
import posl.engine.api.Lexeme;
import posl.engine.api.Token;
import posl.engine.api.TokenVisitor;
import posl.engine.core.BasicToken;
import posl.engine.core.Stream;
import posl.engine.type.ListCollector;
import posl.engine.type.MultiLineStatement;
import posl.engine.type.Statement;

/**
 * Represents the physical structure components of a basic Posl implementation.
 * The following represent built in types of structures
 * <p>
 * `{` and `}` surround a block of tokens which can be on multiple lines
 * `[` and `]` surround a single expression 
 * `(` and `)` represent a list of tokens
 * </p>
 * @author je bailey
 * 
 */
public class Grammar implements Lexeme {

	@Override
	public boolean consume(List<Token> tokens, Stream ps) {
		ps.setMark();
		boolean grammar = false;
		switch (ps.val()) {
		case '{':
		case '}':
		case '(':
		case ')':
		case '[':
		case ']':
			grammar = true;
		}
		if (grammar) {
			tokens.add(new Inner((char) ps.pop(), ps.getMark()));
		}
		return grammar;
	}

	private class Inner extends BasicToken {

		private char charValue;

		public Inner(char value, int i) {
			this.charValue = value;
			this.value = Character.toString(value);
			this.startPos = i;
			this.endPos = i + 1;
		}

		@Override
		public Collector consume(Collector collector,
				Stack<Collector> collectors, Stack<Character> charStack) {
			switch (charValue) {
			case '[':
				charStack.push(']');
				collectors.push(collector);
				collector = new Statement(startPos);
				break;
			case '(':
				charStack.push(')');
				collectors.push(collector);
				collector = new ListCollector();
				break;
			case '{':
				charStack.push('}');
				collectors.push(collector);
				collector = new MultiLineStatement();
				break;
			case ')':
				if (!charStack.empty() && charStack.pop() == charValue) {
					Collector temp = collector;
					collector = collectors.pop();
					collector.add((List<?>) temp.get());
				} else {
					// throw new
					// PoslException(lineNumber,"could not match parenthesis");
				}
				break;
			case ']':
				if (!charStack.empty() && charStack.pop() == charValue) {
					Object temp = collector;
					collector = collectors.pop();
					collector.add(temp);
				} else {
					// throw new
					// PoslException(lineNumber,"could not match square bracket");
				}
				break;
			case '}':
				if (!charStack.empty() && charStack.pop() == charValue) {
					collector.isFinished();
					Object temp = collector;
					collector = collectors.pop();
					collector.add(temp);
				} else {
					// throw new
					// PoslException(lineNumber,"could not match brace");
				}
				break;
			}
			return collector;
		}

		@Override
		public void accept(TokenVisitor visitor) {
			visitor.visitGrammar(this);
		}

	}

}
