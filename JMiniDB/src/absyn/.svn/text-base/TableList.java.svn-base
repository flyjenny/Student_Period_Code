package absyn;

import java.util.LinkedList;
import java.util.List;

public class TableList extends Absyn {
	Table head;
	TableList tail;

	public TableList(Table h) {
		head = h;
		tail = null;
	}

	public TableList(Table h, TableList t) {
		head = h;
		tail = t;
	}

	public Table[] toArray() {

		List<Table> list = new LinkedList<Table>();
		TableList p = this;
		while (null != p) {
			list.add(p.head);
			p = p.tail;
		}
		// Collections.reverse(list);
		return list.toArray(new Table[0]);

	}
}
