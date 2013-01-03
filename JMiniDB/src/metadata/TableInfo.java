package metadata;

import java.util.*;
import static common.Types.*;
import io.Page;

public class TableInfo {

	private Schema schema;
	private Map<String, Integer> offsets;
	private int recordLen;
	private String tblName;
	private String def;

	public TableInfo(String tn, Schema sch) {
		schema = sch;
		tblName = tn;
		offsets = new HashMap<String, Integer>();
		int pos = 0;
		for (String fldName : schema.getFields()) {
			offsets.put(fldName, pos);
			pos += lengthInBytes(fldName);
		}
		recordLen = pos;
	}

	public TableInfo(String tn, String defsql, Schema sch,
			Map<String, Integer> ofs, int rl) {
		tblName = tn;
		def = defsql;
		schema = sch;
		offsets = ofs;
		recordLen = rl;
	}

	public String getFileName() {
		return tblName + ".tbl";
	}

	public Schema getSchema() {
		return schema;
	}

	public int offset(String fldName) {
		return offsets.get(fldName);
	}

	public int getRecordLength() {
		return recordLen;
	}

	public String getDef() {
		return def;
	}

	private int lengthInBytes(String fldName) {
		int fldType = schema.getType(fldName);
		if (fldType == INT)
			return INT_SIZE;
		else if (fldType == FLOAT)
			return FLOAT_SIZE;
		else
			return Page.STR_SIZE(schema.getLength(fldName));
	}

	public String getTableName() {
		// TODO Auto-generated method stub
		return tblName;
	}
}
