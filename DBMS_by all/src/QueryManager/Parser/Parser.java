package QueryManager.Parser;

import java.io.StringReader;

import Absyn.Absyn;
import QueryManager.Lexer.*;

public class Parser {

	public Absyn result;

	public Parser(String sqlStr) throws Exception {
		StringReader reader;
		reader = new StringReader(sqlStr);
		Grm parser = new Grm(new Yylex(reader));

		try {
			result = parser.getResult();
		} catch (Throwable e) {
			throw new Exception(e.getMessage());
		} finally {
			reader.close();
		}
	}

}
