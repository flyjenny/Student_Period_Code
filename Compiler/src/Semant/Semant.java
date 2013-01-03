package Semant;
import Absyn.*;
import Symbol.*;
import Types.*;

public class Semant{
	Env env;	
	
	void error(int pos, String e) throws Error{
		env.errorMsg.error(pos, e);
		throw new Error(e);
	}
		
	public Semant(ErrorMsg.ErrorMsg err) { 
		env=err;
	}
		
	
		
	/**********************transEXP*************************/
	ExpTy transExp(Exp e) {
		if (e instanceof IntExp) return transExp((IntExp) e);
    else if (e instanceof StringExp) return transExp((StringExp) e);
    else if (e instanceof VarExp) return transExp((VarExp) e);
    else if (e instanceof NilExp) return transExp((NilExp) e);
    else if (e instanceof OpExp) return transExp((OpExp)e);
    else if (e instanceof CallExp) return transExp((CallExp) e);
    else if (e instanceof RecordExp) return transExp((RecordExp) e);
    else if (e instanceof SeqExp) return transExp((SeqExp) e);
		else if (e instanceof AssignExp) return transExp((AssignExp) e);
		else if (e instanceof IfExp) return transExp((IfExp) e);
    else if (e instanceof WhileExp) return transExp((WhileExp) e);
    else if (e instanceof ForExp) return transExp((ForExp) e);
    else if (e instanceof BreakExp) return transExp((BreakExp) e);
    else if (e instanceof LetExp) return transExp((LetExp) e);
    else if (e instanceof ArrayExp) return transExp((ArrayExp) e);
    else throw new Error("ERROR: Undeclared Parse Exp Type");
  }
  
	/***IntExp, StringExp, NilExp, VarExp: 无需检查***/
	//intExp()
	//	int value;
	ExpTy transExp(IntExp e){
		return new ExpTy(e, Type._int);
	}	
	//StringExp()
	//	String value;
	ExpTy transExp(StringExp e){
//		return new ExpTy(null, Type._string);
		return new ExpTy(e, Type._string);
	}	
	//NilExp()
	//
	ExpTy transExp(NilExp e){
//		return new ExpTy(null, Type._nil);
	return new ExpTy(e, Type._nil);
	}	
	//VarExp()
	//	Var var;
	ExpTy transExp(VarExp e){
		return transVar(e.var);
	}
	
	//OpExp()
	//	Exp left; Exp right; int oper;
	ExpTy transExp(OpExp e){
		ExpTy left = transExp(e.left);
		ExpTy right = transExp(e.right);
  	if ( e.oper==19 || e.oper == 20){ // ==, !=
  		if (left.ty == Type._void || right.ty == Type._void)
  			error(e.left.pos, "ERROR: Unexpected type VOID in equation expression.");
  		else if (left.ty == Type._nil && right.ty == Type._nil)
  			error(e.left.pos, "ERROR: NIL type of both side of equation expression.");	
  		else if( !(right.ty.coerceTo(left.ty) || left.ty.coerceTo(right.ty)) ) //record <> nil
  			error(e.right.pos, "ERROR: Unmatched type in equation expression.");
  		if (right.ty == Type._string)
//			return new ExpTy(null, Type._int);	
  			return new ExpTy(e, Type._int);
  		return new ExpTy(e, Type._int);
		}
		if( e.oper>=15 && e.oper<=18) { //+, -, *, /
			if (! (left.ty == Type._int) )
  			error(e.left.pos, "ERROR: Required integer of calculate left expression.");
  		if (! (right.ty == Type._int) )
  			error(e.right.pos, "ERROR: Required integer of calculate right expression.");
//  		return new ExpTy(null, Type._int);	
			return new ExpTy(e, Type._int);	
		}
		if( e.oper>=6 && e.oper<=9 ){ //<, <=, >, >=
  		if ( left.ty == Type._string && right.ty == Type._string)
  			return new ExpTy(e, Type._int);
  		if ( left.ty == Type._int && right.ty == Type._int)
//			return new ExpTy(null, Type._int);
  			return new ExpTy(e, Type._int);
  		error(e.left.pos, "ERROR: Unmatched type in relation expression.");
  		return new ExpTy(null, Type._int);
		}
		
		error(e.left.pos, "ERROR: Undeclared operation type.");
		return new ExpTy(null, Type._int); // ???
	}
		
