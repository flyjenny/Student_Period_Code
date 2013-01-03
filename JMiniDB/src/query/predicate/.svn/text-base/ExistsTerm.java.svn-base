package query.predicate;

import planner.SimpleQueryPlanner;
import absyn.Query;
import query.plan.Plan;
import query.scan.Scan;
import tx.Transaction;

/**
 * An exists-operation term.
 * 
 * @author NIL
 */

public class ExistsTerm implements Term {

	private Query query;

	/**
	 * Create an ExistsTerm
	 * 
	 * @param q
	 *            the subquery
	 */
	public ExistsTerm(Query q) {
		query = q;
	}

	public boolean eval(Scan s) {
		SimpleQueryPlanner planner = new SimpleQueryPlanner();
		Plan p = planner.createPlan(query, new Transaction());
		Scan scan = p.open(null);
		scan.setParentScan(s);
		return scan.next();
	}

}
