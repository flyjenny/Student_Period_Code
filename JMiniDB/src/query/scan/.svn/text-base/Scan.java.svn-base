package query.scan;

import query.predicate.Constant;

/**
 * An object that represents a relational algebra query.
 * 
 * @author Alex
 */

public interface Scan {

	/**
	 * Reset the scan before the first record.
	 */
	public void reset();

	/**
	 * Move the scan to next record.
	 * 
	 * @return false if there is no record
	 */
	public boolean next();

	/**
	 * Close the scan and its sub-scans.
	 */
	public void close();

	/**
	 * Returns the value of the specified field in the current record. The value
	 * is expressed as a Constant.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @return the value of that field, expressed as a Constant.
	 */
	public Constant getVal(String fldName);

	/**
	 * Returns the value of the specified integer field in the current record.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @return the field's integer value in the current record
	 */
	public int getInt(String fldName);

	/**
	 * Returns the value of the specified float field in the current record.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @return the field's float value in the current record
	 */
	public double getFloat(String fldName);

	/**
	 * Returns the value of the specified string field in the current record.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @return the field's string value in the current record
	 */
	public String getString(String fldName);

	/**
	 * Returns true if the scan has the specified field.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @return true if the scan has that field
	 */
	public boolean hasField(String fldName);

	/**
	 * Returns the super scan
	 * 
	 * @return the super scan
	 */
	public Scan getParentScan();

	/**
	 * Set the super scan
	 * 
	 * @param s
	 *            the super scan
	 */
	public void setParentScan(Scan s);

	/**
	 * Whether the scan has single column
	 * 
	 * @return true if the scan has single column
	 */
	public boolean isSingleCol();

	/**
	 * Returns the single column's field name
	 * 
	 * @return the single column's field name
	 */
	public String getSingleFldName();
}
