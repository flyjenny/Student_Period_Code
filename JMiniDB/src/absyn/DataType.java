package absyn;

import io.Page;
import common.Types;

public class DataType extends Absyn {
	public static final int INT = 1, FLOAT = 2, CHAR = 3, VARCHAR = 4,
			BOOLEAN = 5, DATE = 6, TIME = 7;

	int type;
	int len;

	public DataType(int ty) {
		type = ty;
		len = 0;
	}

	public DataType(int ty, int l) {
		type = ty;
		len = l;
	}

	public int getType() {
		switch (type) {
		case INT:
			return Types.INT;
		case FLOAT:
			return Types.FLOAT;
		case CHAR:
		case VARCHAR:
		case BOOLEAN:
		case DATE:
		case TIME:
			return Types.STRING;
		}
		return Types.NONE;
	}

	public int getLength() {
		switch (type) {
		case INT:
			return Types.INT_SIZE;
		case FLOAT:
			return Types.FLOAT_SIZE;
		case CHAR:
		case VARCHAR:
			return Page.STR_SIZE(len);
		case BOOLEAN:
			return Types.BOOL_SIZE;
		case DATE:
			return Types.DATE_SIZE;
		case TIME:
			return Types.TIME_SIZE;
		}
		return Types.NONE;
	}
}
