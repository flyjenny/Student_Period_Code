package test.planner;

import jminidb.JMiniDB;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import planner.Planner;
import query.plan.Plan;
import query.scan.Scan;

public class TestExtendPlanner extends TestPlanner {

	@Test
	public void testExtend() {
		Planner p = getPlane();
		Plan pp = p.createQueryPlanPassStr(
				"select chk.a1 as a from chk where a>5", null);
		Scan s = pp.open(null);
		int i = 0;
		while (s.next()) {
			Assert.assertEquals(s.getInt("a"), (6 + (i++)));
			// System.out.println("a="+s.getInt("a"));
			// System.out.println("a=" + s.getInt("chk.a1"));

		}
		Assert.assertEquals(i, 4);

		JMiniDB.Close();
	}

	@BeforeClass
	public static void clean() {
		TestPlanner.clean();
		Planner p = getPlane();
		p.executeUpdatePassStr("create table chk (a1 int check (chk.a1<=50) )",
				null);

		for (int i = 0; i < 10; i++) {
			if (0 == p.executeUpdatePassStr("insert into chk values (" + i
					+ ")", null)) {
				throw new RuntimeException();
			}
		}
		JMiniDB.Close();

	}
}
