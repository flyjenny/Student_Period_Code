package absyn;

import planner.SimpleQueryPlanner;
import query.plan.Plan;
import query.predicate.*;
import query.scan.Scan;
import tx.Transaction;

public class AtomValue extends Value {
	Query q;

	public AtomValue(Query sq) {
		q = sq;
	}

	@Override
	public Expression toExpression(Scan s) {
		SimpleQueryPlanner planner = new SimpleQueryPlanner();
		Plan p = planner.createPlan(q, new Transaction());
		Scan scan = p.open(s);

		if (scan.isSingleCol()) {
			String fieldName = scan.getSingleFldName();
			if (scan.next()) {
				Constant c = scan.getVal(fieldName);
				if (scan.next())
					throw new SubQueryMismatchException();
				return new ConstantExpr(c);
			} else
				throw new SubQueryMismatchException();

		} else
			throw new SubQueryMismatchException();
	}
}
