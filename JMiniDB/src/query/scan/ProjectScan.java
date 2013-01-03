package query.scan;

import java.util.*;

import exception.FieldNotFound;

import query.predicate.Constant;

/**
 * The scan class corresponding to the <i>project</i> relational algebra
 * operator. All methods except hasField delegate their work to the underlying
 * scan.
 * 
 * @author NIL
 */

public class ProjectScan implements Scan {

	private Scan s;
	private Collection<String> fieldList;
	private Scan parentScan;

	/**
	 * Creates a project scan having the specified underlying scan and field
	 * list.
	 * 
	 * @param s
	 *            the underlying scan
	 * @param fieldlist
	 *            the list of field names
	 */
	public ProjectScan(Scan s, Collection<String> fl) {
		this.s = s;
		s.setParentScan(this);
		fieldList = fl;
	}

	public void reset() {
		s.reset();
	}

	public boolean next() {
		return s.next();
	}

	public void close() {
		s.close();
	}

	public Constant getVal(String fldName) {
		if (hasField(fldName))
			return s.getVal(fldName);
		else
			throw new FieldNotFound(fldName);
	}

	public int getInt(String fldName) {
		if (hasField(fldName))
			return s.getInt(fldName);
		else
			throw new FieldNotFound(fldName);
	}

	public double getFloat(String fldName) {
		if (hasField(fldName))
			return s.getFloat(fldName);
		else
			throw new FieldNotFound(fldName);
	}

	public String getString(String fldName) {
		if (hasField(fldName))
			return s.getString(fldName);
		else
			throw new FieldNotFound(fldName);
	}

	public boolean hasField(String fldName) {
		return fieldList.contains(fldName);
	}

	public Scan getParentScan() {
		return parentScan;
	}

	public void setParentScan(Scan s) {
		parentScan = s;
	}

	public String getSingleFldName() {
		if (isSingleCol()) {
			return fieldList.iterator().next();
		} else {
			return null;
		}
	}

	public boolean isSingleCol() {
		return (fieldList.size() == 1);
	}

}