	//AssignExp()
	//	Var var; Exp exp;
	ExpTy transExp(AssignExp e){
		ExpTy left = transVar(e.var);
		ExpTy right = transExp(e.exp);
		if (right.ty == Type._void) 
			error(e.exp.pos, "ERROR: Unexpected type VOID in assign expression.");
		else if (! right.ty.coerceTo(left.ty))
			error(e.exp.pos, "ERROR: Unmatched type in right of assign expression.");
//	return new ExpTy(null, Type._void);
		return new ExpTy(e, Type._void);
	}
	
	//CallExp()
	//	Symbol func; ExpList args;	
	ExpTy transExp(CallExp e){
		Entry x =(Entry) env.vEnv.get(e.func);
		RECORD fml;
		if (x instanceof FuncEntry) {
			fml = ((FuncEntry) x).formals;
		}
		else if (x instanceof StdFuncEntry) {
			fml = ((StdFuncEntry) x).formals;
		}
		else { 
			error(e.pos, "ERROR: Undeclared function.");
			return new ExpTy(null, Type._void);
		}
		
		ExpList arg = e.args;
		ExpTy exp;
		java.util.ArrayList<Exp> args = new java.util.ArrayList<Exp>();
		while (arg != null && fml != null){
			exp = transExp(arg.head);
			if (exp.ty == Type._void)
				error(arg.head.pos, "ERROR: Unexpected type VOID in function formals.");
			else if (! fml.fieldType.coerceTo(exp.ty))
				error(arg.head.pos, "ERROR: Unmatched type in function formals.");
			args.add(exp.exp);
			arg = arg.tail; fml = fml.tail;
		}
		if ( arg == null && fml != null)
			error(e.args.head.pos, "ERROR: Lack formals in function call.");
		else if (arg != null && fml==null)
			error(e.args.head.pos, "ERROR: Too much formals found in function call.");
		if (x instanceof FuncEntry)
			return new ExpTy(e, ((FuncEntry) x).result);
		else
			return new ExpTy( e, ((FuncEntry) x).result);	
//		return new ExpTy(null, ((FuncEntry) x).result);
	}
	
	//SeqExp()
	//	ExpList list;
	ExpTy transExp(SeqExp e){
		ExpList list = e.list;
		if (list == null) return new ExpTy(e, Type._void);
		ExpTy exp = transExp(list.head);
		if (list.tail == null) return exp;
		Exp body = exp.exp;
		list = list.tail;
		while (list.tail != null){
			exp = transExp(list.head);
			body = translator.combine2Stm(body, exp.exp);
			list = list.tail;
		}
		exp = transExp(list.head);
		if (exp.ty == Type._void) 
			return new ExpTy(translator.combine2Stm(body, exp.exp), Type._void);
		else 
			return new ExpTy(translator.combine2Exp(body, exp.exp), exp.ty);
//		return exp;
	}
	
	//RecordExp()
	//	FieldExpList fields; Symbol typ;
	//RECORD 
	//	Symbol fieldName; Type fieldType; RECORD tail;
	//transRecordExp(Level , ArrayList ) 
	ExpTy transExp(RecordExp e){
		Type ty = (Type) env.tEnv.get(e.typ);
		java.util.ArrayList<Exp> array = new java.util.ArrayList<Exp>();
		if (! (ty instanceof RECORD))
			error(e.pos, "ERROR: Undeclared record type: " + e.typ);
		else {
			RECORD rec = (RECORD) ty;
			FieldExpList list = e.fields;
			ExpTy exp;
			while (rec != null && list != null) {
				if (list.name != rec.fieldName)
					error(list.pos, "ERROR: Unmatched name in record fields.");
				exp = transExp( list.init );
				array.add(exp.exp);
				if (!(exp.ty.coerceTo( rec.fieldType ))) {
					error(list.init.pos, "ERROR: Unmatched type in record fields.");
				}
				rec = rec.tail; list = list.tail;
			}
			if (rec != null)
				error(list.pos, "ERROR: Lack fields in variable declaration.");
			if (list != null)
				error(list.pos, "ERROR: Too much fields in variable declaration.");
  	}
		return new ExpTy(e, ty);
//		return new ExpTy(null, ty);
	}
	
