package query.plan;

import java.util.*;

import absyn.*;

import planner.Planner;

import jminidb.JMiniDB;

import metadata.Schema;
import metadata.TableInfo;
import query.predicate.Predicate;
import query.scan.CheckScan;
import query.scan.Scan;
import query.scan.UpdateScan;

public class CheckPlan implements Plan {

	private Schema sch;
	private Map<String, Integer> fmap;
	private Predicate ps[];
	private UpdateScan scan;

	public CheckPlan(String tblName, UpdateScan us, boolean ignPriKey) {
		this.scan = us;

		TableInfo ti = JMiniDB.getMdMgr().getTableInfo(tblName, null);
		String def = ti.getDef();

		// check predicate
		CreateTbl ctb = (CreateTbl) Planner.parseStr(def);
		CreateDef cds[] = ctb.getDefinition().toArray();
		ArrayList<Predicate> ps = new ArrayList<Predicate>();
		for (CreateDef cd : cds) {
			if (cd instanceof ColDef) {
				ColDef chk = (ColDef) cd;
				if (chk.getCond() != null) {
					Predicate p = new Predicate(chk.getCond());
					ps.add(p);
				}
				if (chk.hasForgnKey()) {
					Predicate p = buildForgnKeyPred(tblName, chk);
					ps.add(p);
				}
				if (chk.isPriKey() && !ignPriKey) {
					Predicate p = buildPriKeyPred(tblName, chk);
					ps.add(p);
				}
			} else if (cd instanceof CheckDef) {
				CheckDef chk = (CheckDef) cd;
				if (chk.getCond() != null) {
					Predicate p = new Predicate(chk.getCond());
					ps.add(p);
				}
			} else if (cd instanceof ForgnKeyDef) {
				ForgnKeyDef chk = (ForgnKeyDef) cd;
				Predicate p = buildForgnKeyPred(tblName, chk);
				ps.add(p);
			} else if (cd instanceof PriKeyDef && !ignPriKey) {
				PriKeyDef chk = (PriKeyDef) cd;
				Predicate p = buildPriKeyPred(tblName, chk);
				ps.add(p);
			}
		}

		this.sch = ti.getSchema();
		this.ps = ps.toArray(new Predicate[0]);
		// dummy sch
		String fs[] = sch.getFields().toArray(new String[0]);
		fmap = new HashMap<String, Integer>(fs.length);
		for (int i = 0; i < fs.length; i++) {
			{
				fmap.put(fs[i], i);
			}
		}

	}

	private Predicate buildForgnKeyPred(String tblName, ForgnKeyDef chk) {
		Predicate p = new Predicate(new Condition(new ValTerm(0, new Attribute(
				0, tblName, chk.getLocal().getColName()), ValTerm.IN,
				new Query(0, new SFW(0,
						new SelList(new SelExp(chk.getForgn())), new TableList(
								new Table(0, chk.getForgn().getTblName())),
						null, null, null, null)))));
		return p;
	}

	private Predicate buildForgnKeyPred(String tblName, ColDef chk) {
		Predicate p = new Predicate(new Condition(
				new ValTerm(0, new Attribute(0, tblName, chk.getAttribute()
						.getColName()), ValTerm.IN, new Query(0, new SFW(0,
						new SelList(new SelExp(chk.getForgnKey())),
						new TableList(new Table(0, chk.getForgnKey()
								.getTblName())), null, null, null, null)))));
		return p;
	}

	private Predicate buildPriKeyPred(String tblName, PriKeyDef chk) {
		Attribute key = new Attribute(0, tblName, chk.getPriKeys().toArray()[0]
				.getColName());
		Predicate p = new Predicate(new Condition(new BoolTerm(BoolTerm.NOT,
				new ValTerm(0, key, ValTerm.IN, new Query(0, new SFW(0,
						new SelList(new SelExp(key)), new TableList(new Table(
								0, tblName)), null, null, null, null))))));
		return p;
	}

	private Predicate buildPriKeyPred(String tblName, ColDef chk) {
		Attribute key = new Attribute(0, tblName, chk.getAttribute()
				.getColName());
		Predicate p = new Predicate(new Condition(new BoolTerm(BoolTerm.NOT,
				new ValTerm(0, key, ValTerm.IN, new Query(0, new SFW(0,
						new SelList(new SelExp(key)), new TableList(new Table(
								0, tblName)), null, null, null, null))))));
		return p;
	}

	@Override
	public int blocksAccessed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int distinctValues(String fldName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Schema getSchema() {
		return sch;
	}

	@Override
	public Scan open(Scan sca) {
		if (ps.length > 0)
			return new CheckScan(scan, fmap, ps);
		else
			return null;
	}

	@Override
	public int recordsOutput() {
		// TODO Auto-generated method stub
		return 0;
	}

}
