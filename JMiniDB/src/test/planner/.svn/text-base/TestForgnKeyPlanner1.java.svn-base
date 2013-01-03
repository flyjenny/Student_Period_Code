package test.planner;

import jminidb.JMiniDB;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.CheckFailError;

import planner.Planner;

public class TestForgnKeyPlanner1 extends TestPlanner {

	@Test
	public void testUpdateForgnKey1() {
		clean();
		Planner p = getPlane();
		int u = p.executeUpdatePassStr("update t2 set t2.a2=5", null);
		JMiniDB.Close();
	}

	@Test
	public void testUpdateForgnKey2() {
		clean();
		Planner p = getPlane();
		int u = p.executeUpdatePassStr(
				"update t1 set t1.a1=30 where t1.a1>=10", null);
		JMiniDB.Close();
	}

	@Test
	public void testUpdateForgnKey3() {
		clean();
		Planner p = getPlane();
		try {
			int u = p.executeUpdatePassStr("update t2 set t2.a2=20", null);
		} catch (CheckFailError e) {
			return;
		} finally {
			JMiniDB.Close();
		}
		Assert.fail();
	}

	@Test
	public void testUpdateForgnKey4() {
		clean();
		Planner p = getPlane();
		int u = p.executeUpdatePassStr("update t1 set t1.a1=20", null);
		JMiniDB.Close();
	}

	@Test
	public void testInsertForgnKey1() {
		clean();
		Planner p = getPlane();
		int u = p.executeUpdatePassStr("insert into t2 values(11)", null);
		JMiniDB.Close();
	}

	@Test
	public void testInsertForgnKey2() {
		Planner p = getPlane();
		try {
			int u = p.executeUpdatePassStr("insert into t2 values(20)", null);
		} catch (CheckFailError e) {
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
		p.executeUpdatePassStr("create table t1 (a1 int)", null);

		for (int i = 0; i < 12; i++) {
			p.executeUpdatePassStr("insert into t1 values (" + i + ")", null);
		}
		p.executeUpdatePassStr("create table t2 (a2 int references t1(a1))",
				null);

		for (int i = 0; i < 10; i++) {
			p.executeUpdatePassStr("insert into t2 values (" + i + ")", null);
		}
		JMiniDB.Close();

	}
}
