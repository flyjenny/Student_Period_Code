package absyn;

import query.predicate.*;
import query.scan.Scan;

public class Const extends Value {
	public static final int INT = 0, FLOAT = 1, STRING = 2, DATE = 4, TIME = 5;

	int type;
	Object value;

	public Const(int p, int ty, Object v) {
		pos = p;
		type = ty;
		value = v;
	}

	@Override
	public Expression toExpression(Scan s) {
		if (type == INT) {
			return new ConstantExpr(new IntConstant((Integer) value));
		} else if (type == FLOAT) {
			return new ConstantExpr(new FloatConstant((Double) value));
		} else {
			return new ConstantExpr(new StringConstant(value.toString()));
		}
	}
}
