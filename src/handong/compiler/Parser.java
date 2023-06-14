package handong.compiler;


import java.util.Stack ;
import java.util.ArrayList;

public class Parser {
	private Stack<String> pstack = new Stack<>();
	private ArrayList<String> declaredID = new ArrayList<>();
	private ArrayList<Token> lexemes = new ArrayList<Token>();
	private int index;
	
	public void parse(ArrayList<Token> tokens) {
		index = 0;
		lexemes = tokens;
		declaredID.add("temp");
        pstack.push("$") ;
		System.out.println("Parsing Stack: " + pstack.toString());
		codeBlock();
        System.out.println("Paring OK!!");
	}
	
	private void codeBlock() {
		pstack.push("program_end");
		pstack.push("statements");
		pstack.push("program_begin");
		pstack.push("IDENTIFIER");
		pstack.push("program") ;
		System.out.println("Parsing Stack: " + pstack.toString());
		match("KEYWORD", "program");
        match("IDENTIFIER");
        match("KEYWORD", "program_begin");
        statements();
        match("KEYWORD", "program_end");
	}
	
	private void statements() {
        // System.out.println("Statements");
        while (check("KEYWORD") || check("IDENTIFIER") || check("SPECIAL_CHAR") || check("NUMBER") || check("STRING_LITERAL")) {
        	if (check("KEYWORD", "end") || check("KEYWORD", "program_end") || check("KEYWORD", "EOS")) {
                break;
            }
            statement();
        }
        pstack.pop();
		System.out.println("Parsing Stack: " + pstack.toString());
    }
	
	private void statement() {
        // System.out.println("Statement");
		pstack.push("statement") ;
		System.out.println("Parsing Stack: " + pstack.toString());
        if (check("KEYWORD")) {
            if (check("KEYWORD", "integer") || check("KEYWORD", "float") ) {
                assignStmt();
            }
            else if (check("KEYWORD", "if")) {
                ifStmt();
            }
            else if (check("KEYWORD", "while")) {
                loopStmt();
            }
            else if (check("KEYWORD", "for")) {
                loopStmt();
            }
            else if (check("KEYWORD", "display")) {
                fcallStmt();
            }
            else if (check("KEYWORD", "break")) {
            	pstack.pop();
            	pstack.push("break");
            	pstack.push(";");
        		System.out.println("Parsing Stack: " + pstack.toString());
                match("KEYWORD");
                match("STATEMENT_TERMINATOR");
            }
//            else if (check("KEYWORD", "end") || check("program_end")) {
//            	
//            }
            else {
                throw new RuntimeException("Unexpected keyword: " + lexemes.get(index).getTokenValue());
            }
        }
        else if (check("IDENTIFIER")) {
        	assignStmt();
        }
        else {
            throw new RuntimeException("\nUnexpected token: " + lexemes.get(index).getTokenCategory() + " : " + lexemes.get(index).getTokenValue());
        }
    }
	
	private void assignStmt() {
    	pstack.pop();
    	pstack.push("assignStmt");
		System.out.println("Parsing Stack: " + pstack.toString());
		pstack.pop();
		pstack.push(";");
		pstack.push("declator");
		pstack.push("assingExpr");
		pstack.push("IDENTIFIER");
		pstack.push("integer");
		System.out.println("Parsing Stack: " + pstack.toString());
//        System.out.println("	AssignStmt");
        if (check("KEYWORD")) {
        	match("KEYWORD");
        	declaredID.add(lexemes.get(index).getTokenValue());
        }
        else {
        	int ix = 0;
        	for(String id : declaredID) {
        		if(lexemes.get(index).getTokenValue().equals(id))
        			ix++;
        	}
        	if(ix == 0) {
        		throw new RuntimeException("\nUndeclared IDENTIFIED: " + lexemes.get(index).getTokenValue());
            }
        	pstack.pop();
    		System.out.println("Parsing Stack: " + pstack.toString());
        }
        match("IDENTIFIER");
        assignExpr();
        declator();
        match("STATEMENT_TERMINATOR");
    }
	
