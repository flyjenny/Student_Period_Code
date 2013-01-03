package test.record;

import java.io.File;

import jminidb.JMiniDB;

import metadata.Schema;
import metadata.TableInfo;

import org.junit.Assert;
import org.junit.Test;

import record.RecordFile;

public class TestInsert {
	@Test
	public void testIns() {

		File f = new File("D:\\dbtest\\ins.tbl");
		if (f.exists())
			f.delete();
		JMiniDB.initTest();
		Schema sc = new Schema();
		sc.addIntField("b");
		sc.addStringField("tp", 80);
		TableInfo ti = new TableInfo("ins", sc);
		RecordFile rf = new RecordFile(ti, null);
		rf.beforeFirst();
		for (int i = 0; i < 10; i++) {
			rf.insert();
			rf.setInt("b", i);
		}
		rf.beforeFirst();
		for (int i = 0; i < 10; i++) {
			rf.next();
			int b = rf.getInt("b");
			Assert.assertEquals(i, b);
		}

		JMiniDB.Close();

	}

}
