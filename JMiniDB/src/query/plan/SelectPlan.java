package query.plan;

import query.scan.*;
import query.predicate.*;
import metadata.*;

/**
 * The Plan class corresponding to the select relational algebra operator.
 * 
 * @author NILL
 */

public class SelectPlan implements Plan {

	private Plan p;
	private Predicate pred;

	/**
	 * Creates a new select node in the query tree, having the specified
	 * subquery and predicate.
	 * 
	 * @param p
	 *            the subquery
	 * @param pred
	 *            the predicate
	 */
	public SelectPlan(Plan p, Predicate pred) {
		this.p = p;
		this.pred = pred;
	}

	public Scan open(Scan sca) {
		Scan s = p.open(sca);
		return new SelectScan(s, pred);
	}

	public int blocksAccessed() {
		return 0;
	}

	public int recordsOutput() {
		return 0;
	}

	public int distinctValues(String fldName) {
		return 0;
	}

	public Schema getSchema() {
		return p.getSchema();
	}
}
