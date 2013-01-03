package query.predicate;

public class StringConstant implements Constant {

	/**
	 * 
	 */
	private static final long serialVersionUID = -197940704647680735L;
	private String val;

	public StringConstant(String s) {
		val = s;
	}

	public String toJavaVal() {
		return val;
	}

	@Override
	public boolean equals(Object obj) {
		StringConstant sc = (StringConstant) obj;
		return sc != null && val.equals(sc.val);
	}

	public int compareTo(Constant c) {
		StringConstant sc = (StringConstant) c;
		return val.compareTo(sc.val);
	}

}
