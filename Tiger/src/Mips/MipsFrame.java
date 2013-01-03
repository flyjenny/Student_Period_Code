package Mips;

import java.util.HashSet;
import java.util.Iterator;

import Tree.TEMP;

public class MipsFrame implements Frame.Frame{
	
	Frame.AccessList formals;
    public static HashSet<Temp.Label> labelRecord = new HashSet<Temp.Label>();
	Temp.Label frameName;
	Tree.StmList saveArgStms = null;
	public int argsize = 0;
	public static Temp.TempList returnSink;
	public static Temp.Temp zero;
	public static Temp.Temp at;
	public static Temp.Temp v0;
	public static Temp.Temp v1;
	public static Temp.Temp k0;
	public static Temp.Temp k1;
	public static Temp.Temp gp;
	public static Temp.Temp sp;
	public static Temp.Temp fp;
	public static Temp.Temp ra;   
	public int frameSize = 0;
	public static int argRegNum =  4;
	public static int callerSaveNum = 10;
	public static int calleeSaveNum = 8;
	public static Temp.TempList argRegs;
	public static Temp.TempList callerSaveRegs;
	public static Temp.TempList calleeSaveRegs;
	public static Temp.TempList callDefs;
	
	public static MipsFrame instance = new MipsFrame(); 

	public MipsFrame(){
		callDefs = null;
		argRegs = null;
		callerSaveRegs = null;
		calleeSaveRegs = null;
		returnSink = null;
		zero = new Temp.Temp();
		returnSink = new Temp.TempList(zero,returnSink);
		at = new Temp.Temp();
		v0 = new Temp.Temp();
        v1 = new Temp.Temp();
		for(int i = 0; i != argRegNum; ++i)
			argRegs = new Temp.TempList(new Temp.Temp(),argRegs); 
		for(int i = 0; i != callerSaveNum-2; ++i){
			Temp.Temp callerSaveReg = new Temp.Temp();
			callDefs = new Temp.TempList(callerSaveReg,callDefs);
			callerSaveRegs = new Temp.TempList(callerSaveReg,callerSaveRegs);
		}
		for(int i = 0; i != calleeSaveNum; ++i){
			Temp.Temp calleeSave = new Temp.Temp();
			returnSink = new Temp.TempList(calleeSave,returnSink);
			calleeSaveRegs = new Temp.TempList(calleeSave,calleeSaveRegs);
		}
	    for(int i = 0; i != 2; ++i){
			Temp.Temp callerSaveReg = new Temp.Temp();
			callDefs = new Temp.TempList(callerSaveReg,callDefs);
			callerSaveRegs = new Temp.TempList(callerSaveReg,callerSaveRegs);
	    }
		argRegs = argRegs.reverse();
        callerSaveRegs = callerSaveRegs.reverse();
        calleeSaveRegs = calleeSaveRegs.reverse();
		k0 = new Temp.Temp();
		k1 = new Temp.Temp();
		gp = new Temp.Temp();
        sp = new Temp.Temp();
		fp = new Temp.Temp();
		ra = new Temp.Temp();
		returnSink = new Temp.TempList(ra,returnSink);
		returnSink = new Temp.TempList(sp,returnSink);
	}
	
	public MipsFrame(Temp.Label n){
		frameName = n;
		formals = null;
	}
	
	public Frame.Frame newFrame(Temp.Label name,Util.BoolList formalbools){
		MipsFrame product = new MipsFrame(name);
		Util.BoolList pointer = formalbools;
		while(pointer != null){
			Frame.Access access =  product.allocLocal(pointer.head);//pointer.head
			product.formals = new Frame.AccessList(access,product.formals);
			pointer = pointer.tail;
		}
		formals = product.formals;
		return product;
	}
	
    public Frame.Access allocLocal( boolean escape ){
    	if(escape){
    		int temp = frameSize;
    		frameSize += wordsize();
    		return new InFrame(this,frameSize);
    	}
    	return new InReg(new Temp.Temp());
    }
    
    public Frame.Access staticLink(){
    	return formals.head;
    }
    
