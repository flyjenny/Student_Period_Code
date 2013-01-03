package QueryManager.Plans;

import java.util.ArrayList;

public class LogicalQueryPlan {
	public PreTreeToPlan preTreeToPlan;
	public Absyn.SelectColumnList projection;
	public Absyn.WhereExp condition;
	public ArrayList<Absyn.WhereExp> conditionlist = new ArrayList<Absyn.WhereExp>();
	public Absyn.FromTableList production;
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
	public Absyn.WhereExp toCondition(
			Absyn.WhereExp tmp) {
		Absyn.WhereExp whereExp = tmp;

		if (whereExp.cond != null) {// 不是只有group 和order
			
			if (whereExp.cond instanceof Absyn.LikeExp) {// like语句，不会出现子查询
				conditionlist.add(whereExp);
				return whereExp;
			} else if (whereExp.cond instanceof Absyn.InExp) {// in语句，一定出现子查询
				
				return In_exp(whereExp);
			} else {// 普通条件语句，可能有子查询
				return Cmp_exp(whereExp);
			}
		} else {// 没有whereExp.cond
			conditionlist.add(whereExp);
			return whereExp;
		}

	}

	public Absyn.WhereExp withCondBinToConditon() {
		Absyn.WhereExp Exp = null;
		for (int i = 0; i < preTreeToPlan.whereCondBinExpCount; i++) {
			Absyn.WhereCondBinExp wherecondbinexp = preTreeToPlan.whereCondBinExpList
					.get(i);
			if (preTreeToPlan.WhereCondBinExpCount(wherecondbinexp, 0) == 0) {// 没有嵌套的WhereCondBinExp
				Exp = new Absyn.WhereExp(0,
						wherecondbinexp.left, null, null);
				wherecondbinexp.left = toCondition(Exp).cond;
				Exp = new Absyn.WhereExp(0,
						wherecondbinexp.right, null, null);
				wherecondbinexp.right = toCondition(Exp).cond;
			} else {
				if (!((wherecondbinexp.left instanceof Absyn.WhereCondBinExp) && (wherecondbinexp.right instanceof Absyn.WhereCondBinExp))) {
					if (wherecondbinexp.left instanceof Absyn.WhereCondBinExp) {
						Exp = new Absyn.WhereExp(0,
								wherecondbinexp.right, null, null);
						wherecondbinexp.right = toCondition(Exp).cond;
					} else {
						Exp = new Absyn.WhereExp(0,
								wherecondbinexp.left, null, null);
						wherecondbinexp.left = toCondition(Exp).cond;
					}
				}
			}
		}
		Exp = new Absyn.WhereExp(0,
				preTreeToPlan.whereCondBinExpList
						.get(preTreeToPlan.whereCondBinExpCount - 1), null,
				null);
		return Exp;
	}

	public Absyn.WhereExp In_exp(
			Absyn.WhereExp wherec) {
		
		Absyn.InExp inExp = (Absyn.InExp) wherec.cond;
		
		Absyn.CmpOp cmpOp1 = new Absyn.CmpOp(
				0, Absyn.Const.SOLO, inExp.column, null,
				null);

		Absyn.SelectOp selectOp = (Absyn.SelectOp) inExp.selectExp;
		
		PreTreeToPlan pttp = new PreTreeToPlan(selectOp);
		
		LogicalQueryPlan lqp = new LogicalQueryPlan(pttp);
		
		plans.add(lqp);

		Absyn.Column column = ((Absyn.AlgebraOp) selectOp.cList.head.algebraExp).column;
		Absyn.CmpOp cmpOp2 = new Absyn.CmpOp(
				0, Absyn.Const.SOLO, column, null, null);
		Absyn.CmpExp cmpExp;

		if (inExp.isIn) {
			cmpExp = new Absyn.CmpExp(0,
					Absyn.Const.EQ, cmpOp1, cmpOp2);
		} else {// not in
			cmpExp = new Absyn.CmpExp(0,
					Absyn.Const.NEQ, cmpOp1, cmpOp2);
		}
		Absyn.WhereExp wExp = new Absyn.WhereExp(
				0, cmpExp, wherec.groupExp, wherec.orderExp);
		conditionlist.add(wExp);
		return wExp;
	}

	public Absyn.WhereExp Cmp_exp(
			Absyn.WhereExp whereE) {
		Absyn.WhereExp wExp = whereE;
		Absyn.CmpExp cmpExp = (Absyn.CmpExp) wExp.cond;
		if (cmpExp.right.selectExp == null) {// 没有子查询
			conditionlist.add(wExp);
			return wExp;
		} else {
			Absyn.SelectOp selectOp = (Absyn.SelectOp) cmpExp.right.selectExp;
			Absyn.Column column = ((Absyn.AlgebraOp) selectOp.cList.head.algebraExp).column;
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
