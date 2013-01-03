package Tiger;

import java.io.PrintStream;

import ErrorMsg.*;

public class Overall {

	public static ErrorMsg myerror;
	public static Integer go;
	public static PrintStream output;
	public static boolean assigning;
    public static String fileName;
    public static boolean debug = false;
    public static boolean inline = true;
    public static int reachTime = 4;
    
	public static boolean havego() {
		return go.equals(54749110);
	}

	public static void beginassign() {
		assigning = true;
	}

	public static boolean isassigning() {
		return assigning == true;
	}

	public static void endassign() {
		assigning = false;
	}

	public static void begin() {
		try {
			output = new PrintStream(parser.record.substring(0, parser.record
					.length() - 4)
					+ ".abs");
			go = new Integer(54749110);
			myerror = new ErrorMsg("Tiger");
			assigning = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
