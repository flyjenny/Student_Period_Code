package absyn;

public class BoolTerm extends BoolExp {
	public static final int NOT = 1, AND = 2, OR = 3;

	BoolExp lbexp;
	BoolExp rbexp;
	int op;

	public BoolTerm(int bop, BoolExp be) {
		lbexp = be;
		op = bop;
		rbexp = null;
	}

	public BoolTerm(BoolExp l, int bop, BoolExp r) {
		lbexp = l;
		op = bop;
		rbexp = r;
	}

	public BoolExp getLeftExp() {
		return lbexp;
	}

	public BoolExp getRightExp() {
		return rbexp;
	}

	public int getOP() {
		return op;
	}
}
