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

	@Override
	public String toString() {
		return string;
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
	
	@Override
	public int hashCode() {
		  return string.hashCode();
	}

}
