package Parse;
import Absyn.Exp;
public class Parse {

  public ErrorMsg.ErrorMsg errorMsg;
  public Absyn.Exp absyn;

  public Parse(String filename) {
      errorMsg = new ErrorMsg.ErrorMsg(filename);
      {java.io.InputStream inp;
       try {inp=new java.io.FileInputStream(filename);
       } catch (java.io.FileNotFoundException e) {
	 throw new Error("File not found: " + filename);
       }
       parser parser = new parser(new Yylex(inp,errorMsg), errorMsg);
      /* open input files, etc. here */

      try {
        absyn = (Exp) parser./* debug_ */parse().value;
		Absyn.Print p=new Absyn.Print(System.out);
		p.prExp(absyn, absyn.pos);
      } catch (Throwable e) {
	e.printStackTrace();
	throw new Error(e.toString());
      } 
      finally {
         try {inp.close();} catch (java.io.IOException e) {}
      }
} }   }