	//ArrayExp()
	//	Exp size; Exp init; Symbol typ;
	ExpTy transExp(ArrayExp e){
		Type ty = (Type) env.tEnv.get(e.typ);
		if (!(ty instanceof ARRAY)){
			error(e.pos,"ERROR: Undeclared array type: " + e.typ + " : " + ty);
			return new ExpTy(null, Type._void);
		}	else {
			ExpTy size = transExp(e.size);
			if (!(size.ty == Type._int))
				error(e.size.pos, "ERROR: The size of array must be integer.");
			ExpTy init = transExp(e.init);
			Type elem = ((ARRAY) ty).element;
			if ( ! (init.ty.coerceTo(elem)) ){
				error(e.init.pos, "ERROR: Unmatched type of array initial declaration.");
			}
//		return new ExpTy(null, ty);
			return new ExpTy(translator.ArrayExp(level, size.exp, init.exp), ty);
		}
	}
	
	//IfExp()
	//	Exp test; Exp thenclause; Exp elseclause;
	ExpTy transExp(IfExp e){
		ExpTy test = transExp(e.test);
		if (!(test.ty == Type._int)){
			error(e.test.pos, "ERROR: Bad test condition in IF expression.");
			return new ExpTy(null, Type._void);
		}
		ExpTy thenExp = transExp(e.thenclause);
		if (e.elseclause == null) {
			if (!(thenExp.ty == Type._void))
				error(e.thenclause.pos, "ERROR: Expected VOID type return from THEN clause.");
			return new ExpTy(e, Type._void);
		}
		ExpTy elseExp = transExp(e.elseclause);
		if ( !(elseExp.ty.coerceTo(thenExp.ty) || thenExp.ty.coerceTo(elseExp.ty)) )
			error(e.thenclause.pos, "ERROR: Unmatched return types of THEN-ELSE clause.");
//	return new ExpTy(null, elseExp.ty);
		Type typ = thenExp.ty;
		if (thenExp.ty == Type._nil) typ = elseExp.ty;
		return new ExpTy(e, typ);
	}
	
	//LetExp()
	//	DecList decs; Exp body;
	ExpTy transExp(LetExp e){
		env.vEnv.beginScope();
		env.tEnv.beginScope();
		Exp dec = translator.NoOp();
		for (DecList p = e.decs; p != null; p = p.tail)
			dec = translator.combine2Stm(dec, transDec(p.head));
		ExpTy body;
		if (e.body != null) body = transExp(e.body);
		else body = new ExpTy(null, Type._void);
		env.vEnv.endScope();
		env.tEnv.endScope();
		if (body.ty == Type._void)
			dec = translator.combine2Stm(dec, body.exp);
		else
			dec = translator.combine2Exp(dec, body.exp);
		return new ExpTy( dec, body.ty );
//	return new ExpTy(null, body.ty);
	}
			
	//ForExp()
	//	VarDec var; Exp hi; Exp body;
	ExpTy transExp(ForExp e){
		ExpTy low = transExp(e.var.init);
		if (!(low.ty == Type._int))
			error(e.var.init.pos, "ERROR: Expected integer type of loop variable.");
		ExpTy high = transExp(e.hi);
		if (!(high.ty == Type._int))
			error(e.hi.pos, "ERROR: Expected integer type of loop variable.");
		env.vEnv.beginScope();
		Access acc = level.allocLocal(true);
		env.vEnv.put(e.var.name, new LoopVarEntry(acc, Type._int));
		
		Label done = new Label();
		loopStack.add(done);
		ExpTy body = transExp(e.body);	
		loopStack.pop();
		
		if (! (body.ty == Type._void))
			error(e.body.pos, "ERROR: Unexpected return type of for loop.");
		env.vEnv.endScope();				
		//return new ExpTy(null, Type._void);
		// ? Access
		return new ExpTy( translator.ForExp(level, acc, low.exp, high.exp, body.exp, done), Type._void);
	}
	
	//BreakExp()
	ExpTy transExp(BreakExp e){
		if ( loopStack.empty())
			error(e.pos, "ERROR: Unexpected break expression.");
		return new ExpTy(translator.BreakExp(loopStack.peek()), Type._void);
	}
	
