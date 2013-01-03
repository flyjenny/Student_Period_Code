package Translate;

public class WhileExp extends Exp{

	Exp test;
	Exp body;
	Temp.Label entry;
	Temp.Label exit;
	
	public WhileExp(Exp t,Exp b,Temp.Label e){
		test = t;
		body = b;
		entry = new Temp.Label();
		exit = e;
	}
	
	public Tree.Expv unEx(){
		return null;
	}
	
	public Tree.Stm unCx(Temp.Label t,Temp.Label f){
		return null;
	}
	
	public Tree.Stm unNx(){
		Temp.Label mid = new Temp.Label();
		return 
		new Tree.SEQ(new Tree.LABEL(entry),
		new Tree.SEQ(test.unCx(mid, exit),
		new Tree.SEQ(new Tree.LABEL(mid),
		new Tree.SEQ(body.unNx(),
		new Tree.SEQ(new Tree.JUMP(entry),
		new Tree.LABEL(exit))))));
	}

}
