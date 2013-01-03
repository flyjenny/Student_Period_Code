package record;

import common.Types;

import metadata.TableInfo;
import io.Page;

/**
 * An object that can format a page to look like a block of empty records.
 * 
 * @author NIL
 */

class RecordFormatter implements buffer.PageFormatter {
	private TableInfo ti;

	/**
	 * Creates a formatter for a new page of a table.
	 * 
	 * @param ti
	 *            the table's metadata
	 */
	public RecordFormatter(TableInfo ti) {
		this.ti = ti;
	}

	/**
	 * Formats the page by allocating as many record slots as possible, given
	 * the record length. Each record slot is assigned a flag of EMPTY. Each
	 * integer field is given a value of 0, and each string field is given a
	 * value of "".
	 * 
	 * @see simpledb.buffer.PageFormatter#format(simpledb.file.Page)
	 */
	public void format(Page page) {
		int recsize = ti.getRecordLength() + Page.INT_SIZE;
		for (int pos = 0; pos + recsize < Page.BLOCK_SIZE; pos += recsize) {
			page.setInt(pos, RecordPage.EMPTY);
			makeDefaultRecord(page, pos);
		}
	}

	private void makeDefaultRecord(Page page, int pos) {
		for (String fldname : ti.getSchema().getFields()) {
			int offset = ti.offset(fldname);
			switch (ti.getSchema().getType(fldname)) {
			case Types.INT:
				page.setInt(pos + Page.INT_SIZE + offset, 0);
				break;
			case Types.STRING:
				page.setString(pos + Page.INT_SIZE + offset, "");
				break;
			case Types.FLOAT:
				page.setFloat(pos + Page.FLOAT_SIZE + offset, 0);
				break;
			}
		}
	}

}