	//Exp.WhileExp()
	//	Exp test; Exp body;
	ExpTy transExp(WhileExp e) {
		ExpTy test = transExp(e.test);
		//检查测试部分
		if (!(test.ty == Type._int))
			error(e.test.pos, "ERROR: Bad test condition WHILE expression!");
		Label done = new Label();
		loopStack.add(done);
		//检查并翻译循环体部分
		ExpTy body = transExp(e.body);
		loopStack.pop();
		if (!(body.ty == Type._void))
			error(e.body.pos, "ERROR: WHILE body should not return a value!");
		//调用translate部分，翻译整个while表达式，无返回类型
//		return new ExpTy(null, Type._void);
		return new ExpTy(translator.WhileExp(test.exp, body.exp,done), Type._void);
	}
	
	/*****************transVAR*******************/

	ExpTy transVar(Var v){
		if (v instanceof SimpleVar) return transVar((SimpleVar) v);
		else if (v instanceof FieldVar) return transVar((FieldVar) v);
		else if (v instanceof SubscriptVar) return transVar((SubscriptVar) v);
		else throw new Error("ERROR: Undeclared Parse Var Type.");
	}
	
	//SimpleVar()
	//	Symbol name; int pos;
	ExpTy transVar(SimpleVar v){
		Entry x = (Entry) env.vEnv.get(v.name);
		if (x instanceof VarEntry) {
			VarEntry ent = (VarEntry) x;			
//			return new ExpTy(null, ent.ty);
			return new ExpTy(translator.SimpleVar(ent.access, level), ent.ty);
		} 
		if (x instanceof LoopVarEntry) {
			LoopVarEntry ent = (LoopVarEntry) x;
//			return new ExpTy(null, ent.ty);
			return new ExpTy(translator.SimpleVar(ent.access, level), ent.ty);
		}
		error(v.pos, "ERROR: Undeclared variable: " + v.name);
		return new ExpTy(null, Type._nil);	//anything will do!
	}
		
	//FieldVar()
	//	Var var; Symbol field;
	//  var:RECORD; 
	ExpTy transVar(FieldVar v){
		ExpTy var = transVar(v.var);
		if (!(var.ty instanceof RECORD)) {
			error(v.pos, "ERROR: Expected record type here.");
			return new ExpTy(null, Type._void);			
		}
		//逐个查找记录的域，如果没有一个匹配当前域变量的域，则报错
		RECORD list = (RECORD) var.ty;
		int num = 0;
		while (list != null){
			if (list.fieldName == v.field)
				//return new ExpTy(null, list.fieldType);
				//?????
				return new ExpTy(translator.FieldVar( var.exp, num), list.fieldType);
			list = list.tail; num++;
		}
		error(v.var.pos, "ERROR: Undeclared field name.");
		return new ExpTy(null, Type._void);
	}
	
	//SubscriptVar()
	//	Var var; Exp index;
	ExpTy transVar(SubscriptVar v){
		ExpTy var = transVar(v.var);
		if (! (var.ty instanceof ARRAY)) {
			error(v.pos, "ERROR: Expected array type here.");
			return new ExpTy(null, Type._void);
		}
		ExpTy index = transExp(v.index);
		if (! (index.ty == Type._int)) {
			error(v.index.pos, "ERROR: Expected int type of index.");
			return new ExpTy(null, Type._void);
		}
		return new ExpTy(translator.SubscriptVar(var.exp, index.exp), ((ARRAY)var.ty).element);
		//return new ExpTy(null, ((ARRAY)var.ty).element);
	}
		
