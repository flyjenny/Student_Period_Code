package metadata;

import java.util.*;

import jminidb.JMiniDB;
import record.RecordFile;
import tx.Transaction;

public class IndexMgr {
	private static int nextIdxNum = 0;
	private static final String idxTable = "idxcat";
	private TableInfo ti;

	public IndexMgr(boolean isnew, TableMgr tblmgr, Transaction tx) {
		if (isnew) {
			Schema sch = new Schema();
			sch.addStringField("idxname", TableMgr.MAX_NAME);
			sch.addStringField("tablename", TableMgr.MAX_NAME);
			sch.addStringField("fieldname", TableMgr.MAX_NAME);
			sch.addIntField("indexnumber");
			tblmgr.createTable(idxTable, "", sch, tx);
		}
		ti = tblmgr.getTableInfo(idxTable, tx);
	}

	public void createIndex(String idxname, String tblname, String fldname,
			Transaction tx) {
		int idxnum = nextIndexNumber();
		RecordFile rf = new RecordFile(ti, tx);
		rf.insert();
		rf.setString(idxTable + "idxname", idxname);
		rf.setString(idxTable + "tablename", tblname);
		rf.setString(idxTable + "fieldname", fldname);
		rf.setInt(idxTable + "indexnumber", idxnum);
		rf.close();
		Schema sch = new Schema();
		sch.addIntField("block");
		TableInfo ti = JMiniDB.getMdMgr().getTableInfo(tblname, tx);

		sch.addField("dataval",
				ti.getSchema().getType(tblname + "." + fldname), ti.getSchema()
						.getLength(tblname + "." + fldname));

		// Index id=new BTreeIndex(idxnum,sch,tx);
	}

	public void dropIndex(String idxname, Transaction tx) {
		RecordFile rf = new RecordFile(ti, tx);
		while (rf.next())
			if (rf.getString(idxTable + "idxname").equals(idxname)) {
				rf.delete();
			}
		rf.close();
	}

	public IndexInfo getIndexInfo(String idxname, Transaction tx) {
		RecordFile rf = new RecordFile(ti, tx);
		while (rf.next())
			if (rf.getString(idxTable + "idxname").equals(idxname)) {
				String tblname = rf.getString(idxTable + "tablename");
				String fldname = rf.getString(idxTable + "fieldname");
				int indexnum = rf.getInt(idxTable + "indexnumber");
				IndexInfo ii = new IndexInfo(tblname, fldname, indexnum, tx);
				rf.close();
				return ii;
			}
		rf.close();
		return null;
	}

	public Map<String, IndexInfo> getTableIndexInfo(String tblname,
			Transaction tx) {
		Map<String, IndexInfo> result = new HashMap<String, IndexInfo>();
		RecordFile rf = new RecordFile(ti, tx);
		while (rf.next())
			if (rf.getString(idxTable + "tablename").equals(tblname)) {
				String fldname = rf.getString(idxTable + "fieldname");
				int indexnum = rf.getInt(idxTable + "indexnumber");
				IndexInfo ii = new IndexInfo(tblname, fldname, indexnum, tx);
				result.put(fldname, ii);
			}
		rf.close();
		return result;
	}

	private static synchronized int nextIndexNumber() {
		nextIdxNum++;
		return nextIdxNum;
	}

	public boolean hasIndex(String indexName, Transaction tx) {
		RecordFile rf = new RecordFile(ti, tx);
		while (rf.next())
			if (rf.getString(idxTable + "idxname").equals(indexName)) {
				rf.close();
				return true;
			}
		rf.close();
		return false;
	}
}
