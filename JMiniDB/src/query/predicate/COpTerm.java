package query.predicate;

import absyn.Value;
import query.scan.Scan;
import common.Compare;

/**
 * A compare-operation term.
 * 
 * @author Alex
 */

public class COpTerm implements Term {

	private Value lvalue;
	private Value rvalue;
	private int op;

	/**
	 * Create a COpTerm.
	 * 
	 * @param l
	 *            lvalue
	 * @param op
	 *            operator
	 * @param r
	 *            rvalue
	 */
	public COpTerm(Value l, int op, Value r) {
		lvalue = l;
		this.op = op;
		rvalue = r;
	}

	public boolean eval(Scan s) {
		Constant lv = lvalue.toExpression(s).evaluate(s);
		Constant rv = rvalue.toExpression(s).evaluate(s);
		int ans = lv.compareTo(rv);
		return Compare.apply(op, ans);
	}

}
