package query.predicate;

import planner.*;
import absyn.*;
import query.scan.*;
import query.plan.*;
import tx.Transaction;

/**
 * An in-operation term.
 * 
 * @author NIL
 */

public class InTerm implements Term {

	private Value value;
	private Query query;

	/**
	 * Create an InTerm
	 * 
	 * @param v
	 *            the value
	 * @param q
	 *            the subquery
	 */
	public InTerm(Value v, Query q) {
		value = v;
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
				if (c.equals(scan.getVal(fieldName)))
					return true;
			}
		} else
			throw new SubQueryMismatchException();
		return false;
	}
}
