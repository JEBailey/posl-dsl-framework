package posl.editorkit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import posl.engine.api.Token;

/**
 * Provides some additional information based on the type of token that's being provided
 * 
 * 
 * 
 * @author jason
 *
 */
public class DocAttributes {

	private Token token;

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

	public Token getPairedToken() {
		return token;
	}

	public void setPairedToken(Token token) {
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
	private static Color commandColor = new Color(147, 0, 71);// Color.red.darker().darker();
	
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
		g2.setColor(isCommand() ? commandColor : color);
		g2.setFont(g2.getFont().deriveFont(isCommand() ? Font.BOLD : Font.PLAIN));

	}

}
