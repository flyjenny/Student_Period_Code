package Record;

import Absyn.DataType;

public class AttrDataType {
	public int type;
	public int size;
	public int p;
	public int s;

	public AttrDataType(int type, int size, int p, int s) {
		this.type = type;
		this.size = size;
		this.p = p;
		this.s = s;
	}

	public AttrDataType(DataType dataType) {
		this.type = dataType.type;
		this.size = dataType.size;
		this.p = dataType.p;
		this.s = dataType.s;
	}

	public boolean equals(AttrDataType d) {
		if (type == d.type && size == d.size && p == d.p && s == d.s)
			return true;
		else
			return false;
	}

}
