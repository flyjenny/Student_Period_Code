package Translate;


public class RecordExp extends Exp{

    Tree.ExpList initList;
    int length;
    
    public RecordExp(Tree.ExpList i,int l){
    	initList = i;
    	length = l;
    }
    
	public Tree.Stm unCx(Temp.Label t, Temp.Label f) {
		return null;
	}

	public Tree.Expv unEx() {
		Temp.Temp recordAddr = new Temp.Temp();
		Tree.ExpList formals = new Tree.ExpList(new Tree.CONST(length),null);
		Tree.Expv callAlloc = Level.instance.externalCall("initArray", formals); 
		Tree.Stm allocRecord = new Tree.MOVE(new Tree.TEMP(recordAddr),callAlloc);
		int index = 0;
		Tree.ExpList pointer = initList;
		Tree.Stm moveList = Translate.noOp.unNx();
		while(pointer != null){
			Tree.Expv addr = new Tree.BINOP(Tree.BINOP.PLUS,new Tree.TEMP(recordAddr),new Tree.CONST(index*4));
			Tree.MOVE newMove = new Tree.MOVE(new Tree.MEM(addr),pointer.head);
			moveList = new Tree.SEQ(newMove,moveList);
			++index;
			pointer = pointer.tail;
		}
		
		return new Tree.ESEQ(new Tree.SEQ(allocRecord,moveList),new Tree.TEMP(recordAddr));
	}

	@Override
	public Tree.Stm unNx() {
		return new Tree.EXP(unEx());
	}

}
