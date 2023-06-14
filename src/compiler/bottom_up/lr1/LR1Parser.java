package compiler.bottom_up.lr1;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import org.apache.poi.xssf.usermodel.*;

import handong.compiler.Reader;

public class LR1Parser {

	private static ArrayList<Token> symbolTable = new ArrayList<Token>() ;
	private static String[][] parsingTable = new String[154][46];
	private static Stack<String> parsingStack = new Stack<>();
	
	static int cntToken;
	static int cntStep;
	
	public static void main(String[] args) throws Exception {
		createSymbolTable(args[0]);
		createParsingTable();
		
		parser();
	}
	
	private static void createSymbolTable(String inputFile) throws IOException {
		Reader reader = new Reader();
		String inputCode = reader.readFile(inputFile+".txt");
		// System.out.println(inputCode);
		Scanner tokenizer = new Scanner();
		symbolTable = tokenizer.scanner(inputCode);
		symbolTable.add(new Token("EOS", "$"));
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile+"_symbol.txt"));
		for (Token t: symbolTable) {
			if (t.getTokenCategory().equals("IDENTIFIER"))
				writer.write("identifier ");
			else if (t.getTokenCategory().equals("NUMBER"))
				writer.write("number ");
			else if (t.getTokenCategory().equals("STRING_LITERAL"))
				writer.write("literal ");
			else if (t.getTokenCategory().equals("OPERATOR") && !((t.getTokenValue().equals("+")) || t.getTokenValue().equals("=") || t.getTokenValue().equals("*") || t.getTokenValue().equals("++")))
				writer.write("relational_operator ");
			else
				writer.write(t.getTokenValue() + " ");
		}
		writer.close();
	}
	
	private static void createParsingTable() throws IOException {
		// read parsing table in excel
		// save them to 2-dimension array
//		String filePath = "C:\\Users\\USER\\Downloads\\JaehoBae_LRparser\\ParsingTable.xlsx";
//        String sheetName = "ParsingTable";
//        int startRow = 3;  // Starting row index (0-based)
//        int endRow = 156;  // Ending row index (0-based)
//        int startColumn = 1;  // Starting column index (0-based)
//        int endColumn = 46;  // Ending column index (0-based)
//
//        try {
//        	FileInputStream fis = new FileInputStream(filePath);
//        	XSSFWorkbook workbook = new XSSFWorkbook(fis);
//            XSSFSheet sheet = workbook.getSheet(sheetName);
//            if (sheet == null) {
//                System.out.println("Sheet '" + sheetName + "' not found!");
//                return;
//            }           
//            for (int rowIndex = startRow; rowIndex <= endRow; rowIndex++) {
//                XSSFRow row = sheet.getRow(rowIndex);
//                if (row == null)
//                    continue;
//
//                for (int columnIndex = startColumn; columnIndex <= endColumn; columnIndex++) {
//                    XSSFCell cell = row.getCell(columnIndex);
//                    if (cell == null)
//                        continue;
//                    parsingTable[rowIndex-3][columnIndex-1] = cell.getStringCellValue();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
		parsingTable[0][0] 	= "s2";
		parsingTable[0][27] = "1";
		parsingTable[1][25] = "acc";
		parsingTable[2][1] 	= "s3";
		parsingTable[3][2] 	= "s4";
		parsingTable[4][1] 	= "r33";
		parsingTable[4][4] 	= "s13";
		parsingTable[4][9] 	= "s14";
		parsingTable[4][10] = "s15";
		parsingTable[4][12] = "s17";
		parsingTable[4][14] = "s18";
		parsingTable[4][24] = "s20";
		parsingTable[4][28] = "5";
		parsingTable[4][29] = "6";
		parsingTable[4][30] = "7";
		parsingTable[4][31] = "8";
		parsingTable[4][32] = "9";
		parsingTable[4][33] = "16";
		parsingTable[4][34] = "10";
		parsingTable[4][35] = "11";
		parsingTable[4][36] = "12";
		parsingTable[4][45] = "19";
		parsingTable[5][3] 	= "s21";
		parsingTable[6][1] 	= "r33";
		parsingTable[6][3] 	= "r2";
		parsingTable[6][4] 	= "s13";
		parsingTable[6][9] 	= "s14";
		parsingTable[6][10] = "s15";
		parsingTable[6][12] = "s17";
		parsingTable[6][14] = "s18";
		parsingTable[6][24] = "s20";
		parsingTable[6][28] = "22";
		parsingTable[6][29] = "6";
		parsingTable[6][30] = "7";
		parsingTable[6][31] = "8";
		parsingTable[6][32] = "9";
		parsingTable[6][33] = "16";
		parsingTable[6][34] = "10";
		parsingTable[6][35] = "11";
		parsingTable[6][36] = "12";
		parsingTable[6][45] = "19";
		parsingTable[7][1] 	= "r4";
		parsingTable[7][3] 	= "r4";
		parsingTable[7][4] 	= "r4";
		parsingTable[7][9] 	= "r4";
		parsingTable[7][10] = "r4";
		parsingTable[7][11] = "r4";
		parsingTable[7][12] = "r4";
		parsingTable[7][14] = "r4";
		parsingTable[7][24] = "r4";
		parsingTable[8][1] 	= "r5";
		parsingTable[8][3] 	= "r5";
		parsingTable[8][4] 	= "r5";
		parsingTable[8][9] 	= "r5";
		parsingTable[8][10] = "r5";
		parsingTable[8][11] = "r5";
		parsingTable[8][12] = "r5";
		parsingTable[8][14] = "r5";
		parsingTable[8][24] = "r5";
		parsingTable[9][1] 	= "r6";
		parsingTable[9][3] 	= "r6";
		parsingTable[9][4] 	= "r6";
		parsingTable[9][9] 	= "r6";
		parsingTable[9][10] = "r6";
		parsingTable[9][11] = "r6";
		parsingTable[9][12] = "r6";
		parsingTable[9][14] = "r6";
		parsingTable[9][24] = "r6";
		parsingTable[10][1] = "r7";
		parsingTable[10][3] = "r7";
		parsingTable[10][4] = "r7";
		parsingTable[10][9] = "r7";
		parsingTable[10][10] = "r7";
		parsingTable[10][11] = "r7";
		parsingTable[10][12] = "r7";
		parsingTable[10][14] = "r7";
		parsingTable[10][24] = "r7";
		parsingTable[11][1]	 = "r8";
		parsingTable[11][3]	 = "r8";
		parsingTable[11][4]	 = "r8";
		parsingTable[11][9]	 = "r8";
		parsingTable[11][10] = "r8";
		parsingTable[11][11] = "r8";
		parsingTable[11][12] = "r8";
		parsingTable[11][14] = "r8";
		parsingTable[11][24] = "r8";
		parsingTable[12][1]	 = "r9";
		parsingTable[12][3]	 = "r9";
		parsingTable[12][4]	 = "r9";
		parsingTable[12][9]	 = "r9";
		parsingTable[12][10] = "r9";
		parsingTable[12][11] = "r9";
		parsingTable[12][12] = "r9";
		parsingTable[12][14] = "r9";
		parsingTable[12][24] = "r9";
		parsingTable[13][5] = "s23";
		parsingTable[14][5] = "s24";
		parsingTable[15][5] = "s25";
		parsingTable[16][11] = "s26";
		parsingTable[17][5] = "s27";
		parsingTable[18][11] = "s28";
		parsingTable[19][1] = "s29";
		parsingTable[20][1] = "r32";
		parsingTable[21][25] = "r1";
		parsingTable[22][3] = "r3";
		parsingTable[23][1] = "s31";
		parsingTable[23][40] = "30";
		parsingTable[24][1] = "s31";
		parsingTable[24][40] = "32";
		parsingTable[25][1] = "r33";
		parsingTable[25][24] = "s20";
		parsingTable[25][33] = "33";
		parsingTable[25][45] = "19";
		parsingTable[26][1] = "r16";
		parsingTable[26][3] = "r16";
		parsingTable[26][4] = "r16";
		parsingTable[26][8] = "r16";
		parsingTable[26][9] = "r16";
		parsingTable[26][10] = "r16";
		parsingTable[26][11] = "r16";
		parsingTable[26][12] = "r16";
		parsingTable[26][14] = "r16";
		parsingTable[26][24] = "r16";
		parsingTable[27][13] = "s34";
		parsingTable[28][1] = "r18";
		parsingTable[28][3] = "r18";
		parsingTable[28][4] = "r18";
		parsingTable[28][8] = "r18";
		parsingTable[28][9] = "r18";
		parsingTable[28][10] = "r18";
		parsingTable[28][11] = "r18";
		parsingTable[28][12] = "r18";
		parsingTable[28][14] = "r18";
		parsingTable[28][24] = "r18";
		parsingTable[29][11] = "r31";
		parsingTable[29][18] = "s36";
		parsingTable[29][23] = "s38";
		parsingTable[29][39] = "35";
		parsingTable[29][44] = "37";
		parsingTable[30][6] = "s39";
		parsingTable[31][19] = "s40";
		parsingTable[32][6] = "s41";
		parsingTable[33][11] = "s42";
		parsingTable[34][6] = "s43";
		parsingTable[35][11] = "r15";
		parsingTable[36][1] = "s47";
		parsingTable[36][20] = "s48";
		parsingTable[36][41] = "44";
		parsingTable[36][42] = "45";
		parsingTable[36][43] = "46";
		parsingTable[37][11] = "r22";
		parsingTable[38][1] = "s49";
		parsingTable[39][15] = "s51";
		parsingTable[39][37] = "50";
		parsingTable[40][1] = "s55";
		parsingTable[40][20] = "s56";
		parsingTable[40][41] = "52";
		parsingTable[40][42] = "53";
		parsingTable[40][43] = "54";
		parsingTable[41][15] = "s58";
		parsingTable[41][37] = "57";
		parsingTable[42][1] = "s60";
		parsingTable[42][40] = "59";
		parsingTable[43][11] = "s61";
		parsingTable[44][11] = "r31";
		parsingTable[44][21] = "s63";
		parsingTable[44][22] = "s64";
		parsingTable[44][23] = "s38";
		parsingTable[44][44] = "62";
		parsingTable[45][11] = "r24";
		parsingTable[45][21] = "r24";
		parsingTable[45][22] = "r24";
		parsingTable[45][23] = "r24";
		parsingTable[46][11] = "r25";
		parsingTable[46][21] = "r25";
		parsingTable[46][22] = "r25";
		parsingTable[46][23] = "r25";
		parsingTable[47][11] = "r26";
		parsingTable[47][21] = "r26";
		parsingTable[47][22] = "r26";
		parsingTable[47][23] = "r26";
		parsingTable[48][11] = "r27";
		parsingTable[48][21] = "r27";
		parsingTable[48][22] = "r27";
		parsingTable[48][23] = "r27";
		parsingTable[49][11] = "r31";
		parsingTable[49][18] = "s36";
		parsingTable[49][23] = "s38";
		parsingTable[49][39] = "65";
		parsingTable[49][44] = "37";
		parsingTable[50][1] = "r10";
		parsingTable[50][3] = "r10";
		parsingTable[50][4] = "r10";
		parsingTable[50][7] = "s66";
		parsingTable[50][9] = "r10";
		parsingTable[50][10] = "r10";
		parsingTable[50][11] = "r10";
		parsingTable[50][12] = "r10";
		parsingTable[50][14] = "r10";
		parsingTable[50][24] = "r10";
		parsingTable[51][1] = "r33";
		parsingTable[51][4] = "s75";
		parsingTable[51][9] = "s76";
		parsingTable[51][10] = "s77";
		parsingTable[51][12] = "s79";
		parsingTable[51][14] = "s80";
		parsingTable[51][24] = "s20";
		parsingTable[51][28] = "67";
		parsingTable[51][29] = "68";
		parsingTable[51][30] = "69";
		parsingTable[51][31] = "70";
		parsingTable[51][32] = "71";
		parsingTable[51][33] = "78";
		parsingTable[51][34] = "72";
		parsingTable[51][35] = "73";
		parsingTable[51][36] = "74";
		parsingTable[51][45] = "19";
		parsingTable[52][6] = "r23";
		parsingTable[52][21] = "s81";
		parsingTable[52][22] = "s82";
		parsingTable[53][6] = "r24";
		parsingTable[53][21] = "r24";
		parsingTable[53][22] = "r24";
		parsingTable[54][6] = "r25";
		parsingTable[54][21] = "r25";
		parsingTable[54][22] = "r25";
		parsingTable[55][6] = "r26";
		parsingTable[55][21] = "r26";
		parsingTable[55][22] = "r26";
		parsingTable[56][6] = "r27";
		parsingTable[56][21] = "r27";
		parsingTable[56][22] = "r27";
		parsingTable[57][1] = "r13";
		parsingTable[57][3] = "r13";
		parsingTable[57][4] = "r13";
		parsingTable[57][9] = "r13";
		parsingTable[57][10] = "r13";
		parsingTable[57][11] = "r13";
		parsingTable[57][12] = "r13";
		parsingTable[57][14] = "r13";
		parsingTable[57][24] = "r13";
		parsingTable[58][1] = "r33";
		parsingTable[58][4] = "s75";
		parsingTable[58][9] = "s76";
		parsingTable[58][10] = "s77";
		parsingTable[58][12] = "s79";
		parsingTable[58][14] = "s80";
		parsingTable[58][24] = "s20";
		parsingTable[58][28] = "83";
		parsingTable[58][29] = "68";
		parsingTable[58][30] = "69";
		parsingTable[58][31] = "70";
		parsingTable[58][32] = "71";
		parsingTable[58][33] = "78";
		parsingTable[58][34] = "72";
		parsingTable[58][35] = "73";
		parsingTable[58][36] = "74";
		parsingTable[58][45] = "19";
		parsingTable[59][11] = "s84";
		parsingTable[60][19] = "s85";
		parsingTable[61][1] = "r17";
		parsingTable[61][3] = "r17";
		parsingTable[61][4] = "r17";
		parsingTable[61][9] = "r17";
		parsingTable[61][10] = "r17";
		parsingTable[61][11] = "r17";
		parsingTable[61][12] = "r17";
		parsingTable[61][14] = "r17";
		parsingTable[61][24] = "r17";
		parsingTable[62][11] = "r21";
		parsingTable[63][1] = "s47";
		parsingTable[63][20] = "s48";
		parsingTable[63][41] = "86";
		parsingTable[63][42] = "45";
		parsingTable[63][43] = "46";
		parsingTable[64][1] = "s47";
		parsingTable[64][20] = "s48";
		parsingTable[64][41] = "87";
		parsingTable[64][42] = "45";
		parsingTable[64][43] = "46";
		parsingTable[65][11] = "r30";
		parsingTable[66][5] = "s88";
		parsingTable[67][16] = "s89";
		parsingTable[68][1] = "r33";
		parsingTable[68][4] = "s75";
		parsingTable[68][9] = "s76";
		parsingTable[68][10] = "s77";
		parsingTable[68][12] = "s79";
		parsingTable[68][14] = "s80";
		parsingTable[68][16] = "r2";
		parsingTable[68][24] = "s20";
		parsingTable[68][28] = "90";
		parsingTable[68][29] = "68";
		parsingTable[68][30] = "69";
		parsingTable[68][31] = "70";
		parsingTable[68][32] = "71";
		parsingTable[68][33] = "78";
		parsingTable[68][34] = "72";
		parsingTable[68][35] = "73";
		parsingTable[68][36] = "74";
		parsingTable[68][45] = "19";
		parsingTable[69][1] = "r4";
		parsingTable[69][4] = "r4";
		parsingTable[69][9] = "r4";
		parsingTable[69][10] = "r4";
		parsingTable[69][11] = "r4";
		parsingTable[69][12] = "r4";
		parsingTable[69][14] = "r4";
		parsingTable[69][16] = "r4";
		parsingTable[69][24] = "r4";
		parsingTable[70][1] = "r5";
		parsingTable[70][4] = "r5";
		parsingTable[70][9] = "r5";
		parsingTable[70][10] = "r5";
		parsingTable[70][11] = "r5";
		parsingTable[70][12] = "r5";
		parsingTable[70][14] = "r5";
		parsingTable[70][16] = "r5";
		parsingTable[70][24] = "r5";
		parsingTable[71][1] = "r6";
		parsingTable[71][4] = "r6";
		parsingTable[71][9] = "r6";
		parsingTable[71][10] = "r6";
		parsingTable[71][11] = "r6";
		parsingTable[71][12] = "r6";
		parsingTable[71][14] = "r6";
		parsingTable[71][16] = "r6";
		parsingTable[71][24] = "r6";
		parsingTable[72][1] = "r7";
		parsingTable[72][4] = "r7";
		parsingTable[72][9] = "r7";
		parsingTable[72][10] = "r7";
		parsingTable[72][11] = "r7";
		parsingTable[72][12] = "r7";
		parsingTable[72][14] = "r7";
		parsingTable[72][16] = "r7";
		parsingTable[72][24] = "r7";
		parsingTable[73][1] = "r8";
		parsingTable[73][4] = "r8";
		parsingTable[73][9] = "r8";
		parsingTable[73][10] = "r8";
		parsingTable[73][11] = "r8";
		parsingTable[73][12] = "r8";
		parsingTable[73][14] = "r8";
		parsingTable[73][16] = "r8";
		parsingTable[73][24] = "r8";
		parsingTable[74][1] = "r9";
		parsingTable[74][4] = "r9";
		parsingTable[74][9] = "r9";
		parsingTable[74][10] = "r9";
		parsingTable[74][11] = "r9";
		parsingTable[74][12] = "r9";
		parsingTable[74][14] = "r9";
		parsingTable[74][16] = "r9";
		parsingTable[74][24] = "r9";
		parsingTable[75][5] = "s91";
		parsingTable[76][5] = "s92";
		parsingTable[77][5] = "s93";
		parsingTable[78][11] = "s94";
		parsingTable[79][5] = "s95";
		parsingTable[80][11] = "s96";
		parsingTable[81][1] = "s55";
		parsingTable[81][20] = "s56";
		parsingTable[81][41] = "97";
		parsingTable[81][42] = "53";
		parsingTable[81][43] = "54";
		parsingTable[82][1] = "s55";
		parsingTable[82][20] = "s56";
		parsingTable[82][41] = "98";
		parsingTable[82][42] = "53";
		parsingTable[82][43] = "54";
		parsingTable[83][16] = "s99";
		parsingTable[84][1] = "s101";
		parsingTable[84][38] = "100";
		parsingTable[85][1] = "s105";
		parsingTable[85][20] = "s106";
		parsingTable[85][41] = "102";
		parsingTable[85][42] = "103";
		parsingTable[85][43] = "104";
		parsingTable[86][11] = "r28";
		parsingTable[86][21] = "s63";
		parsingTable[86][22] = "s64";
		parsingTable[86][23] = "r28";
		parsingTable[87][11] = "r29";
		parsingTable[87][21] = "s63";
		parsingTable[87][22] = "s64";
		parsingTable[87][23] = "r29";
		parsingTable[88][1] = "s31";
		parsingTable[88][40] = "107";
		parsingTable[89][1] = "r19";
		parsingTable[89][3] = "r19";
		parsingTable[89][4] = "r19";
		parsingTable[89][7] = "r19";
		parsingTable[89][9] = "r19";
		parsingTable[89][10] = "r19";
		parsingTable[89][11] = "r19";
		parsingTable[89][12] = "r19";
		parsingTable[89][14] = "r19";
		parsingTable[89][24] = "r19";
		parsingTable[90][16] = "r3";
		parsingTable[91][1] = "s31";
		parsingTable[91][40] = "108";
		parsingTable[92][1] = "s31";
		parsingTable[92][40] = "109";
		parsingTable[93][1] = "r33";
		parsingTable[93][24] = "s20";
		parsingTable[93][33] = "110";
		parsingTable[93][45] = "19";
		parsingTable[94][1] = "r16";
		parsingTable[94][4] = "r16";
		parsingTable[94][9] = "r16";
		parsingTable[94][10] = "r16";
		parsingTable[94][11] = "r16";
		parsingTable[94][12] = "r16";
		parsingTable[94][14] = "r16";
		parsingTable[94][16] = "r16";
		parsingTable[94][24] = "r16";
		parsingTable[95][13] = "s111";
		parsingTable[96][1] = "r18";
		parsingTable[96][4] = "r18";
		parsingTable[96][9] = "r18";
		parsingTable[96][10] = "r18";
		parsingTable[96][11] = "r18";
		parsingTable[96][12] = "r18";
		parsingTable[96][14] = "r18";
		parsingTable[96][16] = "r18";
		parsingTable[96][24] = "r18";
		parsingTable[97][6] = "r28";
		parsingTable[97][21] = "s81";
		parsingTable[97][22] = "s82";
		parsingTable[98][6] = "r29";
		parsingTable[97][21] = "s81";
		parsingTable[97][22] = "s82";
		parsingTable[99][1] = "r19";
		parsingTable[99][3] = "r19";
		parsingTable[99][4] = "r19";
		parsingTable[99][9] = "r19";
		parsingTable[99][10] = "r19";
		parsingTable[99][11] = "r19";
		parsingTable[99][12] = "r19";
		parsingTable[99][14] = "r19";
		parsingTable[99][24] = "r19";
		parsingTable[100][6] = "s112";
		parsingTable[101][17] = "s113";
		parsingTable[102][11] = "r23";
		parsingTable[102][21] = "s114";
		parsingTable[102][22] = "s115";
		parsingTable[103][11] = "r24";
		parsingTable[103][21] = "r24";
		parsingTable[103][22] = "r24";
		parsingTable[104][11] = "r25";
		parsingTable[104][21] = "r25";
		parsingTable[104][22] = "r25";
		parsingTable[105][11] = "r26";
		parsingTable[105][21] = "r26";
		parsingTable[105][22] = "r26";
		parsingTable[106][11] = "r27";
		parsingTable[106][21] = "r27";
		parsingTable[106][22] = "r27";
		parsingTable[107][6] = "s116";
		parsingTable[108][6] = "s117";
		parsingTable[109][6] = "s118";
		parsingTable[110][6] = "s119";
		parsingTable[111][6] = "s120";
		parsingTable[112][15] = "s58";
		parsingTable[112][37] = "121";
		parsingTable[113][6] = "r20";
		parsingTable[114][1] = "s105";
		parsingTable[114][20] = "s106";
		parsingTable[114][41] = "122";
		parsingTable[114][42] = "103";
		parsingTable[114][43] = "104";
		parsingTable[115][1] = "s105";
		parsingTable[115][20] = "s106";
		parsingTable[115][41] = "122";
		parsingTable[115][42] = "103";
		parsingTable[115][43] = "104";
		parsingTable[116][15] = "s125";
		parsingTable[116][37] = "124";
		parsingTable[117][15] = "s127";
		parsingTable[117][37] = "126";
		parsingTable[118][15] = "s129";
		parsingTable[118][37] = "128";
		parsingTable[119][1] = "s60";
		parsingTable[119][40] = "130";
		parsingTable[120][11] = "s131";
		parsingTable[121][1] = "r14";
		parsingTable[121][3] = "r14";
		parsingTable[121][4] = "r14";
		parsingTable[121][9] = "r14";
		parsingTable[121][10] = "r14";
		parsingTable[121][11] = "r14";
		parsingTable[121][12] = "r14";
		parsingTable[121][14] = "r14";
		parsingTable[121][24] = "r14";
		parsingTable[122][11] = "r28";
		parsingTable[122][21] = "s114";
		parsingTable[122][22] = "s115";
		parsingTable[123][11] = "r29";
		parsingTable[123][21] = "s114";
		parsingTable[123][22] = "s115";
		parsingTable[124][1] = "r12";
		parsingTable[124][3] = "r12";
		parsingTable[124][4] = "r12";
		parsingTable[124][8] = "s132";
		parsingTable[124][9] = "r12";
		parsingTable[124][10] = "r12";
		parsingTable[124][11] = "r12";
		parsingTable[124][12] = "r12";
		parsingTable[124][14] = "r12";
		parsingTable[124][24] = "r12";
		parsingTable[125][1] = "r33";
		parsingTable[125][4] = "s75";
		parsingTable[125][9] = "s76";
		parsingTable[125][10] = "s77";
		parsingTable[125][12] = "s79";
		parsingTable[125][14] = "s80";
		parsingTable[125][24] = "s20";
		parsingTable[125][28] = "133";
		parsingTable[125][29] = "68";
		parsingTable[125][30] = "69";
		parsingTable[125][31] = "70";
		parsingTable[125][32] = "71";
		parsingTable[125][33] = "78";
		parsingTable[125][34] = "72";
		parsingTable[125][35] = "73";
		parsingTable[125][36] = "74";
		parsingTable[125][45] = "19";
		parsingTable[126][1] = "r10";
		parsingTable[126][4] = "r10";
		parsingTable[126][7] = "s134";
		parsingTable[126][9] = "r10";
		parsingTable[126][10] = "r10";
		parsingTable[126][11] = "r10";
		parsingTable[126][12] = "r10";
		parsingTable[126][14] = "r10";
		parsingTable[126][16] = "r10";
		parsingTable[126][24] = "r10";
		parsingTable[127][1] = "r33";
		parsingTable[127][4] = "s75";
		parsingTable[127][9] = "s76";
		parsingTable[127][10] = "s77";
		parsingTable[127][12] = "s79";
		parsingTable[127][14] = "s80";
		parsingTable[127][24] = "s20";
		parsingTable[127][28] = "135";
		parsingTable[127][29] = "68";
		parsingTable[127][30] = "69";
		parsingTable[127][31] = "70";
		parsingTable[127][32] = "71";
		parsingTable[127][33] = "78";
		parsingTable[127][34] = "72";
		parsingTable[127][35] = "73";
		parsingTable[127][36] = "74";
		parsingTable[127][45] = "19";
		parsingTable[128][1] = "r13";
		parsingTable[128][4] = "r13";
		parsingTable[128][9] = "r13";
		parsingTable[128][10] = "r13";
		parsingTable[128][11] = "r13";
		parsingTable[128][12] = "r13";
		parsingTable[128][14] = "r13";
		parsingTable[128][16] = "r13";
		parsingTable[128][24] = "r13";
		parsingTable[129][1] = "r33";
		parsingTable[129][4] = "s75";
		parsingTable[129][9] = "s76";
		parsingTable[129][10] = "s77";
		parsingTable[129][12] = "s79";
		parsingTable[129][14] = "s80";
		parsingTable[129][24] = "s20";
		parsingTable[129][28] = "136";
		parsingTable[129][29] = "68";
		parsingTable[129][30] = "69";
		parsingTable[129][31] = "70";
		parsingTable[129][32] = "71";
		parsingTable[129][33] = "78";
		parsingTable[129][34] = "72";
		parsingTable[129][35] = "73";
		parsingTable[129][36] = "74";
		parsingTable[129][45] = "19";
		parsingTable[130][11] = "s137";
		parsingTable[131][1] = "r17";
		parsingTable[131][4] = "r17";
		parsingTable[131][9] = "r17";
		parsingTable[131][10] = "r17";
		parsingTable[131][11] = "r17";
		parsingTable[131][12] = "r17";
		parsingTable[131][14] = "r17";
		parsingTable[131][16] = "r17";
		parsingTable[131][24] = "r17";
		parsingTable[132][15] = "s58";
		parsingTable[132][37] = "138";
		parsingTable[133][16] = "s139";
		parsingTable[134][5] = "s140";
		parsingTable[135][16] = "s141";
		parsingTable[136][16] = "s142";
		parsingTable[137][1] = "s101";
		parsingTable[137][38] = "143";
		parsingTable[138][1] = "r11";
		parsingTable[138][3] = "r11";
		parsingTable[138][4] = "r11";
		parsingTable[138][9] = "r11";
		parsingTable[138][10] = "r11";
		parsingTable[138][11] = "r11";
		parsingTable[138][12] = "r11";
		parsingTable[138][14] = "r11";
		parsingTable[138][24] = "r11";
		parsingTable[139][1] = "r19";
		parsingTable[139][3] = "r19";
		parsingTable[139][4] = "r19";
		parsingTable[139][8] = "r19";
		parsingTable[139][9] = "r19";
		parsingTable[139][10] = "r19";
		parsingTable[139][11] = "r19";
		parsingTable[139][12] = "r19";
		parsingTable[139][14] = "r19";
		parsingTable[139][24] = "r19";
		parsingTable[140][1] = "s31";
		parsingTable[140][40] = "144";
		parsingTable[141][1] = "r19";
		parsingTable[141][4] = "r19";
		parsingTable[141][7] = "r19";
		parsingTable[141][9] = "r19";
		parsingTable[141][10] = "r19";
		parsingTable[141][11] = "r19";
		parsingTable[141][12] = "r19";
		parsingTable[141][14] = "r19";
		parsingTable[141][16] = "r19";
		parsingTable[141][24] = "r19";
		parsingTable[142][1] = "r19";
		parsingTable[142][4] = "r19";
		parsingTable[142][9] = "r19";
		parsingTable[142][10] = "r19";
		parsingTable[142][11] = "r19";
		parsingTable[142][12] = "r19";
		parsingTable[142][14] = "r19";
		parsingTable[142][16] = "r19";
		parsingTable[142][24] = "r19";
		parsingTable[143][6] = "s145";
		parsingTable[144][6] = "s146";
		parsingTable[145][15] = "s129";
		parsingTable[145][37] = "147";
		parsingTable[146][15] = "s149";
		parsingTable[146][37] = "148";
		parsingTable[147][1] = "r14";
		parsingTable[147][4] = "r14";
		parsingTable[147][9] = "r14";
		parsingTable[147][10] = "r14";
		parsingTable[147][11] = "r14";
		parsingTable[147][12] = "r14";
		parsingTable[147][14] = "r14";
		parsingTable[147][16] = "r14";
		parsingTable[147][24] = "r14";
		parsingTable[148][1] = "r12";
		parsingTable[148][4] = "r12";
		parsingTable[148][8] = "s150";
		parsingTable[148][9] = "r12";
		parsingTable[148][10] = "r12";
		parsingTable[148][11] = "r12";
		parsingTable[148][12] = "r12";
		parsingTable[148][14] = "r12";
		parsingTable[148][16] = "r12";
		parsingTable[148][24] = "r12";
		parsingTable[149][1] = "r33";
		parsingTable[149][4] = "s75";
		parsingTable[149][9] = "s76";
		parsingTable[149][10] = "s77";
		parsingTable[149][12] = "s79";
		parsingTable[149][14] = "s80";
		parsingTable[149][24] = "s20";
		parsingTable[149][28] = "151";
		parsingTable[149][29] = "68";
		parsingTable[149][30] = "69";
		parsingTable[149][31] = "70";
		parsingTable[149][32] = "71";
		parsingTable[149][33] = "78";
		parsingTable[149][34] = "72";
		parsingTable[149][35] = "73";
		parsingTable[149][36] = "74";
		parsingTable[150][15] = "s129";
		parsingTable[150][37] = "152";
		parsingTable[151][16] = "s153";
		parsingTable[152][1] = "r11";
		parsingTable[152][4] = "r11";
		parsingTable[152][9] = "r11";
		parsingTable[152][10] = "r11";
		parsingTable[152][11] = "r11";
		parsingTable[152][12] = "r11";
		parsingTable[152][14] = "r11";
		parsingTable[152][16] = "r11";
		parsingTable[152][24] = "r11";
		parsingTable[153][1] = "r19";
		parsingTable[153][4] = "r19";
		parsingTable[153][8] = "r19";
		parsingTable[153][9] = "r19";
		parsingTable[153][10] = "r19";
		parsingTable[153][11] = "r19";
		parsingTable[153][12] = "r19";
		parsingTable[153][14] = "r19";
		parsingTable[153][16] = "r19";
		parsingTable[153][24] = "r19";
	}

	private static ArrayList<String> declaredId = new ArrayList<>();
	
	private static void parser() throws Exception {
		int currentState = 0;
		Token currentToken = null;
		Token previousToken;
		cntToken = 0;
		cntStep = 1;
		parsingStack.add(currentState+"");
		
		while(currentState != -1) {
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			previousToken = currentToken;
			currentToken = symbolTable.get(cntToken);
			try {
				currentState = action(currentState, currentToken.getToken());
			} catch(Exception e) {
				throw new Exception("\nPrevious - " + (cntToken != 0 ? previousToken.toString() : "\n")
				   					 +"Current - " + currentToken.toString()
				   					 +"Lookahead - " + (cntToken != symbolTable.size()-1 ? symbolTable.get(cntToken + 1).toString() : "\n") );
			}
		}
	}
	
	private static int action(int currentState, String currentToken) throws Exception {
		String action = parsingTable[currentState][getColumn(currentToken)];
		if (action.startsWith("s")) {	// shift
			parsingStack.add(currentToken);
			parsingStack.add(action.substring(1));
			cntToken++;
			return Integer.parseInt(action.substring(1));
		}
		else if (action.startsWith("r")) {	// reduce	
			 return reduceAction(action.substring(1));
		}
		else if (action.equals("acc")) {
			System.out.println("Accepted!!");
			return -1 ;
		}
		else {	// goto
			parsingStack.add(action);		
			return Integer.parseInt(action);
		}
	}
	
	private static int reduceAction(String state) throws Exception{
		int preState = 0;
		if(state.equals("1")) {	// code -> program id program_begin statements program_end
			parsingStack.pop();
			if(!parsingStack.pop().equals("program_end"))	throw new Exception(state+" : program_end");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("statements"))	throw new Exception(state+" : statements");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("program_begin"))	throw new Exception(state+" : program_begin");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("identifier"))	throw new Exception(state+" : identifier");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("program"))		throw new Exception(state+" : program");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("code");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "code");	// todo: add the pop
		}
		else if(state.equals("2")) {	// statements -> statement
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("statement"))
				throw new Exception(state+" : statement");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("statements");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "statements");
		}
		else if(state.equals("3")) {	// statements -> statement statements
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("statements"))	throw new Exception(state+" : statements");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("statement"))		throw new Exception(state+" : statement");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("statements");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "statements");
		}
		else if(state.equals("4")) {	// statement -> if_stmt
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("if_stmt"))		throw new Exception(state+" : if_stmt");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("statement");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "statement");
		}
		else if(state.equals("5")) {	// statement -> while_stmt
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("while_stmt"))	throw new Exception(state+" : while_stmt");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("statement");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "statement");	
		}
		else if(state.equals("6")) {	// statement -> for_stmt
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("for_stmt"))
				throw new Exception(state+" : for_stmt");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("statement");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "statement");
		}
		else if(state.equals("7")) {	// statement -> assign_stmt
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("assign_stmt"))	throw new Exception(state+" : assign_stmt");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("statement");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "statement");
		}
		else if(state.equals("8")) {	// statement -> fcall_stmt
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("fcall_stmt"))	throw new Exception(state+" : fcall_stmt");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("statement");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "statement");
		}
		else if(state.equals("9")) {	// statement -> break_stmt
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("break_stmt"))	throw new Exception(state+" : break_stmt");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("statement");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "statement");
		}
		else if(state.equals("10")) {	// if_stmt -> if ( relational_expr ) block
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("block"))			throw new Exception(state+" : block");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(")"))				throw new Exception(state+" : )");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("relational_expr"))	throw new Exception(state+" : relational_expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("("))				throw new Exception(state+" : (");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("if"))			throw new Exception(state+" : if");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("if_stmt");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "if_stmt");
		}
		else if(state.equals("11")) {	// if_stmt -> if ( relational_expr ) block elseif ( relational_Expr ) block else block
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("block"))			throw new Exception(state+" : block");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("else"))			throw new Exception(state+" : else");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("block"))			throw new Exception(state+" : block");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(")"))				throw new Exception(state+" : )");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("relational_expr"))	throw new Exception(state+" : relational_expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("("))				throw new Exception(state+" : (");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("elseif"))		throw new Exception(state+" : elseif");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("block"))			throw new Exception(state+" : block");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(")"))				throw new Exception(state+" : )");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("relational_expr"))	throw new Exception(state+" : relational_expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("("))				throw new Exception(state+" : (");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("if"))			throw new Exception(state+" : if");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("if_stmt");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "if_stmt");
		}
		else if(state.equals("12")) {	// if_stmt -> if ( relational_expr ) block elseif ( relational_expr ) block
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("block"))			throw new Exception(state+" : block");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(")"))				throw new Exception(state+" : )");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("relational_expr"))	throw new Exception(state+" : relational_expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("("))				throw new Exception(state+" : (");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("elseif"))		throw new Exception(state+" : elseif");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("block"))			throw new Exception(state+" : block");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(")"))				throw new Exception(state+" : )");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("relational_expr"))	throw new Exception(state+" : relational_expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("("))				throw new Exception(state+" : (");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("if"))			throw new Exception(state+" : if");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("if_stmt");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "if_stmt");
		}
		else if(state.equals("13")) {	// while_stmt -> while ( relational_expr ) block
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("block"))			throw new Exception(state+" : block");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(")"))				throw new Exception(state+" : )");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("relational_expr"))	throw new Exception(state+" : relational_expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("("))				throw new Exception(state+" : (");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("while"))			throw new Exception(state+" : while");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("while_stmt");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "while_stmt");
		}
		else if(state.equals("14")) {	// for_stmt -> for ( id_init ; relational_Expr ; unary_expr ) block
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("block"))			throw new Exception(state+" : block");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(")"))				throw new Exception(state+" : )");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("unary_expr"))	throw new Exception(state+" : unary_expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(";"))				throw new Exception(state+" : ;");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("relational_expr"))	throw new Exception(state+" : relational_expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(";"))				throw new Exception(state+" : ;");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("id_init"))		throw new Exception(state+" : id_init");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("("))				throw new Exception(state+" : (");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("for"))			throw new Exception(state+" : for");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("for_stmt");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "for_stmt");
		}
		else if(state.equals("15")) {	// id_init -> type identifier assign_expr
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("assign_expr"))	throw new Exception(state+" : assign_expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("identifier"))	throw new Exception(state+" : identifier");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("type"))			throw new Exception(state+" : type");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("id_init");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "id_init");
			
		}
		else if(state.equals("16")) {	// assign_stmt -> id_init ;
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(";"))				throw new Exception(state+" : ;");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("id_init"))		throw new Exception(state+" : id_init");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("assign_stmt");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "assign_stmt");
		}
		else if(state.equals("17")) {	// fcall_stmt -> display ( literal ) ;
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(";"))				throw new Exception(state+" : ;");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(")"))				throw new Exception(state+" : )");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("literal"))		throw new Exception(state+" : literal");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("("))				throw new Exception(state+" : (");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("display"))		throw new Exception(state+" : display");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("fcall_stmt");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "fcall_stmt");			
		}
		else if(state.equals("18")) {	// break_stmt -> break ;
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(";"))				throw new Exception(state+" : ;");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("break"))			throw new Exception(state+" : break");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("break_stmt");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "break_stmt");
		}
		else if(state.equals("19")) {	// block -> begin statements end
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("end"))			throw new Exception(state+" : end");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("statements"))	throw new Exception(state+" : statements");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("begin"))			throw new Exception(state+" : begin");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("block");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "block");
		}
		else if(state.equals("20")) {	// unary_expr -> identifier ++
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("++"))			throw new Exception(state+" : ++");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("identifier"))	throw new Exception(state+" : identifier");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("unary_expr");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "unary_expr");			
		}
		else if(state.equals("21")) {	// assign_expr -> = expr declator
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("declator"))		throw new Exception(state+" : declator");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("expr"))			throw new Exception(state+" : expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("="))				throw new Exception(state+" : =");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("assign_expr");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "assign_expr");
		}
		else if(state.equals("22")) {	// assign_expr -> declator
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("declator"))		throw new Exception(state+" : declator");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("assign_expr");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "assign_expr");			
		}
		else if(state.equals("23")) {	// relational_expr -> identifier relational_operator expr
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("expr"))			throw new Exception(state+" : expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("relational_operator"))	throw new Exception(state+" : relational_operator");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("identifier"))			throw new Exception(state+" : identifier");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("relational_expr");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "relational_expr");
		}
		else if(state.equals("24")) {	// expr -> add_expr
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("add_expr"))		throw new Exception(state+" : add_expr");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("expr");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "expr");			
		}
		else if(state.equals("25")) {	// expr -> mul_expr
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("mul_expr"))		throw new Exception(state+" : mul_expr");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("expr");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "expr");		
		}
		else if(state.equals("26")) {	// expr -> identifier
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("identifier"))	throw new Exception(state+" : identifier");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("expr");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "expr");		
		}
		else if(state.equals("27")) {	// expr -> number
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("number"))		throw new Exception(state+" : number");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("expr");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "expr");
		}
		else if(state.equals("28")) {	// add_expr -> expr + expr
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("expr"))			throw new Exception(state+" : expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("+"))				throw new Exception(state+" : +");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("expr"))			throw new Exception(state+" : expr");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("add_expr");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "add_expr");			
		}
		else if(state.equals("29")) {	// mul_expr -> expr * expr
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("expr"))			throw new Exception(state+" : expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("*"))				throw new Exception(state+" : *");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("expr"))			throw new Exception(state+" : expr");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("mul_expr");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "mul_expr");			
		}
		else if(state.equals("30")) {	// declator -> , identifier assign_expr
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("assign_expr"))		throw new Exception(state+" : assign_expr");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("identifier"))			throw new Exception(state+" : identifier");
			parsingStack.pop(); 
			if(!parsingStack.pop().equals(","))				throw new Exception(state+" : ,");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("declator");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "declator");			
		}
		else if(state.equals("31")) {	// declator -> ''
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("declator");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "declator");
		}
		else if(state.equals("32")) {	// type -> integer
			parsingStack.pop(); 
			if(!parsingStack.pop().equals("integer"))		throw new Exception(state+" : integer");
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("type");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "type");
		}
		else if(state.equals("33")) {	// type -> ''
			preState = Integer.parseInt(parsingStack.pop());
			parsingStack.add(preState+"");
			parsingStack.add("type");
			System.out.println((cntStep++) + " : " + parsingStack.toString());
			return action(preState, "type");
		}
			
		System.out.println("Reduce Error : "+state);
		return -1;
	}
	
	private static int getColumn(String token) {
		if(token.equals("program"))					return 0;
		else if (token.equals("identifier"))		return 1;
		else if (token.equals("program_begin"))		return 2;
		else if (token.equals("program_end"))		return 3;
		else if (token.equals("if"))				return 4;
		else if (token.equals("("))					return 5;
		else if (token.equals(")"))					return 6;
		else if (token.equals("elseif"))			return 7;
		else if (token.equals("else"))				return 8;
		else if (token.equals("while"))				return 9;
		else if (token.equals("for"))				return 10;
		else if (token.equals(";"))					return 11;
		else if (token.equals("display"))			return 12;
		else if (token.equals("literal"))			return 13;
		else if (token.equals("break"))				return 14;
		else if (token.equals("begin"))				return 15;
		else if (token.equals("end"))				return 16;
		else if (token.equals("++"))				return 17;
		else if (token.equals("="))					return 18;
		else if (token.equals("relational_operator"))	return 19;
		else if (token.equals("number"))			return 20;
		else if (token.equals("+"))					return 21;
		else if (token.equals("*"))					return 22;
		else if (token.equals(","))					return 23;
		else if (token.equals("integer"))			return 24;
		else if (token.equals("$"))					return 25;
		else if (token.equals("code'"))				return 26;
		else if (token.equals("code"))				return 27;
		else if (token.equals("statements"))		return 28;
		else if (token.equals("statement"))			return 29;
		else if (token.equals("if_stmt"))			return 30;
		else if (token.equals("while_stmt"))		return 31;
		else if (token.equals("for_stmt"))			return 32;
		else if (token.equals("id_init"))			return 33;
		else if (token.equals("assign_stmt"))		return 34;
		else if (token.equals("fcall_stmt"))		return 35;
		else if (token.equals("break_stmt"))		return 36;
		else if (token.equals("block"))				return 37;
		else if (token.equals("unary_expr"))		return 38;
		else if (token.equals("assign_expr"))		return 39;
		else if (token.equals("relational_expr"))	return 40;
		else if (token.equals("expr"))				return 41;
		else if (token.equals("add_expr"))			return 42;
		else if (token.equals("mul_expr"))			return 43;
		else if (token.equals("declator"))			return 44;
		else if (token.equals("type"))				return 45;
		
		System.out.println("Token Error : "+token);
		return -1;
	}
}