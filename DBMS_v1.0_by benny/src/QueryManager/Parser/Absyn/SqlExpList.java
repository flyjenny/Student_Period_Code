// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package QueryManager.Parser.Absyn;

public class SqlExpList extends List {

  public SqlExp head;
  public SqlExpList tail;

  public SqlExpList (int pos, SqlExp head, SqlExpList tail) {
    this.head = head;
    this.tail = tail;
    this.pos = pos;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("SqlExpList(\n");
      if (head != null)
        buffer.append(head.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
      if (tail != null)
        buffer.append(tail.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [SqlExpList]");
    return buffer.toString();
  }
}
