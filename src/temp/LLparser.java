package temp;

import java.util.*;

import temp.SmallScan;

public class LLparser {
    private Stack<String> stack = new Stack<>();
    private Map<String, Map<String, String>> parsingTable;

    private SmallScan smallScan = new SmallScan();
    private static ArrayList<SmallScan.Token> tokens;
    private int pos = 0;
    private SmallScan.Token lookahead;

    int i = 0;
 
    public static void main(String[] args) {
        LLparser parser = new LLparser();
        parser.run(args);
    }


    private void run(String[] args) {
        tokens = smallScan.run(args);
        lookahead = nextToken();
        // Start the parsing process.
        System.out.println("Parsing Stack:");
        parse();
        System.out.println("Paring OK!!");
    }

    public void parse() {
        compilationUnit();
    }

    private void match(String expectedType) {
        if (lookahead.getType().equals(expectedType)) {
            System.out.println("Match: " + lookahead.getType() + " : " + lookahead.getValue());
            lookahead = nextToken();
        } else {
            throw new RuntimeException("Unexpected token: " + lookahead.getType() + " : " + lookahead.getValue() + " expected type: " + expectedType);
        }
    }

    private void match(String expectedType, String word) {
        if (lookahead.getType().equals(expectedType) && lookahead.getValue().equals(word)) {
            System.out.println("Match: " + lookahead.getType() + " : " + lookahead.getValue());
            lookahead = nextToken();
        } else {
            throw new RuntimeException("Unexpected token: " + lookahead.getType() + " : " + lookahead.getValue());
        }
    }

    private void compilationUnit() {
        System.out.println("Non-terminal: compilation unit");
        match("KEYWORD", "program");
        match("IDENTIFIER");
        match("KEYWORD", "program_begin");
        statements();
        match("KEYWORD", "program_end");
    }

    private void statements() {
        System.out.println("Non-terminal: statements");
        while (lookahead.getType().equals("KEYWORD") || lookahead.getType().equals("IDENTIFIER") || lookahead.getType().equals("specialChar") || lookahead.getType().equals("NUMBER") || lookahead.getType().equals("STRING_LITERAL")) {
            if (lookahead.getType().equals("KEYWORD") && (lookahead.getValue().equals("end") || lookahead.getValue().equals("program_end"))) {
                break;
            }
            statement();
        }
    }

    private void statement() {
        System.out.println("Non-terminal: statement");
        if (lookahead.getType().equals("KEYWORD")) {
            String keyword = lookahead.getValue();
            if (keyword.equals("integer")) {
                declarationStmt();
            } else if (keyword.equals("if")) {
                selectStmt();
            } else if (keyword.equals("while")) {
                loopStmt();
            } else if (keyword.equals("for")) {
                loopStmt();
            } else if (keyword.equals("display")) {
                displayStmt();
            } else if (keyword.equals("break")) {       // to handle break; in while loop
                match("KEYWORD");
                match("STATEMENT_TERMINATOR");
            } else {
                throw new RuntimeException("Unexpected keyword: " + keyword);
            }
        } else if (lookahead.getType().equals("IDENTIFIER") || lookahead.getType().equals("SPECIAL_CHAR") || lookahead.getType().equals("NUMBER") || lookahead.getType().equals("STRING_LITERAL")) {
            statementExpr();
        } else {
            throw new RuntimeException("Unexpected token: " + lookahead.getType() + " : " + lookahead.getValue());
        }
    }

    private void displayStmt() {
        match("KEYWORD", "display");
        match("SPECIAL_CHAR", "(");
        if (lookahead.getType().equals("STRING_LITERAL")) {
            lookahead = nextToken();
        }
        match("SPECIAL_CHAR", ")");
        match("STATEMENT_TERMINATOR");
    }

    private void selectStmt() {
        System.out.println("Non-terminal: select-stmt");
        ifThenElseifStmt();
    }

    private void ifThenElseifStmt() {
        System.out.println("Non-terminal: if-then-elseif stmt");
        match("KEYWORD");
        match("SPECIAL_CHAR");
        expr();
        match("SPECIAL_CHAR");
        block();
        elsePart();
    }

    private void elsePart() {
        System.out.println("Non-terminal: else part");
        if (lookahead.getType().equals("KEYWORD") && lookahead.getValue().equals("else")) {
            match("KEYWORD");
            if (lookahead.getType().equals("KEYWORD") && lookahead.getValue().equals("if")) {
                selectStmt();
            }
            block();
        } else if (lookahead.getType().equals("KEYWORD") && lookahead.getValue().equals("elseif")) {
            match("KEYWORD");
            match("SPECIAL_CHAR");
            expr();
            match("SPECIAL_CHAR");
            block();
            elsePart();
        }
    }

    private void loopStmt() {
        System.out.println("Non-terminal: loop stmt");
        if (lookahead.getValue().equals("while")) {
            whileStmt();
        } else if (lookahead.getValue().equals("for")) {
            forStmt();
        } else {
            throw new RuntimeException("Unexpected token: " + lookahead.getType() + " : " + lookahead.getValue());
        }
    }

    private void whileStmt() {
        System.out.println("Non-terminal: while stmt");
        match("KEYWORD");
        match("SPECIAL_CHAR");
        expr();
        match("SPECIAL_CHAR");
        block();
    }

    private void forStmt() {
        System.out.println("Non-terminal: for stmt");
        match("KEYWORD");
        match("SPECIAL_CHAR");
        forInit();
        match("STATEMENT_TERMINATOR");
        expr();
        match("STATEMENT_TERMINATOR");
        forUpdate();
        match("SPECIAL_CHAR", ")");
        block();
    }

