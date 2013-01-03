package test.driver;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jminidb.driver.JMiniDBStatement;
import jminidb.server.DBServerPrx;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestStatement {
	@Test
	public void testStatement() {
		DBServerPrx db = new DBServerMock();
		db.connection("test");
		Statement s = new JMiniDBStatement(db);
		try {
			ResultSet rs = s.executeQuery("select * from a");
			int i = 0;
			while (rs.next()) {
				// System.out.println("b=" + rs.getInt("a.b"));
				Assert.assertEquals(i++, rs.getInt("a.b"));
			}
			Assert.assertEquals(i, 10);
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
	}

	@BeforeClass
	public static void clean() {

		File fs = new File("d:\\jminidb\\test");
		if (fs.exists()) {
			File fl[] = fs.listFiles();
			for (File f : fl) {
				f.delete();
			}
		}
		fs.delete();

		DBServerPrx db = new DBServerMock();
		db.connection("test");
		Statement s = new JMiniDBStatement(db);
		try {
			s.executeUpdate("create table a (b int)");
			for (int i = 0; i < 10; i++) {
				s.executeUpdate("insert into a values(" + i + ")");
			}
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
	}
}
