package handong.compiler;



public class Token {

	private String tokenCategory;
	private String tokenValue;
	
	public Token(String category, String value) {
		this.tokenCategory = category;
		this.tokenValue = value;
		
		String keyword[] = {"program", "program_begin", "integer", "float", "if", "begin", "display", "end", "elseif", "else",  "while", "for", "break", "program_end", "EOS"};
		for(String key : keyword) {
			if(value.equals(key)) {
				this.tokenCategory = "KEYWORD";
			}
		}
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

	@Override
	public String toString() {
		return tokenCategory + " : " + tokenValue + "\n";
	}


}
