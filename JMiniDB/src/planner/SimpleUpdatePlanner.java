package planner;

import java.util.*;

import exception.ObjectExistError;
import exception.ObjectNotExistError;

import metadata.*;
import jminidb.JMiniDB;
import tx.Transaction;
import absyn.*;
import query.plan.*;
import query.scan.*;
import query.predicate.*;

public class SimpleUpdatePlanner implements UpdatePlanner {

	public int executeCreateTable(CreateTbl cmd, String sql, Transaction tx) {
		String tblName = cmd.getTblName();
		if (JMiniDB.getMdMgr().hasTable(tblName, tx)
				|| JMiniDB.getMdMgr().hasView(tblName, tx)) {
			throw new ObjectExistError(tblName);
		} else {
			Schema sch = cmd.getDefinition().toSchema();
			JMiniDB.getMdMgr().createTable(tblName, sql, sch, tx);
			return 0;
		}
	}

	public int executeDropTable(DropTbl cmd, Transaction tx) {
		String tblName = cmd.getTblName();
		if (JMiniDB.getMdMgr().hasTable(tblName, tx)
				|| JMiniDB.getMdMgr().hasView(tblName, tx)) {
			return JMiniDB.getMdMgr().dropTable(tblName, tx);
		} else {
			throw new ObjectNotExistError(tblName);
		}

	}

	public int executeCreateView(CreateView cmd, String sql, Transaction tx) {
		String viewName = cmd.getViewName();
		if (JMiniDB.getMdMgr().hasTable(viewName, tx)
				|| JMiniDB.getMdMgr().hasView(viewName, tx)) {
			throw new ObjectExistError(viewName);
		}
		JMiniDB.getMdMgr().createView(viewName, sql, tx);
		return 0;
	}

	public int executeDropView(DropView cmd, Transaction tx) {
		String viewName = cmd.getViewName();
		if (JMiniDB.getMdMgr().hasTable(viewName, tx)
				|| JMiniDB.getMdMgr().hasView(viewName, tx)) {
			JMiniDB.getMdMgr().dropView(viewName, tx);
			return 0;
		} else {
			throw new ObjectNotExistError(viewName);
		}
	}

	public int executeCreateIndex(CreateIdx cmd, Transaction tx) {
		String indexName = cmd.getIndexName();
		if (JMiniDB.getMdMgr().hasIndex(indexName, tx)) {
			throw new ObjectExistError(indexName);
		}
		JMiniDB.getMdMgr().createIndex(indexName, cmd.getTblName(),
				cmd.getColName(), tx);
		return 0;
	}

	public int executeDropIndex(DropIdx cmd, Transaction tx) {
		String indexName = cmd.getIndexName();
		if (JMiniDB.getMdMgr().hasIndex(indexName, tx)) {
			JMiniDB.getMdMgr().dropIndex(indexName, tx);
			return 0;
		} else {
			throw new ObjectNotExistError(indexName);
		}
	}

	public int executeInsert(InsertClause cmd, Transaction tx) {
		if (!(JMiniDB.getMdMgr().hasTable(cmd.getTblName(), tx) || JMiniDB
				.getMdMgr().hasView(cmd.getTblName(), tx))) {
			throw new ObjectNotExistError(cmd.getTblName());
		}
		Plan p = new TablePlan(cmd.getTblName(), tx);
		List<String> fields = (List<String>) p.getSchema().getFields();
		UpdateScan us = (UpdateScan) p.open(null);

		// Do check
		Plan chkPlan = new CheckPlan(cmd.getTblName(), null, false);
		UpdateScan chk = (UpdateScan) chkPlan.open(null);
		if (chk != null) {
			Value vls[] = cmd.getValues().toArray();
			if (vls.length != fields.size())
				throw new TypeMismatchException();

			int pos = 0;
			for (String fldname : fields) {
				Constant val = vls[pos++].toExpression(chk).toConstant();
				chk.setVal(fldname, val);
			}

			// check @ close
			chk.close();
			// expection if fail
		}

		us.insert();
		if (cmd.getValues() != null) {

			// ValueList vl = cmd.getValues();
			Value vls[] = cmd.getValues().toArray();
			int pos = 0;
			for (String fldname : fields) {
				Constant val = vls[pos++].toExpression(us).toConstant();
				us.setVal(fldname, val);
			}
			us.close();
			return 1;
		} else {
			// TODO insert subquery
			return 0;
		}
	}

	public int executeDelete(DeleteClause cmd, Transaction tx) {
		if (!(JMiniDB.getMdMgr().hasTable(cmd.getTblName(), tx) || JMiniDB
				.getMdMgr().hasView(cmd.getTblName(), tx))) {
			throw new ObjectNotExistError(cmd.getTblName());
		}
		Plan p = new TablePlan(cmd.getTblName(), tx);
		p = new SelectPlan(p, new Predicate(cmd.getCond()));
		UpdateScan us = (UpdateScan) p.open(null);
		int count = 0;
		while (us.next()) {
			us.delete();
			count++;
		}
		us.close();
		return count;
	}

	public int executeUpdate(UpdateClause cmd, Transaction tx) {
		if (!(JMiniDB.getMdMgr().hasTable(cmd.getTblName(), tx) || JMiniDB
				.getMdMgr().hasView(cmd.getTblName(), tx))) {
			throw new ObjectNotExistError(cmd.getTblName());
		}
		Plan p = new TablePlan(cmd.getTblName(), tx);
		p = new SelectPlan(p, new Predicate(cmd.getCond()));
		UpdateScan us = (UpdateScan) p.open(null);

		int count = 0;
		while (us.next()) {
			// Do check
			Plan chkPlan = new CheckPlan(cmd.getTblName(), us, true);
			UpdateScan chk = (UpdateScan) chkPlan.open(null);
			if (chk != null) {
				SetList sl = cmd.getSetList();
				while (sl != null) {
					SetCol item = sl.getHead();
					Constant val = item.getValue().toExpression(chk).evaluate(
							chk);
					chk.setVal(item.getFieldName(), val);
					sl = sl.getTail();
				}
				// check @ close
				chk.close();
				// expection if fail
			}

			// Real update
			SetList sl = cmd.getSetList();
			while (sl != null) {
				SetCol item = sl.getHead();
				Constant val = item.getValue().toExpression(us).evaluate(us);
				us.setVal(item.getFieldName(), val);
				sl = sl.getTail();
			}
			count++;
		}
		return count;
	}
}
