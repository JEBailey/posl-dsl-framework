POSL
===========
Posl is an expression based scripting framework which is tied to java method invokation. It provides a minimalistic structure from which to define a grammar for a Domain Specific Language. 


First Steps
-----------------
To use POSL, a Context is created which contains the desired functionality and then passed into the Interpreter with the script that needs to be evaluated.

```java
  Context context = new Context();
  context.load(CoreLangFeatures.class);
  context.load(CoreList.class);
  
  Object result = Interpreter.process(context, script);
```

Results from the processing can be accessed in two ways. The first is in the response of the Interpreter which will return the result of the last evaluation. The second is from the context itelf, which has access to any values that were set up in the top level scope.

