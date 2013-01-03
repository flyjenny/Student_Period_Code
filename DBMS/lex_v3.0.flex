package QueryManager.Lexer;

import ErrorMsg.ErrorMsg;

%%
%implements Lexer
%function nextToken
%type java_cup.runtime.Symbol
%char
%{
	private int count;
	StringBuffer stringvalue = new StringBuffer();

	private java_cup.runtime.Symbol tok(int kind, Object value) 
	{
		return new java_cup.runtime.Symbol(kind, yychar, yychar+yylength(), value);
	}

%}

%eofval{
	{
		if (yystate()==STRINGSTATE)  throw new Exception("String error");
		return tok(sym.EOF, null);
    }
%eofval} 

createindex = [C|c][R|r][E|e][A|a][T|t][E|e][ ]+[I|i][N|n][D|d][E|e][X|x]
createtable = [C|c][R|r][E|e][A|a][T|t][E|e][ ]+[T|t][A|a][B|b][L|l][E|e]
createview = [C|c][R|r][E|e][A|a][T|t][E|e][ ]+[V|v][I|i][E|e][W|w]
dropindex = [D|d][R|r][O|o][P|p][ ]+[I|i][N|n][D|d][E|e][X|x]
droptable = [D|d][R|r][O|o][P|p][ ]+[T|t][A|a][B|b][L|l][E|e]
dropview = [D|d][R|r][O|o][P|p][ ]+[V|v][I|i][E|e][W|w]
dropcolumn= [D|d][R|r][O|o][P|p][ ]+[C|c][O|o][L|l][U|u][M|m][N|n]
altertable=[A|a][L|l][T|t][E|e][R|r][ ]+[T|t][A|a][B|b][L|l][E|e]
altercolumn=[A|a][L|l][T|t][E|e][R|r][ ]+[C|c][O|o][L|l][U|u][M|m][M|n]

primarykey=[P|p][R|r][I|i][M|m][A|a][R|r][Y|y][ ]+[K|k][E|e][Y|y]
default=[D|d][E|e][F|f][A|a][U|u][L|l][T|t]
unique=[U|u][N|n][I|i][Q|q][U|u][E|e]
foreignkey=[F|f][O|o][R|r][E|e][I|i][G|g][N|n][ ]+[K|k][E|e][Y|y]
references=[R|r][E|e][F|f][E|e][R|r][E|e][N|n][C|c][E|e][S|s]
check = [C|c][H|h][E|e][C|c][K|k]

select = [S|s][E|e][L|l][E|e][C|c][T|t]
delete = [D|d][E|e][L|l][E|e][T|t][E|e]
update = [U|u][P|p][D|d][A|a][T|t][E|e]
insertinto = [I|i][N|n][S|s][E|e][R|r][T|t][ ]+[I|i][N|n][T|t][O|o]

where = [W|w][H|h][E|e][R|r][E|e]
from = [F|f][R|r][O|o][M|m]
as = [A|a][S|s]
distinct = [D|d][I|i][S|s][T|t][I|i][N|n][C|c][T|t]
orderby= [O|o][R|r][D|d][E|e][R|r][ ]+[B|b][Y|y]
groupby = [G|g][R|r][O|o][U|u][P|p][ ]+[B|b][Y|y]
in = [I|i][N|n]
like = [L|l][I|i][K|k][E|e]
having = [H|h][A|a][V|v][I|i][N|n][G|g]
all	= [A|a][L|l][L|l]
any	= [A|a][N|n][Y|y]
values = [V|v][A|a][L|l][U|u][E|e][S|s]
add = [A|a][D|d][D|d]
set = [S|s][E|e][T|t]
union = [U|u][N|n][I|i][O|o][N|n]
on = [O|o][N|n]
notnull = [N|n][O|o][T|t][ ]+[N|n][U|u][L|l][L|l]

null= [N|n][U|u][L|l][L|l]

not = [N|n][O|o][T|t]
and = [A|a][N|n][D|d]
or  = [O|o][R|r]
asc = [A|a][S|s][C|c]
desc= [D|d][E|e][S|s][C|c]

true= [T|t][R|r][U|u][E|e]
false=[F|f][A|a][L|l][S|s][E|e]

int = [I|i][N|n][T|t]
float = [F|f][L|l][O|o][A|a][T|t]
double = [D|d][O|o][U|u][B|b][L|l][E|e]
char = [C|c][H|h][A|a][R|r]
varchar = [V|v][A|a][R|r][C|c][H|h][A|a][R|r]
decimal = [D|d][E|e][C|c][I|i][M|m][A|a][L|l]
bool = [B|b][O|o][O|o][L|l]

avg = [A|a][V|v][G|g]
max = [M|m][A|a][X|x]
min = [M|m][I|i][N|n]
sum = [S|s][U|u][M|m]
count = [C|c][O|o][U|u][N|n][T|t]

