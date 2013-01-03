package absyn;

public class UpdateClause extends Update {
	String tblName;
	SetList setList;
	Condition cond;

	public UpdateClause(int p, String tn, SetList sl) {
		pos = p;
		tblName = tn;
		setList = sl;
		cond = null;
	}

	public UpdateClause(int p, String tn, SetList sl, Condition c) {
		pos = p;
		tblName = tn;
		setList = sl;
		cond = c;
	}

	public String getTblName() {
		return tblName;
	}

	public SetList getSetList() {
		return setList;
	}

	public Condition getCond() {
		return cond;
	}
}
