package index;

import buffer.ZeroPageFormatter;
import jminidb.JMiniDB;
import index.btree.*;
import io.Block;
import io.DBFiles;
import metadata.*;
import query.predicate.*;
import record.Record;
import tx.Transaction;

public class BTreeIndex implements Index {

	private Transaction tx;
	private TableInfo dirmd, leafmd;
	private String dirfile, leaffile;
	private DBFiles fileMgr;
	private BTreeLeaf leaf = null;
	private Block rootblk;

	public BTreeIndex(int idxnumber, Schema leafsch, Transaction tx) {
		this.tx = tx;
		fileMgr = JMiniDB.getDBFile();
		leaffile = "btleaf" + idxnumber + ".bt";
		leafmd = new TableInfo(leaffile, leafsch);
		if (fileMgr.fileSize(leaffile) == 0)
			JMiniDB.getBufferMrg().allocateNew(leaffile,
					new ZeroPageFormatter());

		Schema dirsch = new Schema();
		dirsch.add("block", leafsch);
		dirsch.add("dataval", leafsch);
		dirfile = "btdir" + idxnumber + ".bt";
		dirmd = new TableInfo(dirfile, dirsch);
		rootblk = new Block(dirfile, 0);
		if (fileMgr.fileSize(dirfile) == 0) {
			/*
			 * JMiniDB.getBufferMrg() .allocateNew(dirfile, new
			 * ZeroPageFormatter());
			 */
			/*
			 * BTreePage page = new BTreePage(rootblk, dirmd, tx); int fldtype =
			 * dirsch.getType("dataval"); Constant minval; if (fldtype == INT)
			 * minval = new IntConstant(Integer.MIN_VALUE); else if (fldtype ==
			 * FLOAT) minval = new FloatConstant(Float.MIN_VALUE); minval = new
			 * StringConstant(""); page.insertDir(0, minval, 0); page.close();
			 */
		}
	}

	@Override
	public void beforeFirst(Constant searchKey) {
		close();
		BTreeDir root = new BTreeDir(rootblk, dirmd, tx);
		int blknum = root.search(searchKey);
		root.close();
		Block leafblk = new Block(leaffile, blknum);
		leaf = new BTreeLeaf(leafblk, leafmd, searchKey, tx);
	}

	@Override
	public void close() {
		if (leaf != null)
			leaf.close();
	}

	@Override
	public void delete(Constant dataVal, Record r) {
		beforeFirst(dataVal);
		leaf.delete(r);
		leaf.close();
	}

	@Override
	public Record getDataRecord() {
		return leaf.getDataRecord();
	}

	@Override
	public void insert(Constant dataVal, Record r) {
		beforeFirst(dataVal);
		DirEntry e = leaf.insert(r);
		leaf.close();
		if (e == null)
			return;
		BTreeDir root = new BTreeDir(rootblk, dirmd, tx);
		e = root.insert(e);
		if (e != null)
			root.makeNewRoot(e);
		root.close();
	}

	@Override
	public boolean next() {
		return leaf.next();
	}

}
