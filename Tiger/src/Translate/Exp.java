package Translate;
/**
 * 	This class is the base interface of Translate.Exp
 * 	which can generates IR as you need 
 * 
 * 	comment:
 * 		the use of Translate.Exp is called strategy pattern.
 *		which provide abstract define of strategy and require
 *		each subclass to provide specific implementation.	 
 *		@see the book Design Pattern for detail	
 */
abstract public class Exp {
	/**
	 * get the value of this expression
	 * @return the value IR representation 
	 */
	 public abstract Tree.Expv unEx();
	
		/**
		 * get the action of this expression
		 * @return the statement IR representation
		 */
	 public abstract Tree.Stm unNx();
	 	
		/**
		 * create a statement that act as follows:
		 * 		if this expression is true, 
		 * 			then jump to label t
		 * 			else jump to label f
		 * @param t 	true label
		 * @param f	false label
		 * @return	the statement created
		 */
	 public abstract Tree.Stm unCx(Temp.Label t,Temp.Label f);

}
