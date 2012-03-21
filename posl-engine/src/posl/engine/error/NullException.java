package posl.engine.error;

@SuppressWarnings("serial")
public class NullException extends Throwable {

	private String value;
	
	public NullException(String message) {
		this.value = message;
	}

	@Override
	public String toString() {
		return this.value;
	}
	
	

}
