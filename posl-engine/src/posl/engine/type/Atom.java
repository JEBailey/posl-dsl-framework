package posl.engine.type;

/**
 * Textual identifier which represents another type.
 * 
 * The item represented could be any object.
 * 
 * 
 * @author je bailey
 *
 */
public class Atom {

	private String string;
	
	public Atom(String key){
		this.string = key;
	}

	public String toString() {
		return string;
	}
	

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
