POSL
===========

Posl is a java based framework to implement custom domain specific languages (DSLs).  


How does it Work?
-----------
The parser for posl works in a single pass, procedural manner, converting input into tokens until it reaches an end of line token, and then attempts to execute that line.

sample:
```
print [+ [+ 4 5] [/ 9 3]]
```
The above statement will capture 11 tokens, with the brackets representing a single token that encompasses an unprocessed statement.

When the print command is executed it will be passed a single token of type statement which it will attempt to resolve to a value, this results in the parsing of the internal statement of
```
+ [+ 4 5] [/ 9 3]
```
Which results in '+' receiving two tokens both of which are then immediately evaluated.

This process of encapsulation of tokens can also be used to modify the behaviour of the capture to ignore EOLs if necessary, in the case of multi line comments
```
/**
* The tokenizer here ignores EOLs
*
*/
```
Or for Multiline statement blocks
```
while [> [-- x] 1 ] {
  print x
}
```
For the parser, the above is a single line of code, the fact that there are multiple end of lines embedded within the braces is hidden from the parser until it has to evaluate the content of that block.

