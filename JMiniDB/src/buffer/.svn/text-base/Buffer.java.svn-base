package buffer;

import io.Block;
import io.Page;

public class Buffer {
	private Page contents = new Page();
	private Block blk = null;
	private int pins = 0;
	private int modifiedBy = -1;
	public int buffId;

	// private int logSequenceNumber;

	public int getInt(int offset) {
		return contents.getInt(offset);
	}

	public String getString(int offset) {
		return contents.getString(offset);
	}

	public void setInt(int offset, int val/* , int txnum, int lsn */) {
		// modified(txnum);
		// logSequenceNumber = lsn;
		modifiedBy = 1;
		contents.setInt(offset, val);
	}

	/**
	 * @param txnum
	 */
	private void modified(int txnum) {
		modifiedBy = txnum;
	}

	public void setString(int offset, String val/* , int txnum, int lsn */) {
		// modified(txnum);
		modifiedBy = 1;
		contents.setString(offset, val);
	}

	public Block block() {
		return blk;
	}

	void flush() {
		if (modifiedBy >= 0) {
			// SimpleDB.logMgr().flush(logSequenceNumber);
			contents.write(blk);
		}
		modifiedBy = -1;
	}

	void pin() {
		pins++;
	}

	void unpin() {
		pins--;
	}

	boolean isPinned() {
		return pins > 0;
	}

	boolean isModifiedBy(int txnum) {
		return txnum == modifiedBy;
	}

	void assignToBlock(Block b) {
		flush();
		blk = b;
		contents.read(blk);
		pins = 0;

	}

	void assignToNew(String filename, PageFormatter fmtr) {
		flush();
		fmtr.format(contents);
		blk = contents.append(filename);
		pins = 0;

	}

	public void setFloat(int offset, Double val) {
		modifiedBy = 1;
		contents.setFloat(offset, val);
	}

	public double getFloat(int offset) {
		return contents.getFloat(offset);
	}

}
