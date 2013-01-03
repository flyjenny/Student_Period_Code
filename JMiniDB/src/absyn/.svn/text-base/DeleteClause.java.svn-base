package absyn;

public class DeleteClause extends Update {
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
	 * @return the cond
	 */
	public Condition getCond() {
		return cond;
	}

	/**
	 * @param cond
	 *            the cond to set
	 */
	public void setCond(Condition cond) {
		this.cond = cond;
	}

	String tblName;
	Condition cond;

	public DeleteClause(int p, String tn) {
		pos = p;
		tblName = tn;
		cond = null;
	}

	public DeleteClause(int p, String tn, Condition c) {
		pos = p;
		tblName = tn;
		cond = c;
	}
}
