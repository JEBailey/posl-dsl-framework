#Method Invocation
When a method is registered in the framework, it's wrapped in a MethodProxy. The MethodProxy parses the parameters and sets the requirements for what get's passed into the method at the time the call from the script is invoked. This is done by the MethodProxy creating a new ParameterInfo object for each parameter on the method. When the Resolver is used to match script provided values to the wrapped Method, these ParameterInfo objects provide context on the mapping resolution.

![example screen](images/methodinvoke.png?raw=true)

##Types of Parameters
There are 3 types of Annotations and 2 Java Types that a method can use, to affect the handling of the script variables. If none of these Types or annotations are used, the Resolver will attempt to directly map the Object directly to the requested Type.

###Annotations
####Optional
`@Optional` If the script does not provide a value, provides a null value to the method.

####Context Property
`@Property` Obtain the value for the parameter from the Context object

####Collection
`@Collection` Takes the remaining variables and puts it into a Collection object


<more>

