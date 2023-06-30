%{
#include <stdio.h>
int lineno = 1 ;
%}
word (ab)*\n
%option noyywrap
%%
{word} { printf("%5d %s", lineno++, yytext); }
.*\n { printf("Wrong word!\n"); }
%%
int main(void)
{ yylex(); return 0; }
