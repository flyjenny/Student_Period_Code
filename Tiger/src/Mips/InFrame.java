package Mips;

import Tree.Expv;


/**
 *	access implementation which indicates that the
 *	variable stays in memory
 */
public class InFrame implements Frame.Access{
    
	MipsFrame frame;
	int offset;

	public InFrame(MipsFrame f,int o){
		frame = f;
		offset = o;
	}
	
	public Tree.Expv Fpexp( Tree.Expv faddr ) {
		return new Tree.MEM(new Tree.BINOP(Tree.BINOP.PLUS,faddr,new Tree.CONST(-offset)));
	}
	
}
