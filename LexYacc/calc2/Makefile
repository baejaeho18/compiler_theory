all : calc2.y calc2.l
	bison -y -d calc2.y
	flex calc2.l
	gcc -c y.tab.c lex.yy.c
	gcc y.tab.o lex.yy.o -o calc2.exe
	./calc2.exe

clean:
	rm -rf *.o *.c *.h *.exe