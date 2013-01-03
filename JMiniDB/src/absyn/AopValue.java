package absyn;

import query.predicate.*;
import query.scan.Scan;

public class AopValue extends Value {

	public static final int PLUS = 0, MINUS = 1, TIMES = 2, DIVIDE = 3;

	Value lvalue;
	Value rvalue;
	int op;

	public AopValue(int p, Value lv, int aop, Value rv) {
		pos = p;
		lvalue = lv;
		op = aop;
		rvalue = rv;
	}

	@Override
	public Expression toExpression(Scan s) {
		return new AOpExpr(lvalue.toExpression(s), op, rvalue.toExpression(s));
	}
}
