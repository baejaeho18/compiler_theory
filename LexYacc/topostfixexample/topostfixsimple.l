%{
#include "y.tab.h"
#include <stdlib.h>
void yyerror(char *);
%}
%%
[0-9]+ {
	yylval = atoi(yytext);
	return DIGIT;
}
[-+()=/*\n] {return *yytext ; }
[\t] ;
.	yyerror("Unknown character");
%%
int yywrap(void) {
	return 1;
}
