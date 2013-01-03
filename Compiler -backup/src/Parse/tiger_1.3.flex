package Parse;
import ErrorMsg.ErrorMsg;

%% 

%implements Lexer
%function nextToken
%type java_cup.runtime.Symbol
%char

%{
int count;
StringBuffer string =new StringBuffer();
private void newline() {
  errorMsg.newline(yychar);
}

private void err(int pos, String s) {
  errorMsg.error(pos,s);
}

private void err(String s) {
  err(yychar,s);
}

private java_cup.runtime.Symbol tok(int kind, Object value) {
    return new java_cup.runtime.Symbol(kind, yychar, yychar+yylength(), value);
}

private ErrorMsg errorMsg;

Yylex(java.io.InputStream s, ErrorMsg e) {
  this(s);
  errorMsg=e;
}

%}

%eofval{
	{
	 if (yystate()==COMMENT) err("Comment error");
	 if (yystate()==STRING)  err("String error");
	 if (yystate()==INSLASH) err("In slash error");

	 return tok(sym.EOF, null);
        }
%eofval}       


delim=  [ \t\n\f\r]
alpha=	[A-Za-z]
digit=	[0-9]
id   =	{alpha}({alpha}|{digit}|_)*
int  =	{digit}+
ws   =  {delim}+

%state STRING
%state COMMENT
%state INSLASH
%%

<YYINITIAL> \" 		{string.setLength(0);yybegin(STRING);}
<YYINITIAL> "/*" 	{count=1;yybegin(COMMENT);}
<YYINITIAL> "*/" 	{err("Comment error");}
<YYINITIAL> {ws} 	{}
<YYINITIAL> "," 	{return tok(sym.COMMA,null);}
<YYINITIAL> "/"		{return tok(sym.DIVIDE,null);}
<YYINITIAL> ":"		{return tok(sym.COLON,null);}
<YYINITIAL> "else"	{return tok(sym.ELSE,null);}
<YYINITIAL> "|"		{return tok(sym.OR,null);}
<YYINITIAL> "nil"	{return tok(sym.NIL,null);}
<YYINITIAL> "do"	{return tok(sym.DO,null);}
<YYINITIAL> ">"		{return tok(sym.GT,null);}
<YYINITIAL> ">="	{return tok(sym.GE,null);}
<YYINITIAL> "<"		{return tok(sym.LT,null);}
<YYINITIAL> "<="	{return tok(sym.LE,null);}
<YYINITIAL> "of"	{return tok(sym.OF,null);}
<YYINITIAL> "for"	{return tok(sym.FOR,null);}
<YYINITIAL> "array"	{return tok(sym.ARRAY,null);}
<YYINITIAL> "type"	{return tok(sym.TYPE,null);}
<YYINITIAL> "to"	{return tok(sym.TO,null);}
<YYINITIAL> "in"	{return tok(sym.IN,null);}
<YYINITIAL> "end"	{return tok(sym.END,null);}
<YYINITIAL> "-"		{return tok(sym.MINUS,null);}
<YYINITIAL> "*"		{return tok(sym.TIMES,null);}
<YYINITIAL> ":="	{return tok(sym.ASSIGN,null);}
<YYINITIAL> "."		{return tok(sym.DOT,null);}
<YYINITIAL> "("		{return tok(sym.LPAREN,null);}
<YYINITIAL> ")"		{return tok(sym.RPAREN,null);}
<YYINITIAL> "if"	{return tok(sym.IF,null);}
<YYINITIAL> ";"		{return tok(sym.SEMICOLON,null);}
<YYINITIAL> "while"	{return tok(sym.WHILE,null);}
<YYINITIAL> "var"	{return tok(sym.VAR,null);}
<YYINITIAL> "function"	{return tok(sym.FUNCTION,null);}
<YYINITIAL> "["		{return tok(sym.LBRACK,null);}
<YYINITIAL> "]"		{return tok(sym.RBRACK,null);}
<YYINITIAL> "<>"	{return tok(sym.NEQ,null);}
<YYINITIAL> "break"	{return tok(sym.BREAK,null);}
<YYINITIAL> "&"		{return tok(sym.AND,null);}
<YYINITIAL> "+"		{return tok(sym.PLUS,null);}
<YYINITIAL> "{"		{return tok(sym.LBRACE,null);}
<YYINITIAL> "}"		{return tok(sym.RBRACE,null);}
<YYINITIAL> "let"	{return tok(sym.LET,null);}
<YYINITIAL> "then"	{return tok(sym.THEN,null);}
<YYINITIAL> "="		{return tok(sym.EQ,null);}

<YYINITIAL> {id}	{return tok(sym.ID,yytext());}
<YYINITIAL> {int}	{return tok(sym.INT,new Integer(yytext()));}


<STRING> {
	\"
		{yybegin(YYINITIAL); return tok(sym.STRING,string.toString());}
	\\[0-9][0-9][0-9]
		{int temp=Integer.parseInt(yytext().substring(1,4));
		if(temp>255) err("\\ddd overflow"); 
		else string.append((char)temp);}
	
	[\t\\]+
		{string.append(yytext());}
	\\n
		{string.append('\n');}
	\\t
		{string.append('\t');}
	\\\"	{string.append('\"');}
	\\\\
		{string.append('\\');}
	[\n\f]
		{err("String error");}
  	[ ]
    		{string.append(' ');}
	[^]	
		{string.append(yytext());}
	\\
		{yybegin(INSLASH);}
	
	}

<INSLASH> {
	{ws}	{}
	\\	{yybegin(STRING);}
	\"	{err("\\ Unmatch");}
	[^]	{string.append(yytext());}
	}

<COMMENT> {
	"/*"	{count++;}
	"*/"	{count--; if (count==0) {yybegin(YYINITIAL);}}
	[^]     {}
	}


