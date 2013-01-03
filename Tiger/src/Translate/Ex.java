package Translate;

import Tree.ESEQ;


public class Ex extends Exp{
	Tree.Expv exp;
	boolean isSeq;
	
	public Ex(Tree.Expv e){
		exp = e;
		isSeq = false;
	}
	

	public Ex(Tree.Expv e, boolean i) {
        exp = e;		
        isSeq = i;
	}


	public Tree.Expv unEx(){
		return exp;
	}
	
	public Tree.Stm unNx(){
		return new Tree.EXP(exp);
	}
	
	public Tree.Stm unCx(Temp.Label t,Temp.Label f){
		return new Tree.CJUMP(Tree.CJUMP.NE,unEx(),Tree.CONST.ZERO,t,f);
	}
	
	boolean isSeq(){
		return isSeq;
	}
}
