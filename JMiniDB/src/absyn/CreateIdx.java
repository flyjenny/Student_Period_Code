package absyn;

public class CreateIdx extends Update {
	String indexName;
	String tblName;
	Attribute column;
	boolean isUnique;

	public CreateIdx(int p, String idx, String tn, Attribute cn, boolean b) {
		pos = p;
		indexName = idx;
		tblName = tn;
		column = cn;
		isUnique = b;
	}

	public String getIndexName() {
		return indexName;
	}

	public String getTblName() {
		return tblName;
	}

	public String getColName() {
		return column.getColName();
	}
}
