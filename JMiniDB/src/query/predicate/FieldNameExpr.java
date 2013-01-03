package query.predicate;

import query.scan.Scan;

public class FieldNameExpr implements Expression {

	private String fldName;

	public FieldNameExpr(String fldName) {
		this.fldName = fldName;
	}

	public boolean isConstant() {
		return false;
	}

	public boolean isFieldName() {
		return true;
	}

	public boolean isAOpExpr() {
		return false;
	}

	public Constant toConstant() {
		throw new ClassCastException();
	}

	public String toFieldName() {
		return fldName;
	}

	public Constant evaluate(Scan s) {
		Scan olds = s;
		while (s != null) {
			if (s.hasField(fldName))
				return s.getVal(fldName);
			else
				s = s.getParentScan();
		}
		throw new QueryScopeException();
	}

}
