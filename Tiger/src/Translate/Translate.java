package Translate;

import java.util.Stack;
import java.util.ArrayList;
import Tree.BINOP;
import Tree.CONST;
import Tree.TEMP;

public class Translate {
	
	public Stack<Temp.Label> exitsRecord;
	public StringFrag stringFrags;
	public ProcFrag procFrags;
	public static Ex noOp = new Ex(Tree.CONST.ZERO);

	public Translate(){
		exitsRecord = new Stack<Temp.Label>();
	}
	
	public void stringDisplay(){
		StringFrag pointer = stringFrags;
		while(pointer != null){
			pointer.display();
			pointer = (StringFrag)pointer.tail;
		}
	}
	
	public void procDisplay(){
		ProcFrag pointer = procFrags;
		while(pointer != null){
			pointer.display();
			pointer = (ProcFrag)pointer.tail;
		}		
	}
	
	public void fragDisplay(){
		stringDisplay();
		procDisplay();
	}
	
	public Exp createIntExp(int value){
		return new IntExp(value);
	}
		
	public Exp createStringExp(String value){
		Temp.Label name = new Temp.Label();
		stringFrags = new StringFrag(name,value,stringFrags);
		return new Ex(new Tree.NAME(name));
	}
	
	public Exp createIfExp(Exp cond,Exp t,Exp f,boolean isvoid){
		return new IfExp(cond,t,f,isvoid);
	}
		
	public Exp createOpExp(int op,Exp left,Exp right){
		return new OpExp(op,left,right);
	}
	
	public Exp createVarDec(Access access,Exp init){
		Tree.Expv fp = new Tree.TEMP(Level.instance.frame.FP());
		Tree.Expv address = access.acc.Fpexp(fp);
		return new Nx(new Tree.MOVE(address,init.unEx()));
	}
	
	public Exp createAssignExp(Exp var,Exp exp){
		return new Nx(new Tree.MOVE(var.unEx(),exp.unEx()));
	}
	
	public Exp createWhileExp(Exp test,Exp body,Temp.Label exit){
		return new WhileExp(test,body,exit);
	}
	
	public Exp createOpStringExp(int op,Exp left,Exp right){
		Tree.ExpList formals = new Tree.ExpList(left.unEx(),new Tree.ExpList(right.unEx(),null));
		Tree.Expv compare = Level.instance.externalCall("strcmp",formals);
		return new OpExp(op,new Ex(compare),noOp);
	}
	
	public Exp createSimpleVar(Access var,Level currentLevel){
		Level recordcurrentLevel = currentLevel;
		Tree.Expv faddr = new Tree.TEMP(Level.instance.FP());
		while(currentLevel != var.home){
			faddr = currentLevel.staticLink().Fpexp(faddr);
			currentLevel = currentLevel.parent;			
		}
		currentLevel = recordcurrentLevel;
		return new Ex(var.acc.Fpexp(faddr));
	}

	/*
	 * the first formal of initArray is the size;
	 * the second formal of initArray is the initial value;
	 */
	public Exp createArrayExp(Level currentLevel,Exp size,Exp init){
		    return new ArrayExp(size,init,currentLevel);
	}
	
	public Exp createSubscripVar(Exp var,Exp index){
		Tree.Expv baseaddress = var.unEx();
		Tree.Expv distance = new BINOP(BINOP.MUL,index.unEx(),CONST.WORDSIZE);
        return new Ex(new Tree.MEM(new Tree.BINOP(BINOP.PLUS,baseaddress,distance)));
	}
	
	public Exp createCallExp(Temp.Label funcName,Tree.ExpList formals,Level currentLevel,Level funcDecLevel){
		Level recordcurrentLevel = currentLevel;
		Tree.Expv faddr = new Tree.TEMP(Level.instance.FP());
		while(currentLevel != funcDecLevel.parent){
			faddr = currentLevel.staticLink().Fpexp(faddr);
			currentLevel = currentLevel.parent;			
		}
		currentLevel = recordcurrentLevel;		
		Tree.NAME realfuncName = new Tree.NAME(funcName);
		Tree.ExpList addstaticLink = new Tree.ExpList(faddr,formals);
		return new Ex(new Tree.CALL(realfuncName,addstaticLink));
	}

