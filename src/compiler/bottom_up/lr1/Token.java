package compiler.bottom_up.lr1;



public class Token {

	private String tokenCategory;
	private String tokenValue;
	private String token;	// for non-terminals
	
	public Token(String category, String value) {
		this.tokenCategory = category;
		this.tokenValue = value;
		
		String keyword[] = {"program", "program_begin", "integer", "float", "if", "begin", "display", "end", "elseif", "else",  "while", "for", "break", "program_end", "EOS"};
		for(String key : keyword) {
			if(value.equals(key)) {
				this.tokenCategory = "KEYWORD";
			}
		}
		
		if (tokenCategory == "IDENTIFIER")
			token = "identifier";
		else if (tokenCategory == "NUMBER")
			token = "number";
		else if (tokenCategory == "STRING_LITERAL")
			token = "literal";
		else if (tokenCategory == "OPERATOR" && !(tokenValue.equals("+") || tokenValue.equals("=") || tokenValue.equals("*") || tokenValue.equals("++")))
			token = "relational_operator";
		else
			token = tokenValue;
	}
	
	public Token(String token) {
		this.token = token ;
	}
	
	public String getTokenCategory() {
		return tokenCategory;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenCategory(String tokenCategory) {
		this.tokenCategory = tokenCategory;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	
	public String getToken() {
		return token ;
	}

	@Override
	public String toString() {
		return tokenCategory + " : " + tokenValue + "\n";
	}


}
