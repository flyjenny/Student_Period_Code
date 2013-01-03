package query.predicate;

import query.scan.Scan;

public class AOpExpr implements Expression {

	public static final int PLUS = 0, MINUS = 1, TIMES = 2, DIVIDE = 3;

	Expression lexpr;
	Expression rexpr;
	int op;

	public AOpExpr(Expression lexpr, int op, Expression rexpr) {
		this.lexpr = lexpr;
		this.op = op;
		this.rexpr = rexpr;
	}

	public boolean isConstant() {
		return false;
	}

	public boolean isFieldName() {
		return false;
	}

	public boolean isAOpExpr() {
		return true;
	}

	public Constant toConstant() {
		throw new ClassCastException();
	}

	public String toFieldName() {
		throw new ClassCastException();
	}

	public Constant evaluate(Scan s) {
		Constant lc = lexpr.evaluate(s);
		Constant rc = rexpr.evaluate(s);
		Object lv = lc.toJavaVal();
		Object rv = rc.toJavaVal();
		Object ans = new Object();
		if (lc instanceof IntConstant && rc instanceof IntConstant) {
			switch (op) {
			case PLUS:
				ans = (Integer) lv + (Integer) rv;
				break;
			case MINUS:
				ans = (Integer) lv - (Integer) rv;
				break;
			case TIMES:
				ans = (Integer) lv * (Integer) rv;
				break;
			case DIVIDE:
				ans = (Integer) lv / (Integer) rv;
				break;
			}
			return new IntConstant((Integer) ans);
		} else if (lc instanceof FloatConstant && rc instanceof FloatConstant) {
			switch (op) {
			case PLUS:
				ans = (Double) lv + (Double) rv;
				break;
			case MINUS:
				ans = (Double) lv - (Double) rv;
				break;
			case TIMES:
				ans = (Double) lv * (Double) rv;
				break;
			case DIVIDE:
				ans = (Double) lv / (Double) rv;
				break;
			}
			return new FloatConstant((Double) ans);
		} else
			throw new TypeMismatchException();
	}

}
