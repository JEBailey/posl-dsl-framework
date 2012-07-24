package posl.editorkit;

import posl.editorkit.token.IToken;


public class DocAttributes {
	
	private IToken token;
	
	private boolean command;

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

}
