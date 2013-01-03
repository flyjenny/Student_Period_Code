package absyn;

import java.util.LinkedList;
import java.util.List;

public class SelList extends Absyn {

	SelExp head;
	SelList tail;

	public SelList(SelExp h) {
		head = h;
		tail = null;
	}

	public SelList(SelExp h, SelList t) {
		head = h;
		tail = t;
	}

	public SelExp[] toArray() {

		List<SelExp> list = new LinkedList<SelExp>();
		SelList p = this;
		while (null != p) {
			list.add(p.head);
			p = p.tail;
		}
		// Collections.reverse(list);
		return list.toArray(new SelExp[0]);

	}

	SelList Resverse() {
		SelList ans = null;
		SelList p = this;
		while (p != null) {
			ans = new SelList(p.head, ans);
			p = p.tail;

		}
		return ans;
	}
}
