// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package QueryManager.Parser.Absyn;

public class HavingCondBinExp extends HavingCondExp {

  public boolean isAnd;
  public HavingCondExp left;
  public HavingCondExp right;

  public HavingCondBinExp (int pos, boolean isAnd, HavingCondExp left, HavingCondExp right) {
    this.isAnd = isAnd;
    this.left = left;
    this.right = right;
    this.pos = pos;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("HavingCondBinExp(\n");
    buffer.append("  "+tab+isAnd);
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
    buffer.append(") [HavingCondBinExp]");
    return buffer.toString();
  }
}
