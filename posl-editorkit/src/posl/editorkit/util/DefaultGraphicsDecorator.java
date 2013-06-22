package posl.editorkit.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import posl.editorkit.DocAttributes;
import posl.engine.api.Token;

public class DefaultGraphicsDecorator implements GraphicsDecorator {

	
	private Graphics2D g2d;
	
	private static Color commandColor = new Color(147, 0, 71);// Color.red.darker().darker();
	private static Color defaultColor = Color.black;

	@Override
	public void visitComments(Token token) {
		set(token,Color.gray);
	}

	@Override
	public void visitEol(Token token) {
		set(token,defaultColor);
	}

	@Override
	public void visitGrammar(Token token) {
		set(token,Color.blue);
	}

	@Override
	public void visitIdentifier(Token token) {
		set(token,defaultColor);
	}

	@Override
	public void visitNumbers(Token token) {
		set(token,Color.green.darker().darker());
	}

	@Override
	public void visitQuote(Token token) {
		set(token,Color.green.darker().darker());
	}

	@Override
	public void visitWhitespace(Token token) {
		set(token,defaultColor);
	}

	@Override
	public GraphicsDecorator setGraphics(Graphics2D g2d) {
		this.g2d = g2d;
		return this;
	}
	
	
	private boolean isCommand(Token token){
		return ((DocAttributes)token.getMeta()).isCommand();
	}
	
	private void set(Token token,Color color){
		g2d.setColor(isCommand(token) ? commandColor : color);
		g2d.setFont(g2d.getFont().deriveFont(isCommand(token) ? Font.BOLD : Font.PLAIN));
	}

}
