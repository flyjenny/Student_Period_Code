package parse;

import java_cup.runtime.*;

%%

%public
%class Scanner
%function nextToken
%type Symbol
%char

%{
	String string = new String();
	private ErrorMsg errorMsg;

	private void newline() {
		errorMsg.newline(yychar);
	}

	private void newline(int pos) {
		errorMsg.newline(pos);
	}

	private Symbol symbol(int type) {
		return new Symbol(type, yychar, yychar+yylength());
	}

	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yychar, yychar+yylength(), value);
	}
	
	public Symbol next_token() throws java.lang.Exception {
		return nextToken();
	}

	private void err(int pos, String s) {
		errorMsg.error(pos,s);
	}

	private void err(String s) {
		errorMsg.error(yychar,s);
	}
	
	public Scanner(java.io.InputStream s, ErrorMsg e) {
		this(s);
		errorMsg = e;
	}

	public Scanner(java.io.Reader r, ErrorMsg e) {
		this(r);
		errorMsg = e;
	}

%}

%eofval{
	return symbol(sym.EOF,null);
%eofval}

WHITESPACE=[\ \t\n\r\f]
NEWLINEWS=\n
NONENEWLINEWS=[\ \t\f\r]
ID = [a-z]+([a-z0-9]|_)*
INTEGER = [0-9]+
FLOAT = [0-9]+[.][0-9]+
DATE = [0-9][0-9][0-9][0-9]"-"(0[1-9]|1[0-2])"-"(0[1-9]|1[0-9]|2[0-9]|30|31)
TIME = (0[0-9]|1[0-9]|2[0-3])":"[0-5][0-9]":"[0-5][0-9]
OTHERCHAR=["`~!@#$%^&*()_+-=[];,./{}|:<>? "]
STRINGTEXT = ([A-Za-z]|[0-9]|{OTHERCHAR})*

%state STRING

%%

<YYINITIAL> {NONENEWLINEWS} {}
<YYINITIAL> {NEWLINEWS} {newline();}

<YYINITIAL> ";" {return symbol(sym.SEMICOLON,null);}
<YYINITIAL> "," {return symbol(sym.COMMA,null);}
<YYINITIAL> "." { return symbol(sym.DOT, null);}
<YYINITIAL> "=" {return symbol(sym.EQ,null);}
<YYINITIAL> "<>" {return symbol(sym.NEQ,null);}
<YYINITIAL> ">" {return symbol(sym.GT,null);}
<YYINITIAL> "<" {return symbol(sym.LT,null);}
<YYINITIAL> ">=" {return symbol(sym.GE,null);}
<YYINITIAL> "<=" {return symbol(sym.LE,null);}
<YYINITIAL> "+" {return symbol(sym.PLUS,null);}
<YYINITIAL> "-" {return symbol(sym.MINUS,null);}
<YYINITIAL> "*" {return symbol(sym.TIMES,null);}
<YYINITIAL> "/" {return symbol(sym.DIVIDE,null);} 
<YYINITIAL> "(" {return symbol(sym.LPAREN,null);}
<YYINITIAL> ")" {return symbol(sym.RPAREN,null);}
<YYINITIAL> "{"  {return symbol(sym.LBRACE,null);}
<YYINITIAL> "}" {return symbol(sym.RBRACE,null);}

<YYINITIAL> "not" {return symbol(sym.NOT,null);}
<YYINITIAL> "and" {return symbol(sym.AND,null);}
<YYINITIAL> "or" {return symbol(sym.OR,null);}
<YYINITIAL> "like" {return symbol(sym.LIKE,null);}
<YYINITIAL> "escape" {return symbol(sym.ESCAPE,null);}

<YYINITIAL> "exists" {return symbol(sym.EXISTS,null);}
<YYINITIAL> "in" {return symbol(sym.IN,null);}
<YYINITIAL> "all" {return symbol(sym.ALL,null);}
<YYINITIAL> "any" {return symbol(sym.ANY,null);}
<YYINITIAL> "null" {return symbol(sym.NULL,null);}

