package merge;

import java.sql.Array;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class Main {

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String dburl = DbConfig.getValue("db_baseurl")
				+ DbConfig.getValue("db_schema")
				+ DbConfig.getValue("character_set");
		String dbuser = DbConfig.getValue("db_user");
		String password = DbConfig.getValue("db_pwd");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection(dburl, dbuser, password);
		Statement statement = conn.createStatement();
		/*
		 * String command = "insert into " + DbConfig.getValue("table1") +
		 * " select * from " + DbConfig.getValue("table2") + " where " +
		 * DbConfig.getValue("table2") + ".id not in (select id from " +
		 * DbConfig.getValue("table1") + ")";
		 */
		String command = "select text from weiboevent.event10 where weiboevent.event10.text like '%<%'";
		System.out.print(command);
		// statement.execute(command);
		ResultSet rs = statement.executeQuery(command);
		Array ar = rs.getArray(0);
		System.out.println((String) ar.getArray(0, 0));
	}

}
