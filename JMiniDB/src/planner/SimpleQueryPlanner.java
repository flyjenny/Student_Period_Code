package planner;

import java.util.*;

import exception.ObjectNotExistError;

import jminidb.JMiniDB;

import absyn.*;
import query.plan.*;
import query.predicate.AggrFcn;
import query.predicate.Predicate;
import tx.Transaction;

public class SimpleQueryPlanner implements QueryPlanner {

	public Plan createPlan(Query q, Transaction tx) {
		List<Plan> plans = new ArrayList<Plan>();
		Table tbs[] = q.sfw.getTableList().toArray();
		for (Table t : tbs) {
			String name = t.getTblName();
			if (JMiniDB.getMdMgr().hasTable(name, tx)) {
				plans.add(new query.plan.TablePlan(name, tx));
			} else if (JMiniDB.getMdMgr().hasView(name, tx)) {
				plans.add(new query.plan.ViewPlan(this, name, tx));
			} else {
				throw new ObjectNotExistError("name");
			}

		}

		Plan p = plans.remove(0);
		for (Plan nextplan : plans)
			p = new ProductPlan(p, nextplan);

		Collection<String> fullNames = p.getSchema().getFields();

		p = new SelectPlan(p, new Predicate(q.sfw.getCondition()));

		// Select
		SelList sl = q.sfw.getSelectList();

		AttributeList grp = q.sfw.getGroup();
		if (null != grp) {
			// Group by
			Attribute gs[] = grp.toArray();
			SelExp sls[] = sl.toArray();
			List<AggrFcn> af = new LinkedList<AggrFcn>();
			List<String> gl = new LinkedList<String>();
			int _func = 0;
			for (SelExp se : sls) {
				if (se.getFunc() != Attribute.NONE) {
					AggrFcn f = new AggrFcn(se.getFieldName() == null ? "_func"
							+ _func++ : se.getFieldName(), se.getFunc());
					af.add(f);
				}

			}
			for (Attribute g : gs)
				gl.add(g.getName());
			HvCondition having = q.sfw.getHaving();
			Predicate hvPred = null;
			if (null != having)
				hvPred = new Predicate(having.getCond());
			p = new GroupByPlan(p, gl, af, hvPred);

		} else {
			// just select
			List<AggrFcn> af = new LinkedList<AggrFcn>();

			SelExp sls[] = sl.toArray();

			int _func = 0;
			for (SelExp se : sls) {
				String fn = se.getFieldName();
				if (null != fn && fn.startsWith(".")) {
					// TODO 语法检查，确定fn是哪个表的
					fn = tbs[0].getTblName() + fn;
				}

				// Test if we need a hide group by

				if (se.getFunc() != Attribute.NONE) {
					AggrFcn f = new AggrFcn(se.getFieldName() == null ? "_func"
							+ _func++ : se.getFieldName(), se.getFunc());
					af.add(f);
				}
			}
			if (af.size() > 0) {
				p = new GroupByPlan(p, new ArrayList<String>(), af, null);
			}
		}

		// Order by
		AttributeList order = q.sfw.getOrder();
		if (null != order) {
			Attribute od[] = order.toArray();
			List<String> f = new ArrayList<String>(od.length);
			List<Integer> o = new ArrayList<Integer>(od.length);
			for (Attribute a : od) {
				f.add(a.getName());
				o.add(a.order);

			}
			p = new SortPlan(p, f, o);
		}
		// Project
		List<String> fl = new ArrayList<String>();

		int _func = 0;

		for (SelExp se : sl.toArray())
			if ((se.getFieldName() == null) && (se.getFunc() == Attribute.NONE)) {
				// this means *

				fl.addAll(p.getSchema().getFields());
			} else {
				if (se.getFieldName() == null && se.getFunc() != Attribute.NONE)
					fl.add("_func" + _func++);
				else
					fl.add(se.getFieldName());
			}
		p = new ProjectPlan(p, fl);

		// Extend
		Map<String, String> extend = new HashMap<String, String>();

		_func = 0;
		for (SelExp se : sl.toArray()) {
			String an = se.getAlias();
			String oname = se.getFieldName();
			if (se.getFieldName() == null && se.getFunc() != Attribute.NONE)
				oname = "_func" + _func++;
			if ((se.getFieldName() == null) && (se.getFunc() == Attribute.NONE)) {
				for (String s : p.getSchema().getFields())
					extend.put(s, s);
			} else if (null != an)
				extend.put(an, oname);
			else
				extend.put(oname, oname);
		}
		p = new ExtendPlan(p, extend);
		return p;
	}

}
