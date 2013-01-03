package Mips;
/**
 *	access implementation which indicates that the
 *	variable stays in a temp.Temp 
 */
public class InReg implements Frame.Access{
	
	Temp.Temp temp;
	
	public InReg(Temp.Temp t){
		temp = t;
	}
	
	public Tree.Expv Fpexp( Tree.Expv faddr ) {
		return new Tree.TEMP(temp);
	}
	
}
