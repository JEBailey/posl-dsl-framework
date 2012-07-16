package posl.engine.error;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PoslException extends Throwable {

	protected List<Entry> stack = new ArrayList<Entry>() {

		@Override
		public boolean add(Entry entry) {
			if (this.contains(entry)) {
				return false;
			}
			return super.add(entry);
		}

	};

	@Override
	public String getLocalizedMessage() {
		// TODO Auto-generated method stub
		return super.getLocalizedMessage();
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PoslException) {
			return initial().equals(((PoslException) obj).initial());
		}
		return false;
	}

	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do
	}

	public PoslException(int lineNumber, String message) {
		stack.add(new Entry(lineNumber, message));
	}

	public PoslException push(int lineNumber, String message) {
		stack.add(new Entry(lineNumber, message));
		return this;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry entry : stack) {
			sb.append(entry.toString());
			sb.append('\n');
		}
		return sb.toString();
	}

	private Entry initial() {
		if (!this.stack.isEmpty()) {
			return stack.get(0);
		}
		return new Entry("");
	}

	class Entry {
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Entry) {
				Entry other = (Entry) obj;
				if (this.lineNumber == other.lineNumber) {
					return this.message.equalsIgnoreCase(other.message);
				}
			}
			return false;
		}

		private int lineNumber = -1;
		private String message = "";

		public Entry(int lineNumber, String message) {
			this.lineNumber = lineNumber;
			this.message = message;
		}

		public Entry(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			if (lineNumber >= 0) {
				sb.append("LINE NO:");
				sb.append(this.lineNumber);
				sb.append(" ");
			}
			sb.append("ERROR:");
			sb.append(this.message);
			return sb.toString();
		}

	}

}
