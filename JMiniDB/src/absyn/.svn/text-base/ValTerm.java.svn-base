package absyn;

import query.predicate.*;

public class ValTerm extends BoolExp {
	public static int EQ = 1, NEQ = 2, GT = 3, LT = 4, GE = 5, LE = 6;
	public static int NONE = 0, EXISTS = 1, IN = 2, ANY = 3, ALL = 4;

	Value lvalue;
	Value rvalue;
	int op;
	int cond;
	Query subquery;
	Term t;

	public ValTerm(int p, Value lv, int cop, Value rv) {
		pos = p;
		lvalue = lv;
		op = cop;
		cond = NONE;
		rvalue = rv;
		subquery = null;
		t = new COpTerm(lv, cop, rv);
	}

	public ValTerm(int p, int c, Query q) {
		pos = p;
		lvalue = null;
		op = NONE;
		cond = c;
		rvalue = null;
		subquery = q;
		t = new ExistsTerm(q);
	}

	public ValTerm(int p, Value v, int c, Query q) {
		pos = p;
		lvalue = v;
		op = NONE;
		cond = c;
		rvalue = null;
		subquery = q;
		t = new InTerm(v, q);
	}

	public ValTerm(int p, Value v, int cop, int c, Query q) {
		pos = p;
		lvalue = v;
		op = cop;
		cond = c;
		rvalue = null;
		subquery = q;
		if (c == ANY) {
			t = new AnyTerm(v, cop, q);
		} else if (c == ALL) {
			t = new AllTerm(v, cop, q);
		}
	}

	public Term toTerm() {
		return t;
	}
}
