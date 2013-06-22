package posl.editorkit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import posl.engine.api.Token;

/**
 * Provides some additional information based on the type of token that's being provided
 * 
 * @author je bailey
 *
 */
public class DocAttributes {

	private Token token;

	private boolean command;

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

}
