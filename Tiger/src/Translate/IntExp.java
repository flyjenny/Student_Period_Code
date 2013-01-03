package Translate;
/**
 * 	this is one implementation of translate.Exp
 * 	constant integer expression,
 * 	
 * 	this code is an example of how can we optimize the code
 * 		see unCx
 */
public class IntExp extends Exp {
	public int value;

	public IntExp(int value) {
		this.value = value;
	}

	public Tree.Expv unEx() {
		return new Tree.CONST(value);
	}

	public Tree.Stm unNx() {
		return new Tree.EXP(this.unEx());
	}

	public Tree.Stm unCx(Temp.Label t, Temp.Label f) {
    	if (value == 0)
			return new Tree.JUMP(f);
		else
			return new Tree.JUMP(t);
	}
}
