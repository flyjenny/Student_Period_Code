// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package Absyn;

public class LikeExp extends WhereCondOp {

  public Column column;
  public String format;

  public LikeExp (int pos, Column column, String format) {
    this.column = column;
    this.format = format;
    this.pos = pos;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("LikeExp(\n");
    buffer.append("  "+tab+isNeg);
    buffer.append("\n");
      if (column != null)
        buffer.append(column.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
    buffer.append("  "+tab+format);
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [LikeExp]");
    return buffer.toString();
  }
}
