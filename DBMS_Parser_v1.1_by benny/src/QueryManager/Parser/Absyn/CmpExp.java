// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package QueryManager.Parser.Absyn;

public class CmpExp extends WhereCondOp {

  public int type;
  public CmpOp left;
  public CmpOp right;

  public CmpExp (int pos, int type, CmpOp left, CmpOp right) {
    this.pos = pos;
    this.type = type;
    this.left = left;
    this.right = right;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("CmpExp(\n");
    buffer.append("  "+tab+isNeg);
    buffer.append("\n");
    buffer.append("  "+tab+type);
    buffer.append("\n");
      if (left != null)
        buffer.append(left.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
      if (right != null)
        buffer.append(right.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [CmpExp]");
    return buffer.toString();
  }
}
