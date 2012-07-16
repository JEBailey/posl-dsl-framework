package posl.engine.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

public class PoslStream {

	private int[] data;
	
	private int index;
	
	private int mark;

	public PoslStream(InputStream in) {
		this(new InputStreamReader(in));
	}
	
	private void internationalize(CharSequence ch) {
		int[] reply = new int[ch.length()];
		int i = 0;
		for (int offset = 0; offset < ch.length(); ) {
			    reply[i] = Character.codePointAt(ch, offset);
			    offset += Character.charCount(reply[i++]);
		}
		data =  Arrays.copyOfRange(reply, 0, i);
	}

	public PoslStream(Reader reader) {
		StringBuilder out = new StringBuilder();
	    char[] b = new char[4096];
	    try {
			for (int n; (n = reader.read(b)) != -1;) {
			    out.append(b, 0, n);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		internationalize(out);
	}

	public int LA(int offset){
		return (index + offset) >= data.length ? -1 : data[index + offset];
	}
	
	public int val(){
		return index < data.length ? data[index] : -1;
	}
	
	
	public int pop(){
		return index < data.length ? data[index++] : -1;
	}
	
	public int pop(int offset){
		index += offset;
		return index < data.length ? data[index] : -1;
	}
	
	public boolean hasMore(){
		return index < data.length;
	}
	
	public int pos(){
		return index;
	}
	
	public void mark(){
		this.mark = index;
	}
	
	public void reset(){
		this.index = mark;
		mark = 0;
	}
	
	public int getMark(){
		return mark;
	}
	
	public String getSubString(){
		return new String(data,mark,index - mark);
	}
	
	public TokenMetaData getTokenMetaData(){
		return new TokenMetaData();
	}
	
	public class TokenMetaData {
		
		private String parsedText = new String(data,mark,index - mark);
		
		private int startIndex = mark;
		
		private int length = index - mark;
		
		public String getParsedText() {
			return parsedText;
		}

		public int getStartIndex() {
			return startIndex;
		}

		public int getLength() {
			return length;
		}
		
	}
}
