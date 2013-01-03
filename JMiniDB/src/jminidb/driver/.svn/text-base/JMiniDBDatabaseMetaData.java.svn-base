package jminidb.driver;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import common.JExceptionResult;
import common.JQueryResult;
import common.JResultSet;
import common.JResultSetFactory;
import common.Types;

import jminidb.server.DBServerPrx;

public class JMiniDBDatabaseMetaData implements DatabaseMetaData {

	DBServerPrx db;

	public JMiniDBDatabaseMetaData(DBServerPrx db) {
		this.db = db;
	}

	@Override
	public boolean allProceduresAreCallable() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean allTablesAreSelectable() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean deletesAreDetected(int type) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getAttributes(String catalog, String schemaPattern,
			String typeNamePattern, String attributeNamePattern)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getBestRowIdentifier(String catalog, String schema,
			String table, int scope, boolean nullable) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getCatalogSeparator() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getCatalogTerm() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getCatalogs() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getClientInfoProperties() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getColumnPrivileges(String catalog, String schema,
			String table, String columnNamePattern) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getColumns(String catalog, String schemaPattern,
			String tableNamePattern, String columnNamePattern)
			throws SQLException {
		JResultSet rst = db.getColumns(tableNamePattern);
		if (null == rst)
			return null;
		else if (rst instanceof JExceptionResult) {
			JExceptionResult e = (JExceptionResult) rst;
			throw new SQLException(e.exception.toString());
		}
		if (rst instanceof JQueryResult)
			return new JMiniDBResultSet((JQueryResult) rst);
		else
			throw new SQLException("error sql type.");
	}

	@Override
	public Connection getConnection() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getCrossReference(String parentCatalog,
			String parentSchema, String parentTable, String foreignCatalog,
			String foreignSchema, String foreignTable) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getDatabaseMajorVersion() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getDatabaseMinorVersion() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getDatabaseProductName() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getDatabaseProductVersion() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getDefaultTransactionIsolation() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getDriverMajorVersion() {
		throw new UnimplementException();

	}

	@Override
	public int getDriverMinorVersion() {
		throw new UnimplementException();

	}

	@Override
	public String getDriverName() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getDriverVersion() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getExportedKeys(String catalog, String schema, String table)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getExtraNameCharacters() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getFunctionColumns(String catalog, String schemaPattern,
			String functionNamePattern, String columnNamePattern)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getFunctions(String catalog, String schemaPattern,
			String functionNamePattern) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getIdentifierQuoteString() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getImportedKeys(String catalog, String schema, String table)
			throws SQLException {
		JResultSet rst = db.getImportedKeys(table);
		if (null == rst)
			return null;
		else if (rst instanceof JExceptionResult) {
			JExceptionResult e = (JExceptionResult) rst;
			throw new SQLException(e.exception.toString());
		}
		if (rst instanceof JQueryResult)
			return new JMiniDBResultSet((JQueryResult) rst);
		else {
			return new JMiniDBResultSet();
		}
	}

	@Override
	public ResultSet getIndexInfo(String catalog, String schema, String table,
			boolean unique, boolean approximate) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getJDBCMajorVersion() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getJDBCMinorVersion() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxBinaryLiteralLength() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxCatalogNameLength() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxCharLiteralLength() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxColumnNameLength() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxColumnsInGroupBy() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxColumnsInIndex() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxColumnsInOrderBy() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxColumnsInSelect() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxColumnsInTable() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxConnections() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxCursorNameLength() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxIndexLength() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxProcedureNameLength() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxRowSize() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxSchemaNameLength() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxStatementLength() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxStatements() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxTableNameLength() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxTablesInSelect() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getMaxUserNameLength() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getNumericFunctions() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getPrimaryKeys(String catalog, String schema, String table)
			throws SQLException {
		return new JMiniDBResultSet();

	}

	@Override
	public ResultSet getProcedureColumns(String catalog, String schemaPattern,
			String procedureNamePattern, String columnNamePattern)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getProcedureTerm() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getProcedures(String catalog, String schemaPattern,
			String procedureNamePattern) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public RowIdLifetime getRowIdLifetime() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getSQLKeywords() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public int getSQLStateType() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getSchemaTerm() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getSchemas() throws SQLException {
		String fld[] = new String[1];
		fld[0] = "TABLE_SCHEM";

		List<Object[]> rs = new LinkedList<Object[]>();
		int type[] = new int[1];
		type[0] = Types.STRING;
		Object r[] = new Object[1];
		r[0] = "default";
		rs.add(r);

		JResultSet rt = JResultSetFactory.create(fld, type, rs);
		return new JMiniDBResultSet((JQueryResult) rt);
	}

