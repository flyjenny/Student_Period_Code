package query.scan;

import query.predicate.Constant;

/**
 * The scan class corresponding to the <i>product</i> relational algebra
 * operator.
 * 
 * @author NIL
 */

public class ProductScan implements Scan {

	private Scan s1, s2;
	private Scan parentScan;
	private boolean emptyS1;

	/**
	 * Creates a product scan having the two underlying scans.
	 * 
	 * @param s1
	 *            the LHS scan
	 * @param s2
	 *            the RHS scan
	 */
	public ProductScan(Scan s1, Scan s2) {
		this.s1 = s1;
		this.s2 = s2;
		s1.setParentScan(this);
		s2.setParentScan(this);
		if (!s1.next()) {
			emptyS1 = true;
		}
	}

	public void reset() {
		s1.reset();

		if (!s1.next()) {
			emptyS1 = true;
		}
		s2.reset();
	}

	public boolean next() {
		if (emptyS1)
			return false;
		if (s2.next())
			return true;
		else {
			s2.reset();
			return s2.next() && s1.next();
		}
	}

	public void close() {
		s1.close();
		s2.close();
	}

	public Constant getVal(String fldName) {
		if (s1.hasField(fldName))
			return s1.getVal(fldName);
		else
			return s2.getVal(fldName);
	}

	public int getInt(String fldName) {
		if (s1.hasField(fldName))
			return s1.getInt(fldName);
		else
			return s2.getInt(fldName);
	}

	public double getFloat(String fldName) {
		if (s1.hasField(fldName))
			return s1.getFloat(fldName);
		else
			return s2.getFloat(fldName);
	}

	public String getString(String fldName) {
		if (s1.hasField(fldName))
			return s1.getString(fldName);
		else
			return s2.getString(fldName);
	}

	public boolean hasField(String fldName) {
		return s1.hasField(fldName) || s2.hasField(fldName);
	}

	public Scan getParentScan() {
		return parentScan;
	}

	public void setParentScan(Scan s) {
		parentScan = s;
	}

	public String getSingleFldName() {
		return null;
	}

	public boolean isSingleCol() {
		return false;
	}

}
