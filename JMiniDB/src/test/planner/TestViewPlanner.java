package test.planner;

import jminidb.JMiniDB;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.ObjectNotExistError;

import planner.Planner;
import query.plan.Plan;
import query.scan.Scan;

public class TestViewPlanner extends TestPlanner {
	@Test
	public void testCreateView() {
		Planner p = getPlane();
		p.executeUpdatePassStr(
				"create view v as select t2.a1, t2.a2 from t2 where t2.a1>13",
				null);
		JMiniDB.Close();
	}

	@Test
	public void testQueryingView() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select t1.a1 from t1, v where t1.a2 = v.a2", null);
		Scan s = q.open(null);
		int rst[] = { 6, 7, 8, 9 };
		int count = 0;
		while (s.next()) {
			// System.out.println("t1.a1=" + s.getInt("t1.a1"));
			Assert.assertEquals(rst[count++], s.getInt("t1.a1"));
		}
		Assert.assertEquals(4, count);
		JMiniDB.Close();
	}

	@Test
	public void testDropView() {
		Planner p = getPlane();
		p.executeUpdatePassStr("drop view v", null);
		JMiniDB.Close();
	}

	@Test
	public void testQueryingViewFail() {
		Planner p = getPlane();
		try {
			Plan q = p.createQueryPlanPassStr(
					"select t1.a1 from t1, v where t1.a2 = v.a2", null);
			Scan s = q.open(null);
		} catch (ObjectNotExistError e) {
			return;
		} finally {
			JMiniDB.Close();
		}
		Assert.fail();
	}

	@BeforeClass
	public static void clean() {
		TestPlanner.clean();
		Planner p = getPlane();
		p.executeUpdatePassStr("create table t1 (a1 int, a2 int)", null);
		p.executeUpdatePassStr("create table t2 (a1 int, a2 int)", null);
		for (int i = 0; i < 10; i++) {
			p.executeUpdatePassStr("insert into t1 values (" + i + ","
					+ (i + 5) + ")", null);
			p.executeUpdatePassStr("insert into t2 values (" + (i + 10) + ","
					+ (20 - i) + ")", null);
		}
		JMiniDB.Close();
	}
}