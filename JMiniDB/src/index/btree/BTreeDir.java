package index.btree;

import query.predicate.Constant;
import io.Block;
import tx.Transaction;
import metadata.TableInfo;

public class BTreeDir {
	private TableInfo md;
	private Transaction tx;
	private String filename;
	private BTreePage contents;

	public BTreeDir(Block blk, TableInfo md, Transaction tx) {
		this.md = md;
		this.tx = tx;
		filename = blk.fileName();
		contents = new BTreePage(blk, md, tx);
	}

	public void close() {
		contents.close();
	}

	public int search(Constant searchkey) {
		Block childblk = findChildBlock(searchkey);
		while (contents.getFlag() > 0) {
			contents.close();
			contents = new BTreePage(childblk, md, tx);
			childblk = findChildBlock(searchkey);
		}
		return childblk.number();
	}

	public void makeNewRoot(DirEntry e) {
		Constant firstval = contents.getDataVal(0);
		int level = contents.getFlag();
		Block newblk = contents.appendNew(filename, level);
		contents.moveTo(newblk);
		DirEntry oldroot = new DirEntry(firstval, newblk.number());
		insertEntry(oldroot);
		insertEntry(e);
		contents.setFlag(level + 1);
	}

	public DirEntry insert(DirEntry e) {
		if (contents.getFlag() == 0)
			return insertEntry(e);
		Block childblk = findChildBlock(e.dataVal());
		BTreeDir child = new BTreeDir(childblk, md, tx);
		DirEntry myentry = child.insert(e);
		child.close();
		return (myentry != null) ? insertEntry(myentry) : null;
	}

	private DirEntry insertEntry(DirEntry e) {
		int newslot = 1 + contents.findSlotBefore(e.dataVal());
		contents.insertDir(newslot, e.dataVal(), e.blockNumber());
		if (!contents.isFull())
			return null;

		int level = contents.getFlag();
		Block newblk = contents.appendNew(filename, level);
		BTreePage newpage = new BTreePage(newblk, md, tx);
		Constant splitval = contents.split(newpage);
		newpage.close();
		return new DirEntry(splitval, newblk.number());
	}

	private Block findChildBlock(Constant searchkey) {
		int slot = contents.findSlotBefore(searchkey);
		if (contents.getDataVal(slot + 1).equals(searchkey))
			slot++;
		int blknum = contents.getChildNum(slot);
		return new Block(filename, blknum);
	}
}
