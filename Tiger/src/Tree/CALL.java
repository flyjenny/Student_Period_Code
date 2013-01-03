package Tree;

public class CALL extends Expv {
	public String name;
	public Expv func;
	public ExpList args;

	public CALL(Expv f, ExpList a) {
		func = f;
		args = a;
	}

	@Override
	public ExpList kids() {
		return new ExpList(func, args);
	}

	@Override
	public Expv build(ExpList kids) {
		return new CALL(kids.head, kids.tail);
	}
}
