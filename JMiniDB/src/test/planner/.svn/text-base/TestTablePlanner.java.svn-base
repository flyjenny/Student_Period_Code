package test.planner;

import jminidb.JMiniDB;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.ObjectNotExistError;

import planner.Planner;
import query.plan.Plan;
import query.scan.Scan;

public class TestTablePlanner extends TestPlanner {
	@Test
	public void testCreateTable() {
		Planner p = getPlane();
		p.executeUpdatePassStr("create table t (a int)", null);
		JMiniDB.Close();
	}

	@Test
	public void testUseTable() {
		Planner p = getPlane();
		p.executeUpdatePassStr("insert into t values (0)", null);
		JMiniDB.Close();
	}

	@Test
	public void testDropTable() {
		Planner p = getPlane();
		int u = p.executeUpdatePassStr("drop table t", null);
		Assert.assertEquals(1, u);
		JMiniDB.Close();
	}

	@Test
	public void testUseTableFail() {
		Planner p = getPlane();
		try {
			Plan plan = p.createQueryPlanPassStr("select * from t", null);
			Scan s = plan.open(null);
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
	}
}