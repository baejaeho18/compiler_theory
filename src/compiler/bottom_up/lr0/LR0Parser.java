package compiler.bottom_up.lr0;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import handong.compiler.Scanner;
import handong.compiler.Token;

public class LR0Parser {

	private int index;
	private static Stack<String> pstack = new Stack<>();

    public static void main(String[] args) throws IOException {
		
		String input = "";
		int cnt = 0;
		int size;
		String filename = args[0];
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		/*		
		String str;
		while ((str = reader.readLine()) != null) {
            input += str;
            input += "\n";
            // System.out.println(str);
        }
        */
		input = reader.readLine();
        reader.close();
        
        Scanner tokenizer = new Scanner();
		ArrayList<Token> symbol_table = new ArrayList<Token>();
		symbol_table = tokenizer.scanner(input);
        System.out.println(symbol_table) ;
        size = input.length();

    	char currentInput = '$';
    	String currentState = "0";
    	String stack = currentInput + currentState;
        pstack.push(stack);
        System.out.println(pstack.toString());
        try {
	        while(true) {
	        	if ((currentInput = reduce(currentInput, currentState)) == '\0') {
	        		currentInput = input.charAt(cnt++);
	        	}
	        	else {
	        		currentState = ""+pstack.lastElement().charAt(1);
	        	}
	        	currentState = shift(currentState, currentInput);
	        	
	        	stack = currentInput + currentState;
	            pstack.push(stack);
	            System.out.println(pstack.toString());
	            if(currentState.equals("1") && cnt == size) {
	            	System.out.println("Parsing ok");
	            	break;
	            }
	            if(cnt > size) {
	            	System.out.println("Parsing Error");
	            	break;
	            }
	        }
        }
        catch (Exception e) {
        	System.out.println("Parsing Error");
        }
	}

    private static char reduce(char currentInput, String currentState) {
    	if(currentState.equals("2")) {
    		if(pstack.contains("a2")) {
    			pstack.pop();
    			return 'A';
    		}
    	}
    	if(currentState.equals("5")) {
    		if (pstack.contains("(3") && pstack.contains("A4") && pstack.contains(")5")) {
        		pstack.pop();
        		pstack.pop();
        		pstack.pop();
        		return 'A';
    		}
    	}
    	
    	return '\0';
    }
    
    private static String shift(String state, char input) {
        return stateChecker(state, input);        
    }
    
	private static String stateChecker(String state, char input) {
		// System.out.println(state+" "+input);
		if(state.equals("0")) {
			if (input == '(')
				return "3";
			else if (input == 'a')
				return "2";
			else if (input == 'A')
				return "1";
		}
		else if (state.equals("3")) {
			if (input == '(')
				return state;
			else if (input == 'a')
				return "2";
			else if (input == 'A')
				return "4";
		}
		else if (state.equals("4")) {
			if (input == ')')
				return "5";
		}
		return "-1";
	}
}
