package metadata;

import jminidb.JMiniDB;
import index.*;
import tx.Transaction;
import static common.Types.*;

public class IndexInfo {

	private String fldname;
	private int idxnum;
	private Transaction tx;
	private TableInfo ti;

	public IndexInfo(String tblname, String fldname, int idxnum, Transaction tx) {
		this.fldname = fldname;
		this.idxnum = idxnum;
		this.tx = tx;
		ti = JMiniDB.getMdMgr().getTableInfo(tblname, tx);
	}

	public Index open() {
		Schema sch = schema();
		return new BTreeIndex(idxnum, sch, tx);
	}

	public int blocksAccessed() {
		return 0;
	}

	public int recordsOutput() {
		return 0;
	}

	private Schema schema() {
		Schema sch = new Schema();
		sch.addIntField("block");
		sch.addIntField("id");
		if (ti.getSchema().getType(fldname) == INT)
			sch.addIntField("dataval");
		else {
			int fldlen = ti.getSchema().getLength(fldname);
			sch.addStringField("dataval", fldlen);
		}
		return sch;
	}
}
