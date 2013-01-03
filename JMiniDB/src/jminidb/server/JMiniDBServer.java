package jminidb.server;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import metadata.Schema;
import metadata.TableInfo;

import common.JResultSet;
import common.JResultSetFactory;
import common.Types;

import planner.Planner;
import query.plan.Plan;

import jminidb.JMiniDB;

import Ice.Current;

public class JMiniDBServer extends _DBServerDisp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8833663656279242749L;
	public AtomicInteger cur = new AtomicInteger(0);
	public Map<Integer, Planner> plans;

	@Override
	public void connection(String dbName, Current current) {
		JMiniDB.Close();
		JMiniDB.init("d:\\jminidb\\" + dbName);
		plans = new TreeMap<Integer, Planner>();
	}

	@Override
	public int createStatement(Current current) {
		int c = cur.addAndGet(1);
		return c;
	}

	@Override
	public JResultSet executeQuery(int s, String sql, Current current) {
		Planner p;
		synchronized (plans) {
			if (!plans.containsKey(s)) {
				plans.put(s, Planner.getSimplePlanner());
			}
			p = plans.get(s);
		}
		JResultSet rst = null;
		try {
			Plan qp = p.createQueryPlanPassStr(sql, null);
			rst = JResultSetFactory.create(qp);
		} catch (RuntimeException e) {
			rst = JResultSetFactory.create(e);
		}
		return rst;
	}

	@Override
	public JResultSet executeUpdate(int s, String sql, Current current) {
		Planner p;
		synchronized (plans) {
			if (!plans.containsKey(s)) {
				plans.put(s, Planner.getSimplePlanner());
			}
			p = plans.get(s);
		}
		JResultSet rst = null;
		try {
			rst = JResultSetFactory.create(p.executeUpdatePassStr(sql, null));
		} catch (RuntimeException e) {
			e.printStackTrace();
			rst = JResultSetFactory.create(e);
		}
		return rst;
	}

	@Override
	public void close(Current current) {
		JMiniDB.Close();
	}

	@Override
	public void closeStatement(int s, Current current) {
		synchronized (plans) {
			if (!plans.containsKey(s)) {
				plans.remove(s);
			}

		}

	}

	@Override
	public JResultSet getImportedKeys(String tlbName, Current current) {
		return new JResultSet();
	}

	@Override
	public JResultSet getTables(String tlbName, Current current) {
		String fld[] = new String[2];
		fld[0] = "TABLE_NAME";
		fld[1] = "TABLE_TYPE";
		List<Object[]> rs = new LinkedList<Object[]>();
		int type[] = new int[2];

		type[0] = type[1] = Types.STRING;
		Collection<String> ts = JMiniDB.getMdMgr().getTables(null);
		for (String t : ts) {
			Object r[] = new Object[2];
			r[0] = t;
			r[1] = "TABLE";
			rs.add(r);
		}
		Collection<String> vs = JMiniDB.getMdMgr().getViews(null);
		for (String t : vs) {
			Object r[] = new Object[2];
			r[0] = t;
			r[1] = "VIEW";
			rs.add(r);
		}
		return JResultSetFactory.create(fld, type, rs);

	}

	@Override
	public JResultSet getColumns(String tlbName, Current current) {
		String fld[] = new String[4];
		fld[0] = "COLUMN_NAME";
		fld[1] = "DATA_TYPE";
		fld[2] = "COLUMN_SIZE";
		fld[3] = "IS_NULLABLE";

		List<Object[]> rs = new LinkedList<Object[]>();
		int type[] = new int[4];
		type[0] = type[2] = type[3] = Types.STRING;
		type[1] = Types.INT;

		TableInfo ti = JMiniDB.getMdMgr().getTableInfo(tlbName, null);
		Schema sch = ti.getSchema();
		for (String f : sch.getFields()) {
			Object r[] = new Object[4];
			r[0] = f;
			switch (sch.getType(f)) {
			case Types.INT:
				r[1] = java.sql.Types.INTEGER;
				break;
			case Types.STRING:
				r[1] = java.sql.Types.CHAR;
				break;
			case Types.FLOAT:
				r[1] = java.sql.Types.DOUBLE;
				break;
			}
			r[2] = sch.getLength(f);
			r[3] = false;
			rs.add(r);
		}
		return JResultSetFactory.create(fld, type, rs);
	}

}
