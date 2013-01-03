package Translate;


public class IfExp extends Exp {
	Exp cond, texp, fexp;

	Temp.Label t = new Temp.Label();
	Temp.Label f = new Temp.Label();
	Temp.Label join = new Temp.Label();
    boolean isvoid = true;
	
	public IfExp(Exp cc, Exp aa, Exp bb, boolean i) {
		cond = cc;
		texp = aa;
		fexp = bb;
		isvoid  = i;
	}

	public Tree.Expv unEx(){
		Temp.Temp value = new Temp.Temp();
		Temp.Label t = new Temp.Label();
		Temp.Label f = new Temp.Label();
		Temp.Label exit = new Temp.Label();
		if(fexp == null){
			return null;
		}else{
			Tree.Stm condition = cond.unCx(t, f);
			Tree.Stm trueLabel = new Tree.LABEL(t);
			Tree.Stm trueStatement = new Tree.MOVE(new Tree.TEMP(value),texp.unEx());
			Tree.Stm trueJump = new Tree.JUMP(exit);
			Tree.Stm falseLabel = new Tree.LABEL(f);
		    Tree.Stm falseStatement = new Tree.MOVE(new Tree.TEMP(value),fexp.unEx());
		    Tree.Stm exitLabel = new Tree.LABEL(exit);
		    Tree.Expv valueTemp = new Tree.TEMP(value);
			return new Tree.ESEQ(new Tree.SEQ(condition,new Tree.SEQ(trueLabel,new Tree.SEQ(trueStatement,new Tree.SEQ(trueJump,new Tree.SEQ(falseLabel,new Tree.SEQ(falseStatement, exitLabel )))))),valueTemp);
		}
	}

	public Tree.Stm unNx() {
		Temp.Label t = new Temp.Label();
		Temp.Label f = new Temp.Label();
		Temp.Label exit = new Temp.Label();
		if(fexp == null){ 
			Tree.Stm condition = cond.unCx(t, exit);
			Tree.Stm trueLabel = new Tree.LABEL(t);
			Tree.Stm trueStm = texp.unNx();
			Tree.LABEL exitLabel = new Tree.LABEL(exit);
			return 
			new Tree.SEQ(condition,new Tree.SEQ(trueLabel,new Tree.SEQ(trueStm,exitLabel)));
		}else{
			Tree.Stm condition = cond.unCx(t, f);
			Tree.Stm trueLabel = new Tree.LABEL(t);
			Tree.Stm trueStatement = texp.unNx();
			Tree.Stm trueJump = new Tree.JUMP(exit);
			Tree.Stm falseLabel = new Tree.LABEL(f);
		    Tree.Stm falseStatement = fexp.unNx();
		    Tree.Stm exitLabel = new Tree.LABEL(exit);
			return 
			new 	Tree.SEQ(condition,new Tree.SEQ(trueLabel,new Tree.SEQ(trueStatement,new Tree.SEQ(trueJump,new Tree.SEQ(falseLabel,new Tree.SEQ(falseStatement,exitLabel))))));
		}
	}

	public Tree.Stm unCx(Temp.Label t, Temp.Label f) {
		return new Tree.CJUMP(Tree.CJUMP.NE,unEx(),Tree.CONST.ZERO,t,f);
	}
}
