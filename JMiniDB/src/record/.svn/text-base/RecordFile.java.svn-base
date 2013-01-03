package record;

import buffer.Buffer;
import buffer.BufferManger;
import tx.Transaction;
import io.Block;
import jminidb.JMiniDB;
import metadata.TableInfo;

public class RecordFile {
	private TableInfo ti;
	private Transaction tx;
	private String filename;
	private RecordPage rp;
	private int currentblknum;

	public RecordFile(TableInfo ti, Transaction tx) {
		this.ti = ti;
		// this.tx = tx;

		filename = ti.getFileName();
		if (size() == 0)
			appendBlock();
		moveTo(0);
	}

	/**
	 * Closes the record file.
	 */
	public void close() {
		rp.close();
	}

	/**
	 * Positions the current record so that a call to method next will wind up
	 * at the first record.
	 */
	public void beforeFirst() {
		moveTo(0);
	}

	/**
	 * Moves to the next record. Returns false if there is no next record.
	 * 
	 * @return false if there is no next record.
	 */
	public boolean next() {
		while (true) {
			if (rp.next())
				return true;
			if (atLastBlock())
				return false;
			moveTo(currentblknum + 1);
		}
	}

	/**
	 * Returns the value of the specified field in the current record.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @return the integer value at that field
	 */
	public int getInt(String fldname) {
		return rp.getInt(fldname);
	}

	/**
	 * Returns the value of the specified field in the current record.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @return the string value at that field
	 */
	public String getString(String fldname) {
		return rp.getString(fldname);
	}

	public Double getFloat(String fldname) {
		return rp.getFloat(fldname);
	}

	public void setFloat(String fldname, double val) {
		rp.setFloat(fldname, val);
	}

	/**
	 * Sets the value of the specified field in the current record.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @param val
	 *            the new value for the field
	 */
	public void setInt(String fldname, int val) {
		rp.setInt(fldname, val);
	}

	/**
	 * Sets the value of the specified field in the current record.
	 * 
	 * @param fldname
	 *            the name of the field
	 * @param val
	 *            the new value for the field
	 */
	public void setString(String fldname, String val) {
		rp.setString(fldname, val);
	}

	/**
	 * Deletes the current record. The client must call next() to move to the
	 * next record. Calls to methods on a deleted record have unspecified
	 * behavior.
	 */
	public void delete() {
		rp.delete();
	}

	/**
	 * Inserts a new, blank record somewhere in the file beginning at the
	 * current record. If the new record does not fit into an existing block,
	 * then a new block is appended to the file.
	 */
	public void insert() {
		while (!rp.insert()) {
			if (atLastBlock())
				appendBlock();
			moveTo(currentblknum + 1);
		}
	}

	/**
	 * Positions the current record as indicated by the specified RID.
	 * 
	 * @param rid
	 *            a record identifier
	 */
	public void moveToRecord(Record rid) {
		moveTo(rid.blockNumber());
		rp.moveToId(rid.id());
	}

	/**
	 * Returns the RID of the current record.
	 * 
	 * @return a record identifier
	 */
	public Record currentRecord() {
		int id = rp.currentId();
		return new Record(currentblknum, id);
	}

	private void moveTo(int b) {
		if (rp != null)
			rp.close();
		currentblknum = b;
		Block blk = new Block(filename, currentblknum);
		rp = new RecordPage(blk, ti);
	}

	private boolean atLastBlock() {
		int s = size();
		return currentblknum == s - 1;
	}

	private int size() {
		return JMiniDB.getDBFile().fileSize(filename);
	}

	private void appendBlock() {
		RecordFormatter fmtr = new RecordFormatter(ti);
		BufferManger bm = JMiniDB.getBufferMrg();
		Buffer bf = bm.allocateNew(filename, fmtr);
		bm.free(bf);
		// tx.append(filename, fmtr);
	}
}