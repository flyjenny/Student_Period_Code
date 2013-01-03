package QueryManager.Plans;

import java.util.ArrayList;

public class PreTreeToPlan {
	public QueryManager.Parser.Parse parser;
	public QueryManager.Parser.Absyn.Absyn result;
	public QueryManager.Parser.Absyn.SqlExp sqlExp;
	public QueryManager.Parser.Absyn.SelectOp selectOp;
	public QueryManager.Parser.Absyn.WhereExp whereExp;
	public QueryManager.Parser.Absyn.WhereCondExp whereCondExp;
	public ArrayList<QueryManager.Parser.Absyn.WhereCondBinExp> whereCondBinExpList = new ArrayList<QueryManager.Parser.Absyn.WhereCondBinExp>();
	public int selectBinExpCount;// 有多少union和union all
	public int whereExpCount;// 是否有Where语句
	public int whereCondBinExpCount;// Where中有多少个and和or
	public int sqlExpType;

	/**
	 * @param filename
	 * 暂时只适用于没有union
	 */
	public PreTreeToPlan(String filename) {
		parser = new QueryManager.Parser.Parse(filename);
		result = parser.result;
		sqlExp = ((QueryManager.Parser.Absyn.SqlExpList) ((QueryManager.Parser.Absyn.List) result)).head;
		selectBinExpCount = 0;
		SelectBinExpCount();

		selectOp = null;
		whereCondExp = null;
		whereExp = null;
		whereCondBinExpCount = 0;
		whereExpCount = 1;
		sqlExpType = this.SqlExpType();
		if (sqlExpType == Marks.SELECTEXP) {
			if (selectBinExpCount == 0) {// 没有union
				selectOp = (QueryManager.Parser.Absyn.SelectOp) sqlExp;
				if (selectOp.whereExp == null) {
					whereExp = null;
				} else {
					whereExp = selectOp.whereExp;
					if (whereExp.cond == null) {
						whereExpCount = 0;
					} else {
						whereCondExp = whereExp.cond;
						if (whereCondExp instanceof QueryManager.Parser.Absyn.WhereCondBinExp) {
							whereCondBinExpCount++;

							QueryManager.Parser.Absyn.WhereCondBinExp whereCondBinExpTmp = (QueryManager.Parser.Absyn.WhereCondBinExp) whereCondExp;
							WhereCondBinExpCount(whereCondBinExpTmp, 1);
						}
					}
				}
			}
		}
	}

	public PreTreeToPlan(QueryManager.Parser.Absyn.SelectOp sop) {
		parser = null;
		result = null;
		sqlExp = null;
		selectBinExpCount = 0;

		selectOp = sop;
		whereCondExp = null;
		whereExp = null;
		whereCondBinExpCount = 0;
		whereExpCount = 1;
		sqlExpType = Marks.SELECTEXP;
		if (selectBinExpCount == 0) {// 没有union
		// System.out.println("I am in the second constructor");
			if (selectOp.whereExp == null) {
				whereExp = null;
			} else {
				whereExp = selectOp.whereExp;
				if (whereExp.cond == null) {
					whereExpCount = 0;
				} else {
					whereCondExp = whereExp.cond;
					if (whereCondExp instanceof QueryManager.Parser.Absyn.WhereCondBinExp) {
						whereCondBinExpCount++;
						QueryManager.Parser.Absyn.WhereCondBinExp whereCondBinExpTmp = (QueryManager.Parser.Absyn.WhereCondBinExp) whereCondExp;
						WhereCondBinExpCount(whereCondBinExpTmp, 1);
					}
				}
			}
		}

	}

	/**
	 * @return SqlExp类型
	 */
	public int SqlExpType() {
		if (sqlExp instanceof QueryManager.Parser.Absyn.SelectExp) {
			System.out.println("QueryManager.Parser.Absyn.SelectExp");
			return Marks.SELECTEXP;
		} else if (sqlExp instanceof QueryManager.Parser.Absyn.CreateExp) {
			System.out.println("QueryManager.Parser.Absyn.CreateExp");
			return Marks.CREATEEXP;
		} else if (sqlExp instanceof QueryManager.Parser.Absyn.InsertExp) {
			System.out.println("QueryManager.Parser.Absyn.InsertExp");
			return Marks.INSERTEXP;
		} else if (sqlExp instanceof QueryManager.Parser.Absyn.DropExp) {
			System.out.println("QueryManager.Parser.Absyn.DropExp");
			return Marks.DROPEXP;
		} else if (sqlExp instanceof QueryManager.Parser.Absyn.DeleteExp) {
			System.out.println("QueryManager.Parser.Absyn.DeleteExp");
			return Marks.DELETEEXP;
		} else if (sqlExp instanceof QueryManager.Parser.Absyn.UpdateExp) {
			System.out.println("QueryManager.Parser.Absyn.UPdateExp");
			return Marks.UPDATEEXP;
		} else if (sqlExp instanceof QueryManager.Parser.Absyn.AlterExp) {
			System.out.println("QueryManager.Parser.Absyn.AlterExp");
			return Marks.ALTEREXP;
		}
		return 0;
	}

	/**
	 * @return SqlList中SqlExp个数
	 * 既union和union all的总数
	 */
	public int SelectBinExpCount() {
		if (sqlExp instanceof QueryManager.Parser.Absyn.SelectBinExp) {
			selectBinExpCount++;
			QueryManager.Parser.Absyn.SelectBinExp selectBinExpTmp = (QueryManager.Parser.Absyn.SelectBinExp) sqlExp;
			while (selectBinExpTmp.left instanceof QueryManager.Parser.Absyn.SelectBinExp) {
				selectBinExpCount++;
				selectBinExpTmp = (QueryManager.Parser.Absyn.SelectBinExp) selectBinExpTmp.left;
			}
		}
		return selectBinExpCount;
	}

	/**
	 * @param whereCondBinExp
	 * @param set
	 * @return whereCondBinExp里的whereCondBinExp的个数
	 * 既 and和 or的总数
	 */
	public int WhereCondBinExpCount(
			QueryManager.Parser.Absyn.WhereCondBinExp whereCondBinExp, int set) {
		int i = 0;
		if ((whereCondBinExp.left instanceof QueryManager.Parser.Absyn.WhereCondBinExp)
				|| (whereCondBinExp.right instanceof QueryManager.Parser.Absyn.WhereCondBinExp)) {
			if (whereCondBinExp.left instanceof QueryManager.Parser.Absyn.WhereCondBinExp) {
				if (set == 1) {
					whereCondBinExpCount++;
				}
				i++;
				i += WhereCondBinExpCount(
						((QueryManager.Parser.Absyn.WhereCondBinExp) whereCondBinExp.left),
						set);
			}
			if (whereCondBinExp.right instanceof QueryManager.Parser.Absyn.WhereCondBinExp) {
				if (set == 1) {
					whereCondBinExpCount++;
				}
				i++;
				i += WhereCondBinExpCount(
						((QueryManager.Parser.Absyn.WhereCondBinExp) whereCondBinExp.right),
						set);
			}
			if (set == 1) {
				whereCondBinExpList.add(whereCondBinExp);
			}
			return i;
		} else {
			if (set == 1) {
				whereCondBinExpList.add(whereCondBinExp);
			}
			return i;
		}
	}

	/**
	 * 打印完整语法树
	 */
	public void print() {
		System.out.println(result.print(""));
	}

}
