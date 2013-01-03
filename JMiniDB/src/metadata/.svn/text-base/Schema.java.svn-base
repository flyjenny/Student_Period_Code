package metadata;

import java.util.*;
import static common.Types.*;

/**
 * An object holds the schema of a table.
 * 
 * @author Alex
 */

public class Schema {

	private Map<String, FieldInfo> info = new HashMap<String, FieldInfo>();
	private List<String> fields = new ArrayList<String>();
	private List<String> keys = new ArrayList<String>();

	public Schema() {
	}

	public void addField(String fldName, int type, int length) {
		info.put(fldName, new FieldInfo(type, length));
		fields.add(fldName);
	}

	public void addIntField(String fldName) {
		addField(fldName, INT, 0);
	}

	public void addFloatField(String fldName) {
		addField(fldName, FLOAT, 0);
	}

	public void addStringField(String fldName, int length) {
		addField(fldName, STRING, length);
	}

	public void add(String fldName, Schema sch) {
		int type = sch.getType(fldName);
		int length = sch.getLength(fldName);
		addField(fldName, type, length);
	}

	public void addAll(Schema sch) {
		info.putAll(sch.info);
		fields.addAll(sch.fields);
	}

	public Collection<String> getFields() {
		return fields;
	}

	public Collection<String> getKeys() {
		return keys;
	}

	public boolean hasField(String fldname) {
		return getFields().contains(fldname);
	}

	public int getType(String fldname) {
		return info.get(fldname).type;
	}

	public int getLength(String fldname) {
		return info.get(fldname).length;
	}

	public int totalFields() {
		return fields.size();
	}

}