	/*********************transTY********************/
	//NameTy(), ArrayTy(), RecordTy()
	//无动作，仅修改tEnv
	//NameTy
	//	Symbol name; int pos;
	Type transTy(Ty e){
		if (e instanceof NameTy) return transTy((NameTy) e);
		if (e instanceof ArrayTy) return transTy((ArrayTy) e);
		if (e instanceof RecordTy) return transTy((RecordTy) e);
		throw new Error("ERROR: Undeclared Parse Type.");
	}
	//NameTy
	//	Symbol name;
	Type transTy(NameTy e){
		Type x =(Type) env.tEnv.get(e.name);
/*		if (x == null) 
			error(e.pos, "ERROR: Undeclared Type.");
		if (x.actual() == null)
			error(e.pos, "ERROR: type.actual() is undefined type.");
		return x;
	*/
		if (x == null || x.actual() == null){
			error(e.pos, "ERROR: Undeclared type: " + e.name + ".");
			return null;
		}
		return x.actual();
	}
	//ArrayTy
	// Symbol typ; int pos;
	Type transTy(ArrayTy e){
		Type x = (Type) env.tEnv.get(e.typ);
		if (x == null){
			error(e.pos, "ERROR: Undeclared type: " + e.typ + ".");
			return null;
		}
		return new ARRAY(x);
	}
	//RecordTy
	//	fields;
	Type transTy(RecordTy e){
		if (e == null) return Type._nil;
		return transExp((FieldList) e.fields);
	}
	/*******************FieldList***************/
	//FieldList()
	//Symbol name;Symbol typ; FieldList tail; Boolean escape;
	RECORD transExp(FieldList e){
		//转成RECORD类型
		java.util.Set<Symbol> set = new java.util.HashSet<Symbol>();
		FieldList list = e;
		Type ty;
		RECORD rechead = null, rec = null;		
		if (list != null){
			ty =(Type) env.tEnv.get(list.typ);
			if (ty == null) 
				error(list.pos, "ERROR: Undeclared Type: " + list.typ );
			else {
				rec = rechead = new RECORD(list.name, ty, null);
				set.add(list.name);
			}
			list = list.tail;
		}
		for ( ; list != null; list = list.tail){
			if (set.contains(list.name)) 
				error(list.pos, "ERROR: Duplicated field name in declaration.");
			set.add(list.name); 
			ty =(Type) env.tEnv.get(list.typ);
			if (ty == null) 
				error(list.pos, "ERROR: Undeclared Type: " + list.typ );
			else {
				rec.tail = new RECORD(list.name, ty, null);
				rec = rec.tail;
			}
		}
		return rechead;
	}
	
	/*********************DEC*******************/
	Translate.Exp transDec(Dec d){
		if (d instanceof FunctionDec) return transDec((FunctionDec) d);
		if (d instanceof TypeDec) return transDec((TypeDec) d);
		if (d instanceof VarDec) return transDec((VarDec) d);
		throw new Error("ERROR: Undeclared Parse Type.");
	}
	//FunctionDec()
	//	FieldList params; Symbol name; NameTy result; Exp body; FunctionDec next;
	Translate.Exp transDec(FunctionDec d){
		java.util.Set<Symbol> set = new java.util.HashSet<Symbol>();
		FunctionDec list = d;
		Type ty;
		FuncEntry func;
		Entry ent;
		Level root = level;
		RECORD fml;
		//nested function declaration
		while (list != null){
			if (set.contains(list.name))
				error(d.pos, "ERROR: Duplicated declaration of function : " + list.name);
			else {
				ent = (Entry) env.vEnv.get(list.name);
				if (ent instanceof StdFuncEntry)
					error(d.pos, "ERROR: Standard function cannot be override.");
				else {
					if (list.result == null) ty = Type._void;
					else ty = transTy(list.result);
					// params 可以为null, result为null即为void
					//FuncEntry(Level, Label, RECORD, Type);
					BoolList boollist = null;
					//VarEntry(Access, Type);
					//Access(Level, Frame.Access);
					for (FieldList fmls = list.params; fmls != null; fmls = fmls.tail)
						boollist = new BoolList( fmls.escape, boollist);
					level = new Level(root, Symbol.symbol('.'+list.name.toString()), boollist);
					func = new FuncEntry(level, level.label, transExp(list.params), ty);
					env.vEnv.put(list.name, func);
					set.add(list.name);
				}
			}
			list = list.next;
		}
		
		list = d;
		ExpTy result;
		while (list != null){		
			func = (FuncEntry) env.vEnv.get(list.name);
			
			env.vEnv.beginScope();			
					
			level = func.level;
			fml = func.formals;		
			Frame.AccessList acc = level.formals.next;
			while (fml != null) {
				env.vEnv.put(fml.fieldName, new VarEntry((Access) acc.head, fml.fieldType));
				fml = fml.tail; acc = acc.next;
			}
			result = transExp(list.body);
			if (list.result == null) {
				if (result.ty != Type._void)
					error(list.body.pos, "ERROR: Expected no return value of procedure.");
			} else if (! result.ty.coerceTo( transTy(list.result) )) {
				error(list.body.pos, "ERROR: Unmatched type of function result.");
			}
			func = (FuncEntry) env.vEnv.get(list.name);
			if (result.exp == null) {
				throw new Error ();
			}
			if (result.ty == Type._void)
				translator.procEntryExit(func.level, result.exp, false);
			else
				translator.procEntryExit(func.level, result.exp, true);
			
			env.vEnv.endScope();
			
			list = list.next;
		}
		
		level = root;
		return null;
		//return translator.NoOp();
	}
	
