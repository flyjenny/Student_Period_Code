package QueryManager.Lexer;

import java.io.File;
import java.io.IOException;

public class Driver {

	public static void main(String[] args) throws IOException {

		String fileName = "/home/micro2fly/workspace/testcases/testcase1.sql";
		java.io.InputStream inp = new java.io.FileInputStream(fileName);
		ErrorMsg.ErrorMsg errorMsg = new ErrorMsg.ErrorMsg(fileName);
		Main.main(inp, errorMsg);
		inp.close();
	}

}