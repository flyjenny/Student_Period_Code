package test.planner;

import jminidb.JMiniDB;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import planner.Planner;
import query.plan.Plan;
import query.scan.Scan;

public class TestGroupByPlanner extends TestPlanner {

	@Test
	public void testGroupBy() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select t1.a1 from t1 group by t1.a1", null);
		Scan s = q.open(null);
		int a = Integer.MIN_VALUE;
		while (s.next()) {
			int a1 = s.getInt("t1.a1");
			Assert.assertTrue(a1 >= a);
			a = a1;
			// System.out.println("a1=" + s.getInt("t1.a1"));
		}
		JMiniDB.Close();
	}

	@Test
	public void testGroupByCount() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select t1.a1,count(t1.a2) from t1 group by t1.a1", null);
		Scan s = q.open(null);
		int a = Integer.MIN_VALUE;
		while (s.next()) {
			int a1 = s.getInt("t1.a1");
			Assert.assertTrue(a1 >= a);
			a = a1;
			Assert.assertEquals(2, s.getInt("t1.a2"));
			// System.out.println("a1=" + s.getInt("t1.a1"));
		}
		JMiniDB.Close();
	}

	@Test
	public void testGroupBySum() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select t1.a1,sum(t1.a2) from t1 group by t1.a1", null);
		Scan s = q.open(null);
		int a = Integer.MIN_VALUE;
		Double rst[] = { 1d, 13d, 41d, 85d, 145d };
		int i = 0;
		while (s.next()) {
			int a1 = s.getInt("t1.a1");
			Assert.assertTrue(a1 >= a);
			a = a1;
			Assert.assertEquals(rst[i++], (Double) s.getFloat("t1.a2"));

			// System.out.println("a2=" + s.getFloat("t1.a2"));
		}
		JMiniDB.Close();
	}

	@Test
	public void testGroupByMax() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select t1.a1,max(t1.a2) as maxf from t1 group by t1.a1", null);
		Scan s = q.open(null);
		int a = Integer.MIN_VALUE;
		Double rst[] = { 1d, 9d, 25d, 49d, 81d };
		int i = 0;
		while (s.next()) {
			int a1 = s.getInt("t1.a1");
			Assert.assertTrue(a1 >= a);
			a = a1;
			Assert.assertEquals(rst[i++], (Double) s.getFloat("maxf"));

			// System.out.println("a2=" + s.getFloat("t1.a2"));
		}
		JMiniDB.Close();
	}

	@Test
	public void testGroupByMin() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select t1.a1,min(t1.a2) from t1 group by t1.a1", null);
		Scan s = q.open(null);
		int a = Integer.MIN_VALUE;
		Double rst[] = { 0d, 4d, 16d, 36d, 64d };
		int i = 0;
		while (s.next()) {
			int a1 = s.getInt("t1.a1");
			Assert.assertTrue(a1 >= a);
			a = a1;
			Assert.assertEquals(rst[i++], (Double) s.getFloat("t1.a2"));

			// System.out.println("a2=" + s.getFloat("t1.a2"));
		}
		JMiniDB.Close();
	}

	@Test
	public void testGroupByAvg() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select t1.a1,avg(t1.a2) from t1 group by t1.a1", null);
		Scan s = q.open(null);
		int a = Integer.MIN_VALUE;
		Double rst[] = { 1d, 13d, 41d, 85d, 145d };
		int i = 0;
		while (s.next()) {
			int a1 = s.getInt("t1.a1");
			Assert.assertTrue(a1 >= a);
			a = a1;
			Assert.assertEquals((Double) (rst[i++] / 2), (Double) s
					.getFloat("t1.a2"));

			// System.out.println("a2=" + s.getFloat("t1.a2"));
		}
		JMiniDB.Close();
	}

	@Test
	public void testSelectCount() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr("select count(*) from t1", null);
		Scan s = q.open(null);

		while (s.next()) {
			int a1 = s.getInt("_func0");
			Assert.assertEquals(a1, 10);
		}
		JMiniDB.Close();

	}

	@Test
	public void testHaving() {
		Planner p = getPlane();
		Plan q = p.createQueryPlanPassStr(
				"select h1.a1 from h1 group by h1.a1 having h1.a2=2", null);
		Scan s = q.open(null);
		while (s.next()) {
			// System.out.println("s="+s.getInt("h1.a1"));
		}
		JMiniDB.Close();

	}

	@BeforeClass
	public static void clean() {
		TestPlanner.clean();
		Planner p = getPlane();
		p.executeUpdatePassStr("create table t1 (a1 int,a2 int)", null);
		for (int i = 0; i < 10; i++) {
			int ef = i * i;
			p.executeUpdatePassStr("insert into t1 values (" + i / 2 + "," + ef
					+ ")", null);
		}
		p.executeUpdatePassStr("create table h1 (a1 int,a2 int)", null);
		for (int j = 0; j < 5; j++)
			for (int i = 0; i < 3; i++) {

				p.executeUpdatePassStr("insert into h1 values (" + j + ","
						+ (i + j) + ")", null);
			}
		JMiniDB.Close();

	}
}
