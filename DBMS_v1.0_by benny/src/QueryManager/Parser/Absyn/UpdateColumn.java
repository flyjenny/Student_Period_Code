// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package QueryManager.Parser.Absyn;

public class UpdateColumn extends Item {

  public String name;
  public ValueExp value;

  public UpdateColumn (int pos, String name, ValueExp value) {
    this.name = name;
    this.value = value;
    this.pos = pos;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("UpdateColumn(\n");
    buffer.append("  "+tab+name);
    buffer.append("\n");
      if (value != null)
        buffer.append(value.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [UpdateColumn]");
    return buffer.toString();
  }
}
