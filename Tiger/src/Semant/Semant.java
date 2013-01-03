package Semant;

import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintStream;
import Mips.MipsFrame;
import RegAlloc.MapDisplay;
import RegAlloc.SetDisplay;
import RegAlloc.TableDisplay;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public class Semant {
	public Env env;
	Types.Type INT, STRING, VOID, NIL;
	Integer forWhileCounter, mark;
	Hashtable sameFieldNameDetected;
	public static Translate.Translate translate;
	public static Translate.Level currentLevel;
	public static Tree.Print IRprint;

	public Semant(ErrorMsg.ErrorMsg err) {
		this(new Env(err));
		translate = new Translate.Translate();
		currentLevel = new Translate.Level(null, new Temp.Label("main"), null);
		INT = new Types.INT();
		NIL = new Types.NIL();
		VOID = new Types.VOID();
		STRING = new Types.STRING();
		mark = new Integer(54746293);
		forWhileCounter = new Integer(0);

		// insert the default type
		Types.NAME stringname = new Types.NAME(Symbol.Symbol.symbol("string"));
		Types.NAME intname = new Types.NAME(Symbol.Symbol.symbol("int"));
		stringname.bind(STRING);
		intname.bind(INT);
		env.tenv.put(Symbol.Symbol.symbol("int"), intname);
		env.tenv.put(Symbol.Symbol.symbol("string"), stringname);

		// insert the default function
		Translate.Level funLevel = new Translate.Level(currentLevel,
				new Temp.Label("print"), null);// new Util.BoolList(true,null));
		env.venv.put(Symbol.Symbol.symbol("print"), new FunEntry(
				new Types.RECORD(Symbol.Symbol.symbol("s"), STRING, null),
				VOID, currentLevel, new Temp.Label("print"), true));

		funLevel = new Translate.Level(currentLevel, new Temp.Label("printi"),
				null);// new Util.BoolList(true,null));
		env.venv.put(Symbol.Symbol.symbol("printi"), new FunEntry(
				new Types.RECORD(Symbol.Symbol.symbol("i"), INT, null), VOID,
				funLevel, new Temp.Label("printi"), true));

		funLevel = new Translate.Level(currentLevel, new Temp.Label("flush"),
				null);// new Util.BoolList(true,null));
		env.venv.put(Symbol.Symbol.symbol("flush"), new FunEntry(null, VOID,
				funLevel, new Temp.Label("flush"), true));

		funLevel = new Translate.Level(currentLevel, new Temp.Label("getchar"),
				null);
		env.venv.put(Symbol.Symbol.symbol("getchar"), new FunEntry(null,
				STRING, funLevel, new Temp.Label("getchar"), true));

		funLevel = new Translate.Level(currentLevel, new Temp.Label("ord"),
				null);// new Util.BoolList(true,null));
		env.venv.put(Symbol.Symbol.symbol("ord"), new FunEntry(
				new Types.RECORD(Symbol.Symbol.symbol("s"), STRING, null), INT,
				funLevel, new Temp.Label("ord"), true));

		funLevel = new Translate.Level(currentLevel, new Temp.Label("chr"),
				null);// new Util.BoolList(true,null));
		env.venv.put(Symbol.Symbol.symbol("chr"), new FunEntry(
				new Types.RECORD(Symbol.Symbol.symbol("i"), INT, null), STRING,
				funLevel, new Temp.Label("chr"), true));

		funLevel = new Translate.Level(currentLevel, new Temp.Label("size"),
				null);// new Util.BoolList(true,null));
		env.venv.put(Symbol.Symbol.symbol("size"), new FunEntry(
				new Types.RECORD(Symbol.Symbol.symbol("s"), STRING, null), INT,
				funLevel, new Temp.Label("size"), true));

		funLevel = new Translate.Level(currentLevel,
				new Temp.Label("substring"), null);
		env.venv.put(Symbol.Symbol.symbol("substring"), new FunEntry(
				new Types.RECORD(Symbol.Symbol.symbol("s"), STRING,
						new Types.RECORD(Symbol.Symbol.symbol("f"), INT,
								new Types.RECORD(Symbol.Symbol.symbol("n"),
										INT, null))), STRING, funLevel,
				new Temp.Label("substring"), true));

		funLevel = new Translate.Level(currentLevel, new Temp.Label("concat"),
				null);// new Util.BoolList(true,new Util.BoolList(true,null)));
		env.venv.put(Symbol.Symbol.symbol("concat"), new FunEntry(
				new Types.RECORD(Symbol.Symbol.symbol("s1"), STRING,
						new Types.RECORD(Symbol.Symbol.symbol("s2"), STRING,
								null)), STRING, funLevel, new Temp.Label(
						"concat"), true));

		funLevel = new Translate.Level(currentLevel, new Temp.Label("not"),
				null);
		env.venv.put(Symbol.Symbol.symbol("not"), new FunEntry(
				new Types.RECORD(Symbol.Symbol.symbol("i"), INT, null), INT,
				funLevel, new Temp.Label("not"), true));

		funLevel = new Translate.Level(currentLevel, new Temp.Label("exit"),
				null);
		env.venv.put(Symbol.Symbol.symbol("exit"), new FunEntry(
				new Types.RECORD(Symbol.Symbol.symbol("i"), INT, null), VOID,
				funLevel, new Temp.Label("exit"), true));
	}

	public void transProg(Absyn.Exp exp) {
		boolean debug = Tiger.Overall.debug;
		RewriteAbsyn rewriteAbsyn = new RewriteAbsyn();
		IRprint = new Tree.Print(System.out);
		
	    if(Tiger.Overall.inline)
           exp = rewriteAbsyn.inline(exp);
   
	    Absyn.Print absynPrint = new Absyn.Print(System.out);
	    if(Tiger.Overall.debug)
	       absynPrint.prExp(exp, 0);
	    FindEscape.FindEscape findescape = new FindEscape.FindEscape(exp);
        ExpTy result = transExp(exp);
		Tree.Stm s = result.exp.unNx();
		translate.procEntryExit(currentLevel, result.exp);
		if(debug) {
			System.out.println("\nIntermediate Representation:");
			translate.fragDisplay();			
		}
		ArrayList<Assem.InstrList> arrayInstrList = new ArrayList<Assem.InstrList>();
		ArrayList<Frame.Frame> arrayFrame = new ArrayList<Frame.Frame>();
		Translate.ProcFrag fragPointer = translate.procFrags;
		while (fragPointer != null) {
			Assem.InstrList frameInstrList = null;
			if (debug)
				IRprint.prStm(fragPointer.stm);
			Tree.StmList stmList = (new Canon.TraceSchedule(
					new Canon.BasicBlocks(Canon.Canon
							.linearize(fragPointer.stm)))).stms;
			Tree.StmList printStmPointer = stmList;

			if (debug){
				System.out.println("\nIntermediate Code after canon:");
				while (printStmPointer != null) {
					IRprint.prStm(printStmPointer.head);
					printStmPointer = printStmPointer.tail;
				}
			}

			Tree.StmList stmPointer = stmList;
			while (stmPointer != null) {
				Assem.InstrList stmInstrList = fragPointer.frame
						.codegen(stmPointer.head);
				Assem.InstrList instrPointer = stmInstrList;
				if (instrPointer != null && debug)
					instrPointer.display();
				while (instrPointer != null) {
					frameInstrList = new Assem.InstrList(instrPointer.head,
							frameInstrList);
					instrPointer = instrPointer.tail;
				}
				stmPointer = stmPointer.tail;
			}
			
			
			frameInstrList = frameInstrList.reverse();
			frameInstrList = fragPointer.frame.procEntryExit2(frameInstrList);
			frameInstrList = fragPointer.frame.procEntryExit3(frameInstrList);
			if (Tiger.Overall.debug){
				System.out.println("\nMipscode before color:\n");
				frameInstrList.display();
			}

			boolean redeclare = fragPointer.frame.isRedeclared();
			if(!redeclare){
				arrayInstrList.add(frameInstrList);
				arrayFrame.add(fragPointer.frame);				
			}
			
			fragPointer = (Translate.ProcFrag) fragPointer.tail;
		}

		try {
			PrintStream mipscode = new PrintStream(Tiger.Overall.fileName
					+ ".s");
			File file = new File("runtime/runtime.s");
			BufferedReader library = new BufferedReader(new FileReader(file));
			mipscode.println(".globl main\n.text\n");
			RegAlloc.ProcRedundency procRedundency = new RegAlloc.ProcRedundency();
			for (int i = 0; i != arrayInstrList.size(); ++i) {
				Assem.InstrList instrsAnalyzing = arrayInstrList.get(i);
				FlowGraph.AssemFlowGraph assemFlowGraph = new FlowGraph.AssemFlowGraph(
						instrsAnalyzing);
				if (debug){
					System.out.println("here comes all the analysis of "
							+ arrayFrame.get(i).frameName().toString());
				    System.out.println("\nassemFlowGraph:\n");
					assemFlowGraph.show(System.out);					
				}
				RegAlloc.Liveness liveness = new RegAlloc.Liveness(
						assemFlowGraph);
				if (debug){
					System.out.println("\nliveness table:\n");
					TableDisplay.display2(liveness.nodeMapToliveInfo);					
				}
				liveness.interfere(assemFlowGraph);

				if (debug){
					System.out.println("\ninterfere graph:\n");
					liveness.show(System.out);				
				}
				RegAlloc.Color color = new RegAlloc.Color(liveness,
						assemFlowGraph);

				if (debug){
					System.out.println("\ncolor map:\n");
					MapDisplay.display(color.tempToRealTemp);						
				}

				RegAlloc.RegAlloc regAlloc = new RegAlloc.RegAlloc(
						instrsAnalyzing, color);
							
				procRedundency.moveRedundency(instrsAnalyzing, color);		
				RegAlloc.ReachOptimize reachOptimize; 
			    reachOptimize = new RegAlloc.ReachOptimize(instrsAnalyzing); 
				procRedundency.moveRedundency2(instrsAnalyzing);					
				procRedundency.moveRedundency3(instrsAnalyzing);

				regAlloc.instrs = instrsAnalyzing;
				regAlloc.spill(arrayFrame.get(i));
				ArrayList<String> finalInstr = regAlloc.finalInstr();
				for (int j = 0; j != finalInstr.size(); ++j) {
					String printInstr = finalInstr.get(j);
					if(debug)
					System.out.println(printInstr);
					mipscode.println(printInstr);
				}

			}
			mipscode.print(translate.frags());
			while (true) {
				String line = library.readLine();
				if (line == null)
					break;
				mipscode.println();
				mipscode.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Semant(Env e) {
		env = e;
	}

	Translate.Exp checkInt(ExpTy et, int pos) {
		if (!(et.type.actual() instanceof Types.INT))
			Tiger.Overall.myerror.error(pos, "INT required!");
		return et.exp;
	}

	Translate.Exp checkString(ExpTy et, int pos) {
		if (!(et.type.actual() instanceof Types.STRING))
			Tiger.Overall.myerror.error(pos, "STRING required!");
		return et.exp;
	}

	Translate.Exp checkVoid(ExpTy et, int pos) {
		if (!(et.type.actual() instanceof Types.VOID))
			Tiger.Overall.myerror.error(pos, "VOID required!");
		return et.exp;
	}

	Translate.Exp checkNil(ExpTy et, int pos) {
		if (!(et.type.actual() instanceof Types.NIL))
			Tiger.Overall.myerror.error(pos, "NIL required");
		return et.exp;
	}

	boolean isSameType(ExpTy e1, ExpTy e2) {
		return e1.type.actual().coerceTo(e2.type.actual())
				|| e2.type.actual().coerceTo(e1.type.actual());
	}

	boolean isSameType(Types.Type t1, Types.Type t2) {
		return t1.actual().coerceTo(t2.actual())
				|| t2.actual().coerceTo(t1.actual());
	}

	ExpTy transVar(Absyn.Var e) {
		if (e instanceof Absyn.SimpleVar)
			return transVar((Absyn.SimpleVar) e);
		if (e instanceof Absyn.FieldVar)
			return transVar((Absyn.FieldVar) e);
		if (e instanceof Absyn.SubscriptVar)
			return transVar((Absyn.SubscriptVar) e);
		return null;
	}

	ExpTy transExp(Absyn.Exp e) {
		if (e instanceof Absyn.StringExp)
			return transExp((Absyn.StringExp) e);
		if (e instanceof Absyn.IntExp)
			return transExp((Absyn.IntExp) e);
		if (e instanceof Absyn.NilExp)
			return transExp((Absyn.NilExp) e);
		if (e instanceof Absyn.VarExp)
			return transExp((Absyn.VarExp) e);
		if (e instanceof Absyn.OpExp)
			return transExp((Absyn.OpExp) e);
		if (e instanceof Absyn.AssignExp)
			return transExp((Absyn.AssignExp) e);
		if (e instanceof Absyn.CallExp)
			return transExp((Absyn.CallExp) e);
		if (e instanceof Absyn.SeqExp)
			return transExp((Absyn.SeqExp) e);
		if (e instanceof Absyn.RecordExp)
			return transExp((Absyn.RecordExp) e);
		if (e instanceof Absyn.ArrayExp)
			return transExp((Absyn.ArrayExp) e);
		if (e instanceof Absyn.IfExp)
			return transExp((Absyn.IfExp) e);
		if (e instanceof Absyn.WhileExp)
			return transExp((Absyn.WhileExp) e);
		if (e instanceof Absyn.ForExp)
			return transExp((Absyn.ForExp) e);
		if (e instanceof Absyn.BreakExp)
			return transExp((Absyn.BreakExp) e);
		if (e instanceof Absyn.LetExp)
			return transExp((Absyn.LetExp) e);
		return null;
	}

	Translate.Exp transDec(Absyn.Dec e) {
		if (e instanceof Absyn.FunctionDec)
			return transDec((Absyn.FunctionDec) e);
		if (e instanceof Absyn.VarDec)
			return transDec((Absyn.VarDec) e);
		if (e instanceof Absyn.TypeDec)
			return transDec((Absyn.TypeDec) e);
		return null;
	}

	Types.Type transTy(Absyn.Ty e) {
		if (e instanceof Absyn.NameTy)
			return transTy((Absyn.NameTy) e);
		if (e instanceof Absyn.RecordTy)
			return transTy((Absyn.RecordTy) e);
		if (e instanceof Absyn.ArrayTy)
			return transTy((Absyn.ArrayTy) e);
		return VOID;
	}

	ExpTy transExp(Absyn.StringExp e) {
		return new ExpTy(translate.createStringExp(e.value), STRING);
	}

	ExpTy transExp(Absyn.IntExp e) {
		return new ExpTy(translate.createIntExp(e.value), INT);
	}

	ExpTy transExp(Absyn.NilExp e) {
		return new ExpTy(translate.noOp, NIL);
	}

	ExpTy transExp(Absyn.VarExp e) {
		return transVar(e.var);
	}

	ExpTy transExp(Absyn.OpExp e) {
		ExpTy left = transExp(e.left);
		ExpTy right = transExp(e.right);
		switch (e.oper) {
		case Absyn.OpExp.PLUS:
			checkInt(left, e.left.pos);
			checkInt(right, e.right.pos);
			return new ExpTy(
					translate.createOpExp(e.oper, left.exp, right.exp), INT);

		case Absyn.OpExp.MINUS:
			checkInt(left, e.left.pos);
			checkInt(right, e.right.pos);
			return new ExpTy(
					translate.createOpExp(e.oper, left.exp, right.exp), INT);

		case Absyn.OpExp.MUL:
			checkInt(left, e.left.pos);
			checkInt(right, e.right.pos);
			return new ExpTy(
					translate.createOpExp(e.oper, left.exp, right.exp), INT);

		case Absyn.OpExp.DIV:
			checkInt(left, e.left.pos);
			checkInt(right, e.right.pos);
			return new ExpTy(
					translate.createOpExp(e.oper, left.exp, right.exp), INT);

		case Absyn.OpExp.GE:
			if (left.type.actual() == STRING && right.type.actual() == STRING)
				return new ExpTy(translate.createOpStringExp(e.oper, left.exp,
						right.exp), INT);
			if (left.type.actual() == INT && right.type.actual() == INT)
				return new ExpTy(translate.createOpExp(e.oper, left.exp,
						right.exp), INT);
			Tiger.Overall.myerror.error(e.pos, "not string or int to compare");
			return new ExpTy(null, INT);

		case Absyn.OpExp.GT:
			if (left.type.actual() == STRING && right.type.actual() == STRING)
				return new ExpTy(translate.createOpStringExp(e.oper, left.exp,
						right.exp), INT);
			if (left.type.actual() == INT && right.type.actual() == INT)
				return new ExpTy(translate.createOpExp(e.oper, left.exp,
						right.exp), INT);

			Tiger.Overall.myerror.error(e.pos, "type not matched in GTexp!");
			return new ExpTy(null, INT);

		case Absyn.OpExp.LE:
			if (left.type.actual() == STRING && right.type.actual() == STRING)
				return new ExpTy(translate.createOpStringExp(e.oper, left.exp,
						right.exp), INT);
			if (left.type.actual() == INT && right.type.actual() == INT)
				return new ExpTy(translate.createOpExp(e.oper, left.exp,
						right.exp), INT);
			Tiger.Overall.myerror.error(e.pos, "type not matched in LEexp!");
			return new ExpTy(null, INT);

		case Absyn.OpExp.LT:
			if (left.type.actual() == STRING && right.type.actual() == STRING)
				return new ExpTy(translate.createOpStringExp(e.oper, left.exp,
						right.exp), INT);
			if (left.type.actual() == INT && right.type.actual() == INT)
				return new ExpTy(translate.createOpExp(e.oper, left.exp,
						right.exp), INT);
			Tiger.Overall.myerror.error(e.pos, "type not matched in LTexp!");
			return new ExpTy(null, INT);

		case Absyn.OpExp.NE:
			if (right.type.actual().coerceTo(left.type.actual())) {
				if (left.type.actual() == STRING
						|| right.type.actual() == STRING)
					return new ExpTy(translate.createOpStringExp(e.oper,
							left.exp, right.exp), INT);
				if (left.type.actual() == INT || right.type.actual() == INT)
					return new ExpTy(translate.createOpExp(e.oper, left.exp,
							right.exp), INT);
				if (left.type.actual() instanceof Types.RECORD
						|| right.type.actual() instanceof Types.RECORD)
					return new ExpTy(translate.createOpExp(e.oper, left.exp,
							right.exp), INT);
				if (left.type.actual() instanceof Types.ARRAY
						|| right.type.actual() instanceof Types.ARRAY)
					return new ExpTy(translate.createOpExp(e.oper, left.exp,
							right.exp), INT);
				if (right.type.actual().coerceTo(NIL)) {
					if (left.type.actual().coerceTo(NIL))
						Tiger.Overall.myerror.error(e.pos,
								"transOpExp happen to be NIL OP NIL!");
					return new ExpTy(null, INT);
				}
			}

			Tiger.Overall.myerror.error(e.pos, "type not matched in NEexp!");
			return new ExpTy(null, INT);

		case Absyn.OpExp.EQ:
			if (right.type.actual().coerceTo(left.type.actual())) {
				if (left.type.actual() == STRING
						|| right.type.actual() == STRING)
					return new ExpTy(translate.createOpStringExp(e.oper,
							left.exp, right.exp), INT);
				if (left.type.actual() == INT && right.type.actual() == INT)
					return new ExpTy(translate.createOpExp(e.oper, left.exp,
							right.exp), INT);
				if (left.type.actual() instanceof Types.RECORD
						|| right.type.actual() instanceof Types.RECORD)
					return new ExpTy(translate.createOpExp(e.oper, left.exp,
							right.exp), INT);
				if (left.type.actual() instanceof Types.ARRAY
						&& right.type.actual() instanceof Types.ARRAY)
					return new ExpTy(translate.createOpExp(e.oper, left.exp,
							right.exp), INT);
				if (right.type.actual().coerceTo(NIL)) {
					if (left.type.actual().coerceTo(NIL))
						Tiger.Overall.myerror.error(e.pos,
								"transOpExp happen to be NIL OP NIL!");
					return new ExpTy(null, INT);
				}
			}

		default:
			Tiger.Overall.myerror.error(e.pos, "transOpExp failed!");
			return new ExpTy(null, INT);
		}
	}

	ExpTy transExp(Absyn.AssignExp e) {
		Tiger.Overall.beginassign();
		ExpTy var = transVar(e.var);
		Tiger.Overall.endassign();
		ExpTy exp = transExp(e.exp);
		if (e.var instanceof Absyn.SimpleVar) {
			VarEntry v = (VarEntry) (env.venv
					.get(((Absyn.SimpleVar) (e.var)).name));
			if (v.iscyclevar()) {
				Tiger.Overall.myerror.error(e.pos, "cann't assign cyclevar");
				return new ExpTy(translate.noOp, VOID);
			}
		}
		if (isSameType(var, exp)) {
			return new ExpTy(translate.createAssignExp(var.exp, exp.exp), VOID);
		}
		Tiger.Overall.myerror.error(e.pos,
				"left and right not same type in AssignExp!");
		return new ExpTy(null, VOID);
	}

	ExpTy transExp(Absyn.CallExp e) {
		FunEntry funentry = (FunEntry) env.venv.get(e.func);
		if (funentry != null) {
			Types.RECORD record = funentry.formals;
			Absyn.ExpList explist = e.args;
			Tree.ExpList formals = null;
			while (e.args != null && record != null) {
				ExpTy expty = transExp(explist.head);
				if (expty.type.actual() != record.fieldType.actual()) {
					Tiger.Overall.myerror.error(e.pos, "parameter unmatched!");
					return new ExpTy(null, VOID);
				}
				formals = new Tree.ExpList(expty.exp.unEx(), formals);
				record = record.tail;
				explist = explist.tail;
			}
			if (formals != null)
				formals = formals.reverse();
			if (explist != null || record != null) {
				Tiger.Overall.myerror.error(e.pos, "parameter unmatched!");
				return new ExpTy(null, VOID);
			}
			if (funentry.isStandard)
				return new ExpTy(new Translate.Ex(MipsFrame.instance
						.externalCall(e.func.toString(), formals)),
						funentry.result);
			return new ExpTy(translate.createCallExp(new Temp.Label(e.func
					.toString()), formals, currentLevel, funentry.level),
					funentry.result);
		}
		Tiger.Overall.myerror.error(e.pos, "function not found!");
		return new ExpTy(null, VOID);
	}

	ExpTy transExp(Absyn.RecordExp e) {
		int recordlength = 0;
		Tree.ExpList recordList = null;

		if ((Types.Type) env.tenv.get(e.typ) == null) {
			Tiger.Overall.myerror.error(e.pos, "this type couldn't be found");
			return new ExpTy(null, VOID);
		}

		Types.Type type = ((Types.Type) env.tenv.get(e.typ)).actual();
		Absyn.FieldExpList fieldexplistpointer = e.fields; // pointer to explist
		Types.RECORD recordpointer = (Types.RECORD) type;// pointer to record
		while (fieldexplistpointer != null && recordpointer != null) {
			if (fieldexplistpointer.name != recordpointer.fieldName) {
				Tiger.Overall.myerror.error(e.pos,
						"symbol couldn't match in the field");
				return new ExpTy(null, VOID);
			}

			ExpTy initexp = transExp(fieldexplistpointer.init);
			Types.Type exptype = initexp.type.actual();
			Types.Type recordtype = recordpointer.fieldType.actual();
			if (!exptype.coerceTo(recordtype)) {
				Tiger.Overall.myerror.error(e.pos, "field type cann't match");
				return new ExpTy(null, VOID);
			}
			recordList = new Tree.ExpList(initexp.exp.unEx(), recordList);
			++recordlength;
			fieldexplistpointer = fieldexplistpointer.tail;
			recordpointer = recordpointer.tail;
		}
		if (fieldexplistpointer != null || recordpointer != null) {
			Tiger.Overall.myerror.error(e.pos, "field type cann't match");
			return new ExpTy(null, VOID);
		}
		recordList = recordList.reverse();
		return new ExpTy(translate.createRecordExp(recordList, recordlength),
				type);
	}

	ExpTy transExp(Absyn.SeqExp e) {
		boolean isvoid = false;
		Absyn.ExpList pointer = e.list;
		Translate.ExpList seqList = null;
		if (pointer == null)
			return new ExpTy(translate.noOp, VOID);
		Types.Type record = null;
		while (pointer != null) {
			ExpTy currentExp = transExp(pointer.head);
			record = currentExp.type;
			seqList = new Translate.ExpList(currentExp.exp, seqList);
			pointer = pointer.tail;
		}
		if (record.coerceTo(VOID))
			isvoid = true;
		return new ExpTy(translate.createSeqExp(seqList, isvoid), record);
	}

	ExpTy transExp(Absyn.ArrayExp e) {
		ExpTy initSize = transExp(e.size);
		checkInt(initSize, e.pos);
		Types.Type arraytype = ((Types.Type) env.tenv.get(e.typ)).actual();
		ExpTy initExp = transExp(e.init);
		if (arraytype != null) {
			Types.Type inittype = initExp.type.actual();
			if (isSameType(((Types.ARRAY) arraytype).element.actual(), inittype))
				return new ExpTy(translate.createArrayExp(currentLevel,
						initSize.exp, initExp.exp), arraytype);
			Tiger.Overall.myerror.error(e.pos,
					"the ARRAY type doesn't get right element!");
			return new ExpTy(translate.noOp, VOID);
		}
		Tiger.Overall.myerror.error(e.pos, "cann't find the ARRAY type!");
		return new ExpTy(translate.noOp, VOID);
	}

	ExpTy transExp(Absyn.IfExp e) {
		boolean isvoid = true;
		ExpTy test = transExp(e.test);
		checkInt(test, e.pos);
		ExpTy thenclause = transExp(e.thenclause);
		ExpTy elseclause;
		if (e.elseclause == null) {
			checkVoid(thenclause, e.thenclause.pos);
			return new ExpTy(translate.createIfExp(test.exp, thenclause.exp,
					null, isvoid), VOID);
		} else {
			elseclause = transExp(e.elseclause);
			if (isSameType(thenclause, elseclause)) {
				if (thenclause.type != VOID)
					isvoid = false;
				return new ExpTy(translate.createIfExp(test.exp,
						thenclause.exp, elseclause.exp, isvoid),
						thenclause.type);
			}
			Tiger.Overall.myerror.error(e.pos,
					"thenclause and elseclause should have the same type!");
			return new ExpTy(null, VOID);
		}
	}

	ExpTy transExp(Absyn.WhileExp e) {
		++forWhileCounter;
		Temp.Label exit = new Temp.Label();
		translate.exitsRecord.add(exit);
		ExpTy testExp = transExp(e.test);
		ExpTy bodyExp = transExp(e.body);
		checkInt(testExp, e.test.pos);
		checkVoid(bodyExp, e.body.pos);
		--forWhileCounter;
		translate.exitsRecord.pop();
		return new ExpTy(translate.createWhileExp(testExp.exp, bodyExp.exp,
				exit), VOID);
	}

	ExpTy transExp(Absyn.ForExp e) {
		++forWhileCounter;
		Temp.Label exit = new Temp.Label();
		translate.exitsRecord.add(exit);
		ExpTy begin = transExp(e.var.init);
		ExpTy end = transExp(e.hi);
		if (begin.type.actual() == INT && end.type.actual() == INT) {
			env.venv.beginScope();
			Translate.Access var = currentLevel.allocLocal(e.var.escape);
			VarEntry cyclevar = new VarEntry(begin.type, var);
			cyclevar.setcycle();
			env.venv.put(e.var.name, cyclevar);
			Translate.Access limit = currentLevel.allocLocal(false);
			VarEntry limitvar = new VarEntry(end.type, limit);
			Symbol.Symbol limitsymbol = Symbol.Symbol.symbol("limit"
					+ Symbol.Symbol.counter);
			env.venv.put(limitsymbol, limitvar);
			ExpTy body = transExp(e.body);
			Translate.Exp forexp = translate.createForExp(var, limit,
					begin.exp, end.exp, body.exp, currentLevel, exit);
			env.venv.endScope();
			translate.exitsRecord.pop();
			return new ExpTy(forexp, VOID);
		}
		Tiger.Overall.myerror.error(e.pos,
				"beginnum or endnum in forexp not integer!");
		--forWhileCounter;
		return new ExpTy(null, VOID);
	}

	ExpTy transExp(Absyn.BreakExp e) {
		if (forWhileCounter == 0)
			Tiger.Overall.myerror.error(e.pos, "break cann't be used here!");
		return new ExpTy(translate.createBreakExp(), VOID);
	}

	ExpTy transExp(Absyn.LetExp e) {
		env.venv.beginScope();
		env.tenv.beginScope();
		Translate.Exp currentVardec;
		Translate.ExpList decSeq = null;
		for (Absyn.DecList p = e.decs; p != null; p = p.tail) {
			Absyn.Dec begin = p.head;
			currentVardec = transDec(begin);
			decSeq = new Translate.ExpList(currentVardec, decSeq);
		}

		ExpTy et = transExp(e.body);
		env.venv.endScope();
		env.tenv.endScope();
		return new ExpTy(translate.createLetExp(decSeq, et.exp), et.type);
	}

	ExpTy transVar(Absyn.SimpleVar v) {
		VarEntry x = (VarEntry) env.venv.get(v.name);
		if (x instanceof VarEntry) {
			VarEntry ent = (VarEntry) x;
			return new ExpTy(translate.createSimpleVar(x.access, currentLevel),
					ent.ty);
		}
		Tiger.Overall.myerror.error(v.pos, "undefined variable");
		return new ExpTy(null, INT);
	}

	ExpTy transVar(Absyn.FieldVar v) {
		ExpTy leftvar = transVar(v.var);
		int index = 0;
		if (leftvar.type.actual() instanceof Types.RECORD) {
			Types.RECORD search = (Types.RECORD) leftvar.type.actual();
			while (search != null) {
				if (v.field == search.fieldName)
					return new ExpTy(translate.createFieldVar(leftvar.exp,
							index), search.fieldType);
				++index;
				search = search.tail;
			}

			Tiger.Overall.myerror.error(v.pos,
					"Couldn't find the symbol in the field!");
			return new ExpTy(null, INT);
		}

		Tiger.Overall.myerror.error(v.pos, "transVar(FieldVar v) failed");
		return new ExpTy(null, INT);
	}

	ExpTy transVar(Absyn.SubscriptVar v) {
		ExpTy indexExp = transExp(v.index);
		checkInt(indexExp, v.pos);
		ExpTy leftvar = transVar(v.var);
		Types.Type type = leftvar.type.actual();
		if (type instanceof Types.ARRAY)
			return new ExpTy(translate.createSubscripVar(leftvar.exp,
					indexExp.exp),
					((Types.ARRAY) leftvar.type.actual()).element);

		Tiger.Overall.myerror.error(v.pos, "transVar(SubscriptVar v) failed!");
		return new ExpTy(null, INT);
	}

	Translate.Exp transDec(Absyn.FunctionDec d) {
		Absyn.FunctionDec functionpointer = d;
		Hashtable blockrecordfunction = new Hashtable();

		while (functionpointer != null) {
			Temp.Label functionname = new Temp.Label(functionpointer.name
					.toString());
			Translate.Level funLevel = new Translate.Level(currentLevel,
					functionname, null);
			if (env.venv.get(functionpointer.name) instanceof VarEntry) {
				Tiger.Overall.myerror.error(d.pos,
						"this name has been used as a var!");
				return translate.noOp;
			}
			if (blockrecordfunction.get(functionpointer.name) != null) {
				Tiger.Overall.myerror.error(functionpointer.pos,
						"This function has been defined in a block! ");
				return translate.noOp;
			}
			blockrecordfunction.put(functionpointer.name, mark);
			if (functionpointer.result == null) {
				if (functionpointer.params == null) {
					env.venv.put(functionpointer.name, new FunEntry(null, VOID,
							funLevel, functionname));
				} else {
					env.venv.put(functionpointer.name, new FunEntry(
							transTypefields(functionpointer.params), VOID,
							funLevel, functionname));
				}
			} else {
				Types.Type type = transTy(functionpointer.result);
				if (functionpointer.params == null) {
					env.venv.put(functionpointer.name, new FunEntry(null, type,
							funLevel, functionname));
				} else {
					env.venv.put(functionpointer.name, new FunEntry(
							transTypefields(functionpointer.params), type,
							funLevel, functionname));
				}
			}
			functionpointer = functionpointer.next;
		}

		functionpointer = d;

		while (functionpointer != null) {
			env.venv.beginScope();
			Translate.Level recordLevel = currentLevel;
			Util.BoolList formalbools = new Util.BoolList(true, null);
			FunEntry currentEntry = ((FunEntry) env.venv
					.get(functionpointer.name));

			for (Absyn.FieldList p = functionpointer.params; p != null; p = p.tail) {
				formalbools = new Util.BoolList(p.escape, formalbools);
			}

			currentLevel = currentEntry.level;
			currentLevel.allocformal(formalbools);
			Translate.AccessList formals = currentLevel.getformals();
			Translate.AccessList pointer = formals.tail;
			for (Absyn.FieldList p = functionpointer.params; p != null; p = p.tail) {
				Translate.Access access = pointer.head;
				env.venv.put(p.name, new VarEntry((Types.Type) env.tenv
						.get(p.typ), access));
				pointer = pointer.tail;
			}

			ExpTy bodyExp = transExp(functionpointer.body);
			Types.Type exptype = (bodyExp).type;
			translate.procEntryExit(currentLevel, bodyExp.exp);
			currentLevel = recordLevel;
			env.venv.endScope();

			FunEntry funentry = (FunEntry) env.venv.get(functionpointer.name);
			if (!(funentry.result.coerceTo(exptype)))
				Tiger.Overall.myerror.error(functionpointer.pos,
						"This function has been defined or param unmatched! ");
			functionpointer = functionpointer.next;
		}

		return translate.noOp;
	}

	Translate.Exp transDec(Absyn.VarDec d) {
		ExpTy initExp = transExp(d.init);
		if (env.venv.get(d.name) instanceof FunEntry) {
			Tiger.Overall.myerror.error(d.pos,
					"this name has been used as a function!");
			return translate.noOp;
		}

		Translate.Access access = currentLevel.allocLocal(d.escape);

		if (d.typ != null) {
			Types.Type type = transTy(d.typ).actual();
			if (initExp.type.coerceTo(type)) {
				VarEntry varentry = new VarEntry(type, access);
				env.venv.put(d.name, varentry);
				return translate.createVarDec(access, initExp.exp);
			}

			Tiger.Overall.myerror.error(d.pos,
					"the expression and the typeid is not the same type");
			return translate.noOp;
		} else {
			if (initExp.type.coerceTo(NIL))
				Tiger.Overall.myerror.error(d.pos,
						"assign NIL to an undefined RECORD!");

			VarEntry varentry = new VarEntry(initExp.type, access);
			env.venv.put(d.name, varentry);
			return translate.createVarDec(access, initExp.exp);
		}

	}

	Translate.Exp transDec(Absyn.TypeDec d) {
		Absyn.TypeDec typedecpointer = d;
		Hashtable blockrecordtype = new Hashtable();
		while (typedecpointer != null) {
			if (blockrecordtype.get(typedecpointer.name) != null) {
				Tiger.Overall.myerror.error(typedecpointer.pos,
						"This type has been defined  in a block! ");
				return translate.noOp;
			}
			blockrecordtype.put(typedecpointer.name, mark);
			env.tenv.put(typedecpointer.name, new Types.NAME(
					typedecpointer.name));
			typedecpointer = typedecpointer.next;
		}

		typedecpointer = d;
		while (typedecpointer != null) {
			sameFieldNameDetected = new Hashtable();
			Types.Type type = transTy(typedecpointer.ty);
			Types.NAME name = (Types.NAME) env.tenv.get(typedecpointer.name);
			name.bind(type);
			typedecpointer = typedecpointer.next;
		}

		typedecpointer = d;
		while (typedecpointer != null) {
			Types.NAME name = (Types.NAME) env.tenv.get(typedecpointer.name);
			if (name.isLoop() == true) {
				Tiger.Overall.myerror.error(typedecpointer.pos,
						"cycling definiton in type! ");
				return translate.noOp;
			}
			typedecpointer = typedecpointer.next;
		}
		return translate.noOp;
	}

	Types.Type transTy(Absyn.NameTy e) {
		Types.Type type = (Types.Type) env.tenv.get(e.name);
		if (type != null)
			return type;
		env.errorMsg.error(e.pos, "this type hasn't been defined!");
		return VOID;
	}

	Types.Type transTy(Absyn.RecordTy e) {
		if (e.fields == null)
			return VOID;
		return transTypefields(e.fields);
	}

	Types.Type transTy(Absyn.ArrayTy e) {
		Types.Type type = (Types.Type) env.tenv.get(e.typ);
		if (type != null)
			return new Types.ARRAY(type);
		env.errorMsg.error(e.pos, "element type hasn't been defined!");
		return VOID;
	}

	Types.RECORD transTypefields(Absyn.FieldList f) {
		Types.Type type = (Types.Type) env.tenv.get(f.typ);
		if (type == null) {
			env.errorMsg.error(f.pos, "field type hasn't been defined!");
			return null;
		}
		if (f.tail == null)
			return new Types.RECORD(f.name, type, null);
		return new Types.RECORD(f.name, type, transTypefields(f.tail));
	}

}
