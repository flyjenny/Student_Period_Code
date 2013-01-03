package QueryManager.Parser;

import QueryManager.Lexer.*;
import QueryManager.Parser.Absyn.Absyn;

public class Parse {

	public ErrorMsg.ErrorMsg errorMsg;
	public Absyn result;

	public Parse(String filename) {
		errorMsg = new ErrorMsg.ErrorMsg(filename);
		{
			java.io.InputStream inp;
			try {
				inp = new java.io.FileInputStream(filename);
			} catch (java.io.FileNotFoundException e) {
				throw new Error("File not found: " + filename);
			}
			Grm parser = new Grm(new Yylex(inp, errorMsg), errorMsg);
			/* open input files, etc. here */

			try {
				result = (Absyn) parser./* debug_ */parse().value;
			} catch (Throwable e) {
				e.printStackTrace();
				throw new Error(e.toString());
			} finally {
				try {
					inp.close();
				} catch (java.io.IOException e) {
				}
			}
		}
	}
}
