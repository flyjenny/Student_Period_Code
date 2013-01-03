package test.driver;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jminidb.driver.JMiniDBDriver;
import jminidb.driver.JMiniDBStatement;
import jminidb.server.DBServerPrx;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestJDBC {
	@Test
	public void testJDBC() {
		try {

			Connection c = DriverManager
					.getConnection("jdbc:jminidb://127.0.0.1:12345/test");
			Statement s = c.createStatement();

			ResultSet rs = s.executeQuery("select * from a");
			while (rs.next()) {
				// System.out.println("b=" + rs.getInt("a.b"));
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		try {
			DriverManager.registerDriver(new JMiniDBDriver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		svr = new Thread(new Runnable() {

			@Override
			public void run() {
				jminidb.server.Main.main(new String[0]);
			}
		});
		svr.start();

	}

	public static Thread svr;

	@AfterClass
	public static void after() {
		jminidb.server.Main.shutdown();
		try {
			svr.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("ok");
	}

}
