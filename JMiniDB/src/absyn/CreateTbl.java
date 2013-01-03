package absyn;

public class CreateTbl extends Update {
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
	 * @return the definition
	 */
	public DefList getDefinition() {
		return definition;
	}

	/**
	 * @param definition
	 *            the definition to set
	 */
	public void setDefinition(DefList definition) {
		this.definition = definition;
	}

	String tblName;
	DefList definition;

	public CreateTbl(int p, String tn, DefList dl) {
		pos = p;
		tblName = tn;
		definition = dl;
	}
}