	private void ifStmt() {
    	pstack.pop();
    	pstack.push("ifStmt");
		System.out.println("Parsing Stack: " + pstack.toString());
		pstack.pop();
		pstack.push("elsesStmt");
		pstack.push("block");
		pstack.push(")");
		pstack.push("eqExpr");
		pstack.push("(");
		pstack.push("if");
		System.out.println("Parsing Stack: " + pstack.toString());
		// System.out.println("	IfStmt");
        match("KEYWORD", "if");
        match("SPECIAL_CHAR", "(");
        eqExpr();
        match("SPECIAL_CHAR", ")");
        block();
        elsesStmt();
    }
	
	private void elsesStmt() {
    	pstack.pop();
        // System.out.println("ElsesStmt");
        if (check("KEYWORD", "else")) {
    		pstack.push("block");
    		pstack.push("else");
    		System.out.println("Parsing Stack: " + pstack.toString());
            match("KEYWORD", "else");
            block();
        }
        else if (check("KEYWORD", "elseif")) {
    		pstack.push("elsesStmt");
    		pstack.push("block");
    		pstack.push(")");
    		pstack.push("exExpr");
    		pstack.push("(");
    		pstack.push("elseif");
    		System.out.println("Parsing Stack: " + pstack.toString());
            match("KEYWORD", "elseif");
            match("SPECIAL_CHAR", "(");
            eqExpr();
            match("SPECIAL_CHAR", ")");
            block();
            elsesStmt();
    		System.out.println("Parsing Stack: " + pstack.toString());
        }
	}
	
	private void block() {
    	pstack.pop();
		pstack.push("end");
		pstack.push("statements");
		pstack.push("begin");
		System.out.println("Parsing Stack: " + pstack.toString());
        // System.out.println("Block");
        match("KEYWORD", "begin");
        statements();
        match("KEYWORD", "end");
    }
	
	private void loopStmt() {
		pstack.pop();
    	pstack.push("loopStmt");
		System.out.println("Parsing Stack: " + pstack.toString());
        // System.out.println("LoopStmt");
        if (check("KEYWORD", "while")) {
            whileStmt();
        }
        else if (check("KEYWORD", "for")) {
            forStmt();
        }
        else {
            throw new RuntimeException("\nUnexpected keyword: " + lexemes.get(index).getTokenValue());
        }
    }
	
	private void whileStmt() {
    	pstack.pop();
    	pstack.push("whileStmt");
		System.out.println("Parsing Stack: " + pstack.toString());
        pstack.push("block");
        pstack.push(")");
        pstack.push("eqExpr");
        pstack.push("(");
        pstack.push("while");
		System.out.println("Parsing Stack: " + pstack.toString());
		// System.out.println("	WhileStmt");
        match("KEYWORD", "while");
        match("SPECIAL_CHAR", "(");
        eqExpr();
        match("SPECIAL_CHAR", ")");
        block();
    }

    private void forStmt() {
    	pstack.pop();
    	pstack.push("forStmt");
		System.out.println("Parsing Stack: " + pstack.toString());
		pstack.push("blcok");
		pstack.push(")");
		pstack.push("forUpdate");
		pstack.push(";");
		pstack.push("forTermination");
		pstack.push(";");
		pstack.push("forInit");
		pstack.push("(");
		pstack.push("for");
		System.out.println("Parsing Stack: " + pstack.toString());
    	// System.out.println("	ForStmt");
        match("KEYWORD", "for");
        match("SPECIAL_CHAR", "(");
        forInit();
        match("STATEMENT_TERMINATOR");
        forTermination();
        match("STATEMENT_TERMINATOR");
        forUpdate();
        match("SPECIAL_CHAR", ")");
        block();
    }
    
    private void forInit() {
    	pstack.pop();
    	pstack.push("assignExpr");
    	pstack.push("IDENTIFIER");
        // System.out.println("ForInit");
    	if(check("IDENTIFIER")) {
    		System.out.println("Parsing Stack: " + pstack.toString());
    		match("IDENTIFIER");
    		assignExpr();
    	}
    	else if (check("KEYWORD", "integer") || check("KEYWORD", "float")) {
    		pstack.push("integer");
    		System.out.println("Parsing Stack: " + pstack.toString());
    		match("KEYWORD");
    		match("IDENTIFIER");
    		assignExpr();
    	}
    }
    
