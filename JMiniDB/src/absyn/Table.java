package absyn;

public class Table extends Absyn {
	Query subquery;
	String tblName;
	String alias;

	public Table(int p, String tn) {
		pos = p;
		subquery = null;
		tblName = tn;
		alias = tn;
	}

	public Table(int p, String tn, String a) {
		pos = p;
		subquery = null;
		tblName = tn;
		alias = a;
	}

	public Table(int p, Query q, String a) {
		pos = p;
		subquery = q;
		tblName = "";
		alias = a;
	}

	/**
	 * @return the subquery
	 */
	public Query getSubquery() {
		return subquery;
	}

	/**
	 * @param subquery
	 *            the subquery to set
	 */
	public void setSubquery(Query subquery) {
		this.subquery = subquery;
	}

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
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
