package Translate;
import java.util.ArrayList;

public class SeqExp extends Exp{

	public ArrayList<Exp> expList = new ArrayList<Exp>();
	public boolean isvoid = false;
	
	public SeqExp(ExpList e, boolean i) {
		isvoid = i;
		ExpList pointer = e;
		while(pointer != null){
			expList.add(pointer.head);
			pointer = pointer.tail;
		}
	}

	@Override
	public Tree.Stm unCx(Temp.Label t, Temp.Label f) {
		return new Tree.CJUMP(Tree.CJUMP.NE,unEx(),Tree.CONST.ZERO,t,f);
	}

	@Override
	public Tree.Expv unEx() {
		    if(isvoid){
		    	return null;
		    }else{
			   if(expList.size() == 0)
				   return Translate.noOp.unEx();
			  Tree.Stm seqWithoutLast = Translate.noOp.unNx(); 
              for(int i = 1; i != expList.size(); ++i)
            	  seqWithoutLast = new Tree.SEQ(expList.get(i).unNx(),seqWithoutLast);
     
              return new Tree.ESEQ(seqWithoutLast,expList.get(0).unEx());			
		    }
   }

	@Override
	public Tree.Stm unNx() {
		if(expList.size() == 0)
			return Translate.noOp.unNx();
		Tree.Stm seqWithoutLast = Translate.noOp.unNx(); 
        for(int i = 0; i != expList.size(); ++i)
        	seqWithoutLast = new Tree.SEQ(expList.get(i).unNx(),seqWithoutLast);
        return seqWithoutLast;			
	}

}
