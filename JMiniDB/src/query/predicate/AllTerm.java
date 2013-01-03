package query.predicate;

import planner.SimpleQueryPlanner;
import absyn.Query;
import absyn.Value;
import query.plan.Plan;
import query.scan.Scan;
import tx.Transaction;
import common.Compare;

/**
 * An all-operation term.
 * 
 * @author NIL
 */

public class AllTerm implements Term {

	private Value value;
	private int cop;
	private Query query;

	/**
	 * Create an AllTerm
	 * 
	 * @param v
	 *            the value
	 * @param op
	 *            comparing operator
	 * @param q
	 *            the subquery
	 */
	public AllTerm(Value v, int op, Query q) {
		value = v;
		cop = op;
		query = q;
	}

	public boolean eval(Scan s) {
		SimpleQueryPlanner planner = new SimpleQueryPlanner();
		Plan p = planner.createPlan(query, new Transaction());
		Scan scan = p.open(null);
		scan.setParentScan(s);
		Constant c = value.toExpression(s).evaluate(s);
		if (scan.isSingleCol()) {
			String fieldName = scan.getSingleFldName();
			while (scan.next()) {
				int ans = c.compareTo(scan.getVal(fieldName));
				if (!Compare.apply(cop, ans))
					return false;
			}
			return true;
		} else
			throw new SubQueryMismatchException();
	}

}