    private void forTermination() {
    	pstack.pop();
    	pstack.push("expr");
    	pstack.push("OPERATOR");
    	pstack.push("IDENTIFIER");
		System.out.println("Parsing Stack: " + pstack.toString());
        // System.out.println("ForTermination");
    	match("IDENTIFIER");
    	match("OPERATOR");
		expr();
    	
    }
    
    private void forUpdate() {
    	pstack.pop();
    	pstack.push("unaryExpr");
		System.out.println("Parsing Stack: " + pstack.toString());
        // System.out.println("ForUpdate");
        if(check("IDENTIFIER")) {
        	unaryExpr();
        }
    }
	
	private void fcallStmt() {
    	pstack.pop();
    	pstack.push("fcallStmt");
		System.out.println("Parsing Stack: " + pstack.toString());
		pstack.pop();
		pstack.push(";");
    	pstack.push(")");
    	pstack.push("expr");
    	pstack.push("(");
    	pstack.push("display");
		System.out.println("Parsing Stack: " + pstack.toString());
		//        System.out.println("	FcallStmt");
        match("KEYWORD", "display");
        match("SPECIAL_CHAR", "(");
        expr();
        match("SPECIAL_CHAR", ")");
        match("STATEMENT_TERMINATOR");
    }
	
	private void expr() {
		// System.out.println("	Expr");
		pstack.pop();
		pstack.push("assignExpr");
		System.out.println("Parsing Stack: " + pstack.toString());
		assignExpr();
	}
	
	private void assignExpr() {
		pstack.pop();
		pstack.push("eqExpr");
		// System.out.println("AssignExpr");
	    eqExpr();
	    if (check("OPERATOR", "=")) {
			System.out.println("Parsing Stack: " + pstack.toString());
			pstack.push("eqExpr");
			pstack.push("=");
			System.out.println("Parsing Stack: " + pstack.toString());
	    	match("OPERATOR", "=");
	        eqExpr();
	    }
	}
	
	private void eqExpr() {
		pstack.pop();
		pstack.push("relationalExpr");
		// System.out.println("EqExpr");
		relationalExpr();
		if ((check("OPERATOR", "==")) || check("OPERATOR", "!=")) {
			// System.out.println("Parsing Stack: " + pstack.toString());
			pstack.push("relationalExpr");
			pstack.push("==");
			match("OPERATOR");
			relationalExpr();
		}
	}

	private void relationalExpr() {
		pstack.pop();
		pstack.push("addExpr");
		// System.out.println("RelationalExpr");
		addExpr();
		if (check("OPERATOR", "<") || check("OPERATOR", ">") || check("OPERATOR", "<=") || check("OPERATOR", ">=")) {
			// System.out.println("Parsing Stack: " + pstack.toString());
			pstack.push("addExpr");
			pstack.push("operator");
			System.out.println("Parsing Stack: " + pstack.toString());
			match("OPERATOR");
			addExpr();
		}
	}

	private void addExpr() {
		pstack.pop();
		pstack.push("mulExpr");
		// System.out.println("AddExpr");
		mulExpr();
		if ((check("OPERATOR", "+")) || check("OPERATOR","-")) {
			// System.out.println("Parsing Stack: " + pstack.toString());
			pstack.push("mulExpr");
			pstack.push("+");
			System.out.println("Parsing Stack: " + pstack.toString());
			match("OPERATOR");
			mulExpr();
		}
	}

	private void mulExpr() {
		pstack.pop();
		pstack.push("primaryExpr");
		System.out.println("Parsing Stack: " + pstack.toString());
		// System.out.println("MulExpr");
		primaryExpr();
		if ((check("OPERATOR", "*")) || check("OPERATOR", "/") || check("OPERATOR", "%")) {	// scanner doesn't process '%' yet
			// System.out.println("Parsing Stack: " + pstack.toString());
			pstack.push("primaryExpr");
			pstack.push("*");
			match("OPERATOR");
			primaryExpr();
		}
	}

