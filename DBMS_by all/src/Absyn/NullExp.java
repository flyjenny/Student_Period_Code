// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package Absyn;

public class NullExp extends ValueExp {

	public String value = "$null";

	public NullExp(int pos) {
		this.pos = pos;
	}

	public String getValue() {
		return value;
	}

	public int getType() {
		return Const.NULLVALUE;
	}

	public String print(String tab) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(tab);
		buffer.append("NullExp(\n");
		buffer.append(tab);
		buffer.append(") [NullExp]");
		return buffer.toString();
	}
}
