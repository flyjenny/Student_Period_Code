package query.predicate;

import query.scan.Scan;

public interface Expression {

	public boolean isConstant();

	public boolean isFieldName();

	public boolean isAOpExpr();

	public Constant toConstant();

	public String toFieldName();

	public Constant evaluate(Scan s);

}
