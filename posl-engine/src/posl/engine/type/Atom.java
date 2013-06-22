package posl.engine.type;

/**
 * An atom is an identifier in a programming language that stands for something else.
 * 
 * Within posl anything that is not a base type, such as a string or number is considered an
 * atom.
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
	
	public String getValue(){
		return string;
	}

	public String toString() {
		return string.toString();
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
