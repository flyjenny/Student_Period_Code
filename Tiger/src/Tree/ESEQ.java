package Tree;

public class ESEQ extends Expv {
	public Stm stm;
	public Expv exp;

	public ESEQ(Stm s, Expv e) {
		stm = s;
		exp = e;
	}

	@Override
	public ExpList kids() {
		throw new Error("kids() not applicable to ESEQ");
	}

	@Override
	public Expv build(ExpList kids) {
		throw new Error("build() not applicable to ESEQ");
	}
}
