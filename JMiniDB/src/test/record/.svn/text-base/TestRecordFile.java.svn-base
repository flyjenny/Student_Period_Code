package test.record;

import org.junit.Assert;
import org.junit.Test;

import jminidb.JMiniDB;
import record.Record;
import record.RecordFile;
import metadata.Schema;
import metadata.TableInfo;

public class TestRecordFile {

	@Test
	public void testCreate() {
		RecordFile rf = initTlb();

		JMiniDB.Close();
	}

	/**
	 * @return
	 */
	private RecordFile initTlb() {
		JMiniDB.initTest();
		Schema sc = new Schema();
		sc.addStringField("a", 10);
		sc.addIntField("b");
		sc.addStringField("c", 10);
		TableInfo ti = new TableInfo("trf", sc);
		RecordFile rf = new RecordFile(ti, null);
		return rf;
	}

	Record rd;

	@Test
	public void testInsert() {

		RecordFile rf = initTlb();
		rf.beforeFirst();
		if (!rf.next()) {
			rf.insert();
		}
		rf.setInt("b", 123);
		rf.setString("c", "456x");
		rf.setString("a", "789");
		rd = rf.currentRecord();
		JMiniDB.Close();
	}

	@Test
	public void testRead() {
		RecordFile rf = initTlb();
		rf.beforeFirst();
		rf.next();
		int b;
		String a, c;
		a = rf.getString("a");
		b = rf.getInt("b");
		c = rf.getString("c");
		JMiniDB.Close();
		Assert.assertEquals(c, "456x");
		Assert.assertEquals(b, 123);
		Assert.assertEquals(a, "789");
	}
}
