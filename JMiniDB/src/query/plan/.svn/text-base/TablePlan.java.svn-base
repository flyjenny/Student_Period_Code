package query.plan;

import jminidb.JMiniDB;
import query.scan.*;
import metadata.*;
import tx.Transaction;

/**
 * The Plan class corresponding to a table.
 * 
 * @author NIL
 */

public class TablePlan implements Plan {

	private Transaction tx;
	private TableInfo ti;

	/**
	 * Creates a leaf node in the query tree corresponding to the specified
	 * table.
	 * 
	 * @param tblname
	 *            the name of the table
	 * @param tx
	 *            the calling transaction
	 */
	public TablePlan(String tblName, Transaction tx) {
		this.tx = tx;
		ti = JMiniDB.getMdMgr().getTableInfo(tblName, tx);
	}

	public Scan open(Scan sca) {
		return new TableScan(ti, tx);
	}

	public int blocksAccessed() {
		// TODO opt-est
		return 0;
	}

	public int recordsOutput() {
		// TODO opt-est
		return 0;
	}

	public int distinctValues(String fldName) {
		// TODO opt-est
		return 0;
	}

	public Schema getSchema() {
		return ti.getSchema();
	}
}
