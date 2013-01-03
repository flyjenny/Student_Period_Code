package absyn;

import query.predicate.*;
import query.scan.Scan;

public class Attribute extends Value {
	public static final int NONE = 0, COUNT = 1, MIN = 2, MAX = 3, SUM = 4,
			AVG = 5;
	public static int ASC = 1, DESC = -1;

	public boolean distinct;
	public int order;
	String tblName;
	String colName;

	public Attribute(int p, String cn) {
		pos = p;
		distinct = false;
		order = ASC;
		tblName = "";
		colName = cn;
	}

	public Attribute(int p, String tn, String cn) {
		pos = p;
		distinct = false;
		order = ASC;
		tblName = tn;
		colName = cn;
	}

	public String getName() {
		return (tblName + "." + colName);
	}

	public String getTblName() {
		return tblName;
	}

	public String getColName() {
		return colName;
	}

	@Override
	public Expression toExpression(Scan s) {
		return new FieldNameExpr(getName());
	}
}
