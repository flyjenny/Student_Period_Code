package ErrorMsg;

public class ErrorMsg {
  private LineList linePos = new LineList(-1,null);
  public int lineNum=1;
  private String filename;
  public boolean anyErrors;

  public ErrorMsg(String f) {
      filename=f;
  }

  public void newline(int pos) {
     lineNum++;
     linePos = new LineList(pos,linePos);
  }
  
  public void error(int pos, String msg) {
	int n = lineNum;
    LineList p = linePos;
	String sayPos="0.0";
	anyErrors=true;

        while (p!=null) {
          if (p.head<pos) {
	     sayPos = ":" + String.valueOf(n) + "." + String.valueOf(pos-p.head);
	     break;
          }
	        p=p.tail; n--;
        }

	   System.out.println("Wrong at "+filename + "\n" + sayPos + "\n " + msg);
  }
 
    public void error(int line,int row,String msg) {
     String sayPos;
 	 sayPos = "�����������" + String.valueOf(line) + "\n���������:" + String.valueOf(row);
	 System.out.println("�����ļ�����"+filename + "\n" + sayPos + "\n" +"������Ϣ:" +msg+"\n");
    }
}

class LineList {
  int head;
  LineList tail;
  LineList(int h, LineList t) {head=h; tail=t;}
}
