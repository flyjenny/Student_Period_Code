package jminidb.driver;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import common.JQueryResult;

public class JMiniDBResultSet implements ResultSet {

	private JQueryResult rst;
	private ResultSetMetaData metaData;
	private int cur;
	private Object[] curR;
	private Map<String, Integer> fmap;

	public JMiniDBResultSet(JQueryResult rst) {
		this.rst = rst;
		metaData = new JMiniDBResultSetMetaData(rst);
		cur = -1;
		curR = null;
		fmap = new HashMap<String, Integer>();
		for (int i = 0; i < rst.fds.length; i++)
			fmap.put(rst.fds[i], i);
	}

	public JMiniDBResultSet() {
		cur = -1;
		rst = null;

	}

	private int getRowIndex(String n) {
		Integer i = fmap.get(n);
		return i == null ? -1 : i;

	}

	@Override
	public boolean absolute(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void afterLast() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void beforeFirst() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void close() throws SQLException {
		rst = null;
		metaData = null;
		curR = null;
	}

	@Override
	public void deleteRow() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int findColumn(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean first() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Array getArray(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Array getArray(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public InputStream getAsciiStream(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public InputStream getAsciiStream(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public BigDecimal getBigDecimal(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public BigDecimal getBigDecimal(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public InputStream getBinaryStream(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public InputStream getBinaryStream(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Blob getBlob(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Blob getBlob(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean getBoolean(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean getBoolean(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public byte getByte(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public byte getByte(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public byte[] getBytes(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public byte[] getBytes(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Reader getCharacterStream(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Reader getCharacterStream(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Clob getClob(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Clob getClob(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getConcurrency() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getCursorName() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Date getDate(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Date getDate(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Date getDate(int arg0, Calendar arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Date getDate(String arg0, Calendar arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public double getDouble(int i) throws SQLException {
		return (Double) curR[i - 1];

	}

	@Override
	public double getDouble(String n) throws SQLException {
		return (Double) curR[getRowIndex(n)];

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
	public float getFloat(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public float getFloat(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getHoldability() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getInt(int i) throws SQLException {

		return (Integer) curR[i - 1];

	}

	@Override
	public int getInt(String n) throws SQLException {
		return (Integer) curR[getRowIndex(n)];

	}

	@Override
	public long getLong(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public long getLong(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return metaData;

	}

	@Override
	public Reader getNCharacterStream(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Reader getNCharacterStream(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public NClob getNClob(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public NClob getNClob(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getNString(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getNString(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Object getObject(int i) throws SQLException {
		return curR[i - 1];

	}

	@Override
	public Object getObject(String n) throws SQLException {
		return curR[getRowIndex(n)];

	}

	@Override
	public Object getObject(int arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Object getObject(String arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Ref getRef(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Ref getRef(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getRow() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public RowId getRowId(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public RowId getRowId(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public SQLXML getSQLXML(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public SQLXML getSQLXML(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public short getShort(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public short getShort(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Statement getStatement() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getString(int i) throws SQLException {
		return curR[i - 1].toString();

	}

	@Override
	public String getString(String n) throws SQLException {
		return curR[getRowIndex(n)].toString();

	}

	@Override
	public Time getTime(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Time getTime(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Time getTime(int arg0, Calendar arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Time getTime(String arg0, Calendar arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Timestamp getTimestamp(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Timestamp getTimestamp(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public Timestamp getTimestamp(String arg0, Calendar arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getType() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public URL getURL(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public URL getURL(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public InputStream getUnicodeStream(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public InputStream getUnicodeStream(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void insertRow() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isAfterLast() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isFirst() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isLast() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean last() throws SQLException {
		throw new UnimplementException();
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void moveToInsertRow() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean next() throws SQLException {
		if (null == rst)
			return false;
		cur++;

		if (cur < rst.count) {
			curR = rst.rows.get(cur);
		}

		return cur < rst.count;

	}

	@Override
	public boolean previous() throws SQLException {

		throw new UnimplementException();
	}

	@Override
	public void refreshRow() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean relative(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean rowDeleted() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean rowInserted() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean rowUpdated() throws SQLException {
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
	public void updateArray(int arg0, Array arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateArray(String arg0, Array arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBigDecimal(String arg0, BigDecimal arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBlob(int arg0, Blob arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBlob(String arg0, Blob arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBlob(int arg0, InputStream arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBlob(String arg0, InputStream arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBlob(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBlob(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBoolean(int arg0, boolean arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBoolean(String arg0, boolean arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateByte(int arg0, byte arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateByte(String arg0, byte arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBytes(int arg0, byte[] arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateBytes(String arg0, byte[] arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, int arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, int arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateClob(int arg0, Clob arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateClob(String arg0, Clob arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateClob(int arg0, Reader arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateClob(String arg0, Reader arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateDate(int arg0, Date arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateDate(String arg0, Date arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateDouble(int arg0, double arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateDouble(String arg0, double arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateFloat(int arg0, float arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateFloat(String arg0, float arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateInt(int arg0, int arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateInt(String arg0, int arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateLong(int arg0, long arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateLong(String arg0, long arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNClob(int arg0, NClob arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNClob(String arg0, NClob arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNClob(int arg0, Reader arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNClob(String arg0, Reader arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNString(int arg0, String arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNString(String arg0, String arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNull(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateNull(String arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateObject(int arg0, Object arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateObject(String arg0, Object arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateObject(int arg0, Object arg1, int arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateObject(String arg0, Object arg1, int arg2)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateRef(int arg0, Ref arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateRef(String arg0, Ref arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateRow() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateRowId(int arg0, RowId arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateRowId(String arg0, RowId arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateShort(int arg0, short arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateShort(String arg0, short arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateString(int arg0, String arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateString(String arg0, String arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateTime(int arg0, Time arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateTime(String arg0, Time arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public void updateTimestamp(String arg0, Timestamp arg1)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean wasNull() throws SQLException {
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
