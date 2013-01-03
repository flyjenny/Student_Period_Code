package planner;

import absyn.Query;
import query.plan.Plan;
import tx.Transaction;

/**
 * A QueryPlanner handles the planning of a query.
 * 
 * @author Alex
 */

public interface QueryPlanner {

	/**
	 * Creates a plan for the parsed query.
	 * 
	 * @param q
	 *            the parsed representation of the query
	 * @param tx
	 *            the calling transaction
	 * @return a plan for that query
	 */
	public Plan createPlan(Query q, Transaction tx);

}
