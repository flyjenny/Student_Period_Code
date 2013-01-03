// The following code was generated by visual classgen 1.0 on 11-7-8 上午10:21

package QueryManager.Parser.Absyn;

public class CreateViewExp extends CreateExp {

  public String name;
  public SelectExp selectExp;

  public CreateViewExp (int pos, String name, SelectExp selectExp) {
    this.pos = pos;
    this.name = name;
    this.selectExp = selectExp;
  }

  public String print(String tab) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tab);
    buffer.append("CreateViewExp(\n");
    buffer.append("  "+tab+name);
    buffer.append("\n");
      if (selectExp != null)
        buffer.append(selectExp.print("  "+tab));
      else
        buffer.append(tab+"  null");
    buffer.append("\n");
    buffer.append(tab);
    buffer.append(") [CreateViewExp]");
    return buffer.toString();
  }
}
