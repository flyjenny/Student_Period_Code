// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package Absyn;

public class AggregateExp extends Absyn {

  public int type;
  public Column column;

  public AggregateExp (int pos, int type, Column column) {
	this.pos = pos;
    this.type = type;
    this.column = column;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("AggregateExp(\n");
    buffer.append("  "+tab+type);
    buffer.append("\n");
      if (column != null)
        buffer.append(column.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [AggregateExp]");
    return buffer.toString();
  }
}
