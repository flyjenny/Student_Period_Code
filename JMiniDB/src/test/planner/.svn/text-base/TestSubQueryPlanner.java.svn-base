package test.planner;

import jminidb.JMiniDB;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import planner.Planner;
import query.plan.Plan;
import query.scan.Scan;

public class TestSubQueryPlanner extends TestPlanner {

	@Test
	public void testSubQuery() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select t1.a1 from t1 where t1.a1 in (select t2.a3 from t2)",
				null);
		Scan s = q.open(null);

		int rst[] = { 10, 11, 12 };
		int count = 0;
		while (s.next()) {

			// Assert.assertEquals(s.getInt("t1.a1") +s.getInt("t2.a3"),15);
			Assert.assertEquals(rst[count++], s.getInt("t1.a1"));

			// System.out.print(" t1.a1="+s.getInt("t1.a1"));
			// System.out.print(" t1.a2="+s.getInt("t1.a2"));
			// System.out.print(" t2.a3="+s.getInt("t2.a3"));
			// System.out.print(" t2.a4="+s.getInt("t2.a4"));
			// System.out.println();

		}
		Assert.assertEquals(3, count);
		JMiniDB.Close();
	}

	@Test
	public void testSubQueryPatern() {
		Planner p = getPlane();
		Plan q = p
				.createQueryPlanPassStr(
						"select t1.a1 from t1 where t1.a1 in (select t2.a3 from t2 where t2.a4+t1.a2>20 and t2.a4*t1.a2<144)",
						null);
		Scan s = q.open(null);

		int rst[] = { 11 };
		int count = 0;
		while (s.next()) {

			// Assert.assertEquals(s.getInt("t1.a1") +s.getInt("t2.a3"),15);
			Assert.assertEquals(rst[count++], s.getInt("t1.a1"));

			// System.out.print(" t1.a1="+s.getInt("t1.a1"));
			// System.out.print(" t1.a2="+s.getInt("t1.a2"));
			// System.out.print(" t2.a3="+s.getInt("t2.a3"));
			// System.out.print(" t2.a4="+s.getInt("t2.a4"));
			// System.out.println();

		}
		Assert.assertEquals(1, count);
		JMiniDB.Close();
	}

	@Test
	public void testSubQueryExists() {
		Planner p = getPlane();
		Plan q = p
				.createQueryPlanPassStr(
						"select t1.a1 from t1 where exists (select t2.a3 from t2 where t2.a3>100)",
						null);
		Scan s = q.open(null);
		int count = 0;
		Assert.assertTrue(!s.next());
		s.close();
		q = p
				.createQueryPlanPassStr(
						"select t1.a1 from t1 where exists (select t2.a3 from t2 where t2.a3<100)",
						null);
		s = q.open(null);

		while (s.next()) {
			count++;
			// System.out.print(" t1.a1="+s.getInt("t1.a1"));
			// System.out.println();
		}
		Assert.assertEquals(5, count);
		JMiniDB.Close();
	}

	@Test
	public void testSubQueryALL() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select t1.a1 from t1 where t1.a2<all (select t2.a3 from t2)",
				null);
		Scan s = q.open(null);
		int count = 0;
		int rst[] = { 8, 9 };
		while (s.next()) {
			Assert.assertEquals(rst[count++], s.getInt("t1.a1"));
			// count++;
			// System.out.print(" t1.a1=" + s.getInt("t1.a1"));
			// System.out.println();
		}
		Assert.assertEquals(2, count);
		JMiniDB.Close();
	}

	@Test
	public void testSubQueryAny() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select t1.a1 from t1 where t1.a2>any(select t2.a3 from t2)",
				null);
		Scan s = q.open(null);
		int count = 0;
		int rst[] = { 11, 12 };
		while (s.next()) {
			Assert.assertEquals(rst[count++], s.getInt("t1.a1"));
			// count++;
			// System.out.print(" t1.a1=" + s.getInt("t1.a1"));
			// System.out.println();
		}
		Assert.assertEquals(2, count);
		JMiniDB.Close();
	}

	@BeforeClass
	public static void clean() {
		TestPlanner.clean();
		Planner p = getPlane();
		p.executeUpdatePassStr("create table t1 (a1 int,a2 int)", null);
		p.executeUpdatePassStr("create table t2 (a3 int,a4 int)", null);
		for (int i = 0; i < 5; i++) {
			p.executeUpdatePassStr("insert into t1 values (" + (i + 8) + ","
					+ (i + 8) + ")", null);
			p.executeUpdatePassStr("insert into t2 values (" + (i + 10) + ","
					+ (i + 10) + ")", null);
		}
		JMiniDB.Close();

	}
}
