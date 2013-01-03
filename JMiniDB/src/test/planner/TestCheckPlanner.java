package test.planner;

import jminidb.JMiniDB;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.CheckFailError;

import planner.Planner;

public class TestCheckPlanner extends TestPlanner {

	@Test
	public void testUpdateChk1() {
		Planner p = getPlane();
		int ef = p.executeUpdatePassStr("update chk set chk.a1=5", null);
		JMiniDB.Close();
	}

	@Test
	public void testUpdateChk2() {
		Planner p = getPlane();
		try {
			int ef = p.executeUpdatePassStr("update chk set chk.a1=20", null);
		} catch (CheckFailError e) {
			return;
		} finally {
			JMiniDB.Close();
		}
		Assert.fail();

	}

	@Test
	public void testUpdateChk3() {
		Planner p = getPlane();
		int ef = p.executeUpdatePassStr("update chk2 set chk2.a1=5", null);
		JMiniDB.Close();

		p = getPlane();
		try {
			ef = p.executeUpdatePassStr("update chk2 set chk2.a1=20", null);
		} catch (CheckFailError e) {
			return;
		} finally {
			JMiniDB.Close();
		}
		Assert.fail();

	}

	@Test
	public void testInsertChk1() {
		Planner p = getPlane();
		for (int i = 0; i < 10; i++) {
			p.executeUpdatePassStr("insert into chk values (" + i + ")", null);
		}
		JMiniDB.Close();

		p = getPlane();
		for (int i = 0; i < 10; i++) {
			try {
				p.executeUpdatePassStr("insert into chk values (" + (i + 20)
						+ ")", null);
			} catch (CheckFailError e) {
				continue;
			}
			Assert.fail();
		}
		JMiniDB.Close();

	}

	@Test
	public void testInsertChk2() {
		Planner p = getPlane();
		for (int i = 0; i < 10; i++) {
			p.executeUpdatePassStr("insert into chk2 values (" + i + ")", null);
		}
		JMiniDB.Close();

		p = getPlane();
		for (int i = 0; i < 10; i++) {
			try {
				p.executeUpdatePassStr("insert into chk2 values (" + (i + 20)
						+ ")", null);
			} catch (CheckFailError e) {
				continue;
			}
			Assert.fail();
		}
		JMiniDB.Close();

	}

	@BeforeClass
	public static void clean() {
		TestPlanner.clean();
		Planner p = getPlane();
		p.executeUpdatePassStr("create table chk (a1 int check (chk.a1<=10) )",
				null);

		for (int i = 0; i < 10; i++) {
			p.executeUpdatePassStr("insert into chk values (" + i + ")", null);
		}

		p.executeUpdatePassStr(
				"create table chk2 (a1 int,check (chk2.a1<=10) )", null);

		for (int i = 0; i < 10; i++) {
			p.executeUpdatePassStr("insert into chk2 values (" + i + ")", null);
		}
		JMiniDB.Close();

	}
}
