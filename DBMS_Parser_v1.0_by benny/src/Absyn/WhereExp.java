// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package Absyn;

public class WhereExp extends Absyn {

  public WhereCondExp cond;
  public GroupExp groupExp;
  public OrderExp orderExp;

  public WhereExp (int pos, WhereCondExp cond, GroupExp groupExp, OrderExp orderExp) {
    this.cond = cond;
    this.groupExp = groupExp;
    this.orderExp = orderExp;
    this.pos = pos;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("WhereExp(\n");
      if (cond != null)
        buffer.append(cond.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
      if (groupExp != null)
        buffer.append(groupExp.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
      if (orderExp != null)
        buffer.append(orderExp.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [WhereExp]");
    return buffer.toString();
  }
}