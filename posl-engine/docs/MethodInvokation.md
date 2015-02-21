#Method Invocation
When a method is registered in the framework, it's wrapped in a MethodProxy. The MethodProxy parses the parameters and sets the requirements for what get's passed into the method at the time the call from the script is invoked. This is done by the MethodProxy creating  new ParameterInfo object for each parameter on the method. When the Resolver class is used to match script provided values to the wrapped Method, these ParameterInfo objects provide context on the mapping

##Types of Parameters
###Normal
An attempt will be made to map the object associated variable to the Type in the variable.

###Optional
`@Optional` If the script does not provide a value, provides a null value to the method.

###Context Property
`@Property` Obtain the value for the parameter from the Context object

###Collection
`@Collection` Takes the remaining variables and puts it into a Collection object


<more>

