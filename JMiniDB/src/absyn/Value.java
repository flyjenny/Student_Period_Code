package absyn;

import query.predicate.Expression;
import query.scan.Scan;

abstract public class Value extends Absyn {

	public abstract Expression toExpression(Scan s);

}
