package parse;

public class Main {
	static String filename = "test/test2.sql";
	static ErrorMsg errorMsg;
	static Scanner lexer;
	static parser parser;
	static absyn.Absyn absyn;

	public static void main(String[] args) {
		errorMsg = new ErrorMsg(filename);
		java.io.InputStream inp;
		try {
			inp = new java.io.FileInputStream(filename);
		} catch (java.io.FileNotFoundException e) {
			throw new Error("File not found: " + filename);
		}
		parser parser = new parser(new Scanner(inp, errorMsg), errorMsg);
		/* open input files, etc. here */

		try {
			parser./* debug_ */parse();
		} catch (Throwable e) {
			errorMsg.anyErrors = true;
			e.printStackTrace();
			throw new Error(e.toString());
		} finally {
			try {
				inp.close();
			} catch (java.io.IOException e) {
			}
		}
		absyn = parser.result;
		System.out.println("Parse successfully");
	}
}
