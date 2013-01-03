package test.planner;

import jminidb.JMiniDB;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import planner.Planner;
import query.plan.Plan;
import query.scan.Scan;

public class TestOrderPlanner extends TestPlanner {

	@Test
	public void testOrderByAsc() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select ab.cd,ab.ef from ab order by ab.ef asc", null);
		Scan s = q.open(null);
		int ef = Integer.MIN_VALUE;
		while (s.next()) {
			int nef = s.getInt("ab.ef");
			Assert.assertTrue(ef <= nef);
			ef = nef;
			/*
			 * System.out.print("cd="+s.getInt("ab.cd"));
			 * System.out.print("#######");
			 * System.out.println("ef="+s.getInt("ab.ef"));
			 */
		}
		JMiniDB.Close();
	}

	@Test
	public void testOrderByDesc() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select ab.cd,ab.ef from ab order by ab.ef desc", null);
		Scan s = q.open(null);
		int ef = Integer.MAX_VALUE;
		while (s.next()) {
			int nef = s.getInt("ab.ef");
			Assert.assertTrue(ef >= nef);
			ef = nef;
			/*
			 * System.out.print("cd="+s.getInt("ab.cd"));
			 * System.out.print("#######");
			 * System.out.println("ef="+s.getInt("ab.ef"));
			 */
		}
		JMiniDB.Close();
	}

	@Test
	public void testOrderByDef() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr("select ef from ab order by ef desc",
				null);
		Scan s = q.open(null);
		int ef = Integer.MAX_VALUE;
		while (s.next()) {
			int nef = s.getInt("ef");
			Assert.assertTrue(ef >= nef);
			ef = nef;
		}
		JMiniDB.Close();
	}

	@BeforeClass
	public static void clean() {
		TestPlanner.clean();
		Planner p = getPlane();
		p.executeUpdatePassStr("create table ab (cd int,ef int)", null);
		for (int i = 0; i < 10; i++) {
			int ef = 10 * i - i * i;
			p.executeUpdatePassStr("insert into ab values (" + i + "," + ef
					+ ")", null);
		}
		JMiniDB.Close();

	}
}
