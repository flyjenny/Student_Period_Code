package Record;

public class TableInfo {
	public String tableName;
	public AttrDataType[] attrDataTypes;
	public String[] attrNames;
	public String[] attrDefaultValues;
	public int[] attrConstarints;

	public TableInfo(String tableName, AttrDataType[] attrDataType, String[] attrName,
			String[] attrDefaultValue, int[] attrConstarints) {
		this.tableName = tableName;
		this.attrDataTypes = attrDataType;
		this.attrNames = attrName;
		this.attrDefaultValues = attrDefaultValue;
		this.attrConstarints = attrConstarints;
	}

}
