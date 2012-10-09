package posl.editorkit;

import java.awt.Graphics2D;

import posl.engine.api.IToken;


public class DocAttributes {
	
	private IToken token;
	
	private boolean command;
	
	public enum style {
		COMMENTS,INDENTIFIER,NUMBER,STRING,COMMAND, GRAMMAR
	}

	public boolean isPair() {
		return token != null;
	}

	public IToken getToken() {
		return token;
	}

	public void setToken(IToken token) {
		this.token = token;
	}

	public boolean isCommand() {
		return command;
	}

	public void setCommand(boolean command) {
		this.command = command;
	}
	
	public String textLocation(){
		return "text";
	}

	public void consume(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}

	public void setStyle(style style) {
		// TODO Auto-generated method stub
		
	}

}
