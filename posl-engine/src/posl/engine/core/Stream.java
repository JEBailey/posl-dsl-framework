package posl.engine.core;


public class Stream {

	private char[] data;
	
	private int index;
	
	private int mark;

	public Stream(String data) {
		this.data = data.toCharArray();
	}
	

	/**
	 * looks ahead into the stream and returns the value
	 * located at the offset
	 * 
	 * @param offset
	 * @return value at stream location or -1 for end of stream
	 */
	public int la(int offset){
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
