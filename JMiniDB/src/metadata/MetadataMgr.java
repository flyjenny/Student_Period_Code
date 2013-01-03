package metadata;

import java.util.*;
import tx.Transaction;

/**
 * A metadata manager is the portion of the database system that stores and
 * retrieves its metadata.
 * 
 * @author Alex
 */

public class MetadataMgr {

	private static TableMgr tblMgr;
	private static ViewMgr viewMgr;
	private static IndexMgr indexMgr;

	public MetadataMgr(boolean isNew, Transaction tx) {
		tblMgr = new TableMgr(isNew, tx);
		viewMgr = new ViewMgr(isNew, tblMgr, tx);
		indexMgr = new IndexMgr(isNew, tblMgr, tx);
	}

	public void createTable(String tblName, String def, Schema sch,
			Transaction tx) {
		tblMgr.createTable(tblName, def, sch, tx);
	}

	public TableInfo getTableInfo(String tblName, Transaction tx) {
		return tblMgr.getTableInfo(tblName, tx);
	}

	public int dropTable(String tblName, Transaction tx) {
		return tblMgr.dropTable(tblName, tx);
	}

	public void createView(String viewname, String viewdef, Transaction tx) {
		viewMgr.createView(viewname, viewdef, tx);
	}

	public String getViewDef(String viewname, Transaction tx) {
		return viewMgr.getViewDef(viewname, tx);
	}

	public void dropView(String viewname, Transaction tx) {
		viewMgr.dropView(viewname, tx);
	}

	public void createIndex(String indexname, String tblname, String fldname,
			Transaction tx) {
		indexMgr.createIndex(indexname, tblname, fldname, tx);
	}

	public void dropIndex(String indexname, Transaction tx) {
		indexMgr.dropIndex(indexname, tx);
	}

	public IndexInfo getIndexInfo(String idxname, Transaction tx) {
		return indexMgr.getIndexInfo(idxname, tx);
	}

	public Map<String, IndexInfo> getTableIndexInfo(String tblname,
			Transaction tx) {
		return indexMgr.getTableIndexInfo(tblname, tx);
	}

	public boolean hasView(String viewName, Transaction tx) {
		return viewMgr.hasView(viewName, tx);
	}

	public boolean hasTable(String tblName, Transaction tx) {
		return tblMgr.hasTable(tblName, tx);
	}

	public boolean hasIndex(String indexName, Transaction tx) {
		return indexMgr.hasIndex(indexName, tx);
	}

	public Collection<String> getTables(Transaction tx) {
		return tblMgr.getTables(tx);
	}

	public Collection<String> getViews(Transaction tx) {
		return viewMgr.getViews(tx);
	}
}
