package jminidb.driver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

public class JMiniDBDriver implements Driver {

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		System.out.println("dbg-1");
		throw new UnimplementException();
	}

	@Override
	public Connection connect(String url, Properties arg1) throws SQLException {
		System.out.println("JDBC Driver Connection create");
		if (url.startsWith("jdbc:jminidb://")) {

			url = url.substring("jdbc:jminidb://".length());
			String host;
			String port;
			String db;
			try {

				String t[] = url.split(":");
				host = t[0];
				url = t[1];
				t = url.split("/");
				port = t[0];
				db = t[1];

			} catch (RuntimeException e) {
				throw new IllegalArgumentException("url");
			}

			int status = 0;
			Ice.Communicator ic = null;
			try {
				ic = Ice.Util.initialize(new String[0]);
				try {
					return new JMiniDBConnection(ic, host, port, db);
				} catch (RuntimeException e) {
					if (ic != null) {
						try {
							ic.destroy();
						} catch (Exception e2) {
							System.err.println(e2.getMessage());
						}
					}
					throw e;
				}

			} catch (Ice.LocalException e) {
				// e.printStackTrace();
				status = 1;
				throw new SQLException(e.toString());
			} catch (Exception e) {
				// System.err.println(e.getMessage());
				status = 1;
				throw new SQLException(e.toString());
			}
		} else
			return null;

	}

	@Override
	public int getMajorVersion() {
		return 1;
	}

	@Override
	public int getMinorVersion() {
		return 1;

	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String arg0, Properties arg1)
			throws SQLException {
		return null;

	}

	@Override
	public boolean jdbcCompliant() {
		return false;

	}

}
