package absyn;

public class DropDB extends Update {
	String dbName;

	public DropDB(int p, String n) {
		pos = p;
		dbName = n;
	}
}
