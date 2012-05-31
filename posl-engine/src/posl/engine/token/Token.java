/*
 * Token.java
 *
 * Created on October 10, 2003, 5:35 PM
 */

package posl.engine.token;

import java.util.Stack;
import java.util.logging.Logger;

import posl.engine.api.IStatement;
import posl.engine.api.IToken;


/**
 * 
 * @author jason bailey
 */
public class Token implements IToken {
	
	private String string;
	public TokenType type;
	private int startOffset;
	private Object attributes;
	

	private static Logger log = Logger.getLogger(Token.class.getName());

	/** Creates a new instance of Token */
	private Token(String value, TokenType type,int startOffset) {
		log.fine("creating new token of value "+value);
		this.string = value;
		this.type = type;
		this.startOffset = startOffset;
	}
	
	
	public static Token STRING(String value,int startOffset){
		return new Token(value,TokenType.STRING, startOffset);
	}
	
	public static Token NUMBER(String value,int startOffset){
		return new Token(value,TokenType.NUMBER,startOffset);
	}
	
	
	public static Token EOL(int startOffset){
		return new Token("\n",TokenType.EOL,startOffset);
	}
	
	public static Token WORD(String value,int startOffset){
		return new Token(value,TokenType.ATOM,startOffset);
	}
	
	public static Token GRAMMAR(char i,int startOffset){
		return new Token(String.valueOf(i),TokenType.GRAMMAR, startOffset);
	}
	

	public static Token COMMENT(String value,int startOffset) {
		return new Token(value,TokenType.COMMENT,startOffset);
	}


	public String getScanValue() {
		return string.toString();
	}

	public String toString() {
		switch (type){
		case STRING:
		case ATOM:
		case NUMBER:
			return type.name() + "["+string.toString()+"]";
		case GRAMMAR:
			return "\'"+getCharValue()+"\'";
		case EOL:
			return "EOL";
		default:
			return type.toString();
		}
	}
	
	public char getCharValue(){
		return string.charAt(0);
	}


	public int getEndOffset() {
		int length = string.length();
		if (type == TokenType.STRING){
			length +=2;
		}
		return startOffset + length;
	}


	public Object getAttribute() {
		return attributes;
	}


	public void setAttribute(Object value) {
		this.attributes = value;
	}
	
	public String getString() {
		return string;
	}


	public int getStartOffset() {
		return startOffset;
	}
	
	public enum TokenType {
		EOL, NUMBER, ATOM, STRING, GRAMMAR, COMMENT
	}

	@Override
	public boolean consume(IStatement statement, Stack<IStatement> statements,
			Stack<Character> charStack) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
