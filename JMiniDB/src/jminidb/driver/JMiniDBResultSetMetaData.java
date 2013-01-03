package jminidb.driver;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import common.JQueryResult;
import common.Types;

public class JMiniDBResultSetMetaData implements ResultSetMetaData {

	private JQueryResult rst;

	public JMiniDBResultSetMetaData(JQueryResult rst) {
		this.rst = rst;
	}

	@Override
	public String getCatalogName(int arg0) throws SQLException {
		return rst.fds[arg0 - 1];

	}

	@Override
	public String getColumnClassName(int arg0) throws SQLException {
		switch (rst.type[arg0 - 1]) {
		case Types.INT:
			return "int";
		case Types.FLOAT:
			return "double";
		case Types.STRING:
			return "string";

		}
		return "";

	}

	@Override
	public int getColumnCount() throws SQLException {
		return rst.fds.length;

	}

	@Override
	public int getColumnDisplaySize(int arg0) throws SQLException {
		return rst.length[arg0 - 1];

	}

	@Override
	public String getColumnLabel(int arg0) throws SQLException {
		return getColumnName(arg0);
	}

	@Override
	public String getColumnName(int arg0) throws SQLException {
		return rst.fds[arg0 - 1];
	}

	@Override
	public int getColumnType(int arg0) throws SQLException {
		switch (rst.type[arg0 - 1]) {
		case Types.INT:
			return java.sql.Types.INTEGER;
		case Types.FLOAT:
			return java.sql.Types.DOUBLE;
		case Types.STRING:
			return java.sql.Types.CHAR;

		}
		return 0;

	}

	@Override
	public String getColumnTypeName(int arg0) throws SQLException {
		throw new UnimplementException();
	}

	@Override
	public int getPrecision(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getScale(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getSchemaName(int arg0) throws SQLException {
		throw new UnimplementException();
	}

	@Override
	public String getTableName(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isAutoIncrement(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isCaseSensitive(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isCurrency(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isDefinitelyWritable(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int isNullable(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isReadOnly(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isSearchable(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isSigned(int arg0) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isWritable(int arg0) throws SQLException {
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
