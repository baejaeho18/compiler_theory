package handong.compiler;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

	public String readFile(String filePath) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		String inputCode = "";
		String str;
		while ((str = reader.readLine()) != null) {
            inputCode += str;
            inputCode += "\n";
            // System.out.println(str);
        }
        reader.close();

		return inputCode;
	}
	
}
