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
	
	/**
	 * current value 
	 * @return
	 */
	public int val(){
		return index < data.length ? data[index] : -1;
	}
	
	
	/**
	 * moves the index and returns the current val
	 * returns -1 if at the end of the stream
	 * 
	 * @return
	 */
	public int pop(){
		return index < data.length ? data[index++] : -1;
	}
	
	
	/**
	 * moves the index the specified amount
	 * 
	 * 
	 * @param offset
	 * @return
	 */
	public int pop(int offset){
		index += offset;
		return index < data.length ? data[index] : -1;
	}
	
	/**
	 * declares availability of content
	 * 
	 * @return
	 */
	public boolean hasMore(){
		return index < data.length;
	}
	
	/**
	 * the current location
	 * 
	 * @return
	 */
	public int getPos(){
		return index;
	}
	
	
	/**
	 * marks the current position
	 * 
	 * 
	 */
	public void setMark(){
		this.mark = index;
	}
	
	
	/**
	 * resets the index to the saved
	 * position and sets the saved position
	 * to zero
	 */
	public void reset(){
		this.index = mark;
		mark = 0;
	}
	
	/**
	 * resets the index to the provided
	 * position and sets the saved position
	 * to zero
	 */
	public void reset(int newPos){
		this.index = newPos;
		mark = 0;
	}
	
	
	/**
	 * returns the saved position
	 * 
	 * @return
	 */
	public int getMark(){
		return mark;
	}
	
	/**
	 * Returns a section of the underlying stream starting 
	 * at the last marked point and ending at the current index
	 * 
	 * @return
	 */
	public String getSubString(){
		return new String(data,mark,index - mark);
	}
	
}
