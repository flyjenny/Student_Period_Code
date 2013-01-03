package query.plan;

import java.util.*;
import query.scan.*;
import metadata.*;

/**
 * The Plan class corresponding to the <i>project</i> relational algebra
 * operator.
 * 
 * @author NIL
 */

public class ProjectPlan implements Plan {

	private Plan p;
	private Schema schema = new Schema();

	/**
	 * Creates a new project node in the query tree, having the specified
	 * subquery and field list.
	 * 
	 * @param p
	 *            the subquery
	 * @param fieldlist
	 *            the list of fields
	 */
	public ProjectPlan(Plan p, Collection<String> fieldlist) {
		this.p = p;
		for (String fldname : fieldlist)
			if (fldname.startsWith(".")) {
				String fd = null;
				for (String f : p.getSchema().getFields()) {
					if (f.indexOf(fldname) > 0) {
						fd = f;
						break;
					}
				}
				if (null != fd) {
					schema.add(fd, p.getSchema());
				}
			} else {
				schema.add(fldname, p.getSchema());
			}
	}

	public Scan open(Scan sca) {

		Scan s = p.open(sca);
		s = new ProjectScan(s, schema.getFields());
		/*
		 * if(null!=sca) s.setParentScan(sca);
		 */
		return s;
	}

	public int blocksAccessed() {
		// TODO opt-est
		return 0;
	}

	public int recordsOutput() {
		// TODO opt-est
		return 0;
	}

	public int distinctValues(String fldName) {
		// TODO opt-est
		return 0;
	}

	public Schema getSchema() {
		return schema;
	}
}
