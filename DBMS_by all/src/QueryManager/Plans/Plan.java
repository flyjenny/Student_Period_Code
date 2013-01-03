package QueryManager.Plans;

import java.util.ArrayList;

public class Plan {
	public ArrayList<LogicalQueryPlan> lqplist = new ArrayList<LogicalQueryPlan>();
	public ArrayList<Boolean> uni = new ArrayList<Boolean>();
	public Absyn.CreateExp createexp;
	public Absyn.InsertExp insertexp;
	public Absyn.DropExp dropexp;
	public Absyn.DeleteExp deleteexp;
	public Absyn.UpdateExp updateexp;
	public Absyn.AlterExp alterexp;
	public int type;
	public int selectCondBinCount;
	public PreTreeToPlan pttp;

	public Plan(String sqlStr) throws Exception {
		createexp = null;
		insertexp = null;
		dropexp = null;
		updateexp = null;
		alterexp = null;

		pttp = new PreTreeToPlan(sqlStr);
		type = pttp.sqlExpType;
		selectCondBinCount = pttp.selectBinExpCount;
		if (type != 1) {
			judgetype();
		} else {
			if (selectCondBinCount == 0) {
				LogicalQueryPlan lqp = new LogicalQueryPlan(pttp);
				lqplist.add(lqp);
			} else {
				Absyn.SelectBinExp selectbinexp = (Absyn.SelectBinExp) pttp.sqlExp;
				changelqplist(selectbinexp);
			}
		}
		if (type == Marks.CREATEEXP) {
			createViewStatus();
		}

	}

	public void judgetype() {
		switch (type) {
		case 2:
			createexp = (Absyn.CreateExp) pttp.sqlExp;
			break;
		case 3:
			insertexp = (Absyn.InsertExp) pttp.sqlExp;
			break;
		case 4:
			dropexp = (Absyn.DropExp) pttp.sqlExp;
			break;
		case 5:
			deleteexp = (Absyn.DeleteExp) pttp.sqlExp;
			break;
		case 6:
			updateexp = (Absyn.UpdateExp) pttp.sqlExp;
			break;
		case 7:
			alterexp = (Absyn.AlterExp) pttp.sqlExp;
			break;
		default:
			break;
		}
	}

	public void changelqplist(Absyn.SelectBinExp sbe) {
		if (sbe.left instanceof Absyn.SelectBinExp) {
			changelqplist((Absyn.SelectBinExp) sbe.left);
			uni.add(sbe.unionAll);
			PreTreeToPlan p = new PreTreeToPlan((Absyn.SelectOp) sbe.right);
			LogicalQueryPlan l = new LogicalQueryPlan(p);
			lqplist.add(l);
		} else {
			uni.add(sbe.unionAll);
			PreTreeToPlan p = new PreTreeToPlan((Absyn.SelectOp) sbe.left);
			LogicalQueryPlan l = new LogicalQueryPlan(p);
			lqplist.add(l);
			p = new PreTreeToPlan((Absyn.SelectOp) sbe.right);
			l = new LogicalQueryPlan(p);
			lqplist.add(l);
		}
	}

	public void createViewStatus() {
		if (createexp instanceof Absyn.CreateViewExp) {
			Absyn.CreateViewExp cv = (Absyn.CreateViewExp) createexp;
			PreTreeToPlan p = new PreTreeToPlan((Absyn.SelectOp) cv.selectExp);
			LogicalQueryPlan l = new LogicalQueryPlan(p);
			lqplist.add(l);
		}
	}

}