	public Exp createRecordExp(Tree.ExpList initList,int length){
		return new RecordExp(initList,length);
	}
		
	public Exp createFieldVar(Exp var,int index){
		Tree.Expv baseaddress = var.unEx();
		int distance = index*Level.instance.wordsize();
		Tree.Expv distanceExp = new CONST(distance);
        return new Ex(new Tree.MEM(new Tree.BINOP(BINOP.PLUS,baseaddress,distanceExp)));		
	}
	
	
	public Exp createSeqExp(ExpList explist,boolean isvoid){
		return new SeqExp(explist,isvoid);
	}
	
	public Exp createDecSeqExp(ExpList explist){
		if(explist == null)
			return noOp;
		Tree.Stm seq = noOp.unNx(); 
		ExpList pointer = explist;
		while(pointer != null){
			Tree.Stm attachStm = pointer.head.unNx();
			if(pointer.head != noOp)
			seq = new Tree.SEQ(attachStm,seq);
            pointer = pointer.tail;			
		}
		return new Nx(seq);
	}
	
	public Exp createLetExp(ExpList decSeq,Exp body){
		if(decSeq == null && body == null)
			return noOp;
		if(body == null)
			return createSeqExp(decSeq,true);
		if(decSeq == null)
			return body;
		if(body.unEx() == null || (body instanceof IfExp && ((IfExp)body).isvoid)){
		    	return new Nx(new Tree.SEQ(createDecSeqExp(decSeq).unNx(),body.unNx()));
		}
		
		Tree.Stm decStm = createDecSeqExp(decSeq).unNx();
		Tree.Expv bodyExpv = body.unEx();
		return new Ex(new Tree.ESEQ(decStm,bodyExpv));
	} 
	
	public Exp createForExp(Access var,Access limit,Exp begin,Exp end,Exp body,Level currentLevel,Temp.Label exit){
		Exp varDec = createVarDec(var,begin);
		Exp limitDec = createVarDec(limit,end);
		ExpList expList = null;
		expList = new ExpList(limitDec,expList);
		expList = new ExpList(varDec,expList);
		Exp test = createOpExp(Absyn.OpExp.LE,createSimpleVar(var,currentLevel),createSimpleVar(limit,currentLevel));
        Tree.MOVE increment = new Tree.MOVE(var.acc.Fpexp(new Tree.TEMP(var.home.frame.FP())),new BINOP(BINOP.PLUS,var.acc.Fpexp(new Tree.TEMP(var.home.frame.FP())),CONST.ONE));
        body = new Nx(new Tree.SEQ(body.unNx(),increment));
        return createLetExp(expList,createWhileExp(test,body,exit));
	}
	
	public Exp createBreakExp(){
		return new Nx(new Tree.JUMP(exitsRecord.peek()));
	}
	
	public String frags(){
		StringBuffer s = new StringBuffer();
		for(StringFrag pointer = stringFrags;pointer != null; pointer =  (StringFrag)pointer.tail)
			s.append(".data\n"+pointer.label.toString()+":"/*+".word    "+pointer.value.length()*/+"\n    .asciiz \""+pointer.value+"\"\n");
		return s.toString();
	}

	public void procEntryExit(Level level,Exp funcBody){
		Tree.Stm funcStm;
		if(funcBody.unEx() == null){
			funcStm = funcBody.unNx(); 
		}else{
			if(funcBody instanceof IfExp && ((IfExp)funcBody).isvoid)
				funcStm = funcBody.unNx();
			else
			    funcStm = new Tree.MOVE(new Tree.TEMP(Level.instance.frame.RV()),funcBody.unEx());
		}
		
		funcStm = level.frame.procEntryExit1(funcStm);
		procFrags = new ProcFrag(level.frame,funcStm,procFrags);
	}
	

}
