// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package Absyn;

public class Column extends Item {

  public String tName;
  public String cName;

  public Column (int pos, String tName, String cName) {
    this.pos = pos;
    this.tName = tName;
    this.cName = cName;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("Column(\n");
    buffer.append("  "+tab+tName);
    buffer.append("\n");
    buffer.append("  "+tab+cName);
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [Column]");
    return buffer.toString();
  }
}