    private void forInit() {
        System.out.println("Non-terminal: for init");
        if (lookahead.getValue().equals("integer") || lookahead.getType().equals("IDENTIFIER")) {
            if (lookahead.getValue().equals("integer")) {
                match("KEYWORD");
            }
            match("IDENTIFIER");
            if (lookahead.getType().equals("OPERATOR") && lookahead.getValue().equals("=")) {
                match("OPERATOR");
                expr();
            }
            while (lookahead.getType().equals("SPECIAL_CHAR") && lookahead.getValue().equals(",")) {
                match("SPECIAL_CHAR");
                match("IDENTIFIER");
                if (lookahead.getType().equals("OPERATOR") && lookahead.getValue().equals("=")) {
                    match("OPERATOR");
                    expr();
                }
            }
        } else if (lookahead.getType().equals("SPECIAL_CHAR")) {
            // epsilon
        } else {
            throw new RuntimeException("Unexpected token: " + lookahead.getType() + " : " + lookahead.getValue());
        }
    }

    private void forUpdate() {
        System.out.println("Non-terminal: for update");
        if (lookahead.getType().equals("IDENTIFIER") || lookahead.getType().equals("OPERATOR")) {
            incrementalExpr();
        } else if (lookahead.getType().equals("SPECIAL_CHAR")) {
            // epsilon
        } else {
            throw new RuntimeException("Unexpected token: " + lookahead.getType() + " : " + lookahead.getValue());
        }
    }

    private void declarationStmt() {
        System.out.println("Non-terminal: declaration stmt");
        match("KEYWORD");
        match("IDENTIFIER");
        variableDeclarators();
        match("STATEMENT_TERMINATOR");
    }


    private void expr() {
        System.out.println("Non-terminal: expr");
        assignExpr();
    }

    private void statementExpr() {
        System.out.println("Non-terminal: statement-expr");
        expr();
        match("STATEMENT_TERMINATOR");
    }

    private void assignExpr() {
        System.out.println("Non-terminal: assign-expr");
        equalExpr();
        while (lookahead.getType().equals("OPERATOR") && lookahead.getValue().equals("=")) {
            match("OPERATOR");
            equalExpr();
        }
    }


    private void equalExpr() {
        System.out.println("Non-terminal: equal-expr");
        compareExpr();
        while ((lookahead.getType().equals("OPERATOR") && lookahead.getValue().equals("==")) || (lookahead.getType().equals("OPERATOR") && lookahead.getValue().equals("!="))) {
            match("OPERATOR");
            compareExpr();
        }
    }

    private void compareExpr() {
        System.out.println("Non-terminal: compare-expr");
        addExpr();
        while (lookahead.getType().equals("OPERATOR") &&
                (lookahead.getValue().equals("<") ||
                        lookahead.getValue().equals(">") ||
                        lookahead.getValue().equals("<=") ||
                        lookahead.getValue().equals(">=") ||
                        lookahead.getValue().equals("==") ||
                        lookahead.getValue().equals("!="))) {
            match("OPERATOR");
            addExpr();
        }
    }

    private void addExpr() {
        System.out.println("Non-terminal: add-expr");
        mulExpr();
        while ((lookahead.getType().equals("OPERATOR") && lookahead.getValue().equals("+")) || (lookahead.getType().equals("OPERATOR") && lookahead.getValue().equals("-"))) {
            match("OPERATOR");
            mulExpr();
        }
    }

    private void mulExpr() {
        System.out.println("Non-terminal: mul-expr");
        postfixExpr();
        while ((lookahead.getType().equals("OPERATOR") && lookahead.getValue().equals("*")) || (lookahead.getType().equals("OPERATOR") && lookahead.getValue().equals("/"))) {
            match("OPERATOR");
            postfixExpr();
        }
    }

    private void postfixExpr() {
        System.out.println("Non-terminal: postfix-expr");
        primaryExpr();
    }

    private void primaryExpr() {
        System.out.println("Non-terminal: primary-expr");
        if (lookahead.getType().equals("SPECIAL_CHAR") || lookahead.getType().equals("IDENTIFIER") || lookahead.getType().equals("NUMBER") || lookahead.getType().equals("STRING_LITERAL")) {
            match(lookahead.getType());
        } else {
            throw new RuntimeException("Unexpected token: " + lookahead.getType() + " : " + lookahead.getValue());
        }
    }

    private void variableDeclarators() {
        System.out.println("Non-terminal: variable-declarators");
        variableDeclarator();
        while (lookahead.getType().equals("SPECIAL_CHAR") && lookahead.getValue().equals(",")) {
            match("SPECIAL_CHAR");
            match("IDENTIFIER");
            variableDeclarator();
        }
    }

    private void variableDeclarator() {
        System.out.println("Non-terminal: variable-declarator");
        if (lookahead.getType().equals("OPERATOR") && lookahead.getValue().equals("=")) {
            match("OPERATOR");
            expr();
        } else if (lookahead.getType().equals("SPECIAL_CHAR") && lookahead.getValue().equals(",")) {
            // epsilon
        } else if (lookahead.getType().equals("STATEMENT_TERMINATOR")) {
            // epsilon
        } else {
            throw new RuntimeException("Unexpected token: " + lookahead.getType() + " : " + lookahead.getValue());
        }
    }

    private void incrementalExpr() {
        System.out.println("Non-terminal: incremental-expr");
        match("IDENTIFIER");
        match("OPERATOR");
    }

    private void block() {
        System.out.println("Non-terminal: block");
        match("KEYWORD", "begin");
        statements();
        match("KEYWORD", "end");
    }


    private SmallScan.Token nextToken() {
        SmallScan.Token temp = tokens.get(pos);
        pos++;
        return temp;
    }
}
