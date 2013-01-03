package index;

import query.predicate.Constant;
import record.Record;

public interface Index {

	public void beforeFirst(Constant searchKey);

	public boolean next();

	public Record getDataRecord();

	public void insert(Constant dataVal, Record r);

	public void delete(Constant dataVal, Record r);

	public void close();
}
