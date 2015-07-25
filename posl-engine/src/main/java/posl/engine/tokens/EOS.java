package posl.engine.tokens;

import java.util.Stack;

import posl.engine.api.Collector;
import posl.engine.api.TokenVisitor;
import posl.engine.core.BasicToken;
import posl.engine.type.Statement;

public class EOS extends BasicToken {

	public EOS(String value, int start, int end) {
		this.value = value;
		this.startPos = start;
		this.endPos = end;
	}

	@Override
	public Collector consume(Collector collector,
			Stack<Collector> collectors, Stack<Character> charStack) {
		if (collector.isFinished()) {
			collectors.add(collector);
			collector = new Statement(startPos,collector.getLineNumber());
		}
		return collector;
	}

	@Override
	public void accept(TokenVisitor visitor) {
		visitor.visitEol(this);
	}

}
