package absyn;

import java.util.LinkedList;
import java.util.List;

import metadata.Schema;

public class DefList extends Absyn {
	CreateDef head;
	DefList tail;
	Schema schema;

	public DefList(CreateDef h) {
		head = h;
		tail = null;
	}

	public DefList(CreateDef h, DefList t) {
		head = h;
		tail = t;
	}

	public Schema toSchema() {
		schema = new Schema();
		DefList dl = this;
		while (dl != null) {
			if (dl.head instanceof ColDef) {
				((ColDef) dl.head).addToSchema(schema);
			} else {
				// TODO register primary key and foreign key
			}
			dl = dl.tail;
		}
		return schema;
	}

	public CreateDef[] toArray() {
		List<CreateDef> list = new LinkedList<CreateDef>();
		DefList p = this;
		while (null != p) {
			list.add(p.head);
			p = p.tail;
		}
		// Collections.reverse(list);
		return list.toArray(new CreateDef[0]);
	}
}
