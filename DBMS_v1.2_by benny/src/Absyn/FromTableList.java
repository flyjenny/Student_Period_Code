// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package Absyn;

public class FromTableList extends List {

  public FromTable head;
  public FromTableList tail;

  public FromTableList (int pos, FromTable head, FromTableList tail) {
    this.head = head;
    this.tail = tail;
    this.pos = pos;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("FromTableList(\n");
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
    buffer.append(") [FromTableList]");
    return buffer.toString();
  }
}
