package posl.engine.core;


public class Stream {

	private CharSequence data;
	
	private int index;
	
	private int mark;
	
	private int length;

	public Stream(CharSequence data) {
		this.data = data;
		this.length = data.length();
	}
	

	/**
	 * looks ahead into the stream and returns the value
	 * located at the offset
	 * 
	 * @param offset
	 * @return value at stream location or -1 for end of stream
	 */
	public int la(int offset){
		return (index + offset) >= length ? -1 : data.charAt(index + offset);
	}
	
	/**
	 * current value 
	 * @return
	 */
	public char val(){
		return data.charAt(index);
	}
	
	
	/**
	 * moves the index and returns the current val
	 * 
	 * @return current <code>char</code>
	 */
	public char pop(){
		return data.charAt(index++);
	}
	
	
	/**
	 * moves the index the specified amount
	 * 
	 * 
	 * @param offset how far to move the index
	 */
	public void chomp(int offset){
		index += offset;
	}
	
	/**
	 * declares availability of content
	 * 
	 * @return 
	 */
	public boolean hasMore(){
		return index < length;
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
		return data.subSequence(mark,index - mark).toString();
	}
	
}