<YYINITIAL> "select" {return symbol(sym.SELECT,null);}
<YYINITIAL> "from" {return symbol(sym.FROM,null);}
<YYINITIAL> "where" {return symbol(sym.WHERE,null);}
<YYINITIAL> "group by" {return symbol(sym.GROUPBY,null);}
<YYINITIAL> "having" {return symbol(sym.HAVING,null);}
<YYINITIAL> "order by" {return symbol(sym.ORDERBY,null);}
<YYINITIAL> "asc" {return symbol(sym.ASC,null);}
<YYINITIAL> "desc" {return symbol(sym.DESC,null);}
<YYINITIAL> "as" {return symbol(sym.AS,null);}
<YYINITIAL> "distinct" {return symbol(sym.DISTINCT,null);}

<YYINITIAL> "create" {return symbol(sym.CREATE,null);}
<YYINITIAL> "drop" {return symbol(sym.DROP,null);}
<YYINITIAL> "alter" {return symbol(sym.ALTER,null);}
<YYINITIAL> "database" {return symbol(sym.DATABASE,null);}
<YYINITIAL> "table" {return symbol(sym.TABLE,null);}
<YYINITIAL> "insert into" {return symbol(sym.INSERTINTO,null);}
<YYINITIAL> "update" {return symbol(sym.UPDATE,null);}
<YYINITIAL> "delete" {return symbol(sym.DELETE,null);}
<YYINITIAL> "values" {return symbol(sym.VALUES,null);}
<YYINITIAL> "set" {return symbol(sym.SET,null);}
<YYINITIAL> "add" {return symbol(sym.ADD,null);}
<YYINITIAL> "default" {return symbol(sym.DEFAULT,null);}
<YYINITIAL> "primary key" {return symbol(sym.PRIMARYKEY,null);}
<YYINITIAL> "foreign key" {return symbol(sym.FOREIGNKEY,null);}
<YYINITIAL> "references" {return symbol(sym.REFERENCES,null);}
<YYINITIAL> "unique" {return symbol(sym.UNIQUE,null);}
<YYINITIAL> "check" {return symbol(sym.CHECK,null);}
<YYINITIAL> "index" {return symbol(sym.INDEX,null);}
<YYINITIAL> "view" {return symbol(sym.VIEW,null);}
<YYINITIAL> "on" {return symbol(sym.ON,null);}

<YYINITIAL> "start" {return symbol(sym.START,null);}
<YYINITIAL> "transaction" {return symbol(sym.TRANSACTION,null);}
<YYINITIAL> "commit" {return symbol(sym.COMMIT,null);}
<YYINITIAL> "rollback" {return symbol(sym.ROLLBACK,null);}

<YYINITIAL> "int" {return symbol(sym.INT,null);}
<YYINITIAL> "float" {return symbol(sym.FLOAT,null);}
<YYINITIAL> "char" {return symbol(sym.CHAR,null);}
<YYINITIAL> "varchar" {return symbol(sym.VARCHAR,null);}
<YYINITIAL> "boolean" {return symbol(sym.BOOLEAN,null);}
<YYINITIAL> "date" {return symbol(sym.DATE,null);}
<YYINITIAL> "time" {return symbol(sym.TIME,null);}

<YYINITIAL> "avg" {return symbol(sym.AVG,null);}
<YYINITIAL> "count" {return symbol(sym.COUNT,null);}
<YYINITIAL> "min" {return symbol(sym.MIN,null);}
<YYINITIAL> "max" {return symbol(sym.MAX,null);}
<YYINITIAL> "sum" {return symbol(sym.SUM,null);}

<YYINITIAL> {ID} {return symbol(sym.ID,yytext());}
<YYINITIAL> {INTEGER} {return symbol(sym.INTVAL,new Integer(yytext()));}
<YYINITIAL> {FLOAT} {return symbol(sym.FLOATVAL,new Float(yytext()));} 
<YYINITIAL> {DATE} {return symbol(sym.DATEVAL,yytext());}
<YYINITIAL> {TIME} {return symbol(sym.TIMEVAL,yytext());}

<YYINITIAL> \' {yybegin(STRING); string="";}
<STRING> {STRINGTEXT} {string = string + yytext();}
<STRING> \' {yybegin(YYINITIAL); return symbol(sym.STRING,string);}