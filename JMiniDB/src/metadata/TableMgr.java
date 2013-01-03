package metadata;

import java.util.*;

import record.RecordFile;
import tx.Transaction;

public class TableMgr {
	public static final int MAX_NAME = 32;
	public static final int MAX_DEF = 128;

	private TableInfo tcatInfo, fcatInfo;

	public TableMgr(boolean isNew, Transaction tx) {
		Schema tcatSchema = new Schema();
		tcatSchema.addStringField("tblname", MAX_NAME);
		tcatSchema.addIntField("reclength");
		tcatSchema.addStringField("defsql", MAX_DEF);
		tcatInfo = new TableInfo("tblcat", tcatSchema);

		Schema fcatSchema = new Schema();
		fcatSchema.addStringField("tblname", MAX_NAME);
		fcatSchema.addStringField("fldname", MAX_NAME);
		fcatSchema.addIntField("type");
		fcatSchema.addIntField("length");
		fcatSchema.addIntField("offset");
		fcatInfo = new TableInfo("fldcat", fcatSchema);

		if (isNew) {
			createTable("tblcat", "", tcatSchema, tx);
			createTable("fldcat", "", fcatSchema, tx);
		}
	}

	public void createTable(String tblName, String def, Schema sch,
			Transaction tx) {
		TableInfo ti = new TableInfo(tblName, sch);
		RecordFile tcatfile = new RecordFile(tcatInfo, tx);
		tcatfile.insert();
		tcatfile.setString("tblname", tblName);
		tcatfile.setInt("reclength", ti.getRecordLength());
		tcatfile.setString("defsql", def);
		tcatfile.close();

		RecordFile fcatfile = new RecordFile(fcatInfo, tx);
		for (String fldName : sch.getFields()) {
			fcatfile.insert();
			fcatfile.setString("tblname", tblName);
			fcatfile.setString("fldname", fldName);
			fcatfile.setInt("type", sch.getType(fldName));
			fcatfile.setInt("length", sch.getLength(fldName));
			fcatfile.setInt("offset", ti.offset(fldName));
		}
		fcatfile.close();
	}

	public TableInfo getTableInfo(String tblName, Transaction tx) {
		RecordFile tcatfile = new RecordFile(tcatInfo, tx);
		int reclen = -1;
		String def = null;
		while (tcatfile.next())
			if (tcatfile.getString("tblname").equals(tblName)) {
				reclen = tcatfile.getInt("reclength");
				def = tcatfile.getString("defsql");
				break;
			}
		tcatfile.close();

		RecordFile fcatfile = new RecordFile(fcatInfo, tx);
		Schema sch = new Schema();
		Map<String, Integer> offsets = new HashMap<String, Integer>();
		while (fcatfile.next())
			if (fcatfile.getString("tblname").equals(tblName)) {
				String fldname = fcatfile.getString("fldname");
				int fldtype = fcatfile.getInt("type");
				int fldlen = fcatfile.getInt("length");
				int offset = fcatfile.getInt("offset");
				offsets.put(tblName + fldname, offset);
				// TODO to check if isKey
				sch.addField(tblName + fldname, fldtype, fldlen);
			}
		fcatfile.close();
		return new TableInfo(tblName, def, sch, offsets, reclen);
	}

	public int dropTable(String tblName, Transaction tx) {
		RecordFile tcatfile = new RecordFile(tcatInfo, tx);
		while (tcatfile.next())
			if (tcatfile.getString("tblname").equals(tblName)) {
				tcatfile.delete();
				break;
			}
		tcatfile.close();

		RecordFile fcatfile = new RecordFile(fcatInfo, tx);
		int count = 0;
		while (fcatfile.next())
			if (fcatfile.getString("tblname").equals(tblName)) {
				fcatfile.delete();
				count++;
			}
		fcatfile.close();
		return count;
	}

	public boolean hasTable(String tblName, Transaction tx) {
		RecordFile tcatfile = new RecordFile(tcatInfo, tx);
		int reclen = -1;
		while (tcatfile.next())
			if (tcatfile.getString("tblname").equals(tblName)) {
				reclen = tcatfile.getInt("reclength");
				tcatfile.close();
				break;
			}
		tcatfile.close();
		return reclen >= 0;
	}

	public Collection<String> getTables(Transaction tx) {
		List<String> tbs = new LinkedList<String>();
		RecordFile tcatfile = new RecordFile(tcatInfo, tx);
		while (tcatfile.next()) {
			String tn = tcatfile.getString("tblname");
			if (!(tn.equals("tblcat") || tn.equals("fldcat")
					|| tn.equals("viewcat") || tn.equals("idxcat")))
				tbs.add(tn);
		}

		tcatfile.close();
		return tbs;
	}
}