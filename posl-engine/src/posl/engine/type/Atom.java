package posl.engine.type;

public class Atom {

	private String string;
	
	public Atom(String key){
		this.string = key;
	}
	
	public String getValue(){
		return string;
	}

	@Override
	public String toString() {
		return string.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof Atom){
				return ((Atom)obj).toString().equalsIgnoreCase(string);
			}
		}
		return false;
	}
	
	public int hashCode() {
		  return string.hashCode();
	}

}
