
/* JFlex example: part of Java language lexer specification */
package Tiger;
import java_cup.runtime.*;

/**
 * This class is a simple example lexer.
 */
%%

%public
%class Lexer
%extends sym
%unicode
%char
%line
%column
%cup

%{
  /**
  *If you would like to run the lexer itself 
  *Please add Overall.begin() at head
  *or it would failed
  */

  sym sym;
  int nestedcount = 0;
  StringBuffer string = new StringBuffer();
  
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private java_cup.runtime.Symbol lexsymbol(int type, Object value) {
    return new java_cup.runtime.Symbol(type, yychar, yychar+yylength(),value);
  }
 
 
  private void newline() {
     Overall.myerror.newline(yychar);
  }

  public static String WipeOffLeadingZero(String s){
        if(s.charAt(0) != '0')
        	return s;
    	for(int i = 0; i != s.length();++i){
    		if(s.charAt(i) != '0')
    			return s.substring(i, s.length());    	
    		}
    	return "0";
  }     
  
 

%}

%eof{
%eof}




LineTerminator = \n|\r|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = [ \t\f]
Identifier = [:jletter:] [:jletterdigit:]*
DecIntegerLiteral = [0-9]*
ControlLetter = @|[A-Z]|[|\|]|_|\^
ASCII = [0-9][0-9][0-9]

%state STRING
%state NESTED_COMMENT
%state LINE_COMMENT
%state NESTEDSTRING


%%
/* keywords */
<YYINITIAL> "array"       { return lexsymbol(sym.ARRAY,null); }
<YYINITIAL> "break"      { return lexsymbol(sym.BREAK,null); }
<YYINITIAL> "do"          { return lexsymbol(sym.DO,null); }
<YYINITIAL> "else"        { return lexsymbol(sym.ELSE,null); }
<YYINITIAL> "end"        { return lexsymbol(sym.END,null); }
<YYINITIAL> "for"         { return lexsymbol(sym.FOR,null); }
<YYINITIAL> "function"   { return lexsymbol(sym.FUNC,null); }
<YYINITIAL> "if"         { return lexsymbol(sym.IF,null); }
<YYINITIAL> "in"         { return lexsymbol(sym.IN,null); }
<YYINITIAL> "let"        { return lexsymbol(sym.LET,null); }
<YYINITIAL> "nil"        { return lexsymbol(sym.NIL,null); }
<YYINITIAL> "of"         { return lexsymbol(sym.OF,null); }
<YYINITIAL> "then"       { return lexsymbol(sym.THEN,null); }
<YYINITIAL> "to"         { return lexsymbol(sym.TO,null); }
<YYINITIAL> "type"       { return lexsymbol(sym.TYPE,null);} 
<YYINITIAL> "var"        { return lexsymbol(sym.VAR,null); }
<YYINITIAL> "while"      { return lexsymbol(sym.WHILE,null); }
<YYINITIAL> ","          { return lexsymbol(sym.COMMA,null); }
<YYINITIAL> ":"          { return lexsymbol(sym.COLON,null); }
<YYINITIAL> ";"          { return lexsymbol(sym.SEMICOLON,null); }
<YYINITIAL> "("          { return lexsymbol(sym.LPAREN,null); }
<YYINITIAL> ")"          { return lexsymbol(sym.RPAREN,null); }
<YYINITIAL> "["          { return lexsymbol(sym.LBRACKET,null); }
<YYINITIAL> "]"          { return lexsymbol(sym.RBRACKET,null); }
<YYINITIAL> "{"          { return lexsymbol(sym.LBRACE,null); }
<YYINITIAL> "}"          { return lexsymbol(sym.RBRACE,null); }
<YYINITIAL> "."          { return lexsymbol(sym.PERIOD,null); }
<YYINITIAL> "+"          { return lexsymbol(sym.PLUS,null); }
<YYINITIAL> "-"          { return lexsymbol(sym.MINUS,null); }
<YYINITIAL> "*"          { return lexsymbol(sym.MULT,null); }
<YYINITIAL> "/"          { return lexsymbol(sym.SLASH,null); }
<YYINITIAL> "="          { return lexsymbol(sym.EQ,null); }
<YYINITIAL> "<>"         { return lexsymbol(sym.NOTEQ,null); }
<YYINITIAL> "<"          { return lexsymbol(sym.LT,null); }
<YYINITIAL> "<="         { return lexsymbol(sym.LE,null); }
<YYINITIAL> ">"          { return lexsymbol(sym.GT,null); }
<YYINITIAL> ">="         { return lexsymbol(sym.GE,null); }
<YYINITIAL> "&"          { return lexsymbol(sym.AND,null); }
<YYINITIAL> "|"          { return lexsymbol(sym.OR,null); }
<YYINITIAL> ":="                            { return lexsymbol(sym.ASSIGN,null); }
<YYINITIAL>  \"                             { string.setLength(0); yybegin(STRING); }
<YYINITIAL>  {Identifier}                   { return lexsymbol(sym.ID,yytext().toString()); }
<YYINITIAL>  {WhiteSpace}                   { }

<YYINITIAL>  {DecIntegerLiteral}            { return                                                                           lexsymbol(sym.INTEGER,Integer.parseInt(WipeOffLeadingZero(yytext().toString()))); }         
<YYINITIAL>  "/*"                           { ++nestedcount;yybegin(NESTED_COMMENT);}
<YYINITIAL>  {LineTerminator}               { newline();}


<STRING> {
  \\n                            { string.append('\n'); }
  \\t                            { string.append('\t'); }
  \"                             { yybegin(YYINITIAL); 
                                   return lexsymbol(sym.STRING_LITERAL, string.toString()); }
  \\\                            { string.append('\\'); }
  \\\^{ControlLetter}     {}
  \\{ASCII}                   {}  
   \\                            {yybegin(NESTEDSTRING);}
  [^\n\r\"\\]+                   { string.append( yytext() ); }
}

<NESTED_COMMENT>{
   {LineTerminator}              { newline();}
   "/*"                          {++nestedcount;}
   "*/"                          {
                                    --nestedcount;
                                    if(nestedcount == 0)
                                      yybegin(YYINITIAL);
                                 }
   .|\n                          {/*ignore*/}
}


<NESTEDSTRING>{
   {LineTerminator}             { newline();}
   \\                           {yybegin(STRING);}                                   
   {WhiteSpace}                  {/*ignore*/}
                               
   .|\n                          {throw new Error("Illegal character <"+ yytext()+"> in nested string");}
}


/* error fallback */

<YYINITIAL>.|\n                  { throw new Error("Illegal character <"+ yytext()+">"); }

