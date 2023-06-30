%{
	#include <stdio.h>
	#include <ctype.h>
	int yylex() ;
	void yyerror(char *) ;
%}
%token DIGIT
%%
line : expr '\n' { putchar('\n'); } 
;
expr : expr '+' term { putchar('+'); }
	| expr '-' term { putchar('-'); }
	| term
;
term : DIGIT { printf("%d", yylval); }
;
%%
int yylex()
{
	int c ;
	while (1) {
		c = getchar() ;
		if (c == ' ' || c == '\t') ;
		else if (isdigit(c)) {
			yylval = c - '0' ;
			return DIGIT ;
		}
		else return c ;
	}
}
void yyerror(char *s) {
	fprintf(stderr, "%s\n", s) ;
}
int main(void) {
	if (yyparse() == 0) printf("Parsing Success!\n\n") ;
	else printf("Parsing Failed!\n\n") ;
	return 0 ;
}
