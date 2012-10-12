package posl.editorkit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import posl.engine.api.IToken;

public class DocAttributes {

	private IToken token;

	private boolean command;

	private style currentStyle;

	public enum style {
		COMMENTS, INDENTIFIER, NUMBER, STRING, COMMAND, GRAMMAR, DEFAULT
	}
	
	public DocAttributes(){
		this.currentStyle = style.DEFAULT;
	}

	public void setStyle(style currentStyle) {
		this.currentStyle = currentStyle;
	}

	public boolean isPair() {
		return token != null;
	}

	public IToken getPairedToken() {
		return token;
	}

	public void setPairedToken(IToken token) {
		this.token = token;
	}

	public boolean isCommand() {
		return command;
	}

	public void setCommand(boolean command) {
		this.command = command;
	}

	public String textLocation() {
		return "text";
	}

	public void consume(Graphics2D g2) {
		Color color = Color.black;
		switch (currentStyle) {
		case COMMENTS:
			color = Color.gray;
			break;
		case NUMBER:
		case STRING:
			color = Color.green.darker().darker();
			break;
		case GRAMMAR:
			color = Color.blue;
			break;
		default:
			break;
		}
		g2.setColor(color);
		g2.setFont(g2.getFont().deriveFont(isCommand() ? Font.BOLD : Font.PLAIN));

	}

}
