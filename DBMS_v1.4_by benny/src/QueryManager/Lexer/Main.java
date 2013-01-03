package QueryManager.Lexer;

import java.io.InputStream;
import ErrorMsg.ErrorMsg;

public class Main {

	public static void main(InputStream inp, ErrorMsg errorMsg)
			throws java.io.IOException {
		Lexer lexer = new Yylex(inp, errorMsg);
		java_cup.runtime.Symbol tok;

		do {
			tok = lexer.nextToken();
			System.out.println(symnames[tok.sym] + " " + tok.left);
		} while (tok.sym != sym.EOF);
	}

	static String symnames[] = new String[100];
	static {
		symnames[sym.DIVIDE] = "DIVIDE";
		symnames[sym.GROUPBY] = "GROUPBY";
		symnames[sym.STRINGVALUE] = "STRINGVALUE";
		symnames[sym.INSERTINTO] = "INSERTINTO";
		symnames[sym.ANY] = "ANY";
		symnames[sym.GE] = "GE";
		symnames[sym.CREATETABLE] = "CREATETABLE";
		symnames[sym.INTVALUE] = "INTVALUE";
		symnames[sym.DESC] = "DESC";
		symnames[sym.LPAREN] = "LPAREN";
		symnames[sym.INT] = "INT";
		symnames[sym.UPDATE] = "UPDATE";
		symnames[sym.MINUS] = "MINUS";
		symnames[sym.ORDERBY] = "ORDERBY";
		symnames[sym.DELETE] = "DELETE";
		symnames[sym.STAR] = "STAR";
		symnames[sym.WHERE] = "WHERE";
		symnames[sym.RPAREN] = "RPAREN";
		symnames[sym.CREATEVIEW] = "CREATEVIEW";
		symnames[sym.NOT] = "NOT";
		symnames[sym.NOTNULL] = "NOTNULL";
		symnames[sym.AND] = "AND";
		symnames[sym.LT] = "LT";
		symnames[sym.IN] = "IN";
		symnames[sym.OR] = "OR";
		symnames[sym.COMMA] = "COMMA";
		symnames[sym.REFERENCES] = "REFERENCES";
		symnames[sym.DROPTABLE] = "DROPTABLE";
		symnames[sym.SELECT] = "SELECT";
		symnames[sym.PLUS] = "PLUS";
		symnames[sym.MIN] = "MIN";
		symnames[sym.ALTERTABLE] = "ALTERTABLE";
		symnames[sym.DOT] = "DOT";
		symnames[sym.ID] = "ID";
		symnames[sym.LE] = "LE";
		symnames[sym.REALVALUE] = "REALVALUE";
		symnames[sym.EOF] = "EOF";
		symnames[sym.BOOL] = "BOOLEAN";
		symnames[sym.TRUE] = "TRUE";
		symnames[sym.error] = "error";
		symnames[sym.DISTINCT] = "DISTINCT";
		symnames[sym.LIKE] = "LIKE";
		symnames[sym.VALUES] = "VALUES";
		symnames[sym.ADD] = "ADD";
		symnames[sym.CHECK] = "CHECK";
		symnames[sym.NULL] = "NULL";
		symnames[sym.NEQ] = "NEQ";
		symnames[sym.EQ] = "EQ";
		symnames[sym.FROM] = "FROM";
		symnames[sym.AVG] = "AVG";
		symnames[sym.ASC] = "ASC";
		symnames[sym.DROPCOLUMN] = "DROPCOLUMN";
		symnames[sym.FLOAT] = "FLOAT";
		symnames[sym.DROPVIEW] = "DROPVIEW";
		symnames[sym.SUM] = "SUM";
		symnames[sym.CHAR] = "CHAR";
		symnames[sym.VARCHAR] = "VARCHAR";
		symnames[sym.ALL] = "ALL";
		symnames[sym.FOREIGNKEY] = "FOREIGNKEY";
		symnames[sym.COUNT] = "COUNT";
		symnames[sym.ALTERCOLUMN] = "ALTERCOLUMN";
		symnames[sym.FALSE] = "FALSE";
		symnames[sym.AS] = "AS";
		symnames[sym.GT] = "GT";
		symnames[sym.PRIMARYKEY] = "PRIMARYKEY";
		symnames[sym.MAX] = "MAX";
		symnames[sym.SET] = "SET";
		symnames[sym.HAVING] = "HAVING";
		symnames[sym.DOUBLE] = "DOUBLE";
		symnames[sym.DECIMAL] = "DECIMAL";
		symnames[sym.UNION] = "UNION";
		symnames[sym.DEFAULT] = "DEFAULT";
		symnames[sym.CREATEINDEX] = "CREATEINDEX";
		symnames[sym.ON] = "ON";
	}

}
