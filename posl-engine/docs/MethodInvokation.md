#Method Invocation
When a method is registered in the framework, it's wrapped in a MethodProxy. The MethodProxy parses the parameters and sets the requirements for what get's passed into the method at the time the call from the script is invoked. This is done by the MethodProxy creating a new ParameterInfo object for each parameter on the method. When the Resolver is used to match script provided values to the wrapped Method, these ParameterInfo objects provide context on the mapping resolution.

![example screen](images/methodinvoke.png?raw=true)

1. The add value is mapped in the Scope to an Executable which wraps the Method
2. An unevaluated statement, if the Method being invoked had requested a Statement object this would have been passed in directly, since the requested type is a Number, the Statement is evaluated and then the result of that expression is mapped
3. Scope looks for the value associated with `x` in the Scope and attempts to map that Object to a Number.

##Types of Parameters
There are 3 types of Annotations and 2 Java Types that a method can use, to affect the handling of the script variables. If none of these Types or annotations are used, the Resolver will attempt to directly map the Object to the requested Type.

###Annotations
####`@Optional`
If the script does not provide a value, provides a null value to the method.

####`@Property`
Obtain the value for the parameter from the Context object directly. This is not reflected in the call to the method from the script.

####`@Collection` 
Takes the remaining variables and puts it into a Collection object

###Types
####Scope
When a method has an object of the `Scope` Type, it provides the current Scope to the Method

####Reference
`Reference` types are a special Object which encapsulates the unevaluated value.


