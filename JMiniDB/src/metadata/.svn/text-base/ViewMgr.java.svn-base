package metadata;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import record.RecordFile;
import tx.Transaction;

public class ViewMgr {
	private static final int MAX_VIEWDEF = 100;
	private static final String viewTable = "viewcat";
	TableMgr tblMgr;

	public ViewMgr(boolean isNew, TableMgr tblMgr, Transaction tx) {
		this.tblMgr = tblMgr;
		if (isNew) {
			Schema sch = new Schema();
			sch.addStringField("viewname", TableMgr.MAX_NAME);
			sch.addStringField("viewdef", MAX_VIEWDEF);
			tblMgr.createTable(viewTable, "", sch, tx);
		}
	}

	public void createView(String vname, String vdef, Transaction tx) {
		TableInfo ti = tblMgr.getTableInfo("viewcat", tx);
		RecordFile rf = new RecordFile(ti, tx);
		rf.insert();
		rf.setString(viewTable + "viewname", vname);
		rf.setString(viewTable + "viewdef", vdef);
		rf.close();
	}

	public String getViewDef(String vname, Transaction tx) {
		TableInfo ti = tblMgr.getTableInfo("viewcat", tx);
		RecordFile rf = new RecordFile(ti, tx);
		try {
			while (rf.next())
				if (rf.getString(viewTable + "viewname").equals(vname))
					return rf.getString(viewTable + "viewdef");
			return null;
		} finally {
			rf.close();
		}

	}

	public void dropView(String vname, Transaction tx) {
		TableInfo ti = tblMgr.getTableInfo("viewcat", tx);
		RecordFile rf = new RecordFile(ti, tx);
		while (rf.next())
			if (rf.getString(viewTable + "viewname").equals(vname)) {
				rf.delete();
			}
		rf.close();
	}

	public boolean hasView(String viewName, Transaction tx) {
		return null != getViewDef(viewName, tx);
	}

	public Collection<String> getViews(Transaction tx) {
		List<String> vs = new LinkedList<String>();
		TableInfo ti = tblMgr.getTableInfo("viewcat", tx);
		RecordFile rf = new RecordFile(ti, tx);
		try {
			while (rf.next())
				vs.add(rf.getString(viewTable + "viewname"));

		} finally {
			rf.close();
		}
		return vs;

	}
}
