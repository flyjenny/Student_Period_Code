package Tree;

import Temp.Label;
import Temp.LabelList;

public class JUMP extends Stm {
	public Expv exp;
	public LabelList targets;

	public JUMP(Expv e, LabelList t) {
		exp = e;
		targets = t;
	}

	public JUMP(Label target) {
		this(new NAME(target), new LabelList(target, null));
	}

	@Override
	public ExpList kids() {
		return new ExpList(exp, null);
	}

	@Override
	public Stm build(ExpList kids) {
		return new JUMP(kids.head, targets);
	}
}
