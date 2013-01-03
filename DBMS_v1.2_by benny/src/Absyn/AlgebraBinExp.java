// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package Absyn;

public class AlgebraBinExp extends AlgebraExp {

  public int type;
  public AlgebraExp left;
  public AlgebraExp right;

  public AlgebraBinExp (int pos, int type, AlgebraExp left, AlgebraExp right) {
	this.pos = pos;
    this.type = type;
    this.left = left;
    this.right = right;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("AlgebraBinExp(\n");
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
    buffer.append(") [AlgebraBinExp]");
    return buffer.toString();
  }
}
