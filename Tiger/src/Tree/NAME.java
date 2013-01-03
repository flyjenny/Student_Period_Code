package Tree;

import Temp.Label;

public class NAME extends Expv {
	public Label label;

	public NAME(Label l) {
		label = l;
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
