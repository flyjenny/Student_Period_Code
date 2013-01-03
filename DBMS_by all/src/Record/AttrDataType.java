package Record;

import Absyn.DataType;
import RecordManager.Const;
import RecordManager.Transform;

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
	
	public AttrDataType(AttrDataType dataType) {
		this.type = dataType.type;
		this.size = dataType.size;
		this.p = dataType.p;
		this.s = dataType.s;
	}

	public AttrDataType(DataType dataType) {
		this.type = dataType.type;
		this.size = dataType.size;
		this.p = dataType.p;
		this.s = dataType.s;
	}

	public int getValueType() {
		switch (type) {
		case Const.INT:
			return Const.INTVALUE;
		case Const.DECIMAL: {
			if (s != 0)
				return Const.REALVALUE;
			else
				return Const.INTVALUE;
		}
		case Const.DOUBLE:
			return Const.REALVALUE;
		case Const.FLOAT:
			return Const.REALVALUE;
		case Const.BOOL:
			return Const.BOOLVALUE;
		default:
			return Const.STRINGVALUE;
		}
	}

	public boolean equals(AttrDataType d) {
		if (type == d.type && size == d.size && p == d.p && s == d.s)
			return true;
		else
			return false;
	}
	
		public AttrDataType(String source){
		Transform trans=new Transform();

		try {
			String tmp=source.substring(0, 4);
			byte[] bt = tmp.getBytes("8859_1");
			this.type=trans.ByteToInt(bt);
			tmp=source.substring(4, 8);
			bt = tmp.getBytes("8859_1");
			this.size=trans.ByteToInt(bt);
			tmp=source.substring(8, 12);
			bt = tmp.getBytes("8859_1");
			this.p=trans.ByteToInt(bt);
			tmp=source.substring(12, 16);
			bt = tmp.getBytes("8859_1");
			this.s=trans.ByteToInt(bt);
			
		} catch (Exception e) {
			System.out.println("ToString error");
		}
	}
	
	public String toString(){
		String s = new String();
		Transform trans=new Transform();
		try {
			byte[] tmp = new byte[4];
			trans.IntToByte(type, tmp);
			s = s.concat(new String(tmp, "8859_1"));
			trans.IntToByte(size, tmp);
			s = s.concat(new String(tmp, "8859_1"));
			trans.IntToByte(p, tmp);
			s = s.concat(new String(tmp, "8859_1"));
			trans.IntToByte(this.s, tmp);
			s = s.concat(new String(tmp, "8859_1"));
		} catch (Exception e) {
			System.out.println("ToString error");
		}
		return s;
	}

}