    public Temp.Temp FP(){
    	return fp;
    }
    
    public Temp.Temp RV(){
    	return v0;
    }
    
	public Frame.AccessList getFormals(){
    	return formals;
    }
	
	public Tree.Expv externalCall(String func,Tree.ExpList args){
    	return new Tree.CALL(new Tree.NAME(new Temp.Label("_"+func)),args);
    }
	
	public Tree.Stm procEntryExit1(Tree.Stm funcStm){

		int f = frameSize;
		//the temp to save the caller frame size
		Temp.Temp callerSize = new Temp.Temp();
		
		//save arguments;
        Temp.TempList argRegPointer = argRegs;
		Frame.AccessList formalPointer = formals;
		int argOffset = 0;
		while(formalPointer != null){
			Frame.Access argAccess = formalPointer.head;
			if(argRegPointer == null){				
				Tree.MOVE svFormal = new Tree.MOVE(argAccess.Fpexp(new Tree.TEMP(fp)),new Tree.MEM(new Tree.BINOP(Tree.BINOP.PLUS,new Tree.TEMP(fp),new Tree.CONST(argOffset))));
			    funcStm = new Tree.SEQ(svFormal,funcStm);
			    argOffset += 4;
			}else{
				Tree.MOVE svFormal = new Tree.MOVE(argAccess.Fpexp(new Tree.TEMP(fp)),new Tree.TEMP(argRegPointer.head));
			    funcStm = new Tree.SEQ(svFormal,funcStm);
				argRegPointer = argRegPointer.tail;
			}
			formalPointer = formalPointer.tail;
		}
        
    	//save calleeSaveRegs
        Temp.TempList calleeSaveRegsPointer = calleeSaveRegs;
        Frame.AccessList recordCalleeSaveAccess = null;
        while(calleeSaveRegsPointer != null){
        	Frame.Access calleeSaveAccess = allocLocal(true);
        	recordCalleeSaveAccess = new Frame.AccessList(calleeSaveAccess,recordCalleeSaveAccess);
        	funcStm = new Tree.SEQ(new Tree.MOVE(calleeSaveAccess.Fpexp(new Tree.TEMP(fp)),new Tree.TEMP(calleeSaveRegsPointer.head)),funcStm);
        	calleeSaveRegsPointer = calleeSaveRegsPointer.tail;
        }
  
		//save $ra
    	Frame.Access raAccess = allocLocal(true);
    	Tree.MOVE svra = new Tree.MOVE(raAccess.Fpexp(new Tree.TEMP(fp)),new Tree.TEMP(ra));
    	funcStm = new Tree.SEQ(svra,funcStm);
    	        		
    	//save the caller frame size;
    	Frame.Access callerSizeAccess = allocLocal(true);
    	Tree.MOVE svCallerSize = new Tree.MOVE(callerSizeAccess.Fpexp(new Tree.TEMP(fp)),new Tree.TEMP(callerSize));
    	funcStm = new Tree.SEQ(svCallerSize,funcStm);

    	//calculate new $sp
		Tree.MOVE calSP = new Tree.MOVE(new Tree.TEMP(sp),new Tree.BINOP(Tree.BINOP.MINUS,new Tree.TEMP(sp),new Tree.CONST(frameSize)));
		funcStm = new Tree.SEQ(calSP,funcStm);

		//calculate new $fp
        Tree.MOVE calFP = new Tree.MOVE(new Tree.TEMP(fp),new Tree.TEMP(sp));
       	funcStm = new Tree.SEQ(calFP,funcStm);       
            	
    	//calculate the caller frame size
    	Tree.MOVE calcCallerSize = new Tree.MOVE(new Tree.TEMP(callerSize),new Tree.BINOP(Tree.BINOP.MINUS,new Tree.TEMP(fp),new Tree.TEMP(sp)));
    	funcStm = new Tree.SEQ(calcCallerSize,funcStm);
    	
    	//attach to the tail
    	
    	
    	//move back calleeSaveRegs
    	recordCalleeSaveAccess = recordCalleeSaveAccess.reverse();
    	calleeSaveRegsPointer = calleeSaveRegs;
    	while(calleeSaveRegsPointer != null){
    		Tree.MOVE mbcalleeSave = new Tree.MOVE(new Tree.TEMP(calleeSaveRegsPointer.head),recordCalleeSaveAccess.head.Fpexp(new Tree.TEMP(fp)));
    		funcStm = new Tree.SEQ(funcStm,mbcalleeSave);
    		calleeSaveRegsPointer = calleeSaveRegsPointer.tail;
    		recordCalleeSaveAccess = recordCalleeSaveAccess.tail;
    	}
    	
    	//move back $ra
    	Tree.MOVE mbra = new Tree.MOVE(new Tree.TEMP(ra),raAccess.Fpexp(new Tree.TEMP(fp)));
    	funcStm = new Tree.SEQ(funcStm,mbra);

    	//calc $sp
    	Tree.MOVE mbsp = new Tree.MOVE(new Tree.TEMP(sp),new Tree.TEMP(fp));
    	funcStm = new Tree.SEQ(funcStm,mbsp);
    	    	
    	//load the caller size;
        Tree.MOVE lwCallerSize = new Tree.MOVE(new Tree.TEMP(callerSize),callerSizeAccess.Fpexp(new Tree.TEMP(fp)));
        funcStm = new Tree.SEQ(funcStm,lwCallerSize);
    	
        //calc $fp
        Tree.MOVE mvfp = new Tree.MOVE(new Tree.TEMP(fp),new Tree.BINOP(Tree.BINOP.PLUS,new Tree.TEMP(fp),new Tree.TEMP(callerSize)));
        funcStm = new Tree.SEQ(funcStm,mvfp);

        return funcStm;
	}

