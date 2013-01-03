package query.predicate;

import query.scan.Scan;

/**
 * A basic term in the boolean expression.
 * 
 * @author Alex
 */

public interface Term {

	public static int NONE = 0, EXISTS = 1, IN = 2, ANY = 3, ALL = 4;

	/**
	 * Evaluate whether the term satisfies the specified scan.
	 * 
	 * @param s
	 *            the scan
	 * @return true if the predicate is false in the scan
	 */
	public boolean eval(Scan s);

}