	private void unaryExpr() {
		pstack.pop();
		pstack.push("++");
		pstack.push("IDENTIFIER");
		System.out.println("Parsing Stack: " + pstack.toString());
		// System.out.println("UnaryExpr");
//		primaryExpr();
		if (check("IDENTIFIER")) {
			match("IDENTIFIER");
			match("OPERATOR", "++");
		}
	}
	
	private void primaryExpr() {
		pstack.pop();
		// System.out.println("PrimaryExpr");
		if (check("SPECIAL_CHAR", "(")) {
			pstack.push("(");
			System.out.println("Parsing Stack: " + pstack.toString());
			match("SPECIAL_CHAR");
		}
		else if (check("IDENTIFIER")) {
			pstack.push("IDENTIFIER");
    		System.out.println("Parsing Stack: " + pstack.toString());
			match("IDENTIFIER");
		}
		else if (check("NUMBER")) {
			pstack.push("NUMBER");
    		System.out.println("Parsing Stack: " + pstack.toString());
			match("NUMBER");
		}
		else if (check("STRING_LITERAL")) {
			pstack.push("STRING_LITERAL");
    		System.out.println("Parsing Stack: " + pstack.toString());
			match("STRING_LITERAL");
		}
		else {
			
		}
		// throw new RuntimeException("\nUnexpected token: " + lexemes.get(index).getTokenCategory() + " : " + lexemes.get(index).getTokenValue());
	}
	
	private void declator() {
		pstack.pop();
		System.out.println("Parsing Stack: " + pstack.toString());
		// System.out.println("Declator");
		if(check("SPECIAL_CHAR", ",")) {
			pstack.push("declator");
			pstack.push("assignExpr");
			pstack.push("=");
			pstack.push("IDENTIFIER");
			pstack.push(",");
			match("SPECIAL_CHAR", ",");
			declaredID.add(lexemes.get(index).getTokenValue());
			match("IDENTIFIER");
			
			if(check("OPERATOR", "=")) {
				match("OPERATOR");
				assignExpr();
			}
			else if(check("STATEMENT_TERMINATOR", ";")) {
//				match("STATEMENT_TERMINATOR");
				pstack.pop();
				pstack.pop();
				System.out.println("Parsing Stack: " + pstack.toString());
			}
			else {
				throw new RuntimeException("\nUnexpected token: " + lexemes.get(index).getTokenCategory() + " : " + lexemes.get(index).getTokenValue());
			}
			
			if(check("SPECIAL_CHAR", ","))
				declator();
			else {
				pstack.pop();
				System.out.println("Parsing Stack: " + pstack.toString());
			}
        }
	}
	
	private boolean check(String category){
		Token lexeme = lexemes.get(index);
		if(category.equals(lexeme.getTokenCategory()))
			return true;
		return false;
	}
	private boolean check(String category, String value){
		Token lexeme = lexemes.get(index);
		if(category.equals(lexeme.getTokenCategory()) && value.equals(lexeme.getTokenValue()))
			return true;
		return false;
	}
	
	private void match(String category){
		Token lexeme = lexemes.get(index);
		// System.out.println(index + " : " +lexeme.toString());
		if(category.equals(lexeme.getTokenCategory())) {
			System.out.println("   MATCH: <" +category + " : " + lexeme.getTokenValue() + ">");
			index++;
			pstack.pop() ;
			System.out.println("Parsing Stack: " + pstack.toString());
		}
		else {
			throw new RuntimeException("\nUnexpected token: " + lexeme.getTokenCategory() + " : " + lexeme.getTokenValue() + "\nexpected type: " + category);
		}
	}
	private void match(String category, String value){
		Token lexeme = lexemes.get(index);
		// System.out.println(index + " : " +lexeme.toString());
		if(category.equals(lexeme.getTokenCategory()) && value.equals(lexeme.getTokenValue()))  {
			System.out.println("   MATCH: <" + category + " : " + value + ">");
			index++;
			pstack.pop();
			System.out.println("Parsing Stack: " + pstack.toString());
		}
		else {
			throw new RuntimeException("\nUnexpected token: " + lexeme.getTokenCategory() + " : " + lexeme.getTokenValue() + "\nexpected type: " + category +" : "+ value);
		}
	}
}
