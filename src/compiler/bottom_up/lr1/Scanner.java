package compiler.bottom_up.lr1;



import java.util.ArrayList;

public class Scanner {
	
	public ArrayList<Token> scanner(String input) {
		
		int state = 0, idx = 0, isAcceptState;
		char ch;
		String token = "";
		ArrayList<Token> tokens = new ArrayList<>();

		input += ' ';
		// System.out.println(input);
		while(idx < input.length()) {
			isAcceptState = 0;
			
			ch = input.charAt(idx++);
			state = nextState(state, ch);
			switch(state) {
			case -1:
				System.err.printf("typo error occurs.");
				return null;
//			case 12:
//				isAcceptState = 1;
			case 14, 15:
			case 10, 13:
				tokens.add(new Token(categorizer(state), token+ch));
				token = "";
				state = 0;
				break;
			case 4, 6, 8, 12:
				tokens.add(new Token(categorizer(state), token));
			case 2:
				token = ch+"";
				isAcceptState = 1;
				break;
			default:
				token += ch;
				break;
			}
			// System.out.println("("+state+")"+ch+", "+token);
			
			if(isAcceptState == 1) {
				state = nextState(0, ch);	
				switch(state) {
				case 12, 14, 15:
					tokens.add(new Token(categorizer(state), token));
					token = "";
					state = 0;
					break;
				}
				// System.out.println("("+state+")"+ch+", "+token);
			}
		}
		
		return tokens;
	}
	
	private static int nextState(int state, char ch) {
		switch(state) {
		case 0:
			return stateChecker(ch, 1, 3,-1, 5,-1, 9,12,11,11,14,15);
		case 1:
			return stateChecker(ch, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2);
		case 3:
			return stateChecker(ch, 4, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4);
		case 5:
			return stateChecker(ch, 6, 6, 6, 5, 7, 6, 6, 6, 6, 6, 6);
		case 7:
			return stateChecker(ch, 8, 8, 8, 7, 8, 8, 8, 8, 8, 8, 8);
		case 9:
			return stateChecker(ch, 9, 9, 9, 9, 9,10, 9, 9, 9, 9, 9);
		case 11:
			return stateChecker(ch,12,12,12,12,12,12,12,12,13,12,12);
		}
		return -1;
	};	
	
	private static String categorizer(int state) {
		switch(state) {
		case 4:
			return "IDENTIFIER";
		case 6:
		case 8:
			return "NUMBER";
		case 10:
			return "STRING_LITERAL";
		case 12:
		case 13:
			return "OPERATOR";
		case 14:
			return "STATEMENT_TERMINATOR";
		case 15:
			return "SPECIAL_CHAR";
		}
		return "KEYWORD";
	}
	
	private static int stateChecker(char ch, int ws, int letter, int downbar, int digit, int point, 
									int literal, int alOp, int frOp, int scOp, int termin, int spechar) {
	
		char characters[] = {' ', '\n', '\r', '\t', // ws(0-3)
							'*', '/', '<', '>', '!',// alone_op(4-5), first_op(6-11)
							'+', '-', '=',			//  second_op(9-11)
							';', '\"', '.', '_',	// terminator, literal, decimal point, downbar
							'(', ')', ','};			// special character
													// digit and letter left
		
		for(int i=0;i<characters.length;i++) {
			if(ch == characters[i]) {
				if(i < 4)
					return ws;
				else if(i < 6)
					return alOp;
				else if(i < 9)
					return frOp;
				else if(i < 12)
					return scOp;
				else if(i == 12)
					return termin;
				else if(i == 13)
					return literal;
				else if(i == 14)
					return point;
				else if(i == 15)
					return downbar;
				else
					return spechar;
			}
		}
		if(Character.isDigit(ch))
			return digit;
		else if( (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') )
			return letter;
//		else if(ch == '\0')
//			return ws;
		else
			return -1;
	}
	
}
