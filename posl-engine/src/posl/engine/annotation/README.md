# Annotations

These annotations are used when constructing a java library of commands to be used within the posl framework

## @Command
Used to convert the function into a callable from the scripting language.

```java
    @Command("sum")
    public static Object sum(@Collection List<Number> numbers) {
        return numbers.stream().mapToInt(x -> x.intValue()).sum();
    }
```

will add the word `sum` into the scope as a callable which will execute the listed function

## @Collection
Used to indicate that the command will accept as many arguments as possible and collect them into a collection of the requested type

```java
    @Command("sum")
    public static Object sum(@Collection List<Number> numbers) {
        return numbers.stream().mapToInt(x -> x.intValue()).sum();
    }
```

script usage would be
```posl
    sum 2 3 4 5
```

@Collection should only be put on the last argument in the method.

## @Namespace
Used on a class to provide a namespace for the methods that are identified in that class

```java
	// flow control
	@Command("if")
	public static Object ifCommand(Boolean predicate,
			Reference commands,
			@Optional Reference elseCommands) throws PoslException {
		Object result = Boolean.FALSE;
		if (predicate) {
			result = commands.evaluate();
		} else {
			if (elseCommands != null) {//since it's optional
				result = elseCommands.evaluate();
			}
		}
		return result;
	}
```

script usage
```posl
    roger.wilco
```




## @Optional
Used to indicate that the argument is optional, if optional and not passed by the script, the incoming value is null

```java
	// flow control
	@Command("if")
	public static Object ifCommand(Boolean predicate,
			Reference commands,
			@Optional Reference elseCommands) throws PoslException {
		Object result = Boolean.FALSE;
		if (predicate) {
			result = commands.evaluate();
		} else {
			if (elseCommands != null) {//since it's optional
				result = elseCommands.evaluate();
			}
		}
		return result;
	}
```

script usage
```posl
    if [<  x 3] {
       print "43"
    } {
       print "hello"
    }
```