delim=  [ \t\n\f\r]
alpha=	[A-Za-z]
digit=	[0-9]
notquote = [^']

doublequote = [']{2}
id   =	{alpha}({alpha}|{digit}|_)*
intvalue  =	{digit}+
REALVALUE = {digit}+[.]{digit}+
ws   =  {delim}+
str = {notquote}*

%state STRINGSTATE
%%
<YYINITIAL>
{

	"\'"			{stringvalue.setLength(0);yybegin(STRINGSTATE);}
	"("				{return tok(sym.LPAREN,null);}
	")"				{return tok(sym.RPAREN,null);}
	">"				{return tok(sym.GT,null);}
	">="			{return tok(sym.GE,null);}
	"<"				{return tok(sym.LT,null);}
	"<="			{return tok(sym.LE,null);}
	"<>"			{return tok(sym.NEQ,null);}
	"="				{return tok(sym.EQ,null);}
	"*"				{return tok(sym.STAR,null);}
	"/"				{return tok(sym.DIVIDE,null);}
	"+"				{return tok(sym.PLUS,null);}
	"-"				{return tok(sym.MINUS,null);}
	","				{return tok(sym.COMMA,null);}
	"."				{return tok(sym.DOT,null);}
	"&&"			{return tok(sym.AND,null);}
	"||"			{return tok(sym.OR,null);}

	{createindex}	{return tok(sym.CREATEINDEX,null);}
	{createtable}	{return tok(sym.CREATETABLE,null);}
	{createview}	{return tok(sym.CREATEVIEW,null);}
	{dropindex}		{return tok(sym.DROPINDEX,null);}
	{droptable}		{return tok(sym.DROPTABLE,null);}
	{dropview}		{return tok(sym.DROPVIEW,null);}
	{dropcolumn}	{return tok(sym.DROPCOLUMN,null);}
	{altertable}	{return tok(sym.ALTERTABLE,null);}
	{altercolumn}	{return tok(sym.ALTERCOLUMN,null);}
	{primarykey}	{return tok(sym.PRIMARYKEY,null);}
	{default}		{return tok(sym.DEFAULT,null);}
	{unique}		{return tok(sym.UNIQUE,null);}
	{foreignkey}	{return tok(sym.FOREIGNKEY,null);}
	{references}	{return tok(sym.REFERENCES,null);}
	{check}			{return tok(sym.CHECK,null);}
	{select}		{return tok(sym.SELECT,null);}
	{delete}		{return tok(sym.DELETE,null);}
	{update}		{return tok(sym.UPDATE,null);}
	{insertinto}	{return tok(sym.INSERTINTO,null);}
	{where}			{return tok(sym.WHERE,null);}
	{from}			{return tok(sym.FROM,null);}
	{as}			{return tok(sym.AS,null);}
	{distinct}		{return tok(sym.DISTINCT,null);}
	{orderby}		{return tok(sym.ORDERBY,null);}
	{groupby}		{return tok(sym.GROUPBY,null);}
	{in}			{return tok(sym.IN,null);}
	{like}			{return tok(sym.LIKE,null);}
	{having}		{return tok(sym.HAVING,null);}
	{all}			{return tok(sym.ALL,null);}
	{any}			{return tok(sym.ANY,null);}
	{values}		{return tok(sym.VALUES,null);}
	{add}			{return tok(sym.ADD,null);}
	{set}			{return tok(sym.SET,null);}
	{union}			{return tok(sym.UNION,null);}
	{on}			{return tok(sym.ON, null);}
	{null}			{return tok(sym.NULL,null);}
	{notnull}		{return tok(sym.NOTNULL, null);}
	
	{not}			{return tok(sym.NOT,null);}
	{and}			{return tok(sym.AND,null);}
	{or}			{return tok(sym.OR,null);}
	{asc}			{return tok(sym.ASC,null);}
	{desc}			{return tok(sym.DESC,null);}

	{true}			{return tok(sym.TRUE,null);}
	{false}			{return tok(sym.FALSE,null);}
	
	{int}			{return tok(sym.INT,null);}
	{float}			{return tok(sym.FLOAT,null);}
	{double}		{return tok(sym.DOUBLE,null);}
	{decimal}		{return tok(sym.DECIMAL,null);}
	{char}			{return tok(sym.CHAR,null);}
	{varchar}		{return tok(sym.VARCHAR,null);}
	{bool}			{return tok(sym.BOOL,null);}	

	{avg}			{return tok(sym.AVG,null);}
	{max}			{return tok(sym.MAX,null);}
	{min}			{return tok(sym.MIN,null);}
	{sum}			{return tok(sym.SUM,null);}
	{count}			{return tok(sym.COUNT,null);}

	{ws}			{}
	{id}			{return tok(sym.ID,yytext().toLowerCase());}
	{REALVALUE}	{return tok(sym.REALVALUE,new Double(yytext()));}
	{intvalue}		{return tok(sym.INTVALUE,new Integer(yytext()));}

}

<STRINGSTATE>
{
	{doublequote}
		{stringvalue.append("'");}
	[']
		{yybegin(YYINITIAL);return tok(sym.STRINGVALUE,stringvalue.toString());}
	{str}
		{stringvalue.append(yytext());}
	
}
