package absyn;

public class InsertClause extends Update {
	/**
	 * @return the tblName
	 */
	public String getTblName() {
		return tblName;
	}

	/**
	 * @param tblName
	 *            the tblName to set
	 */
	public void setTblName(String tblName) {
		this.tblName = tblName;
	}

	/**
	 * @return the values
	 */
	public ValueList getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(ValueList values) {
		this.values = values;
	}

	/**
	 * @return the query
	 */
	public Query getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            the query to set
	 */
	public void setQuery(Query query) {
		this.query = query;
	}

	String tblName;
	ValueList values;
	Query query;

	public InsertClause(int p, String tn, ValueList vl) {
		pos = p;
		tblName = tn;
		values = vl;
		query = null;
	}

	public InsertClause(int p, String tn, Query q) {
		pos = p;
		tblName = tn;
		values = null;
		query = q;
	}
}
