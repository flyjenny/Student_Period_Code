// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package QueryManager.Parser.Absyn;

public class DeleteExp extends SqlExp {

  public String name;
  public WhereCondExp cond;

  public DeleteExp (int pos, String name, WhereCondExp cond) {
	this.pos = pos;
	this.name = name;
    this.cond = cond;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("DeleteExp(\n");
    buffer.append("  "+tab+name);
    buffer.append("\n");
      if (cond != null)
        buffer.append(cond.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [DeleteExp]");
    return buffer.toString();
  }
}
