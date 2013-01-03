package absyn;

public class CreateDB extends Update {
	String dbName;

	public CreateDB(int p, String n) {
		pos = p;
		dbName = n;
	}
}
