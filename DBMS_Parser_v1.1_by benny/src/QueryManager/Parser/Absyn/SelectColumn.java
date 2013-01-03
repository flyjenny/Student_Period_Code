// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package QueryManager.Parser.Absyn;

public class SelectColumn extends Item {

  public String name;
  public AlgebraExp algebraExp;

  public SelectColumn (int pos, String name, AlgebraExp algebraExp) {
    this.name = name;
    this.algebraExp = algebraExp;
    this.pos = pos;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("SelectColumn(\n");
    buffer.append("  "+tab+name);
    buffer.append("\n");
      if (algebraExp != null)
        buffer.append(algebraExp.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [SelectColumn]");
    return buffer.toString();
  }
}
