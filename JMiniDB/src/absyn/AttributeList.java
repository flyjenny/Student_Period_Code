package absyn;

import java.util.LinkedList;
import java.util.List;

public class AttributeList extends Absyn {

	Attribute head;
	AttributeList tail;

	public AttributeList(Attribute a) {
		head = a;
		tail = null;
	}

	public AttributeList(Attribute a, AttributeList al) {
		head = a;
		tail = al;
	}

	public Attribute[] toArray() {
		List<Attribute> list = new LinkedList<Attribute>();
		AttributeList p = this;
		while (null != p) {
			list.add(p.head);
			p = p.tail;
		}

		// Collections.reverse(list);
		return list.toArray(new Attribute[0]);
	}

}
