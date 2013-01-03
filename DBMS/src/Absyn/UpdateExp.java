// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package Absyn;

public class UpdateExp extends SqlExp {

  public String name;
  public UpdateColumnList list;
  public WhereCondExp cond;

  public UpdateExp (int pos, String name, UpdateColumnList list, WhereCondExp cond) {
    this.name = name;
    this.list = list;
    this.cond = cond;
    this.pos = pos;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("UpdateExp(\n");
    buffer.append("  "+tab+name);
    buffer.append("\n");
      if (list != null)
        buffer.append(list.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
      if (cond != null)
        buffer.append(cond.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [UpdateExp]");
    return buffer.toString();
  }
}
