package jminidb.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import jminidb.driver.JMiniDBDriver;

public class CmdLineClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		try {
			DriverManager.registerDriver(new JMiniDBDriver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection c;
		try {
			c = DriverManager
					.getConnection("jdbc:jminidb://127.0.0.1:12345/test");
			Statement s = c.createStatement();

			do {
				System.out.print("sql>");
				try {
					line = br.readLine().toLowerCase().trim();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
				if (line.equals("quit")) {
					break;
				} else if (line.isEmpty()) {
					continue;
				} else if (line.startsWith("select")) {

					try {
						ResultSet r = s.executeQuery(line);
						printRst(r);
						r.close();
					} catch (SQLException e) {
						System.out.println("error:" + e.toString());
					}
				} else {
					try {
						int ef = s.executeUpdate(line);
						System.out.println("effect " + ef + "rows");
					} catch (SQLException e) {
						System.out.println("error:" + e.toString());
					}
				}

			} while (true);
			s.close();
			c.close();
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

	}

	private static void printRst(ResultSet r) {
		ResultSetMetaData m;
		try {
			m = r.getMetaData();
			for (int i = 1; i <= m.getColumnCount(); i++) {
				System.out.print(m.getColumnName(i) + "\t\t|");
			}
			System.out.println();
			while (r.next()) {
				for (int i = 1; i <= m.getColumnCount(); i++) {
					System.out.print(r.getObject(i) + "\t\t|");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
