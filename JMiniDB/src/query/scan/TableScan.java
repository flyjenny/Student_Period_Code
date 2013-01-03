package query.scan;

import static common.Types.*;
import metadata.*;
import query.predicate.*;
import record.*;
import tx.Transaction;

/**
 * The Scan class corresponding to a table. A table scan is just a wrapper for a
 * RecordFile object; most methods just delegate to the corresponding RecordFile
 * methods.
 * 
 * @author NIL
 */

public class TableScan implements UpdateScan {

	private RecordFile rf;
	private Schema sch;
	private Scan parentScan;
	private String tlbName;

	public TableScan(TableInfo ti, Transaction tx) {
		rf = new RecordFile(ti, tx);
		sch = ti.getSchema();
		tlbName = ti.getTableName();
	}

	public void reset() {
		rf.beforeFirst();
	}

	public boolean next() {
		return rf.next();
	}

	public void close() {
		rf.close();
	}

	public Constant getVal(String fldName) {
		fldName = getDefaultName(fldName);
		if (sch.getType(fldName) == INT)
			return new IntConstant(rf.getInt(fldName));
		else if (sch.getType(fldName) == FLOAT)
			return new FloatConstant(rf.getFloat(fldName));
		else
			return new StringConstant(rf.getString(fldName));
	}

	public int getInt(String fldName) {
		fldName = getDefaultName(fldName);
		return rf.getInt(fldName);
	}

	public double getFloat(String fldName) {
		fldName = getDefaultName(fldName);
		return rf.getFloat(fldName);
	}

	public String getString(String fldName) {
		fldName = getDefaultName(fldName);
		return rf.getString(fldName);
	}

	public String getDefaultName(String fldName) {
		int pos = fldName.indexOf(".");
		if (pos == 0)
			return tlbName + fldName;
		else if (pos > 0)
			return fldName;
		else
			return tlbName + "." + fldName;
	}

	public boolean hasField(String fldName) {
		fldName = getDefaultName(fldName);
		return sch.hasField(fldName);
	}

	public void setVal(String fldName, Constant val) {
		fldName = getDefaultName(fldName);
		if (sch.getType(fldName) == INT)
			rf.setInt(fldName, (Integer) val.toJavaVal());
		else if (sch.getType(fldName) == FLOAT)
			rf.setFloat(fldName, (Double) val.toJavaVal());
		else
			rf.setString(fldName, (String) val.toJavaVal());
	}

	public void setInt(String fldName, int val) {
		fldName = getDefaultName(fldName);
		rf.setInt(fldName, val);
	}

	public void setFloat(String fldName, double val) {
		fldName = getDefaultName(fldName);
		rf.setFloat(fldName, val);
	}

	public void setString(String fldName, String val) {
		fldName = getDefaultName(fldName);
		rf.setString(fldName, val);
	}

	public void delete() {
		rf.delete();
	}

	public void insert() {
		rf.insert();
	}

	public Record getRecord() {
		return rf.currentRecord();
	}

	public void moveToRecord(Record r) {
		rf.moveToRecord(r);
	}

	public Scan getParentScan() {
		return parentScan;
	}

	public void setParentScan(Scan s) {
		parentScan = s;
	}

	public String getSingleFldName() {
		if (isSingleCol()) {
			return sch.getFields().iterator().next();
		} else {
			return null;
		}
	}

	public boolean isSingleCol() {
		return (sch.totalFields() == 1);
	}

}
