package Tree;

public class MEM extends Expv {
	public Expv exp;

	public MEM(Expv e) {
		exp = e;
	}

	@Override
	public ExpList kids() {
		return new ExpList(exp, null);
	}

	@Override
	public Expv build(ExpList kids) {
		return new MEM(kids.head);
	}
}
