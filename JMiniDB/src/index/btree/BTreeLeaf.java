package index.btree;

import io.Block;
import query.predicate.Constant;
import record.Record;
import tx.Transaction;
import metadata.TableInfo;

public class BTreeLeaf {
	private TableInfo md;
	private Transaction tx;
	private String filename;
	private Constant searchkey;
	private BTreePage contents;
	private int currentslot;

	public BTreeLeaf(Block blk, TableInfo md, Constant searchkey, Transaction tx) {
		this.md = md;
		this.tx = tx;
		this.searchkey = searchkey;
		filename = blk.fileName();
		contents = new BTreePage(blk, md, tx);
		currentslot = contents.findSlotBefore(searchkey);
	}

	public void close() {
		contents.close();
	}

	public boolean next() {
		currentslot++;
		while (currentslot >= contents.getNumRecs())
			if (!moveToOverflow())
				return false;
		return contents.getDataVal(currentslot).equals(searchkey);
	}

	public Record getDataRecord() {
		return contents.getDataRecord(currentslot);
	}

	public void delete(Record r) {
		while (next())
			if (getDataRecord().equals(r)) {
				contents.delete(currentslot);
				return;
			}
	}

	public DirEntry insert(Record r) {
		currentslot++;
		contents.insertLeaf(currentslot, searchkey, r);
		if (!contents.isFull())
			return null;

		Block newblk = contents.appendNew(filename, -1);
		BTreePage newpage = new BTreePage(newblk, md, tx);
		Constant splitval = contents.split(newpage);
		if (contents.getDataVal(0).equals(splitval)) {
			newpage.setFlag(contents.getFlag());
			contents.setFlag(newblk.number());
		}
		newpage.close();
		return new DirEntry(splitval, newblk.number());
	}

	private boolean moveToOverflow() {
		int blknum = contents.getFlag();
		if (blknum < 0)
			return false;
		contents.close();
		Block nextblk = new Block(filename, blknum);
		contents = new BTreePage(nextblk, md, tx);
		currentslot = 0;
		return true;
	}
}
