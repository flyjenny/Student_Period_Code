// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package Absyn;

public class CreateIndexExp extends CreateExp {

  public String iName;
  public String tName;
  public IdList list;

  public CreateIndexExp (int pos, String name, String name2, IdList list) {
    this.pos = pos;
    this.iName = name;
    this.tName = name2;
    this.list = list;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("CreateIndexExp(\n");
    buffer.append("  "+tab+iName);
    buffer.append("\n");
    buffer.append("  "+tab+tName);
    buffer.append("\n");
      if (list != null)
        buffer.append(list.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [CreateIndexExp]");
    return buffer.toString();
  }
}
