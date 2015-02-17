#Method Invokation
When a method is registered in the framework, it's wrapped in a MethodProxy. The method proxy parses the parameters and sets the requirements for what get's passed into the method at the time the call from the script is invoked.

To do this, when the library is being loaded, the MethodProxy object creates a new ParameterInfo object for each parameter on the method.

One of the following types 

