#POSL
Posl is an expression based scripting framework which is tied to java method invokation. It provides a minimalistic structure from which to define a grammar for a Domain Specific Language. 


##First Steps
### Using an existing implementation
To use POSL, a Context is created which contains the desired functionality and then passed into the Interpreter with the script that needs to be evaluated.

```java
  Context context = new Context();
  context.load(CoreLangFeatures.class);
  context.load(CoreList.class);
  
  Object result = Interpreter.process(context, script);
```

Results from the processing can be accessed in two ways. The first is in the response of the Interpreter which will return the result of the last expression. The second is from the context itelf, which has access to any values that were set in the top level scope.

In addition to the direct loading of Library objects, plugin functionality is supported through an implementation of the ```PoslImpl``` interface. This will allow you to load a specific set of features into the context via the `PoslProvider`

```java
  Context context = PoslProvider.getContext("posl.lang.core");
```

### Extending a DSL
Extending or enhancing a specific implementation of POSL can be done by implementing a Library class or Object. A Library is a Pojo or class that has annotations added to it that helps the context determine how a specific function will be called. Details on implementing a library object are found here.

Once a Library is defined it can be added to the context via the ```load``` function.


### Implementing a DSL
//TODO:
