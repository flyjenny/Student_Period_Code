package query.scan;

import query.predicate.Constant;
import query.predicate.Predicate;
import record.Record;

/**
 * The scan class corresponding to the <i>select</i> relational algebra
 * operator. All methods except next delegate their work to the underlying scan.
 * 
 * @author NIL
 */

public class SelectScan implements UpdateScan {

	private Scan s;
	private Predicate pred;
	private Scan parentScan;

	/**
	 * Creates a select scan having the specified underlying scan and predicate.
	 * 
	 * @param s
	 *            the scan of the underlying query
	 * @param pred
	 *            the selection predicate
	 */
	public SelectScan(Scan s, Predicate pred) {
		this.s = s;
		this.pred = pred;
		s.setParentScan(this);
	}

	public void reset() {
		s.reset();
	}

	public boolean next() {
		while (s.next())
			if (pred.isSatisfied(s))
				return true;
		return false;
	}

	public void close() {
		s.close();
	}

	public Constant getVal(String fldName) {
		return s.getVal(fldName);
	}

	public int getInt(String fldName) {
		return s.getInt(fldName);
	}

	public double getFloat(String fldName) {
		return s.getFloat(fldName);
	}

	public String getString(String fldName) {
		return s.getString(fldName);
	}

	public boolean hasField(String fldName) {
		return s.hasField(fldName);
	}

	public void setVal(String fldName, Constant val) {
		UpdateScan us = (UpdateScan) s;
		us.setVal(fldName, val);
	}

	public void setInt(String fldName, int val) {
		UpdateScan us = (UpdateScan) s;
		us.setInt(fldName, val);
	}

	public void setFloat(String fldName, double val) {
		UpdateScan us = (UpdateScan) s;
		us.setFloat(fldName, val);
	}

	public void setString(String fldName, String val) {
		UpdateScan us = (UpdateScan) s;
		us.setString(fldName, val);
	}

	public void delete() {
		UpdateScan us = (UpdateScan) s;
		us.delete();
	}

	public void insert() {
		UpdateScan us = (UpdateScan) s;
		us.insert();
	}

	public Record getRecord() {
		UpdateScan us = (UpdateScan) s;
		return us.getRecord();
	}

	public void moveToRecord(Record r) {
		UpdateScan us = (UpdateScan) s;
		us.moveToRecord(r);
	}

	public Scan getParentScan() {
		return parentScan;
	}

	public void setParentScan(Scan s) {
		parentScan = s;
	}

	public String getSingleFldName() {
		if (isSingleCol()) {
			return s.getSingleFldName();
		} else {
			return null;
		}
	}

	public boolean isSingleCol() {
		return s.isSingleCol();
	}

}