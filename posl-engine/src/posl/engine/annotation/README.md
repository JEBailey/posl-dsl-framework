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


## @Optional
Used to indicate that the 

```java
    @Command("sum")
    public static Object sum(@Collection List<Number> numbers) {
        return numbers.stream().mapToInt(x -> x.intValue()).sum();
    }
```

