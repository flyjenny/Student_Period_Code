package common;

public class Compare {

	public static final int EQ = 1, NEQ = 2, GT = 3, LT = 4, GE = 5, LE = 6;

	public static boolean apply(int cop, int compAns) {
		switch (cop) {
		case EQ:
			return compAns == 0;
		case NEQ:
			return compAns != 0;
		case GT:
			return compAns > 0;
		case LT:
			return compAns < 0;
		case GE:
			return compAns >= 0;
		case LE:
			return compAns <= 0;
		}
		return false;
	}

}
