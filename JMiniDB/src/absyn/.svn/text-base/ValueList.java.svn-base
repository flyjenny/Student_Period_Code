package absyn;

import java.util.LinkedList;
import java.util.List;

public class ValueList extends Absyn {
	Value head;
	ValueList tail;

	public ValueList(Value h) {
		head = h;
		tail = null;
	}

	public ValueList(Value h, ValueList t) {
		head = h;
		tail = t;
	}

	public Value[] toArray() {

		List<Value> list = new LinkedList<Value>();
		ValueList p = this;
		while (null != p) {
			list.add(p.head);
			p = p.tail;
		}
		// Collections.reverse(list);
		return list.toArray(new Value[0]);

	}
}
