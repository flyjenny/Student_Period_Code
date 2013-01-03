package index.btree;

import query.predicate.*;
import record.Record;
import tx.Transaction;
import metadata.*;
import io.Block;
import io.Page;
import static common.Types.*;

public class BTreePage {

	private Block blk;
	private Page page;
	private TableInfo ti;
	private Transaction tx;
	private int slotSize;

	public BTreePage(Block blk, TableInfo ti, Transaction tx) {
		this.ti = ti;
		this.tx = tx;
		page.read(blk);
		slotSize = ti.getRecordLength();
	}

	public int findSlotBefore(Constant searchKey) {
		int slot = 0;
		while (slot < getNumRecs() && getDataVal(slot).compareTo(searchKey) < 0)
			slot++;
		return slot - 1;
	}

	public void close() {
		page.write(blk);
	}

	public boolean isFull() {
		return slotpos(getNumRecs() + 1) >= Page.BLOCK_SIZE;
	}

	public Constant split(BTreePage dest) {
		int splitSlot = getNumRecs() / 2;
		Constant splitVal = getDataVal(splitSlot);
		if (!splitVal.equals(getDataVal(0))) {
			while (splitVal.equals(getDataVal(splitSlot - 1)))
				splitSlot--;
		}
		transferRecs(splitSlot, dest);
		return splitVal;
	}

	public Constant getDataVal(int slot) {
		return getVal(slot, "dataval");
	}

	public int getFlag() {
		return page.getInt(0);
	}

	public void setFlag(int val) {
		page.setInt(0, val);
	}

	public Block appendNew(String filename, int flag) {
		return page.append(filename);
	}

	public int getChildNum(int slot) {
		return getInt(slot, "block");
	}

	public void insertDir(int slot, Constant val, int blknum) {
		insert(slot);
		setVal(slot, "dataval", val);
		setInt(slot, "block", blknum);
	}

	public void moveTo(Block blk) {
		BTreePage newpage = new BTreePage(blk, ti, tx);
		transferRecs(0, newpage);
		newpage.close();
	}

	public Record getDataRecord(int slot) {
		return new Record(getInt(slot, "block"), getInt(slot, "id"));
	}

	public void insertLeaf(int slot, Constant val, Record r) {
		insert(slot);
		setVal(slot, "dataval", val);
		setInt(slot, "block", r.blockNumber());
		setInt(slot, "id", r.id());
	}

	public void delete(int slot) {
		for (int i = slot + 1; i < getNumRecs(); i++)
			copyRecord(i, i - 1);
		setNumRecs(getNumRecs() - 1);
		return;
	}

	public int getNumRecs() {
		return page.getInt(INT_SIZE);
	}

	private int getInt(int slot, String fldname) {
		int pos = fldpos(slot, fldname);
		return page.getInt(pos);
	}

	private double getFloat(int slot, String fldname) {
		int pos = fldpos(slot, fldname);
		return page.getFloat(pos);
	}

	private String getString(int slot, String fldname) {
		int pos = fldpos(slot, fldname);
		return page.getString(pos);
	}

	private Constant getVal(int slot, String fldname) {
		int type = ti.getSchema().getType(fldname);
		if (type == INT)
			return new IntConstant(getInt(slot, fldname));
		else if (type == FLOAT)
			return new FloatConstant(getFloat(slot, fldname));
		else
			return new StringConstant(getString(slot, fldname));
	}

	private void setInt(int slot, String fldname, int val) {
		int pos = fldpos(slot, fldname);
		page.setInt(pos, val);
	}

	private void setFloat(int slot, String fldname, double val) {
		int pos = fldpos(slot, fldname);
		page.setFloat(pos, val);
	}

	private void setString(int slot, String fldname, String val) {
		int pos = fldpos(slot, fldname);
		page.setString(pos, val);
	}

	private void setVal(int slot, String fldname, Constant val) {
		int type = ti.getSchema().getType(fldname);
		if (type == INT)
			setInt(slot, fldname, (Integer) val.toJavaVal());
		else if (type == FLOAT)
			setFloat(slot, fldname, (Double) val.toJavaVal());
		else
			setString(slot, fldname, (String) val.toJavaVal());
	}

	private void setNumRecs(int n) {
		page.setInt(INT_SIZE, n);
	}

	private void insert(int slot) {
		for (int i = getNumRecs(); i > slot; i--)
			copyRecord(i - 1, i);
		setNumRecs(getNumRecs() + 1);
	}

	private void copyRecord(int from, int to) {
		Schema sch = ti.getSchema();
		for (String fldname : sch.getFields())
			setVal(to, fldname, getVal(from, fldname));
	}

	private void transferRecs(int slot, BTreePage dest) {
		int destslot = 0;
		while (slot < getNumRecs()) {
			dest.insert(destslot);
			Schema sch = ti.getSchema();
			for (String fldname : sch.getFields())
				dest.setVal(destslot, fldname, getVal(slot, fldname));
			delete(slot);
			destslot++;
		}
	}

	private int fldpos(int slot, String fldname) {
		int offset = ti.offset(fldname);
		return slotpos(slot) + offset;
	}

	private int slotpos(int slot) {
		return INT_SIZE + INT_SIZE + (slot * slotSize);
	}
}
