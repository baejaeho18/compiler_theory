package handong.compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SmallScan {

	public static void main(String[] args) throws IOException {
		
		
		// ArrayList<Token> tokens = new ArrayList<>();
		
		Reader reader = new Reader();
		String inputCode = reader.readFile(args[0]+".txt");
		// System.out.println(args[0]);

		// System.out.println(inputCode);
		Scanner tokenizer = new Scanner();
		ArrayList<Token> symbol_table = new ArrayList<Token>();
		symbol_table = tokenizer.scanner(inputCode);
		
		symbol_table.add(new Token("EOS", ""));
		// configure that symbol_talbe tokenized well
		// for(Token t : symbol_table)
		// 	System.out.print(t.toString());
		
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(args[0]+"_symbol.txt"));
		for (Token t: symbol_table) {
			if (t.getTokenCategory() == "IDENTIFIER")
				writer.write("identifier ");
			else if (t.getTokenCategory() == "NUMBER")
				writer.write("number ");
			else if (t.getTokenCategory() == "STRING_LITERAL")
				writer.write("literal ");
			else if (t.getTokenCategory() == "OPERATOR" && !(t.getTokenValue().equals("+") || t.getTokenValue().equals("=") || t.getTokenValue().equals("*") || t.getTokenValue().equals("++")))
				writer.write("relational_operator ");
			else
				writer.write(t.getTokenValue() + " ");
		}
		writer.close();
		
		
		//Parser parser = new Parser();
		//parser.parse(symbol_table);
	}
	
}
