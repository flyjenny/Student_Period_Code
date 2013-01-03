package Tree;

public class CONST extends Expv {
	public int value;
    public static CONST ZERO = new CONST(0);
	public static CONST ONE = new CONST(1);
	public static CONST WORDSIZE = new CONST(Translate.Level.instance.wordsize());
	public CONST(int v) {
		value = v;
	}

	@Override
	public ExpList kids() {
		return null;
	}

	@Override
	public Expv build(ExpList kids) {
		return this;
	}
}
