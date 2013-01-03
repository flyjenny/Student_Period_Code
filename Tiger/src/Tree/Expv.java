package Tree;

abstract public class Expv {
	abstract public ExpList kids();

	abstract public Expv build(ExpList kids);
}
