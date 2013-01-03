package test.planner;

import io.Page;

import jminidb.JMiniDB;

import metadata.Schema;
import metadata.TableInfo;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import common.Types;

import planner.Planner;
import query.plan.Plan;
import query.scan.Scan;
import record.RecordFile;

public class TestSimplePlanner extends TestPlanner {

	@Test
	public void testPlanCreate() {
		Planner p = getPlane();
		// p.createQueryPlan("test/test.sql", null);
		p.executeUpdatePassStr("create table ab (cd int,ef char(20))", null);
		JMiniDB.Close();

		JMiniDB.initTest();
		Schema fcatSchema = new Schema();
		int MAX_NAME = 32;
		fcatSchema.addStringField("tblname", MAX_NAME);
		fcatSchema.addStringField("fldname", MAX_NAME);
		fcatSchema.addIntField("type");
		fcatSchema.addIntField("length");
		fcatSchema.addIntField("offset");
		TableInfo fcatInfo = new TableInfo("fldcat", fcatSchema);
		RecordFile rf = new RecordFile(fcatInfo, null);
		while (rf.next()) {

			if (rf.getString("tblname").equals("ab")) {
				Assert.assertEquals("ab", rf.getString("tblname"));
				Assert.assertEquals(".cd", rf.getString("fldname"));
				Assert.assertEquals(Types.INT, rf.getInt("type"));
				Assert.assertEquals(Page.INT_SIZE, rf.getInt("length"));
				Assert.assertEquals(0, rf.getInt("offset"));
				break;
			}
		}
		if (rf.next()) {

			Assert.assertEquals("ab", rf.getString("tblname"));
			Assert.assertEquals(".ef", rf.getString("fldname"));
			Assert.assertEquals(Types.STRING, rf.getInt("type"));
			Assert.assertEquals(Page.STR_SIZE(20), rf.getInt("length"));
			Assert.assertEquals(4, rf.getInt("offset"));
		}
		if (rf.next())
			Assert.fail("two record found.");

		JMiniDB.Close();

	}

	@Test
	public void testInsert() {
		Planner p = getPlane();

		// int er= p.executeUpdatePassFile("test/test1.sql", null);
		for (int i = 0; i < 100; i++) {
			int er = p.executeUpdatePassStr("insert into ab values (" + i
					+ ",'i=" + i + "')", null);
			Assert.assertEquals(1, er);
		}

		JMiniDB.Close();

	}

	@Test
	public void testSelect() {
		Planner p = getPlane();
		Plan pp = p.createQueryPlanPassStr(
				"select ab.cd from ab where ab.cd>=2 and ab.cd<=5", null);
		Scan s = pp.open(null);
		int i = 0;
		while (s.next()) {
			int cd = s.getInt("ab.cd");
			i++;
			Assert.assertTrue(cd >= 2 && cd <= 5);
			// Assert.assertEquals("i="+i, s.getInt("ab.ef"));
		}
		Assert.assertEquals(i, 4);

		JMiniDB.Close();
	}

	@Test
	public void testSelectDef() {
		Planner p = getPlane();
		Plan pp = p.createQueryPlanPassStr(
				"select ab.cd from ab where cd>=2 and cd<=5", null);
		Scan s = pp.open(null);
		int i = 0;
		while (s.next()) {
			int cd = s.getInt("ab.cd");
			i++;
			Assert.assertTrue(cd >= 2 && cd <= 5);
			// Assert.assertEquals("i="+i, s.getInt("ab.ef"));
		}
		Assert.assertEquals(i, 4);

		JMiniDB.Close();
	}

	@Test
	public void testSelectDef2() {
		Planner p = getPlane();
		Plan pp = p.createQueryPlanPassStr(
				"select cd from ab where cd>=2 and cd<=5", null);
		Scan s = pp.open(null);
		int i = 0;
		while (s.next()) {
			int cd = s.getInt("cd");
			i++;
			Assert.assertTrue(cd >= 2 && cd <= 5);
			// Assert.assertEquals("i="+i, s.getInt("ab.ef"));
		}
		Assert.assertEquals(i, 4);

		JMiniDB.Close();
	}

	@Test
	public void testSelectDef3() {
		Planner p = getPlane();
		Plan pp = p.createQueryPlanPassStr(
				"select cd as ef from ab where cd>=2 and cd<=5", null);
		Scan s = pp.open(null);
		int i = 0;
		while (s.next()) {
			int cd = s.getInt("ef");
			i++;
			Assert.assertTrue(cd >= 2 && cd <= 5);
			// Assert.assertEquals("i="+i, s.getInt("ab.ef"));
		}
		Assert.assertEquals(i, 4);

		JMiniDB.Close();
	}

	@Test
	public void testSelectAll() {
		Planner p = getPlane();
		Plan pp = p.createQueryPlanPassStr("select * from ab", null);
		Scan s = pp.open(null);
		int i = 0;
		while (s.next()) {
			int cd = s.getInt("ab.cd");

			Assert.assertEquals(i, cd);
			Assert.assertEquals("i=" + i, s.getString("ab.ef"));
			i++;
		}
		Assert.assertEquals(i, 100);

		JMiniDB.Close();
	}

	@Test
	public void testSelectString() {
		Planner p = getPlane();
		Plan pp = p.createQueryPlanPassStr(
				"select ab.cd from ab where ab.ef>='i=10' and ab.ef<='i=15'",
				null);
		Scan s = pp.open(null);
		int i = 0;
		while (s.next()) {
			int cd = s.getInt("ab.cd");
			i++;
			Assert.assertTrue(cd >= 10 && cd <= 15);
			// Assert.assertEquals("i="+i, s.getInt("ab.ef"));
		}
		Assert.assertEquals(i, 6);
		JMiniDB.Close();
	}

	@Test
	public void testDeleteString() {
		Planner p = getPlane();
		int ef = p.executeUpdatePassStr("delete from ab where ab.cd>10", null);
		Assert.assertEquals(ef, 89);
		Plan pp = p.createQueryPlanPassStr("select ab.cd from ab", null);

		Scan s = pp.open(null);
		int i = 0;
		while (s.next()) {
			int cd = s.getInt("ab.cd");
			i++;
			Assert.assertTrue("more than 10", cd <= 10);
			// Assert.assertEquals("i="+i, s.getInt("ab.ef"));
		}
		Assert.assertEquals(i, 11);
		JMiniDB.Close();
	}

	@Test
	public void testUpdate() {
		Planner p = getPlane();
		int ef = p.executeUpdatePassStr(
				"update ab set ab.cd=8 where ab.cd<=10 and ab.cd>1", null);
		Assert.assertEquals(ef, 9);
		Plan pp = p.createQueryPlanPassStr("select ab.cd from ab", null);

		Scan s = pp.open(null);
		int i = 0;
		int j = 0;
		while (s.next()) {
			int cd = s.getInt("ab.cd");

			if (cd == 8)
				i++;
			else if (cd <= 1)
				j++;
			else
				Assert.fail();

		}
		Assert.assertEquals(i, 9);
		Assert.assertEquals(j, 2);
		JMiniDB.Close();
	}

	@BeforeClass
	public static void clean() {
		TestPlanner.clean();

	}

}
