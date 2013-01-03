package test.io;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import io.Block;
import io.DBFiles;
import io.Page;
import jminidb.JMiniDB;

public class IOTest {

	@Test
	public void testDB() {
		JMiniDB.initTest();
		DBFiles dbf = JMiniDB.getDBFile();
		int fs = dbf.fileSize("junk");
		Block b;

		Page p = new Page();
		p.setInt(2, 256);
		Page p2 = new Page();
		p2.setString(20, "hello");
		b = p2.append("junk");
		b = p2.append("junk");

		Page p3 = new Page();
		p3.read(b);
		String s = p3.getString(20);
		// System.out.println("Block " + b.number() + " contains " + s);

		Assert.assertEquals("hello", s);
		try {
			dbf.Close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
