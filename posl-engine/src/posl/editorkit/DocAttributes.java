package posl.editorkit;

import posl.engine.token.Token;

public class DocAttributes {
	
	private Token token;
	
	private boolean command;

	public boolean isPair() {
		return token != null;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
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

}
