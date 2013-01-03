package absyn;

public class HvCondition extends Absyn {

	Condition cond;

	public HvCondition(Condition c) {
		cond = c;
	}

	public Condition getCond() {
		return cond;
	}
}
