package QueryManager.Plans;

import java.util.ArrayList;

public class LogicalQueryPlan {
	public PreTreeToPlan preTreeToPlan;
	public QueryManager.Parser.Absyn.SelectColumnList projection;
	public QueryManager.Parser.Absyn.WhereExp condition;
	public ArrayList<QueryManager.Parser.Absyn.WhereExp> conditionlist = new ArrayList<QueryManager.Parser.Absyn.WhereExp>();
	public QueryManager.Parser.Absyn.FromTableList production;
	public ArrayList<LogicalQueryPlan> plans = new ArrayList<LogicalQueryPlan>();

	public int WhereExpCount;

	public LogicalQueryPlan(PreTreeToPlan pre) {
		preTreeToPlan = pre;
		projection = null;
		production = null;
		WhereExpCount = pre.whereExpCount;
		if (preTreeToPlan.selectBinExpCount == 0
				&& preTreeToPlan.sqlExpType == Marks.SELECTEXP) {// 没有union，属于SFW
			if (pre.whereCondBinExpCount == 0) {// 最外层where中没有and 和 or

				projection = preTreeToPlan.selectOp.cList;
				if (WhereExpCount == 1) {
					condition = toCondition(preTreeToPlan.whereExp);
				}

				production = preTreeToPlan.selectOp.tList;
			} else {
				projection = preTreeToPlan.selectOp.cList;
				production = preTreeToPlan.selectOp.tList;
				// System.out.println("I am here. withCondBinToConditon()");
				condition = withCondBinToConditon();
			}
		}
	}

	/**
	 * @param tmp
	 * @return 没有union，属于SFW，且where中没有and和 or
	 */
	public QueryManager.Parser.Absyn.WhereExp toCondition(
			QueryManager.Parser.Absyn.WhereExp tmp) {
		QueryManager.Parser.Absyn.WhereExp whereExp = tmp;

		if (whereExp.cond != null) {// 不是只有group 和order
			if (whereExp.cond instanceof QueryManager.Parser.Absyn.LikeExp) {// like语句，不会出现子查询
				conditionlist.add(whereExp);
				return whereExp;
			} else if (whereExp.cond instanceof QueryManager.Parser.Absyn.InExp) {// in语句，一定出现子查询
				return In_exp(whereExp.cond);
			} else {// 普通条件语句，可能有子查询
				return Cmp_exp(whereExp);
			}
		} else {// 没有whereExp.cond
			conditionlist.add(whereExp);
			return whereExp;
		}

	}

	public QueryManager.Parser.Absyn.WhereExp withCondBinToConditon() {
		QueryManager.Parser.Absyn.WhereExp Exp = null;
		for (int i = 0; i < preTreeToPlan.whereCondBinExpCount; i++) {
			QueryManager.Parser.Absyn.WhereCondBinExp wherecondbinexp = preTreeToPlan.whereCondBinExpList
					.get(i);
			if (preTreeToPlan.WhereCondBinExpCount(wherecondbinexp, 0) == 0) {// 没有嵌套的WhereCondBinExp
				Exp = new QueryManager.Parser.Absyn.WhereExp(0,
						wherecondbinexp.left, null, null);
				wherecondbinexp.left = toCondition(Exp).cond;
				Exp = new QueryManager.Parser.Absyn.WhereExp(0,
						wherecondbinexp.right, null, null);
				wherecondbinexp.right = toCondition(Exp).cond;
			} else {
				if (!((wherecondbinexp.left instanceof QueryManager.Parser.Absyn.WhereCondBinExp) && (wherecondbinexp.right instanceof QueryManager.Parser.Absyn.WhereCondBinExp))) {
					if (wherecondbinexp.left instanceof QueryManager.Parser.Absyn.WhereCondBinExp) {
						Exp = new QueryManager.Parser.Absyn.WhereExp(0,
								wherecondbinexp.right, null, null);
						wherecondbinexp.right = toCondition(Exp).cond;
					} else {
						Exp = new QueryManager.Parser.Absyn.WhereExp(0,
								wherecondbinexp.left, null, null);
						wherecondbinexp.left = toCondition(Exp).cond;
					}
				}
			}
		}
		Exp = new QueryManager.Parser.Absyn.WhereExp(0,
				preTreeToPlan.whereCondBinExpList
						.get(preTreeToPlan.whereCondBinExpCount - 1), null,
				null);
		return Exp;
	}

	public QueryManager.Parser.Absyn.WhereExp In_exp(
			QueryManager.Parser.Absyn.WhereCondExp wherec) {
		QueryManager.Parser.Absyn.InExp inExp = (QueryManager.Parser.Absyn.InExp) wherec;

		QueryManager.Parser.Absyn.CmpOp cmpOp1 = new QueryManager.Parser.Absyn.CmpOp(
				0, QueryManager.Parser.Absyn.Const.SOLO, inExp.column, null,
				null);

		QueryManager.Parser.Absyn.SelectOp selectOp = (QueryManager.Parser.Absyn.SelectOp) inExp.selectExp;

		PreTreeToPlan pttp = new PreTreeToPlan(selectOp);
		LogicalQueryPlan lqp = new LogicalQueryPlan(pttp);
		plans.add(lqp);

		QueryManager.Parser.Absyn.Column column = ((QueryManager.Parser.Absyn.AlgebraOp) selectOp.cList.head.algebraExp).column;
		QueryManager.Parser.Absyn.CmpOp cmpOp2 = new QueryManager.Parser.Absyn.CmpOp(
				0, QueryManager.Parser.Absyn.Const.SOLO, column, null, null);
		QueryManager.Parser.Absyn.CmpExp cmpExp;

		if (inExp.isIn) {
			cmpExp = new QueryManager.Parser.Absyn.CmpExp(0,
					QueryManager.Parser.Absyn.Const.EQ, cmpOp1, cmpOp2);
		} else {// not in
			cmpExp = new QueryManager.Parser.Absyn.CmpExp(0,
					QueryManager.Parser.Absyn.Const.NEQ, cmpOp1, cmpOp2);
		}
		QueryManager.Parser.Absyn.WhereExp wExp = new QueryManager.Parser.Absyn.WhereExp(
				0, cmpExp, null, null);
		conditionlist.add(wExp);
		return wExp;
	}

	public QueryManager.Parser.Absyn.WhereExp Cmp_exp(
			QueryManager.Parser.Absyn.WhereExp whereE) {
		QueryManager.Parser.Absyn.WhereExp wExp = whereE;
		QueryManager.Parser.Absyn.CmpExp cmpExp = (QueryManager.Parser.Absyn.CmpExp) wExp.cond;
		if (cmpExp.right.selectExp == null) {// 没有子查询
			conditionlist.add(wExp);
			return wExp;
		} else {
			QueryManager.Parser.Absyn.SelectOp selectOp = (QueryManager.Parser.Absyn.SelectOp) cmpExp.right.selectExp;
			QueryManager.Parser.Absyn.Column column = ((QueryManager.Parser.Absyn.AlgebraOp) selectOp.cList.head.algebraExp).column;
			PreTreeToPlan pttp = new PreTreeToPlan(selectOp);
			LogicalQueryPlan lqp = new LogicalQueryPlan(pttp);
			plans.add(lqp);
			cmpExp.right.column = column;
			cmpExp.right.selectExp = null;
			wExp.cond = cmpExp;
			conditionlist.add(wExp);
			return wExp;
		}
	}
}