	//TypeDec()
	//	Symbol name; Ty ty; TypeDec next;
	Translate.Exp transDec(TypeDec d){
//		Type ty = d.ty.translate(this);
		TypeDec list = d;
		java.util.Dictionary<Symbol, NAME> dict = new java.util.Hashtable<Symbol, NAME>();
		//dict记录已有name类型的地址
		NAME left,right;
		Type ty;
		NameTy init;
		while (list != null){
			left = dict.get(list.name);
  		if (left != null)
  			error(list.pos, "ERROR: Duplicated declaration of type: " + list.name);
  		else {
  			ty = (Type) env.tEnv.get(list.name);
  			if (!(ty instanceof NAME))
  				left = new NAME(list.name);
  			else 
  				left = (NAME) ty;
  			dict.put(list.name, left);
  			env.tEnv.put(list.name, left); 	
  		}		
  		list = list.next;
		}
		
		list = d;
		while (list != null){
			left = dict.get(list.name);
			if (list.ty instanceof NameTy){
				init = (NameTy) list.ty;
				ty = dict.get(init.name);
				if (ty == null) {
					left.bind(transTy(init));
				}	else {
					left.bind(ty);
					left.isLoop(); //发现 loopDefinition
				}
			} else left.bind(transTy(list.ty));
			env.tEnv.put(list.name, left.actual());
			list  = list.next;
		}		
		list = d; 
		ARRAY arr; 
		RECORD rec;
		while (list != null){			
			if (!(list.ty instanceof NameTy)){
				ty = (Type) env.tEnv.get(list.name);
				if (ty instanceof ARRAY) {
					arr = (ARRAY) ty;
					arr.element = arr.element.actual();
					env.tEnv.put(list.name, arr);
				} else {
					rec = (RECORD) ty;
					while (rec != null) {
						rec.fieldType = rec.fieldType.actual();
						rec = rec.tail;
					}
					env.tEnv.put(list.name, (RECORD) ty);
				}
			} else if (transTy(list.ty) == Type._void)
				error(list.pos, "Recursive definition of simple types.");
			list = list.next;
		}	
		return null;
		//return translator.NoOp();
	}
	
	//VarDec()
	//	Symbol name; NameTy typ; Exp init (!= null); Boolean escape = true;
	// isLoop...!
	Translate.Exp transDec(VarDec d){
		ExpTy exp = transExp(d.init);
		if (exp.ty == Type._void)
			error(d.init.pos, "ERROR: Expectd unvoid variable declaration.");
		Type ty;
		Access acc;
		if (d.typ !=  null){
			//有显示的类型声明
			ty = transTy(d.typ);;
			if (ty == null)
				error(d.typ.pos, "ERROR: Undeclared type: " + d.typ);
			else if ( exp.ty.coerceTo( ty ) ) {
				acc = level.allocLocal(true);
				env.vEnv.put(d.name, new VarEntry(acc, ty));
				return translator.AssignExp( translator.SimpleVar(acc,level), exp.exp); 
				//return null;
			}	else {
				error(d.init.pos, "ERROR: Required variable initial type: " + ty.getClass().getSimpleName() );
			}
		} else {
			if (exp.ty == Type._nil)
				error(d.init.pos, "ERROR: Nil expression must constrained by record type.");
			else {
				acc = level.allocLocal(true);
				env.vEnv.put(d.name, new VarEntry(acc, exp.ty));
				return translator.AssignExp( translator.SimpleVar(acc,level), exp.exp); 
				//return null;
			}
		}
		return null;
		//return translator.VarDec();
	}
	
}