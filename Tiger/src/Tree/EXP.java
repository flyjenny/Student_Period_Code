package Tree;

public class EXP extends Stm {
	public Expv exp;

	public EXP(Expv e) {
		exp = e;
	}

	@Override
	public ExpList kids() {
		return new ExpList(exp, null);
	}

	@Override
	public Stm build(ExpList kids) {
		return new EXP(kids.head);
	}
}
