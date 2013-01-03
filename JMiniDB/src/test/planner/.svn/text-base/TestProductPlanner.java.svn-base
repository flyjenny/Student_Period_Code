package test.planner;

import jminidb.JMiniDB;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import planner.Planner;
import query.plan.Plan;
import query.scan.Scan;

public class TestProductPlanner extends TestPlanner {
	@Test
	public void testProcduct() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select t1.a1,t1.a2,t2.a3,t2.a4 from t1,t2", null);
		Scan s = q.open(null);

		while (s.next()) {
			/*
			 * System.out.print(" t1.a1="+s.getInt("t1.a1"));
			 * System.out.print(" t1.a2="+s.getInt("t1.a2"));
			 * System.out.print(" t2.a3="+s.getInt("t2.a3"));
			 * System.out.print(" t2.a4="+s.getInt("t2.a4"));
			 * System.out.println();
			 */

		}
		JMiniDB.Close();
	}

	@Test
	public void testProcductJoin() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select t1.a1,t1.a2,t2.a3,t2.a4 from t1,t2 where t1.a2=t2.a3",
				null);
		Scan s = q.open(null);

		while (s.next()) {
			Assert.assertEquals(s.getInt("t1.a2"), s.getInt("t2.a3"));
			Assert.assertEquals(s.getInt("t1.a1") + 15, s.getInt("t2.a4"));
			/*
			 * System.out.print(" t1.a1="+s.getInt("t1.a1"));
			 * System.out.print(" t1.a2="+s.getInt("t1.a2"));
			 * System.out.print(" t2.a3="+s.getInt("t2.a3"));
			 * System.out.print(" t2.a4="+s.getInt("t2.a4"));
			 * System.out.println();
			 */

		}
		JMiniDB.Close();
	}

	@Test
	public void testProcductOpAdd() {
		Planner p = getPlane();
		Plan q = p
				.createQueryPlanPassStr(
						"select t1.a1,t1.a2,t2.a3,t2.a4 from t1,t2 where t1.a1+t2.a3=15",
						null);
		Scan s = q.open(null);

		int count = 0;
		while (s.next()) {
			count++;
			Assert.assertEquals(s.getInt("t1.a1") + s.getInt("t2.a3"), 15);
			/*
			 * System.out.print(" t1.a1="+s.getInt("t1.a1"));
			 * System.out.print(" t1.a2="+s.getInt("t1.a2"));
			 * System.out.print(" t2.a3="+s.getInt("t2.a3"));
			 * System.out.print(" t2.a4="+s.getInt("t2.a4"));
			 * System.out.println();
			 */

		}
		Assert.assertEquals(4, count);
		JMiniDB.Close();
	}

	@Test
	public void testEmptyTable() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr("select t3.a5 from t3", null);
		Scan s = q.open(null);

		int count = 0;
		while (s.next()) {
			Assert.fail();

		}
		Assert.assertEquals(0, count);
		JMiniDB.Close();

		p = getPlane();
		q = p.createQueryPlanPassStr("select * from t1,t3", null);
		s = q.open(null);

		count = 0;
		while (s.next()) {
			Assert.fail();

		}
		Assert.assertEquals(0, count);
		JMiniDB.Close();

		p = getPlane();
		q = p.createQueryPlanPassStr("select * from t3,t1", null);
		s = q.open(null);

		count = 0;
		while (s.next()) {
			Assert.fail();
		}
		Assert.assertEquals(0, count);
		JMiniDB.Close();
	}

	@BeforeClass
	public static void clean() {
		TestPlanner.clean();
		Planner p = getPlane();
		p.executeUpdatePassStr("create table t1 (a1 int,a2 int)", null);
		p.executeUpdatePassStr("create table t2 (a3 int,a4 int)", null);
		p.executeUpdatePassStr("create table t3 (a5 int,a6 int)", null);
		for (int i = 0; i < 5; i++) {
			p.executeUpdatePassStr("insert into t1 values (" + i + ","
					+ (i + 10) + ")", null);
			p.executeUpdatePassStr("insert into t2 values (" + (i + 10) + ","
					+ (i + 15) + ")", null);
		}
		JMiniDB.Close();

	}

}
