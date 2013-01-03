package Translate;


public class ArrayExp extends Exp{
	Exp size;
	Exp init;
    Level level;
	public ArrayExp(Exp s,Exp i, Level currentLevel){
		size = s;
		init = i;
		level = currentLevel;
	}
	
	public Tree.Stm unCx(Temp.Label t, Temp.Label f) {
		return new Tree.CJUMP(Tree.CJUMP.NE,unEx(),Tree.CONST.ZERO,t,f);
	}

	public Tree.Expv unEx() {

		if(init instanceof SeqExp)
			init = ((SeqExp)init).expList.get(0);
		if(init instanceof ArrayExp){
			 ArrayExp arrayInit = (ArrayExp) init;
			 Temp.Temp addr = new Temp.Temp();
			 Temp.Temp addrMirror = new Temp.Temp();
             Tree.ExpList formalOne = new Tree.ExpList(size.unEx(),null); 		
			 Tree.Expv mallocOne = level.externalCall("initArray",formalOne);
			 Tree.ExpList formalTwo = new Tree.ExpList(arrayInit.size.unEx(),new Tree.ExpList(arrayInit.init.unEx(),null));
			 Tree.Expv mallocTwo = level.externalCall("initArray",formalTwo);
			 Access var = level.allocLocal(true);
			 Exp begin = new Ex(Tree.CONST.ZERO);
			 Access limit = level.allocLocal(true);
			 Exp end = size;
			 Temp.Label exit = new Temp.Label();
			 Tree.SEQ mallocCircle = new Tree.SEQ(new Tree.MOVE(new Tree.MEM(new Tree.TEMP(addrMirror)),mallocTwo),
					 new Tree.MOVE(new Tree.TEMP(addrMirror),new Tree.BINOP(Tree.BINOP.PLUS,new Tree.TEMP(addrMirror),Tree.CONST.WORDSIZE)));
			
			 Tree.Stm mallocLittleArray = (Semant.Semant.translate.createForExp(var,limit,begin,end,new Nx(mallocCircle),Semant.Semant.currentLevel,exit)).unNx();
			 return new Tree.ESEQ(new Tree.SEQ(new Tree.MOVE(new Tree.TEMP(addr),mallocOne),
					 new Tree.SEQ(new Tree.MOVE(new Tree.TEMP(addrMirror),new Tree.TEMP(addr)),mallocLittleArray)),
					 new Tree.TEMP(addr));
		}else if(init instanceof RecordExp){
			 RecordExp recordInit = (RecordExp) init;
			 Temp.Temp addr = new Temp.Temp();
			 Temp.Temp addrMirror = new Temp.Temp();
             Tree.ExpList formalOne = new Tree.ExpList(size.unEx(),null); 		
			 Tree.Expv mallocArray = level.externalCall("initArray",formalOne);
			 Tree.Expv mallocRecord = recordInit.unEx();
			 Access var = level.allocLocal(false);
			 Exp begin = new Ex(Tree.CONST.ZERO);
			 Access limit = level.allocLocal(false);
			 Exp end = size;
			 Temp.Label exit = new Temp.Label();
			 Tree.SEQ mallocCircle = new Tree.SEQ(new Tree.MOVE(new Tree.MEM(new Tree.TEMP(addrMirror)),mallocRecord),
					 new Tree.MOVE(new Tree.TEMP(addrMirror),new Tree.BINOP(Tree.BINOP.PLUS,new Tree.TEMP(addrMirror),Tree.CONST.WORDSIZE)));
			
			 Tree.Stm mallocLittleRecord = (Semant.Semant.translate.createForExp(var,limit,begin,end,new Nx(mallocCircle),Semant.Semant.currentLevel,exit)).unNx();
			 return new Tree.ESEQ(new Tree.SEQ(new Tree.MOVE(new Tree.TEMP(addr),mallocArray),
					 new Tree.SEQ(new Tree.MOVE(new Tree.TEMP(addrMirror),new Tree.TEMP(addr)),mallocLittleRecord)),
					 new Tree.TEMP(addr));
			
		}else{
	         Tree.ExpList formals = new Tree.ExpList(size.unEx(),new Tree.ExpList(init.unEx(),null));
	         Tree.Expv mallocArray = level.externalCall("initArray", formals);
	         return mallocArray;
		}
	}
	
	public Tree.Stm unNx() {
		return new Tree.EXP(unEx());
	}
	
	
}
