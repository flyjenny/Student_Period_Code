package Tree;

public class TEMP extends Expv {
	public Temp.Temp temp;

	public TEMP(Temp.Temp t) {
		temp = t;
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
