package QueryManager.Plans;

import java.util.ArrayList;

/**
 * @author BennyChan 生成逻辑查询计划，并做了部分优化，转换使语句没有子查询
 */
public class LogicalQueryPlan {
	public PreTreeToPlan preTreeToPlan;
	public Absyn.SelectColumnList projection;
	public Absyn.WhereExp condition;
	public ArrayList<Absyn.WhereExp> conditionlist = new ArrayList<Absyn.WhereExp>();
	public Absyn.FromTableList production;
	public ArrayList<LogicalQueryPlan> plans = new ArrayList<LogicalQueryPlan>();

	public boolean selectDist = false;
	public boolean selectAll = false;

	public int WhereExpCount;
	public static int tmpname = 0;

	public LogicalQueryPlan(PreTreeToPlan pre) {

		preTreeToPlan = pre;

		if (preTreeToPlan.selectOp.selectAll)
			selectAll = true;
		if (preTreeToPlan.selectOp.selectDist)
			selectDist = true;

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
				condition = withCondBinToConditon();
			}
		}
		if (!checkProjection()) {
			System.out.println("ERROR! Name error!");
			System.exit(0);
		}

	}

	/**
	 * 没有union，属于SFW，且where中没有and和 or
	 * 
	 * @param tmp
	 * @return Absyn.WhereExp
	 */
	public Absyn.WhereExp toCondition(Absyn.WhereExp tmp) {
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

	/**
	 * @return Absyn.WhereExp 针对where中存在结合
	 */
	public Absyn.WhereExp withCondBinToConditon() {
		Absyn.WhereExp Exp = null;
		for (int i = 0; i < preTreeToPlan.whereCondBinExpCount; i++) {
			Absyn.WhereCondBinExp wherecondbinexp = preTreeToPlan.whereCondBinExpList
					.get(i);
			if (preTreeToPlan.WhereCondBinExpCount(wherecondbinexp, 0) == 0) {// 没有嵌套的WhereCondBinExp
				Exp = new Absyn.WhereExp(0, wherecondbinexp.left, null, null);
				wherecondbinexp.left = toCondition(Exp).cond;
				Exp = new Absyn.WhereExp(0, wherecondbinexp.right, null, null);
				wherecondbinexp.right = toCondition(Exp).cond;
			} else {
				if (!((wherecondbinexp.left instanceof Absyn.WhereCondBinExp) && (wherecondbinexp.right instanceof Absyn.WhereCondBinExp))) {
					if (wherecondbinexp.left instanceof Absyn.WhereCondBinExp) {
						Exp = new Absyn.WhereExp(0, wherecondbinexp.right,
								null, null);
						wherecondbinexp.right = toCondition(Exp).cond;
					} else {
						Exp = new Absyn.WhereExp(0, wherecondbinexp.left, null,
								null);
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

	/**
	 * @param wherec
	 * @return Absyn.WhereExp 对In和Not In子查询分析转换
	 */
	public Absyn.WhereExp In_exp(Absyn.WhereExp wherec) {

		Absyn.InExp inExp = (Absyn.InExp) wherec.cond;

		Absyn.CmpOp cmpOp1 = new Absyn.CmpOp(0, Absyn.Const.SOLO, inExp.column,
				null, null);

		Absyn.SelectOp selectOp = (Absyn.SelectOp) inExp.selectExp;

		/*
		 * selectOp.cList.head.name="$mytmp"+tmpname; PreTreeToPlan pttp = new
		 * PreTreeToPlan(selectOp); LogicalQueryPlan lqp = new
		 * LogicalQueryPlan(pttp); plans.add(lqp);
		 */
		Absyn.Column c = ((Absyn.AlgebraOp) selectOp.cList.head.algebraExp).column;
		Absyn.Column column = new Absyn.Column(c.pos, c.tName, "$mytmp"
				+ tmpname);

		selectOp.cList.head.name = "$mytmp" + tmpname;
		selectOp.selectDist = true;
		tmpname++;
		PreTreeToPlan pttp = new PreTreeToPlan(selectOp);
		LogicalQueryPlan lqp = new LogicalQueryPlan(pttp);
		plans.add(lqp);

		// tmpname++;
		Absyn.CmpOp cmpOp2 = new Absyn.CmpOp(0, Absyn.Const.SOLO, column, null,
				null);
		Absyn.CmpExp cmpExp;

		if (inExp.isIn) {
			cmpExp = new Absyn.CmpExp(0, Absyn.Const.EQ, cmpOp1, cmpOp2);
		} else {// not in
			cmpExp = new Absyn.CmpExp(0, Absyn.Const.NEQ, cmpOp1, cmpOp2);
		}
		Absyn.WhereExp wExp = new Absyn.WhereExp(0, cmpExp, wherec.groupExp,
				wherec.orderExp);
		conditionlist.add(wExp);
		return wExp;
	}

	/**
	 * @param whereE
	 * @return Absyn.WhereExp 对where中比较分析，包括子查询转换
	 */
	public Absyn.WhereExp Cmp_exp(Absyn.WhereExp whereE) {
		Absyn.WhereExp wExp = whereE;
		Absyn.CmpExp cmpExp = (Absyn.CmpExp) wExp.cond;
		if (cmpExp.right.selectExp == null) {// 没有子查询
			conditionlist.add(wExp);
			return wExp;
		} else {

			Absyn.SelectOp selectOp = (Absyn.SelectOp) cmpExp.right.selectExp;
			Absyn.Column c = ((Absyn.AlgebraOp) selectOp.cList.head.algebraExp).column;
			Absyn.Column column = new Absyn.Column(c.pos, c.tName, "$mytmp"
					+ tmpname);
			selectOp.cList.head.name = "$mytmp" + tmpname;
			tmpname++;

			if (cmpExp.type == Absyn.Const.GE || cmpExp.type == Absyn.Const.GT) {
				if (cmpExp.right.type == Absyn.Const.ALL) {
					Absyn.AggregateExp aggregateexp = new Absyn.AggregateExp(0,
							Absyn.Const.MAX, c);
					Absyn.AlgebraOp algebraop = (Absyn.AlgebraOp) (selectOp.cList.head.algebraExp);
					algebraop.aggregateExp = aggregateexp;
					algebraop.column = null;
				} else if (cmpExp.right.type == Absyn.Const.ANY) {
					Absyn.AggregateExp aggregateexp = new Absyn.AggregateExp(0,
							Absyn.Const.MIN, c);
					Absyn.AlgebraOp algebraop = (Absyn.AlgebraOp) (selectOp.cList.head.algebraExp);
					algebraop.aggregateExp = aggregateexp;
					algebraop.column = null;
				} else {
					System.out.println("Error! No any or all in your subquery");
					System.exit(0);
				}
			} else if (cmpExp.type == Absyn.Const.LE
					|| cmpExp.type == Absyn.Const.LT) {
				if (cmpExp.right.type == Absyn.Const.ALL) {
					Absyn.AggregateExp aggregateexp = new Absyn.AggregateExp(0,
							Absyn.Const.MIN, c);
					Absyn.AlgebraOp algebraop = (Absyn.AlgebraOp) (selectOp.cList.head.algebraExp);
					algebraop.aggregateExp = aggregateexp;
					algebraop.column = null;
				} else if (cmpExp.right.type == Absyn.Const.ANY) {
					Absyn.AggregateExp aggregateexp = new Absyn.AggregateExp(0,
							Absyn.Const.MAX, c);
					Absyn.AlgebraOp algebraop = (Absyn.AlgebraOp) (selectOp.cList.head.algebraExp);
					algebraop.aggregateExp = aggregateexp;
					algebraop.column = null;
				} else {
					System.out.println("Error! No any or all in your subquery");
					System.exit(0);
				}
			} else
				selectOp.selectDist = true;

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

	/**
	 * @return ture：select语句与from中对应，否则false
	 */
	public boolean checkProjection() {
		ArrayList<String> projectlist = new ArrayList<String>();
		ArrayList<String> fromlist = new ArrayList<String>();
		Absyn.SelectColumnList selectlist = projection;
		Absyn.FromTableList fromtablelist = production;
		Absyn.AlgebraOp algebraop = null;
		Absyn.FromTable fromtable = null;

		while (selectlist != null) {
			algebraop = (Absyn.AlgebraOp) selectlist.head.algebraExp;
			if (algebraop.column != null) {
				if (algebraop.column.tName != null)
					projectlist.add(algebraop.column.tName);
			} else if (algebraop.aggregateExp != null) {
				if (algebraop.aggregateExp.column.tName != null) {
					projectlist.add(algebraop.aggregateExp.column.tName);
				}
			}
			selectlist = selectlist.tail;
		}
		while (fromtablelist != null) {
			fromtable = fromtablelist.head;
			if (fromtable.nName != null) {
				fromlist.add(fromtable.nName);
			}
			fromtablelist = fromtablelist.tail;
		}
		for (int i = 0; i < projectlist.size(); i++) {
			for (int j = 0; j < fromlist.size(); j++) {
				if (projectlist.get(i).equals(fromlist.get(j)))
					break;
				if (j == fromlist.size() - 1)
					return false;

			}
		}

		return true;
	}
}
