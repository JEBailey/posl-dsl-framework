grammar posl;

program :	verticalSpace statement (eos statement)* verticalSpace? EOF;

statement
	:	value+;

block 	:	'{' (eos statement)* verticalSpace '}';

list	:	'(' statement?')';

value	:	id|NUMBER|STRING|block|list|bracketed_statement;


bracketed_statement
	:	'[' statement? ']';


id	:	KEYWORD|SPECIAL;

verticalSpace
	:	EOL*;

eos	:	EOL+;

EOL	:	'\r'?'\n';

KEYWORD  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

NUMBER
    :   '-'? DIGIT+  FLOAT?  EXPONENT?
    ;
    
 SPECIAL:	('<'|'>'|'-'|'+'|'/'|'\\'|'*')+;

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

WS  :   ( ' '
        | '\t'
        ) {$channel=HIDDEN;}
    ;

STRING
	: '"' ( options {greedy=false;} : . )* '"';    
	
	
fragment
FLOAT	: '.' DIGIT*;

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
DIGIT	:	
	('0'..'9');

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;
