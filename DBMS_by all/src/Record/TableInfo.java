package Record;

public class TableInfo {
	public String tableName;
	public AttrDataType[] attrDataTypes;
	public String[] attrNames;
	public String[] attrDefaultValues;
	public int[] attrConstraints;

	public TableInfo(String tableName, AttrDataType[] attrDataType,
			String[] attrName, String[] attrDefaultValue, int[] attrConstarints) {
		this.tableName = tableName;
		this.attrDataTypes = attrDataType;
		this.attrNames = attrName;
		this.attrDefaultValues = attrDefaultValue;
		this.attrConstraints = attrConstarints;
	}

	public TableInfo() {

	}

	public int indexOfAttr(String attrName) {
		for (int i = attrNames.length - 1; i >= 0; i--) {
			if (attrName.equals(attrNames[i])) {
				return i;
			}
		}
		return -1;
	}

}
