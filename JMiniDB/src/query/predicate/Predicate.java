package query.predicate;

import query.scan.Scan;
import absyn.BoolExp;
import absyn.BoolTerm;
import absyn.Condition;
import absyn.ValTerm;

/**
 * A predicate is a Boolean combination of terms.
 * 
 * @author Alex
 */

public class Predicate {
	BoolExp cond;

	/**
	 * Creating a predicate with absyn.Condition
	 * 
	 * @param c
	 *            the where-condition
	 */
	public Predicate(Condition c) {
		if (c != null) {
			cond = c.getBoolExp();
		}
	}

	/**
	 * Creating a predicate with absyn.BoolExp
	 * 
	 * @param c
	 *            the boolean expression
	 */
	public Predicate(BoolExp c) {
		cond = c;
	}

	/**
	 * Check whether the specified scan satisfies the condition
	 * 
	 * @param s
	 *            the scan
	 * @return true if the predicate is true in the scan
	 */
	public boolean isSatisfied(Scan s) {
		if (cond == null)
			return true;
		if (cond instanceof ValTerm) {
			ValTerm t = (ValTerm) cond;
			return t.toTerm().eval(s);
		} else if (cond instanceof BoolTerm) {
			BoolTerm t = (BoolTerm) cond;
			Predicate p1 = new Predicate(t.getLeftExp());
			Predicate p2 = new Predicate(t.getRightExp());
			switch (t.getOP()) {
			case BoolTerm.NOT:
				return (!p1.isSatisfied(s));
			case BoolTerm.AND:
				return (p1.isSatisfied(s) ? p2.isSatisfied(s) : false);
			case BoolTerm.OR:
				return (p1.isSatisfied(s) ? true : p2.isSatisfied(s));
			}
		}
		return false;
	}
}
