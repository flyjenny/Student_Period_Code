package query.predicate;

import metadata.Schema;
import query.scan.Scan;

public class ConstantExpr implements Expression {

	private Constant val;

	public ConstantExpr(Constant c) {
		val = c;
	}

	public boolean isConstant() {
		return true;
	}

	public boolean isFieldName() {
		return false;
	}

	public boolean isAOpExpr() {
		return false;
	}

	public Constant toConstant() {
		return val;
	}

	public String toFieldName() {
		throw new ClassCastException();
	}

	public Constant evaluate(Scan s) {
		return val;
	}

	public boolean appliesTo(Schema sch) {
		return true;
	}

}
