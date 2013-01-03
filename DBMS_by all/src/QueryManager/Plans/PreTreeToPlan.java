package QueryManager.Plans;

import java.util.ArrayList;

public class PreTreeToPlan {
	public QueryManager.Parser.Parser parser;
	public Absyn.Absyn result;
	public Absyn.SqlExp sqlExp;
	public Absyn.SelectOp selectOp;
	public Absyn.WhereExp whereExp;
	public Absyn.WhereCondExp whereCondExp;
	public ArrayList<Absyn.WhereCondBinExp> whereCondBinExpList = new ArrayList<Absyn.WhereCondBinExp>();
	public int selectBinExpCount;// 有多少union和union all
	public int whereExpCount;// 是否有Where语句
	public int whereCondBinExpCount;// Where中有多少个and和or
	public int sqlExpType;

	/**
	 * @param filename
	 * 暂时只适用于没有union
	 * @throws Exception 
	 */
	public PreTreeToPlan(String sqlStr) throws Exception {
		parser = new QueryManager.Parser.Parser(sqlStr);
		result = parser.result;
		sqlExp = ((Absyn.SqlExpList) ((Absyn.List) result)).head;
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
				selectOp = (Absyn.SelectOp) sqlExp;
				if (selectOp.whereExp == null) {
					whereExpCount = 0;
					whereExp = null;
				} else {
					whereExp = selectOp.whereExp;
					if (whereExp.cond == null) {
						
					} else {
						whereCondExp = whereExp.cond;
						if (whereCondExp instanceof Absyn.WhereCondBinExp) {
							whereCondBinExpCount++;

							Absyn.WhereCondBinExp whereCondBinExpTmp = (Absyn.WhereCondBinExp) whereCondExp;
							WhereCondBinExpCount(whereCondBinExpTmp, 1);
						}
					}
				}
			}
		}
	}

	public PreTreeToPlan(Absyn.SelectOp sop) {
//		System.out.println(sop.print(""));
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
				whereExpCount = 0;
			} else {
				whereExp = selectOp.whereExp;
				if (whereExp.cond == null) {
//					whereExpCount = 0;
				} else {
					whereCondExp = whereExp.cond;
					if (whereCondExp instanceof Absyn.WhereCondBinExp) {
						whereCondBinExpCount++;
						Absyn.WhereCondBinExp whereCondBinExpTmp = (Absyn.WhereCondBinExp) whereCondExp;
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
		if (sqlExp instanceof Absyn.SelectExp) {
//			System.out.println("Absyn.SelectExp");
			return Marks.SELECTEXP;
		} else if (sqlExp instanceof Absyn.CreateExp) {
//			System.out.println("Absyn.CreateExp");
			return Marks.CREATEEXP;
		} else if (sqlExp instanceof Absyn.InsertExp) {
//			System.out.println("Absyn.InsertExp");
			return Marks.INSERTEXP;
		} else if (sqlExp instanceof Absyn.DropExp) {
//			System.out.println("Absyn.DropExp");
			return Marks.DROPEXP;
		} else if (sqlExp instanceof Absyn.DeleteExp) {
//			System.out.println("Absyn.DeleteExp");
			return Marks.DELETEEXP;
		} else if (sqlExp instanceof Absyn.UpdateExp) {
//			System.out.println("Absyn.UPdateExp");
			return Marks.UPDATEEXP;
		} else if (sqlExp instanceof Absyn.AlterExp) {
//			System.out.println("Absyn.AlterExp");
			return Marks.ALTEREXP;
		}
		return 0;
	}

	/**
	 * @return SqlList中SqlExp个数
	 * 既union和union all的总数
	 */
	public int SelectBinExpCount() {
		if (sqlExp instanceof Absyn.SelectBinExp) {
			selectBinExpCount++;
			Absyn.SelectBinExp selectBinExpTmp = (Absyn.SelectBinExp) sqlExp;
			while (selectBinExpTmp.left instanceof Absyn.SelectBinExp) {
				selectBinExpCount++;
				selectBinExpTmp = (Absyn.SelectBinExp) selectBinExpTmp.left;
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
			Absyn.WhereCondBinExp whereCondBinExp, int set) {
		int i = 0;
		if ((whereCondBinExp.left instanceof Absyn.WhereCondBinExp)
				|| (whereCondBinExp.right instanceof Absyn.WhereCondBinExp)) {
			if (whereCondBinExp.left instanceof Absyn.WhereCondBinExp) {
				if (set == 1) {
					whereCondBinExpCount++;
				}
				i++;
				i += WhereCondBinExpCount(
						((Absyn.WhereCondBinExp) whereCondBinExp.left),
						set);
			}
			if (whereCondBinExp.right instanceof Absyn.WhereCondBinExp) {
				if (set == 1) {
					whereCondBinExpCount++;
				}
				i++;
				i += WhereCondBinExpCount(
						((Absyn.WhereCondBinExp) whereCondBinExp.right),
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
		if(result!=null)
			System.out.println(result.print(""));
		else
			System.out.println(selectOp.print(""));
	}

}
