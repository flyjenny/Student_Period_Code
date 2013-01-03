// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package Absyn;

public class ColumnDef extends Item {

  public String name;
  public DataType dataType;
  public ColumnCons cons;

  public ColumnDef (int pos, String name, DataType dataType, ColumnCons cons) {
    this.pos = pos;
    this.name = name;
    this.dataType = dataType;
    this.cons = cons;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("ColumnDef(\n");
    buffer.append("  "+tab+name);
    buffer.append("\n");
      if (dataType != null)
        buffer.append(dataType.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
      if (cons != null)
        buffer.append(cons.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [ColumnDef]");
    return buffer.toString();
  }
}
