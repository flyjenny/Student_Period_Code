package query.plan;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import absyn.CreateView;
import planner.Planner;
import planner.QueryPlanner;
import jminidb.JMiniDB;
import query.scan.*;
import metadata.*;
import tx.Transaction;

/**
 * The Plan class corresponding to a table.
 * 
 * @author NIL
 */

public class ViewPlan implements Plan {

	private Transaction tx;
	private TableInfo ti;
	private Plan p;
	private Schema sch;

	/**
	 * Creates a leaf node in the query tree corresponding to the specified
	 * table.
	 * 
	 * @param tblname
	 *            the name of the table
	 * @param tx
	 *            the calling transaction
	 */
	public ViewPlan(QueryPlanner pl, String viewName, Transaction tx) {
		String viewDef = JMiniDB.getMdMgr().getViewDef(viewName, tx);
		if (viewDef != null) {
			CreateView cv = (CreateView) Planner.parseStr(viewDef);
			Plan p = pl.createPlan(cv.getView(), tx);
			Schema ps = p.getSchema();
			Collection<String> fs = ps.getFields();
			Map<String, String> vn = new HashMap<String, String>(fs.size());
			String vname = cv.getViewName();
			for (String f : fs) {
				String nf;
				int pos = f.indexOf(".");
				if (pos == 0)
					nf = vname + f;
				else if (pos > 0)
					nf = vname + "." + f.substring(pos + 1);
				else
					nf = vname + "." + f;
				vn.put(nf, f);
			}

			p = new ExtendPlan(p, vn);
			sch = p.getSchema();
			this.p = p;
		}
	}

	public Scan open(Scan sca) {
		return p.open(null);
	}

	public int blocksAccessed() {
		// TODO opt-est
		return 0;
	}

	public int recordsOutput() {
		// TODO opt-est
		return 0;
	}

	public int distinctValues(String fldName) {
		// TODO opt-est
		return 0;
	}

	public Schema getSchema() {
		return sch;
	}
}
