package test.planner;

import jminidb.JMiniDB;

import org.junit.BeforeClass;
import org.junit.Test;

import planner.Planner;

public class TestIndexPlanner extends TestPlanner {
	@Test
	public void testCreateIndex() {
		Planner p = getPlane();
		p.executeUpdatePassStr("create index idx on t (a1)", null);
		JMiniDB.Close();
	}

	@Test
	public void testDropIndex() {
		Planner p = getPlane();
		p.executeUpdatePassStr("drop index idx on t", null);
		JMiniDB.Close();
	}

	@BeforeClass
	public static void clean() {
		TestPlanner.clean();
		Planner p = getPlane();
		p.executeUpdatePassStr("create table t (a1 int, a2 int)", null);
		JMiniDB.Close();
	}

}