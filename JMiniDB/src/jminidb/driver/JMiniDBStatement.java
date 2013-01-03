package jminidb.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import common.JEffectResult;
import common.JExceptionResult;
import common.JQueryResult;
import common.JResultSet;

import jminidb.server.DBServerPrx;

public class JMiniDBStatement implements Statement {

	private DBServerPrx db;
	private int statementId;
	private JResultSet rst = null;

	public JMiniDBStatement(DBServerPrx db) {
		this.db = db;
		this.statementId = db.createStatement();
		if (statementId == 0)
			throw new RuntimeException("create Statement Fail");

	}

	@Override
	public void addBatch(String arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancel() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void clearBatch() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void close() throws SQLException {
		db.closeStatement(statementId);
		db = null;

	}

	@Override
	public boolean execute(String sql) throws SQLException {
		if (sql.toLowerCase().trim().startsWith("select")) {
			return null != executeQuery(sql);
		} else {
			executeUpdate(sql);
			return false;
		}

	}

	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int[] executeBatch() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		rst = db.executeQuery(statementId, sql);
		return getResultSet();
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		rst = db.executeUpdate(statementId, sql);
		return getUpdateCount();
	}

	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Connection getConnection() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxRows() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean getMoreResults() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getQueryTimeout() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		if (null == rst)
			return null;
		else if (rst instanceof JExceptionResult) {
			JExceptionResult e = (JExceptionResult) rst;
			// e.exception.printStackTrace();
			throw new SQLException(e.exception.toString());
		}
		if (rst instanceof JQueryResult)
			return new JMiniDBResultSet((JQueryResult) rst);
		else
			throw new SQLException("error sql type.");
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getResultSetType() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getUpdateCount() throws SQLException {
		if (rst instanceof JExceptionResult) {
			JExceptionResult e = (JExceptionResult) rst;
			throw new SQLException(e.exception.toString());
		} else if (rst instanceof JEffectResult) {
			JEffectResult e = (JEffectResult) rst;
			return e.effectRow;
		} else
			throw new SQLException("error sql type.");
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
	public boolean isPoolable() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void setCursorName(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void setMaxRows(int arg0) throws SQLException {
		// throw new UnimplementException();

	}

	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void setQueryTimeout(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnimplementException();

	}

}
