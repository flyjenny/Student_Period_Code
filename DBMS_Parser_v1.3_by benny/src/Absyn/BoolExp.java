// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package Absyn;

public class BoolExp extends ValueExp {

  public boolean value;

  public BoolExp (int pos, boolean value) {
    this.pos = pos;
	this.value = value;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("BoolExp(\n");
    buffer.append("  "+tab+value);
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [BoolExp]");
    return buffer.toString();
  }
}