	@Override
	public ResultSet getSchemas(String catalog, String schemaPattern)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getSearchStringEscape() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getStringFunctions() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getSuperTables(String catalog, String schemaPattern,
			String tableNamePattern) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getSuperTypes(String catalog, String schemaPattern,
			String typeNamePattern) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getSystemFunctions() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getTablePrivileges(String catalog, String schemaPattern,
			String tableNamePattern) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getTableTypes() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getTables(String catalog, String schemaPattern,
			String tableNamePattern, String[] types) throws SQLException {
		JResultSet rst = db.getTables(tableNamePattern);
		if (null == rst)
			return null;
		else if (rst instanceof JExceptionResult) {
			JExceptionResult e = (JExceptionResult) rst;
			throw new SQLException(e.exception.toString());
		}
		if (rst instanceof JQueryResult)
			return new JMiniDBResultSet((JQueryResult) rst);
		else
			throw new SQLException("error sql type.");
	}

	@Override
	public String getTimeDateFunctions() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getTypeInfo() throws SQLException {
		String fld[] = new String[2];
		fld[0] = "DATA_TYPE";
		fld[1] = "LOCAL_TYPE_NAME";

		List<Object[]> rs = new LinkedList<Object[]>();
		int type[] = new int[2];
		type[0] = Types.INT;
		type[1] = Types.STRING;

		Object r[];
		r = new Object[2];
		r[0] = java.sql.Types.INTEGER;
		r[1] = "INTEGER";
		rs.add(r);

		r = new Object[2];
		r[0] = java.sql.Types.CHAR;
		r[1] = "CHAR";
		rs.add(r);

		r = new Object[2];
		r[0] = java.sql.Types.DOUBLE;
		r[1] = "FLOAT";
		rs.add(r);
		JResultSet rt = JResultSetFactory.create(fld, type, rs);

		return new JMiniDBResultSet((JQueryResult) rt);
	}

	@Override
	public ResultSet getUDTs(String catalog, String schemaPattern,
			String typeNamePattern, int[] types) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getURL() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public String getUserName() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public ResultSet getVersionColumns(String catalog, String schema,
			String table) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean insertsAreDetected(int type) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isCatalogAtStart() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean isReadOnly() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean locatorsUpdateCopy() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean nullPlusNonNullIsNull() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean nullsAreSortedAtEnd() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean nullsAreSortedAtStart() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean nullsAreSortedHigh() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean nullsAreSortedLow() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean othersDeletesAreVisible(int type) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean othersInsertsAreVisible(int type) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean othersUpdatesAreVisible(int type) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean ownDeletesAreVisible(int type) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean ownInsertsAreVisible(int type) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean ownUpdatesAreVisible(int type) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean storesLowerCaseIdentifiers() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean storesMixedCaseIdentifiers() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean storesUpperCaseIdentifiers() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsANSI92EntryLevelSQL() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsANSI92FullSQL() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsANSI92IntermediateSQL() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsAlterTableWithAddColumn() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsAlterTableWithDropColumn() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsBatchUpdates() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsCatalogsInDataManipulation() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsCatalogsInProcedureCalls() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsCatalogsInTableDefinitions() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsColumnAliasing() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsConvert() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsConvert(int fromType, int toType)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsCoreSQLGrammar() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsCorrelatedSubqueries() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsDataDefinitionAndDataManipulationTransactions()
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsDataManipulationTransactionsOnly()
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsDifferentTableCorrelationNames() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsExpressionsInOrderBy() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsExtendedSQLGrammar() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsFullOuterJoins() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsGetGeneratedKeys() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsGroupBy() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsGroupByBeyondSelect() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsGroupByUnrelated() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsIntegrityEnhancementFacility() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsLikeEscapeClause() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsLimitedOuterJoins() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsMinimumSQLGrammar() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsMixedCaseIdentifiers() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsMultipleOpenResults() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsMultipleResultSets() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsMultipleTransactions() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsNamedParameters() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsNonNullableColumns() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsOrderByUnrelated() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsOuterJoins() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsPositionedDelete() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsPositionedUpdate() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsResultSetConcurrency(int type, int concurrency)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsResultSetHoldability(int holdability)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsResultSetType(int type) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsSavepoints() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsSchemasInDataManipulation() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsSchemasInIndexDefinitions() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsSchemasInProcedureCalls() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsSchemasInTableDefinitions() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsSelectForUpdate() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsStatementPooling() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsStoredProcedures() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsSubqueriesInComparisons() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsSubqueriesInExists() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsSubqueriesInIns() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsSubqueriesInQuantifieds() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsTableCorrelationNames() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsTransactionIsolationLevel(int level)
			throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsTransactions() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsUnion() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean supportsUnionAll() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean updatesAreDetected(int type) throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean usesLocalFilePerTable() throws SQLException {
		throw new UnimplementException();

	}

	@Override
	public boolean usesLocalFiles() throws SQLException {
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
