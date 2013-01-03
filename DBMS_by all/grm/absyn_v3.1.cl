package QueryManager.Parser.Absyn;

attribute "int" pos with Absyn;

Absyn ::= {List}
		| {Item}
		| {ColumnCons}
		| {DataType}
		| {AlgebraExp}
		| {AggregateExp}
		| {WhereExp}
		| {WhereCondExp}
		| {CmpOp}
		| {GroupExp}
		| {HavingExp}
		| {HavingCondExp}
		| {OrderExp}

List::= {SqlExpList}
	  | {ValueExpList}
	  | {ColumnList}
	  | {ColumnDefList}
	  | {SelectColumnList}
	  | {OrderColumnList}
	  | {UpdateColumnList}
	  | {IdList}
	  | {FromTableList}

Item ::= {SqlExp}
	   | {ValueExp}
	   | {Column}
	   | {ColumnDef}
	   | {SelectColumn}
	   | {OrderColumn}
	   | {UpdateColumn}
	   | {FromTable}

Const ::= one of
		  INVALID, INTVALUE, REALVALUE, STRINGVALUE, BOOLVALUE, NULLVALUE
		  PRIMARYKEY, UNIQUE, NOTNULL, NOCONS,
		  INT, FLOAT, DOUBLE, DECIMAL, CHAR, VARCHAR, BOOL,
		  PLUS, MINUS, STAR, DIVIDE,
		  AVG, MAX, MIN, SUM, COUNT,
		  EQ, NEQ, GT, GE, LT, LE,
		  ALL, ANY, SOLO,
		  INDEX, TABLE, VIEW,
		  ADD, ALTER, DROP

SqlExpList ::= SqlExp : head
			   SqlExpList : tail

SqlExp ::= {CreateExp}
		 | {SelectExp}
		 | {InsertExp}
		 | {DeleteExp}
		 | {UpdateExp}
		 | {DropExp}
		 | {AlterExp}

CreateExp ::= {CreateTableExp}
			| {CreateViewExp}
			| {CreateIndexExp}

CreateTableExp ::= "String" : name
				   ColumnDefList : list

CreateViewExp ::= "String" : name
				  SelectExp : selectExp

CreateIndexExp ::= "String" : iName
				   "String" : tName
				   IdList : list

ColumnDefList ::= ColumnDef : head
				  ColumnDefList : tail

ColumnDef ::= "String" : name
			  DataType : dataType
			  ColumnCons : cons

ColumnCons ::= "int" : type
			   ValueExp : value

IdList ::= "String" : head
		   IdList : tail

ValueExp ::= {IntExp}
		   | {RealExp}
		   | {StrExp}
		   | {BoolExp}
		   | {NullExp}

IntExp ::= "String" : value

RealExp ::= "String" : value

StrExp ::= "String" : value

BoolExp ::= "String" : value

NullExp ::=

DataType ::= "int" : type
			 "int" : size
			 "int" : p
			 "int" : s

SelectExp ::= {SelectOp}
			| {SelectBinExp}

SelectOp ::= "boolean" : selectDist
			 "boolean" : selectAll
			 SelectColumnList : cList
			 FromTableList : tList
			 WhereExp : whereExp

SelectBinExp ::= "boolean" : unionAll
				 SelectExp : left
				 SelectExp : right

SelectColumnList ::= SelectColumn : head
					 SelectColumnList : tail

SelectColumn ::= "String" : name
			     AlgebraExp : algebraExp

AlgebraExp ::= {AlgebraOp}
			 | {AlgebraBinExp}

AlgebraOp ::= Column : column
			  ValueExp : value
			  AggregateExp : aggregateExp

AlgebraBinExp ::= "int" : type
				  AlgebraExp : left
				  AlgebraExp : right

Column ::= "String" : tName
		   "String" : cName

AggregateExp ::= "int" : type
				 Column : column

FromTableList ::= FromTable : head
				  FromTableList : tail

FromTable ::= "String" : tName
			  SelectExp : selectExp
			  "String" : nName

WhereExp ::= WhereCondExp : cond
			 GroupExp : groupExp
			 OrderExp : orderExp

WhereCondExp ::= {WhereCondOp}
			   | {WhereCondBinExp}

WhereCondOp ::= {CmpExp}
			  | {LikeExp}
			  | {InExp}

WhereCondBinExp ::= "boolean" : isAnd
					WhereCondExp : left
					WhereCondExp : right

CmpExp ::= "int" : type
		   CmpOp : left
		   CmpOp : right

CmpOp ::= "int" : type
		  Column : column
		  SelectExp : selectExp
		  ValueExp : value

LikeExp ::= Column : column
			String : format

InExp ::= "boolean" : isIn
		  Column : column
		  SelectExp : selectExp

GroupExp ::= ColumnList : list
			 HavingExp : havingExp

ColumnList ::= Column : head
			   ColumnList : tail

HavingExp ::= HavingCondExp : cond

HavingCondExp ::= {HavingCondOp}
				| {HavingCondBinExp}

HavingCondOp ::= "int" : type
				 AggregateExp : aggregateExp
				 ValueExp : value

HavingCondBinExp ::= "boolean" : isAnd
					 HavingCondExp : left
					 HavingCondExp : right

OrderExp ::= OrderColumnList : list

OrderColumnList ::= OrderColumn : head
				    OrderColumnList : tail

OrderColumn ::= "boolean" : isAsc
				Column : column

InsertExp ::= "String" : name
			  IdList : iList 
			  ValueExpList : vlist

ValueExpList ::= ValueExp : head
				 ValueExpList : tail

DeleteExp ::= "String" : name
			  WhereCondExp : cond

UpdateExp ::= "String" : name
			  UpdateColumnList : list 
			  WhereCondExp : cond

UpdateColumnList ::= UpdateColumn : head
					 UpdateColumnList : tail

UpdateColumn ::= "String" : name
				 ValueExp : value

DropExp ::= "int" : type 
			IdList : list

AlterExp ::= "int" : type
			 "String" : tName
			 "String" : cName
			 DataType : dataType