package test.driver;

import java.io.File;
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

public class TestAll {
	@Test
	public void testAll() {
		try {
			DBServerPrx db = new DBServerMock();
			db.connection("test");

			/*
			 * Connection c = DriverManager
			 * .getConnection("jdbc:jminidb://127.0.0.1:12345/test");
			 */
			Statement s = new JMiniDBStatement(db);

			ResultSet rs;
			s
					.execute("create table class (class int primary key, stunum int, check (class.stunum = select count(student.class) from student where student.class=class.class))");
			s
					.execute("create table student (class int references class(class), name char(30))");
			s.execute("create table score (name char(30), grade int)");

			s.execute("insert into class values (1, 0)");
			s.execute("insert into class values (2, 0)");
			// s.execute("insert into class values (2, 0)");
			s.execute("insert into class values (3, 0)");

			s.execute("insert into student values (3, 'fisher')");
			s.execute("insert into student values (3, 'green')");
			s.execute("insert into student values (1, 'alex')");
			s.execute("insert into student values (1, 'ben')");
			s.execute("insert into student values (2, 'cain')");
			s.execute("insert into student values (2, 'dante')");
			s.execute("insert into student values (2, 'elaine')");
			// s.execute("insert into student values (4, 'hate')");

			s.execute("insert into score values ('alex', 93)");
			s.execute("insert into score values ('cain', 96)");
			s.execute("insert into score values ('fisher', 87)");
			s.execute("insert into score values ('zoey', 100)");

			/*
			 * ResultSet
			 * r=s.executeQuery("select count(*) from student where student.class=1"
			 * ); r.next(); System.out.println("count=="+r.getInt(1));
			 */
			s.execute("update class set class.stunum=2 where class.class=1");
			s.execute("update class set class.stunum=3 where class.class=2");
			s.execute("delete from student where student.name='green'");
			s.execute("update class set class.stunum=1 where class.class=3");

			s
					.execute("select student.class, count(student.name) from student group by student.class having student.class+1>2");
			s.execute("select sum(class.stunum) from class");
			s.execute("select avg(score.grade) from score");
			s.execute("select name from student order by name");
			s.execute("select class from student order by name desc");
			s
					.execute("select student.name, grade from student, score where student.name = score.name and grade>90");
			s
					.execute("select name from score where grade >= all (select score.grade from score)");

			s
					.execute("create view goodstu as select score.name from score where score.grade>90");
			s
					.execute("select goodstu.name from goodstu where goodstu.name in (select student.name from student)");
			s.execute("drop view goodstu");

			s.execute("drop table score");
			s.execute("create index stuidx on student(name)");
			s.execute("drop index stuidx on student");

			rs = s.executeQuery("select * from a");

			s.close();
			// c.close();
			db.close();
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

	}

	public static Thread svr;

	@AfterClass
	public static void after() {

	}
}
