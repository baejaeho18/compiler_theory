%{
#include <stdio.h>
%}
ends_with_a .*a\n
begins_with_a a.*\n
%option noyywrap
%%
{ends_with_a} ECHO;
{begins_with_a} ECHO ;
.*\n;
%%
main()
{ yylex(); return 0; }
