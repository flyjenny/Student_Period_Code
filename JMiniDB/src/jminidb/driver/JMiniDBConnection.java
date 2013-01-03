package jminidb.driver;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

import jminidb.server.DBServerPrx;
import jminidb.server.DBServerPrxHelper;

import Ice.Communicator;

public class JMiniDBConnection implements Connection {

	private Communicator ic;

	private DBServerPrx db;

	JMiniDBConnection(Communicator ic, String host, String port, String dbname) {

		this.ic = ic;
		Ice.ObjectPrx base = ic.stringToProxy("jminidb:default -h " + host
				+ " -p " + port);
		db = DBServerPrxHelper.checkedCast(base);
		if (base == null) {
			throw new RuntimeException("Invalid proxy");
		}
		db.connection(dbname);

	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void close() throws SQLException {

		if (ic != null) {
			// Clean up
			//
			db.close();
			try {
				ic.destroy();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		ic = null;
		db = null;

	}

	@Override
	public void commit() throws SQLException {
		//throw new UnimplementException();

	}

	@Override
	public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Blob createBlob() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Clob createClob() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public NClob createNClob() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Statement createStatement() throws SQLException {
		return new JMiniDBStatement(db);
	}

	@Override
	public Statement createStatement(int arg0, int arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Statement createStatement(int arg0, int arg1, int arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getCatalog() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Properties getClientInfo() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getClientInfo(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getHoldability() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return new JMiniDBDatabaseMetaData(db);

	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isReadOnly() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isValid(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String nativeSQL(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public CallableStatement prepareCall(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public CallableStatement prepareCall(String arg0, int arg1, int arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public CallableStatement prepareCall(String arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return new JMiniDBPrearedStatement(db, sql);

	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int[] arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public PreparedStatement prepareStatement(String arg0, String[] arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void releaseSavepoint(Savepoint arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void rollback() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void rollback(Savepoint arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void setAutoCommit(boolean arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void setCatalog(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void setClientInfo(Properties arg0) throws SQLClientInfoException {
		throw new UnimplementException();

	}

	@Override
	public void setClientInfo(String arg0, String arg1)
			throws SQLClientInfoException {
		throw new UnimplementException();

	}

	@Override
	public void setHoldability(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void setReadOnly(boolean arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Savepoint setSavepoint(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void setTransactionIsolation(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		throw new UnimplementException();

	}

}
