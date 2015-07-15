package posl.engine.core;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import posl.engine.annotation.Command;
import posl.engine.annotation.Primitive;
import posl.engine.api.Lexeme;
import posl.engine.api.Parser;
import posl.engine.lexeme.Comments;
import posl.engine.lexeme.Eol;
import posl.engine.lexeme.Grammar;
import posl.engine.lexeme.Identifier;
import posl.engine.lexeme.Numbers;
import posl.engine.lexeme.QuotedString;
import posl.engine.lexeme.Special;
import posl.engine.lexeme.WhiteSpace;

public class Context{

	private Parser parser;
	private Scope scope;
	public List<Lexeme> lexemes;

	private static final String output = "__outputstream";

	private static final Logger log = Logger.getLogger(Context.class.getName());

	public Context() {
		this(new DefaultParser(), new Scope());
	}

	public Context(Scope scope) {
		this(new DefaultParser(), scope);
	}

	public Context(Parser parser, Scope scope) {
		this.parser = parser;
		this.scope = scope;
		this.lexemes = standardLexemes();
		scope.put(output, System.out);
	}

	public void load(Class<?> libraryClass) {
		Method[] methods = libraryClass.getMethods();
		loadMethods(null, methods);
	}

	public void load(Object libraryObject) {
		Method[] methods = libraryObject.getClass().getMethods();
		loadMethods(libraryObject, methods);
	}
	
	@SuppressWarnings("serial")
	public static List<Lexeme> standardLexemes(){
		return new LinkedList<Lexeme>(){{
			add(new WhiteSpace());
			add(new Comments());
			add(new Numbers());
			add(new Identifier());
			add(new QuotedString());
			add(new Grammar());
			add(new Special());
			add(new Eol());
		}};
	}

	public Parser getParser() {
		return parser;
	}

	public Scope getScope() {
		return scope;
	}

	/**
	 * facade to add a key value pair to the underlying scope
	 * 
	 * @param key A string representation
	 * @param value
	 * @return 
	 */
	public Object put(String key, Object value) {
		return scope.put(key, value);
	}
	
	public Object get(String key){
		return scope.get(key);
	}
	
	public boolean containsKey(String key){
		return scope.containsKey(key);
	}

	/**
	 * 
	 * 
	 * 
	 * @param object instance of class that the methods belong to
	 * @param methods all potential methods
	 */
	private void loadMethods(Object object, Method[] methods) {
		for (Method method : methods) {
			boolean isCommand = method.isAnnotationPresent(Command.class);
			boolean isPrimitive = method.isAnnotationPresent(Primitive.class);
			if (isCommand || isPrimitive) {
				if (!Modifier.isStatic(method.getModifiers()) && object == null) {
					log.severe("Attempting to add a non static command without an associated object");
					log.severe(method.getName());
					break;
				} else {
					String id = null;
					Object prior = null;
					if (isCommand) {
						id = method.getAnnotation(Command.class).value();
					}
					if (isPrimitive) {
						id = method.getAnnotation(Primitive.class).value();
					}
					if (id == null) {
						id = method.getName();
						log.fine("no id defined, using the method name: " + id);
					}
					if (isCommand) {
						prior = scope.put(id, new MethodProxy(method, object));
						log.fine("command : " + id + " has been added");
					} else {
						try {
							prior = scope.put(id, method.invoke(object));
						} catch (Exception e) {
							log.warning("error occured when attempting to create primitive: "
									+ id);
							prior = "";
						}
					}
					if (prior != null) {
						log.warning("adding " + id
								+ " displaced previously assigned object");
					}
				}
			}
		}
	}

	/**
	 * 
	 * 
	 * @return state of the parser
	 */
	public boolean isComplete() {
		return parser.complete();
	}

	public void setOutputStream(OutputStream os) {
		scope.put(output, os);
	}

	public void resetParser() {
		this.parser = new DefaultParser();
	}

}
