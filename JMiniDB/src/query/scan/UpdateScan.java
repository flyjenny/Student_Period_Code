package query.scan;

import query.predicate.Constant;
import record.Record;

public interface UpdateScan extends Scan {
	/**
	 * Modifies the field value of the current record.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @param val
	 *            the new value, expressed as a Constant
	 */
	public void setVal(String fldName, Constant val);

	/**
	 * Modifies the field value of the current record.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @param val
	 *            the new integer value
	 */
	public void setInt(String fldName, int val);

	/**
	 * Modifies the field value of the current record.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @param val
	 *            the new float value
	 */
	public void setFloat(String fldName, double val);

	/**
	 * Modifies the field value of the current record.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @param val
	 *            the new string value
	 */
	public void setString(String fldName, String val);

	/**
	 * Inserts a new record somewhere in the scan.
	 */
	public void insert();

	/**
	 * Deletes the current record from the scan.
	 */
	public void delete();

	/**
	 * Returns the RecordID of the current record.
	 * 
	 * @return the RecordID of the current record
	 */
	public Record getRecord();

	/**
	 * Positions the scan so that the current record has the specified RecordID.
	 * 
	 * @param r
	 *            the RecordID of the desired record
	 */
	public void moveToRecord(Record r);
}