	public Assem.InstrList procEntryExit2(Assem.InstrList funcInstr){
		 funcInstr.attach(new Assem.OPER("",null,returnSink));
		 return funcInstr;
	}
	
	public Assem.InstrList procEntryExit3(Assem.InstrList funcInstr){

		Assem.LABEL funcName = new Assem.LABEL(frameName.toString()+":",frameName);
 		funcInstr = new Assem.InstrList(funcName,funcInstr);
		
		Assem.OPER jumpBack = new Assem.OPER("jr $ra",null,new Temp.TempList(ra,null));
		funcInstr.attach(jumpBack);

		Assem.OPER end = new Assem.OPER(".end "+frameName.toString(),null,null);
	    funcInstr.attach(end);
		return funcInstr;
	}
	
	public int wordsize() {
		return 4;
	}

	public Frame.Frame newFrame(Util.BoolList fmls) {
		return null;
	}
	
	public Temp.Label frameName(){
		return frameName;
	}

	
	public Assem.InstrList codegen(Tree.Stm stm) {
		Codegen codegen = new Codegen(this);
		Assem.InstrList instrs = (codegen).codegen(stm);
        return instrs;
	}

	@Override
	public String tempMap(Temp.Temp t) {
		return t.toString();
	}

	public int secondCommaIndex(String assem) {
		boolean findOne = false;
		for(int i = 0; i != assem.length(); ++i){
			if(assem.substring(i, i+1).equals(",")){
				if(findOne)
					return i;
				else
					findOne = true;
			}
		}
		return 0;
	}
	
	public int getNumFromASMDS(String assem){
		return Integer.parseInt(assem.substring(secondCommaIndex(assem)+1,assem.length()));
	}

	public boolean isRedeclared(){
		boolean redeclare = false;
		boolean isContained = false;
	    Iterator<Temp.Label> itr = labelRecord.iterator();
	    while(itr.hasNext()){
	    	Temp.Label temp = itr.next();
	    	if(frameName.toString().equals(temp.toString()))
	    		isContained = true;
	    }
	    
	    if(!isContained){	
	 	    labelRecord.add(frameName);	    	
	 	    redeclare = false;
	    }else{
	    	redeclare = true;
	    }
	    return redeclare;
	}
}
